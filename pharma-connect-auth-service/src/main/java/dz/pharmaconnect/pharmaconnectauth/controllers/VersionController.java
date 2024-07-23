package dz.pharmaconnect.pharmaconnectauth.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/version")
public class VersionController {
    @GetMapping
    public String getVersion(){
        return "0.1";
    }
}
