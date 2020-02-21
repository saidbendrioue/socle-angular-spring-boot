package com.bycnit.socle.service.exception;

import java.net.URI;

public class ErrorConstants {

	private ErrorConstants() {}
	
    public static final String PROBLEM_BASE_URL = "https://www.bycn-it.com/problem";

    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
}
