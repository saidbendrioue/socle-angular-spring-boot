package com.bycnit.socle.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bycnit.socle.constants.WebConstants;

/**
 * Controller for user management
 *
 * @author S.BENDRIOUE
 */
@RestController
@RequestMapping(WebConstants.GREETING)
public class Greeting {

	@GetMapping
	public String sayHello() {
		return "Hello BYCNIT";
	}
}
