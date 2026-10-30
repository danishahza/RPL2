package com.fleetflow.ui;

import com.fleetflow.domain.User;

public class SessionContext {

	private User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public void login(User user) {
		this.currentUser = user;
	}

	public void logout() {
		this.currentUser = null;
	}

}
