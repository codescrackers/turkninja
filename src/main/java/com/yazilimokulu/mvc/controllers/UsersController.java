package com.yazilimokulu.mvc.controllers;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yazilimokulu.mvc.dto.ResponsePageDTO;
import com.yazilimokulu.mvc.dto.UserDTO;
import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.Skill;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.services.AuthException;
import com.yazilimokulu.mvc.services.AvatarService;
import com.yazilimokulu.mvc.services.PostService;
import com.yazilimokulu.mvc.services.UnsupportedFormatException;
import com.yazilimokulu.mvc.services.UploadedAvatarInfo;
import com.yazilimokulu.mvc.services.UserService;
import com.yazilimokulu.utils.JsonUtils;

@Controller
public class UsersController {

	private static final Logger logger = LogManager.getLogger(UsersController.class.getName());
	
    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private Validator userValidator;
    
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("user", new User());

        if (userService.isAuthenticated()) {
            return "redirect:posts";
        }

        String ref = request.getHeader("referer");

        if (ref != null && !ref.contains("/register"))
            session.setAttribute("regRef", ref);

        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Validated({User.CreateValidationGroup.class}) @ModelAttribute(value = "user") User user, BindingResult result, HttpSession session) {
        user.setUsername(StringUtils.trimWhitespace(user.getUsername()));
        user.setEmail(StringUtils.trimWhitespace(user.getEmail()));

        userValidator.validate(user, result);

        if (result.hasErrors())
        {
            return "registration";
        }

        userService.register(user);

        userService.authenticate(user);

        Object regRef = session.getAttribute("regRef");

        return "redirect:" + (StringUtils.isEmpty(regRef) ? "posts" : regRef.toString());
    }

    @RequestMapping(value = "/check_email", method = RequestMethod.GET)
    public @ResponseBody String checkEmail(@RequestParam("email") String email) {
        return userService.emailExists(email) ? "false" : "true";
    }

    @RequestMapping(value = "/check_username", method = RequestMethod.GET)
    public @ResponseBody String checkUsername(@RequestParam("username") String username) {
        return userService.usernameExists(username) ? "false" : "true";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showEditSettingsPage(ModelMap model) {
        if (!model.containsAttribute("user")) {
            User user = userService.currentUser();

            if (user == null) {
                return "redirect:posts";
            }

            model.addAttribute("user", user);
        }

        return "settings";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/change_email", method = RequestMethod.POST)
    public String changeEmail(@Validated({User.ChangeEmailValidationGroup.class}) @ModelAttribute(value = "user") User user, BindingResult result,
                              @RequestParam("currentPassword") String currentPassword, RedirectAttributes redirectAttributes, ModelMap model) {
        model.addAttribute("isEmailForm", true);

        user.setEmail(StringUtils.trimWhitespace(user.getEmail()));

        userValidator.validate(user, result);

        if (!result.hasErrors()) {
            try {
                userService.changeEmail(user.getEmail(), currentPassword);
            } catch (AuthException e) {
                result.rejectValue("password", "NotMatchCurrent");
            }
        }

        if (result.hasErrors()) {
            return "settings";
        }

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("isEmailForm", true);

        return "redirect:/settings";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public String changePassword(@Validated({User.ChangePasswordValidationGroup.class}) @ModelAttribute(value = "user") User user, BindingResult result,
                              @RequestParam("currentPassword") String currentPassword, RedirectAttributes redirectAttributes, ModelMap model) {
        model.addAttribute("isPasswordForm", true);

        if (!result.hasErrors()) {
            try {
                userService.changePassword(user.getPassword(), currentPassword);
            } catch (AuthException e) {
                result.rejectValue("password", "NotMatchCurrent");
            }
        }

        user.setEmail(userService.currentUser().getEmail()); // quick workaround to show e-mail in the e-mail form

        if (result.hasErrors()) {
            return "settings";
        }

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("isPasswordForm", true);

        return "redirect:/settings";
    }

    @RequestMapping(value = "/edit_profile", method = RequestMethod.GET)
    public String showEditProfilePage(ModelMap model) {
        if (!model.containsAttribute("user")) {
            User user = userService.currentUser();
            if (user == null) {
                return "redirect:posts";
            }
            user.setSkillsStr(user.getSkills().stream().map(Skill::getName).collect(Collectors.joining(", ")));
            model.addAttribute("user", user);
        }

        return "editprofile";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/edit_profile", method = RequestMethod.POST)
    public String editProfile(@Validated({User.ProfileInfoValidationGroup.class}) @ModelAttribute(value = "user") User user, BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // quick workaround to show avatar
            User currentUser = userService.currentUser();
            user.setBigAvatarLink(currentUser.getBigAvatarLink());

            return "editprofile";
        }

        userService.changeProfileInfo(user);

        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/edit_profile";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/upload_avatar", method = RequestMethod.POST)
    public @ResponseBody String uploadAvatar(@RequestParam("avatarFile") MultipartFile file) throws IOException {
        try {
            UploadedAvatarInfo result = avatarService.upload(file);

            userService.changeAvatar(result);

            return makeAvatarUploadResponse("ok", result);
        } catch (UnsupportedFormatException e) {
            return makeAvatarUploadResponse("invalid_format", null);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/remove_avatar", method = RequestMethod.POST)
    public @ResponseBody String removeAvatar() throws IOException {
        userService.removeAvatar();

        return "ok";
    }
    
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public ResponsePageDTO<UserDTO> getAllUsers(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,ModelMap model){
    	return userService.findUsersPage(pageNumber, 9);
    	
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String allUsers(ModelMap model){
    	List<User> users=userService.findAll();
    	model.addAttribute("users", users);
    	return "users";
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public String showProfile(@PathVariable("username") String username, @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,ModelMap model) {
        User user = userService.findByUsername(username);
        Page<Post> postsPage = postService.getPostsPageByUsername(username,pageNumber, 3);
		model.addAttribute("postsPage", postsPage);
		model.addAttribute("postUser", user);
		// should implement custom Spring Security UserDetails instead of this,
		// so it will be stored in session
		User currentUser = userService.currentUser();
		if (currentUser != null)
			model.addAttribute("userId", currentUser.getId());

        if (user == null)
            throw new ResourceNotFoundException();

        model.addAttribute("user", user);

        return "profile";
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/changeFollowing/{currentUserId}/{followingUserId}/{command}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String>changeFollowing(@PathVariable("currentUserId") String currentUserId, @PathVariable("followingUserId") String followingUserId, @PathVariable("command") String command) {
        if(userService.currentUser().getId()==Long.valueOf(currentUserId)){
        	User currentUser=userService.findById(Long.valueOf(currentUserId));
    		User user=userService.findById(Long.valueOf(followingUserId));
        	if(command.equals("follow")){
        		try {
					userService.addFollowingUser(currentUser, user);
					userService.addFollower(user, currentUser);
				} catch (Exception e) {
					logger.error("following mistakes");
					return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
				}
        		
        	}else if(command.equals("unfollow")){
        		try {
					userService.removeFollowingUser(currentUser, user);
					userService.removeFollower(user, currentUser);
				} catch (Exception e) {
					logger.error("following mistakes");
					return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
				}
        		
        	}else{
        		logger.error("Wrong following command");
        		return new ResponseEntity<String>("{\"result\":\"error\"}",HttpStatus.OK);
        	}
        	
        }
        return new ResponseEntity<String>("{\"result\":\"success\"}", HttpStatus.OK);
    }
    
    

    private String makeAvatarUploadResponse(String status, UploadedAvatarInfo uploadedAvatarInfo) {
        return "{" + JsonUtils.toJsonField("status", status) +
                (uploadedAvatarInfo == null ? "" : (", " + JsonUtils.toJsonField("link", uploadedAvatarInfo.bigImageLink))) +
                "}";
    }
}
