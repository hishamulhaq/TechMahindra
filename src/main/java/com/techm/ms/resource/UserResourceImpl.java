package com.techm.ms.resource;

import java.util.List;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.techm.ms.exception.CustomError;
import com.techm.ms.model.User;
import com.techm.ms.model.representation.ResourceCollection;
import com.techm.ms.service.UserService;

@Controller
public class UserResourceImpl implements UserResource {
	
	public static final Logger logger = LoggerFactory.getLogger(UserResourceImpl.class);
	
	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work

	private static String baseUrl = "/users";

	@Override
	public Response CreateUser(User user) {
		
		CustomError customError = new CustomError();
		
		List<User> users = userService.findAllUsers();
		if (users == null) {
			return Response.noContent().build();
		}	
		
		Link link = Link.fromUri(baseUrl).rel("self").build();
		
		if (userService.isUserExist(user)) {
			return Response.status(201).links(link).build();
		}
		userService.saveUser(user);
		customError.setErrorCode("409");
		customError.setErrorMessage("Unable to create. A Account with name already exist");
		return Response.ok(customError).links(link).build();
	}

	@Override
	public Response GetUser(long id) {
		
		CustomError customError = new CustomError();
		
		List<User> users = userService.findAllUsers();
		if (users == null) {
			return Response.noContent().build();
		}	
		
		Link link = Link.fromUri(baseUrl).rel("self").build();
		
		if (userService.findById(id)!=null) {
			User user = userService.findById(id);
			return Response.ok(user).links(link).build();
		}
		
		customError.setErrorCode("NOT_FOUND");
		customError.setErrorMessage("Account with id " + id + " not found");
		
		return Response.ok(customError).links(link).build();
	}
	
	@Override
	public Response findAllUsers() {
		List<User> users = userService.findAllUsers();		
		if (users == null) {
			return Response.noContent().build();
		}		
		Link link = Link.fromUri(baseUrl).rel("self").build();		
		ResourceCollection<User> resource = new ResourceCollection<>(users);
		return Response.ok(resource).links(link).build();
	}	
	
}
