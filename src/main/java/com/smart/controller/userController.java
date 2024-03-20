package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.DAO.ContactRepository;
import com.smart.DAO.UserRepository;
import com.smart.entities.User;
import com.smart.entities.contact;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class userController {

	@Autowired
	private UserRepository repo1;

	@Autowired
	private ContactRepository reop2;

	@ModelAttribute
	public void addCommonData(Model m, Principal prince) {
		String username = prince.getName();

		User user = repo1.getUserByUserName(username);
		m.addAttribute("user", user);
	}

	@GetMapping("/index")
	public String getindex(Model m, Principal prince) {

		
		return "normal/user_dashboard";
	}

	@GetMapping("/add_contact")
	public String addContact(Model m) {

		m.addAttribute("contact", new contact());
		return "normal/add_contact";
	}
	
	@PostMapping("/process")
	public String Process(@Valid @ModelAttribute("contact") contact cont, BindingResult result,
			@RequestParam("profileImage") MultipartFile file, Principal prince, HttpSession session, Model model) {
		try {
			String name = prince.getName();

			User user = repo1.getUserByUserName(name);
			if (result.hasErrors()) {
				System.out.println(result.toString());
				return "normal/add_contact";
			}

			if (file.isEmpty()) {
				cont.setImage("contact2.jpg");
			} else {
				cont.setImage(file.getOriginalFilename());
				File file1 = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image Is Uploaded");
			}

			cont.setUser(user);
			user.getContacts().add(cont);
			repo1.save(user);

			session.setAttribute("message", new Message("Your Contact is Added", "success"));
			System.out.println("Added To Data");

		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went Wrong", "danger"));
			e.printStackTrace();
		}
		return "normal/add_contact";
	}

	@GetMapping("/show_contact/{page}")
	public String contact(@PathVariable("page") Integer page, Principal prince, Model m) {
		String username = prince.getName();
		User user = this.repo1.getUserByUserName(username);

		Pageable pageble = PageRequest.of(page, 3);

		Page<contact> list = this.reop2.findById1(user.getId(),pageble);

		m.addAttribute("contact", list);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalPage", list.getTotalPages());

		return "normal/show_contact";
	}

	@GetMapping("/{id}/contact")
	public String contactinfo(@PathVariable("id") Integer id, Model m, Principal prince) {
		contact c = this.reop2.findById(id).get();

		
		String c1 = prince.getName();
		User u = repo1.getUserByUserName(c1);
		if (c.getUser().getId() == u.getId()) {
			m.addAttribute("contact", c);
		}

		return "normal/contact_info";

	}

	@GetMapping("/delete/{id}")
	public String deletecontact(@PathVariable("id") Integer id, Model m, Principal prince) {
		Optional<contact> li = this.reop2.findById(id);
		contact cont = li.get();

		String c1 = prince.getName();
		User u = repo1.getUserByUserName(c1);
		if (cont.getUser().getId() == u.getId()) {
			cont.setUser(null);
			this.reop2.delete(cont);

		}

		return "redirect:/user/show_contact/0 ";
	}

	@PostMapping("/update/{id}")
	public String updateform(@PathVariable("id") Integer id, Model m, Principal prince) {
		Optional<contact> li = this.reop2.findById(id);
		contact cont = li.get();
	
		m.addAttribute("contact", cont);

		return "normal/update_contact";
	}

	@PostMapping("/update_contact")
	public String updateinfo(@ModelAttribute contact cont,@RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal prince) {
		try {

			contact oldCon = this.reop2.findById(cont.getcId()).get();

			if (!file.isEmpty()) {
				//Removing old profile pic
				File deletefile=new ClassPathResource("static/image").getFile();
				File  deletefile1=new File(deletefile, oldCon.getImage());
				deletefile1.delete();
				
				//Adding old profile pic
				File file1 = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(file1.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				cont.setImage(file.getOriginalFilename());
				
			} else {
				cont.setImage(oldCon.getImage());
			}

			User user = this.repo1.getUserByUserName(prince.getName());
			cont.setUser(user);
			this.reop2.save(cont);
			session.setAttribute("message",new Message("Data Is Updated","success"));
			
			
		} catch (Exception e) {
			session.setAttribute("message",new Message("Something Went wrong","danger"));
			e.printStackTrace();
		}

		return "redirect:/user/"+cont.getcId()+"/contact";

	}
	
	@GetMapping("/profile")
	public String profile()
	{
		
		return "normal/profile";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
