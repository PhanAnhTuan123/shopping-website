package com.shopme.admin;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EntityScan({"com.shopme.common.entity","com.shopme.admin.user"})
public class MainController {

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
}