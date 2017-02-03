package com.yazilimokulu.mvc.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazilimokulu.mvc.entities.Comment;
import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.PostEditDto;
import com.yazilimokulu.mvc.entities.Role;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.services.AlreadyVotedException;
import com.yazilimokulu.mvc.services.PostService;
import com.yazilimokulu.mvc.services.UserService;
import com.yazilimokulu.utils.JsonUtils;

@Controller
public class PostsController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@RequestMapping(value = { "/", "/posts" }, method = RequestMethod.GET)
	public String showPostsList(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber, ModelMap model) {
		Page<Post> postsPage = postService.getPostsPage(pageNumber, 3);

		model.addAttribute("postsPage", postsPage);

		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null)
			model.addAttribute("userId", currentUser.getId());

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

	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
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
		model.addAttribute("postsPage", postsPage);
		model.addAttribute("searchUsername", username);
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
		post.setId(postId);
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
		if (!(userRole && userService.currentUser().getUsername().equals(post.getUser().getUsername()) && !adminRole)) {
			throw new AccessDeniedException("Erisim Yetkiniz Yok");
		}
		if (result.hasErrors()) {
			model.addAttribute("edit", true);

			return "editpost";
		}

		postService.updatePost(post);

		return "redirect:/posts/" + postId;
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
		return "{" + JsonUtils.toJsonField("id", post.getId().toString()) + ", "
				+ JsonUtils.toJsonField("title", post.getTitle()) + "}";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/posts/{postId}/like", method = RequestMethod.POST)
	public @ResponseBody String like(@PathVariable("postId") Long postId) {
		try {
			postService.vote(postId, true);
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
}