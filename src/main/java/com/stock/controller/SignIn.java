package com.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.exception.InvalidLoginCredentialException;
import com.stock.jwt.JwtUtil;
import com.stock.model.HeaderDataModel;
import com.stock.model.LoginModel;
import com.stock.model.RegisterModel;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SignIn {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterModel registerModel) {
        try {
            this.userService.signUp(registerModel);
            return ResponseEntity.ok("User registration success!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register user: " + e.getMessage());
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<HeaderDataModel> login(@RequestBody LoginModel loginModel) throws InvalidLoginCredentialException, JsonProcessingException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(),loginModel.getPassword()));
        }catch (Exception e){
            throw new InvalidLoginCredentialException("Invalid Username or Password");
        }
        String token = jwtUtil.generateToken(loginModel.getEmail());
        HeaderDataModel headerData = new HeaderDataModel();
        headerData.setEmail(loginModel.getEmail());
        headerData.setJwtToken(token);
        System.out.println(headerData);
        return new ResponseEntity<HeaderDataModel>(headerData, HttpStatus.OK);
    }

}
