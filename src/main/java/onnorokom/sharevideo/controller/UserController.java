package onnorokom.sharevideo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import onnorokom.sharevideo.dao.UserRepository;
import onnorokom.sharevideo.entities.User;
import onnorokom.sharevideo.entities.Video;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dashboard()
	{
		return "user_dashboard";
	}
	
	
	@GetMapping("/add-video")
	public String openAddContactForm(Model model) {
	
		model.addAttribute("video", new Video());
		return "add_video_form";
	}
	
	@PostMapping("/process-video")
	public String processVideo(@ModelAttribute Video video,@RequestParam("videos") MultipartFile file,Principal principal)
	{
		try {
			
			
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		
		if(file.isEmpty())
		{
			
		}
		else
		{
			video.setVedio(file.getOriginalFilename());
			File saveFile=new ClassPathResource("static/vedio").getFile();
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
		}
		video.setUser(user);
	    user.getVideos().add(video);
	    this.userRepository.save(user);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "add_video_form";
	}

}
