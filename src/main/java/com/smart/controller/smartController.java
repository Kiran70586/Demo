package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.DAO.UserRepository;
import com.smart.entities.User;

import jakarta.validation.Valid;
import jakarta.websocket.Session;

@Controller
public class smartController {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/home")
	public String Home()
	{
		return "home";
	}
	
	@GetMapping("/about")
	public String about()
	{
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/do_register")
	public String doregister(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="agreement" , defaultValue = "false") boolean agreement,Model model  )
	{
		try
		{
			if(result.hasErrors())
			{
				model.addAttribute("user", user);
				System.out.println(result.toString());
				return "signup";
			}	
				
			if(!agreement)
			{
				System.out.println("Check Terms And Condition");
				model.addAttribute("Message", "Please Check CheckBox");
				return "signup";
			}
		
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageurl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("User: "+user);
			
			
			User Result=repo.save(user);
			model.addAttribute("user", Result);
		
		}
		catch (Exception e) {
			
			e.printStackTrace();
			return "signup";
		}
		
		return "home";
		
	}
	
	@GetMapping("/signin")
	public String signin()
	{
		
		return "login";
	}
	
}
