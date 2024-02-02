package com.paymybuddy.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

	@GetMapping("/error")
	public String getPage404(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "403";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value())
				return "400";
		}

		return "error";
	}
}
