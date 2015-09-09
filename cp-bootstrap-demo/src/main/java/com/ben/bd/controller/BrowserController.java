package com.ben.bd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/b/**")
public class BrowserController {

	public BrowserController() {
		System.out.println("xxxxxxxxxx");
	}

	@RequestMapping("/{path1}/{path2}")
	public String opencard(HttpServletRequest request, HttpServletResponse response, @PathVariable("path1") String path1, @PathVariable("path2") String path2) {
		System.out.println("/b/" + path1 + "/" + path2);
		return "/"+path1 + "/" + path2;
	}

}
