package onnorokom.sharevideo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import onnorokom.sharevideo.dao.UserRepository;
import onnorokom.sharevideo.dao.VideoRepository;
import onnorokom.sharevideo.entities.User;
import onnorokom.sharevideo.entities.Video;
import onnorokom.sharevideo.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VideoRepository videoRepository;
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		return "home";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title", "Register - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
    
	@RequestMapping("/do_register")
	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value="agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session )
	{
		try
		{
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Succesfully Register","alert-success"));
			return "signup";
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something Went wrong"+e.getMessage(),"alert-error"));
			return "signup";
		}
	}
	
	@RequestMapping("/signin")
	public String customLogin(Model model)
	{
		return "login";
	}
	
	@RequestMapping("/all-videos")
	public String getVideos(Model model)
	{
		//String userName = principal.getName();
		//System.out.println(userName);
		List<Video> thevideos = this.videoRepository.findAll();
		model.addAttribute("videos", thevideos);
		
		return "list-videos";
	}
}
