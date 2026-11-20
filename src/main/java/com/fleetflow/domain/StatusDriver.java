package com.fleetflow.domain;

public enum StatusDriver {

	AVAILABLE("Available"),
	ASSIGNED("Assigned"),
	IN_TRANSIT("In Transit");

	private final String label;

	StatusDriver(String label) {
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
