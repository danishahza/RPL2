package com.fleetflow.domain;

public enum StatusDeliveryOrder {

	CREATED("Created"),
	ASSIGNED("Assigned"),
	IN_TRANSIT("In Transit"),
	DELIVERED("Delivered"),
	COMPLETED("Completed");

	private final String label;

	StatusDeliveryOrder(String label) {
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
