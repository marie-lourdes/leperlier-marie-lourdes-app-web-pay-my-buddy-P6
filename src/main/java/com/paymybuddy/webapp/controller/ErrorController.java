package com.paymybuddy.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	
	/*@GetMapping("/error")
	public String getPage404() {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-403";
			}

		}

		return "error";
	}*/

	@GetMapping("/error-400")
	public String getPage404() {
		return "400";
	}
}
