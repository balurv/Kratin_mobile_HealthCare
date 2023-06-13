package com.example.healthcare.kratin.model.enums;

public enum FitnessMode {
	EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

	private final String displayName;

	FitnessMode(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
