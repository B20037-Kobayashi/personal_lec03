package personal_lec03.personal_lec03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/sample3")
public class Sample31Controller {

  @GetMapping("step1")
  public String sample31() {
    return "sample31.html";
  }

}
