package cicosy.templete.thymleafController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProcessFlowController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
