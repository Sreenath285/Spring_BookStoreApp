package com.sreenath.bookstore.controller;

import com.sreenath.bookstore.dto.LoginDTO;
import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.dto.UserRegistrationDTO;
import com.sreenath.bookstore.model.UserRegistrationData;
import com.sreenath.bookstore.service.userregistrationservice.IUserRegistrationService;
import com.sreenath.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user_registration")
public class UserRegistrationRestController {
    @Autowired
    private IUserRegistrationService iUserRegistrationService;

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> getUserRegistrationData() {
        List<UserRegistrationData> userRegistrationDataList = iUserRegistrationService.getUserRegistrationData();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", userRegistrationDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_by_id/{userId}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("userId") int userId) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserRegistrationDataByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("email") String email) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserByEmailId(email);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Email", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.createUserRegistrationData(userRegistrationDTO);
        String token = tokenUtil.createToken(userRegistrationData.getUserId());
        ResponseDTO responseDTO = new ResponseDTO("Created User Registration Data", userRegistrationData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/user_login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO loginDTO) {
        iUserRegistrationService.userLogin(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful", loginDTO);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationDate(@PathVariable("userId") int userId,
                                                                  @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.updateUserRegistrationData(userId, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Registration Data for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
