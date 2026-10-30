package com.fleetflow.domain;

public enum Role {

	ADMIN("Admin"),
	OPERASIONAL("Operasional"),
	DISPATCHER("Dispatcher");

	private final String label;

	Role(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

}
