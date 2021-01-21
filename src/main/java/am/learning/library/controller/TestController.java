package am.learning.library.controller;


import am.learning.library.config.SecurityContextProvider;
import am.learning.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
 import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @Autowired
    private SecurityContextProvider securityContextProvider;

    @GetMapping("/test1")
    public ResponseEntity getdata() {
        return ResponseEntity.ok("Hello world");
    }


    @GetMapping("/test2")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity getdata2() {
        return ResponseEntity.ok("Hello world admin");
    }



    @GetMapping("/test3")
    public ResponseEntity getdata(OAuth2Authentication auth2Authentication) {
        User user = securityContextProvider.getByAuthentication(auth2Authentication);
        return ResponseEntity.ok(user);
    }

}
