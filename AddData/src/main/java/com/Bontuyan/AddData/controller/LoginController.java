package com.Bontuyan.AddData.controller;

import com.Bontuyan.AddData.model.LoginRequest;
import com.Bontuyan.AddData.model.User;
import com.Bontuyan.AddData.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest body) {
        String username = body.getUsername();
        String password = body.getPassword();

        Map<String, String> response = new HashMap<>();

        // Check for null parameters
        if (username == null || password == null) {
            response.put("message", "Either of the parameters is null");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Retrieve user from database
        User user = userRepository.findByUsername(username);

        // Validate credentials
        if (user == null || !user.getPassword().equals(password)) {
            response.put("message", "Invalid Credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Successful login
        response.put("message", "Login Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
