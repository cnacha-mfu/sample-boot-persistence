package th.mfu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        // Add data to the model
        model.addAttribute("message", "Welcome to Spring MVC!");

        // Return the view name (located in the "src/main/resources/templates" directory)
        return "greet";
    }
   
}
