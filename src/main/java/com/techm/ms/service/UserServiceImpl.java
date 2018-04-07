package com.techm.ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import com.techm.ms.model.User;

@Service("userservice")
public class UserServiceImpl implements UserService {

	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static {
		users = populateDummyUsers();
	}
	
	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(),"User1", 10, 1));
		users.add(new User(counter.incrementAndGet(),"User2", 11, 2));
		users.add(new User(counter.incrementAndGet(),"User3", 12, 3));
		return users;
	}
	
	@Override
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByName(String name) {
		for(User user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}

	@Override
	public void saveUser(User user) {
		counter.incrementAndGet();
		users.add(user);
	}

	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public boolean isUserExist(User user) {
		return findByName(user.getName())!=null;
	}

}
