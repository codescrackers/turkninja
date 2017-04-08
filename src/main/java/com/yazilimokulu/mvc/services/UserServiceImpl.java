package com.yazilimokulu.mvc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.RoleRepository;
import com.yazilimokulu.mvc.daos.SkillRepository;
import com.yazilimokulu.mvc.daos.UserRepository;
import com.yazilimokulu.mvc.dto.ResponsePageDTO;
import com.yazilimokulu.mvc.dto.UserDTO;
import com.yazilimokulu.mvc.entities.Role;
import com.yazilimokulu.mvc.entities.Skill;
import com.yazilimokulu.mvc.entities.Tag;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.mappers.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private GravatarService gravatarService;
    
    @Autowired
    private SkillRepository skillRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(s, s);

        if (user == null)
            throw new UsernameNotFoundException("no such user");

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.getRoles().add(roleRepository.findByName("ROLE_USER"));

        user.setEnabled(true);
        
        user.setGravatarUrl(gravatarService.getAvatarUrl(user.getEmail()));

        user.setRegistrationDate(LocalDateTime.now());

        userRepository.saveAndFlush(user);
    }

    @Override
    public void changeEmail(String newEmail, String currentPassword) throws AuthException {
        User user = currentUser();
        if (!passwordEncoder.matches(currentPassword, user.getPassword()))
            throw new AuthException("password does not match");

        user.setEmail(newEmail);
        user.setGravatarUrl(gravatarService.getAvatarUrl(newEmail));

        userRepository.saveAndFlush(user);
    }

    @Override
    public void changePassword(String newPassword, String currentPassword) throws AuthException {
        User user = currentUser();

        if (!passwordEncoder.matches(currentPassword, user.getPassword()))
            throw new AuthException("password does not match");

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.saveAndFlush(user);
    }

    @Override
    public void changeProfileInfo(User newProfileInfo) {
        User user = currentUser();

        user.setAboutText(newProfileInfo.getAboutText());

        user.setWebsiteLink(newProfileInfo.getWebsiteLink());
        
        user.setFullName(newProfileInfo.getFullName());
        
        user.setTitle(newProfileInfo.getTitle());
        
        user.setScholl(newProfileInfo.getScholl());
        
        user.setLocation(newProfileInfo.getLocation());
        
        user.getSkills().clear();

		String[] skills = Arrays.stream(newProfileInfo.getSkillsStr().split(",")).map(String::trim).toArray(String[]::new);

		for (String skillname : skills) {
			Skill skill = skillRepository.findByNameIgnoreCase(skillname);

			if (skill == null) {
				skill = new Skill(skillname);
			}

			user.getSkills().add(skill);
		}

        userRepository.saveAndFlush(user);
    }

    @Override
    public void changeAvatar(UploadedAvatarInfo uploadedAvatarInfo) {
        User user = currentUser();

        user.setBigAvatarLink(uploadedAvatarInfo.bigImageLink);

        user.setSmallAvatarLink(uploadedAvatarInfo.smallImageLink);

        userRepository.saveAndFlush(user);
    }

    @Override
    public void removeAvatar() {
        User user = currentUser();

        user.setBigAvatarLink(null);

        user.setSmallAvatarLink(null);

        userRepository.saveAndFlush(user);
    }

    @Override
    public void authenticate(User user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication auth = securityContext.getAuthentication();

        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }

    @Override
    public boolean isAdmin() {
        User user = currentUser();

        return user != null && user.hasRole("ROLE_ADMIN");
    }

    @Override
    public User currentUser() {
        if (!isAuthenticated())
            return null;

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication auth = securityContext.getAuthentication();

        return userRepository.findByUsernameIgnoreCase(auth.getName());
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public ResponsePageDTO<UserDTO> findUsersPage(int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, "username");
		Page<User> users=userRepository.findAll(pageRequest);
		List<UserDTO> userDtos=new ArrayList<>();
		for(User user : users){
			UserMapper mapper= new UserMapper();
			UserDTO userToUserDTO = mapper.userToUserDTO(user);
			userDtos.add(userToUserDTO);
		}
		
		ResponsePageDTO<UserDTO> response= new ResponsePageDTO<>();
		response.setData(userDtos);
		response.setPageNumber(users.getNumber());
		response.setFirst(users.isFirst());
		response.setLast(users.isLast());
		response.setTotalPageNumber(users.getTotalPages());
		return response;
	}

	@Override
	public Page<User> getLatestUsersList(int page,int count) {
		PageRequest pageRequest = new PageRequest(0, 8);

		return userRepository.findAllByOrderByRegistrationDateDesc(pageRequest);
	}

	@Override
	public void addFollowingUser(User currentUser, User user) throws Exception {
		if(!currentUser.getFollowing().contains(user)){
			currentUser.getFollowing().add(user);
			userRepository.save(currentUser);
		}else{
			logger.info(currentUser.getUsername()+" already follow "+user.getUsername());
			throw new Exception();
		}
		
	}

	@Override
	public void addFollower(User user, User currentUser) throws Exception {
		if(!user.getFollowers().contains(currentUser)){
		user.getFollowers().add(currentUser);
		userRepository.save(user);
		}else{
			logger.info(user.getUsername()+" already following by "+currentUser.getUsername());
			throw new Exception();
		}
	}
	

	@Override
	public void removeFollowingUser(User currentUser, User user) throws Exception {
		if(currentUser.getFollowing().contains(user)){
			currentUser.getFollowing().remove(user);
			userRepository.save(currentUser);
		}else{
			logger.info(currentUser.getUsername()+" does not follow "+user.getUsername());
			throw new Exception();
		}
		
	}

	@Override
	public void removeFollower(User user, User currentUser) throws Exception {
		if(user.getFollowers().contains(currentUser)){
			user.getFollowers().remove(currentUser);
			userRepository.save(user);
			}else{
				logger.info(user.getUsername()+" is not being follow by "+currentUser.getUsername());
				throw new Exception();
			}
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}
	
	
}