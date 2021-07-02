package me.andrekunitz.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	SYSTEM_ERROR("System error", "/system-error"),
	INVALID_PARAMETER("Invalid parameter", "/invalid-parameter"),
	INCOMPREHENSIBLE_MESSAGE("Incomprehensible message", "/incomprehensible-message"),
	RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
	ENTITY_IN_USE("Entity in use", "/entity-in-use"),
	BUSINESS_ERROR("Business rule violation", "/business-error");

	private String title;
	private String uri;

	ProblemType(String title, String path) {
		this.title = title;
		this.uri = "https://food.com/" + path;
	}
}
