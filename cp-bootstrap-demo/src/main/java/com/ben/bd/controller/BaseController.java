package com.ben.bd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

@Controller
@RequestMapping({ "/bootstrapdemo", "/angulardemo","/angulardemo/**", "/" })
@Scope("prototype")
public class BaseController {

	@RequestMapping("{path}")
	public ModelAndView basePath(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("path") String path) {
		return new ModelAndView(path, request.getParameterMap());
	}

}
