package com.yazilimokulu.mvc.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazilimokulu.mvc.entities.Comment;
import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.PostEditDto;
import com.yazilimokulu.mvc.entities.Role;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.services.AlreadyVotedException;
import com.yazilimokulu.mvc.services.LikeNotificationService;
import com.yazilimokulu.mvc.services.PhotoService;
import com.yazilimokulu.mvc.services.PostService;
import com.yazilimokulu.mvc.services.UnsupportedFormatException;
import com.yazilimokulu.mvc.services.UploadedPhotoInfo;
import com.yazilimokulu.mvc.services.UserService;
import com.yazilimokulu.utils.JsonUtils;

@Controller
public class PostsController {

	private static final Logger logger = LogManager.getLogger(PostsController.class.getName());
	
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private LikeNotificationService likeNotificationService;

	@RequestMapping(value = { "/posts" }, method = RequestMethod.GET)
	public String showPostsList(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, ModelMap model) {
		Page<Post> postsPage = postService.getPostsPage(pageNumber, 3);

		model.addAttribute("postsPage", postsPage);

		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null){
			model.addAttribute("userId", currentUser.getId());
			model.addAttribute("likeNotificaitons", likeNotificationService.getAllUnCheckedNotifiacitons());
		}
		return "posts";
	}

	@RequestMapping(value = {
			"/posts" }, method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPostsList(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
		List<Post> posts = postService.getPostsList(pageNumber, 10);
		return "[" + posts.stream().map(this::toJsonLink).collect(Collectors.joining(", \n")) + "]";
	}

	@RequestMapping(value = {
			"/posts/top" }, method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTopPostsList() {
		List<Post> posts = postService.getTopPostsList();

		return "[" + posts.stream().map(this::toJsonLink).collect(Collectors.joining(", \n")) + "]";
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET, params = { "tagged" })
	public String searchByTag(@RequestParam("tagged") String tagsStr,
			@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, ModelMap model,
			HttpServletRequest request) {
		List<String> tagNames = Arrays.stream(tagsStr.split(",")).map(String::trim).distinct()
				.collect(Collectors.toList());

		if (tagNames.isEmpty()) {
			return "redirect:/posts";
		}

		Page<Post> postsPage = postService.findPostsByTag(tagNames, pageNumber, 10);

		model.addAttribute("postsPage", postsPage);

		model.addAttribute("searchTags", tagNames);

		String query = "tagged=" + request.getParameter("tagged");
		model.addAttribute("searchQuery", query);

		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null)
			model.addAttribute("userId", currentUser.getId());

		return "posts";
	}

	@RequestMapping(value = "/posts/{postId}/{postShortUrl}", method = RequestMethod.GET)
	public String showPost(@PathVariable("postId") Long postId, ModelMap model) {
		Post post = postService.getPost(postId);

		if (post == null)
			throw new ResourceNotFoundException();

		if (post.isHidden() && !userService.isAdmin())
			throw new ResourceNotFoundException();

		model.addAttribute("post", post);

		if (userService.isAuthenticated()) {
			model.addAttribute("comment", new Comment());
		}

		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null)
			model.addAttribute("userId", currentUser.getId());

		return "post";
	}

	@RequestMapping(value = "/userposts/{username}", method = RequestMethod.GET)
	public String showUserPosts(@PathVariable("username") String username,
			@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, ModelMap model) {
		
		Page<Post> postsPage = postService.getPostsPageByUsername(username,pageNumber, 3);
		User postUser=userService.findByUsername(username);
		model.addAttribute("postsPage", postsPage);
		model.addAttribute("postUser", postUser);
		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null)
			model.addAttribute("userId", currentUser.getId());

		return "userposts";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@RequestMapping(value = "/posts/create", method = RequestMethod.GET)
	public String showCreatePostForm(ModelMap model) {
		model.addAttribute("post", new PostEditDto());

		model.addAttribute("edit", false);

		return "editpost";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@RequestMapping(value = "/posts/create", method = RequestMethod.POST)
	public String createPost(ModelMap model, @Valid @ModelAttribute("post") PostEditDto post, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("edit", false);

			return "editpost";
		}
		postService.saveNewPost(post);

		return "redirect:/posts";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@RequestMapping(value = "/posts/{postId}/edit", method = RequestMethod.GET)
	public String showEditPostForm(@PathVariable("postId") Long postId, ModelMap model) {
		PostEditDto post = postService.getEditablePost(postId);
		List<Role> roles = userService.currentUser().getRoles();
		boolean userRole = false;
		boolean adminRole = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_USER")) {
				userRole = true;
			}
			if (role.getName().equals("ROLE_ADMIN")) {
				adminRole = true;
			}
		}
		if (userRole && !userService.currentUser().getUsername().equals(post.getUser().getUsername()) && !adminRole) {
			throw new AccessDeniedException("Erisim Yetkiniz Yok");
		}
		if (post == null)
			throw new ResourceNotFoundException();

		model.addAttribute("post", post);

		model.addAttribute("edit", true);

		return "editpost";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@RequestMapping(value = "/posts/{postId}/edit", method = RequestMethod.POST)
	public String updatePost(ModelMap model, @Valid @ModelAttribute("post") PostEditDto post, BindingResult result,
			@PathVariable("postId") Long postId) {
		Post currentPost=postService.getPost(postId);
		post.setId(postId);
		post.setUser(currentPost.getUser());
		post.setShortUrl(currentPost.getShortUrl());
		post.setMainpage(currentPost.isMainpage());
		post.setMainpagePhotoUrl(currentPost.getMainpagePhotoUrl());
		post.setMainphotoVisibleupdateTime(currentPost.getMainphotoVisibleupdateTime());
		List<Role> roles = userService.currentUser().getRoles();
		
		boolean userRole = false;
		boolean adminRole = false;
		for (Role role : roles) {
			if (role.getName().equals("ROLE_USER")) {
				userRole = true;
			}
			if (role.getName().equals("ROLE_ADMIN")) {
				adminRole = true;
			}
		}
		if (userRole && !userService.currentUser().getUsername().equals(post.getUser().getUsername()) && !adminRole) {
			throw new AccessDeniedException("Erisim Yetkiniz Yok");
		}
		if (result.hasErrors()) {
			model.addAttribute("edit", true);

			return "editpost";
		}

		postService.updatePost(post);

		return "redirect:/posts/" + postId+"/"+post.getShortUrl();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/posts/{postId}/hide", method = RequestMethod.POST)
	public @ResponseBody String hidePost(@PathVariable("postId") Long postId) {
		postService.setPostVisibility(postId, true);

		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/posts/{postId}/unhide", method = RequestMethod.POST)
	public @ResponseBody String unhidePost(@PathVariable("postId") Long postId) {
		postService.setPostVisibility(postId, false);

		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/posts/{postId}/delete", method = RequestMethod.POST)
	public @ResponseBody String deletePost(@PathVariable("postId") Long postId) {
		postService.deletePost(postId);

		return "ok";
	}

	private String toJsonLink(Post post) {
		String avatar=(post.getUser().getBigAvatarLink() == null) ? "noavatar.jpg" : post.getUser().getBigAvatarLink();
		return "{" + JsonUtils.toJsonField("id", post.getId().toString()) + ", "
				+ JsonUtils.toJsonField("title", post.getTitle()) + ", "
				+ JsonUtils.toJsonField("postDate", post.getDateStr()) +", "
				+ JsonUtils.toJsonField("username",post.getUser().getUsername())+ ", "
				+ JsonUtils.toJsonField("bigAvatarLink", avatar) + ", "
				+ JsonUtils.toJsonField("postShortUrl", post.getShortUrl())
				+ "}";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/posts/{postId}/like", method = RequestMethod.POST)
	public @ResponseBody String like(@PathVariable("postId") Long postId) {
		try {
			postService.vote(postId, true);
			likeNotificationService.save(postService.getPost(postId));
		} catch (AlreadyVotedException e) {
			return "already_voted";
		}

		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/posts/{postId}/dislike", method = RequestMethod.POST)
	public @ResponseBody String dislike(@PathVariable("postId") Long postId) {
		try {
			postService.vote(postId, false);
		} catch (AlreadyVotedException e) {
			return "already_voted";
		}

		return "ok";
	}
	
	 @PreAuthorize("hasRole('ROLE_USER')")
	    @RequestMapping(value = "/upload_post_photo", method = RequestMethod.POST)
	    public @ResponseBody String uploadAvatar(@RequestParam("avatarFile") MultipartFile file) throws IOException {
	        try {
	            UploadedPhotoInfo result = photoService.upload(file,"regular");

	            return makePhotoUploadResponse("ok", result);
	        } catch (UnsupportedFormatException e) {
	            return makePhotoUploadResponse("invalid_format", null);
	        }
	    }
	 
	 private String makePhotoUploadResponse(String status, UploadedPhotoInfo uploadedPhotoInfo) {
	        return "{" + JsonUtils.toJsonField("status", status) +
	                (uploadedPhotoInfo == null ? "" : (", " + JsonUtils.toJsonField("link", uploadedPhotoInfo.imageLink))) +
	                "}";
	    }
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @RequestMapping(value = "/upload_post_main_photo/{postId}", method = RequestMethod.POST)
	    public @ResponseBody String uploadMainpagePhoto(@RequestParam("mainPhotoFile") MultipartFile file,@PathVariable("postId") Long postId) throws IOException {
	        try {
	            UploadedPhotoInfo result = photoService.upload(file,"mainphoto");
	            PostEditDto post= postService.getEditablePost(postId);
	            post.setMainpagePhotoUrl(result.imageLink);
	            postService.updatePost(post);
	            return makePhotoUploadResponse("ok", result);
	        } catch (UnsupportedFormatException e) {
	            return makePhotoUploadResponse("invalid_format", null);
	        }
	    }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @RequestMapping(value = "/changeMainpageVisible/{postId}/{command}", method = RequestMethod.GET)
	    @ResponseBody
	    public ResponseEntity<String>changeMainPagePhotoVisible(@PathVariable("postId") Long postId, @PathVariable("command") String command) {
			PostEditDto post= postService.getEditablePost(postId);
		 	if(command.equals("makeMainpageVisible")){
	        		try {
	        			post.setMainpage(true);
	        			post.setMainphotoVisibleupdateTime(LocalDateTime.now());
	        			postService.updatePost(post);
					} catch (Exception e) {
						logger.error("making photo visible mistakes"+e);
						return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
					}
	        		
	        	}else if(command.equals("makeMainpageUnvisible")){
	        		try {
	        			post.setMainpage(false);
	        			postService.updatePost(post);
					} catch (Exception e) {
						logger.error("making photo unvisible mistakes"+e);
						return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
					}
	        		
	        	}else{
	        		logger.error("Wrong making photo visible command");
	        		return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
	        	}
	        	
	        
	        return new ResponseEntity<String>("{\"result\":\"success\"}", HttpStatus.OK);
	    }
	    
	
	
}