package beggar.beggarzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
@GetMapping("/board/list")
@ResponseBody
    public String list(){
    return "board list";
}
}
