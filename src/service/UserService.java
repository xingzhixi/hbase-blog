package service;

import hbase.UserHbase;

public class UserService {
	private UserHbase userHbase = UserHbase.create();

	private static UserService userService;

	private UserService() {
		userHbase = UserHbase.create();
	}

	public static UserService create() {
		if (userService == null)
			userService = new UserService();
		return userService;
	}

	public boolean register(String uname, String upwd) {
		return userHbase.register(uname, upwd);
	}

	public boolean login(String uname, String upwd) {
		return userHbase.login(uname, upwd);
	}
}
