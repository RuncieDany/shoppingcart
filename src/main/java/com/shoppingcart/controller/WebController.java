package com.shoppingcart.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class WebController {
	
    @GetMapping(value="/index")
    public ModelAndView homepage(){
    	ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
