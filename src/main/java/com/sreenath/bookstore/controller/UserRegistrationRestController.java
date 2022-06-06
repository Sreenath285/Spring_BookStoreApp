package com.sreenath.bookstore.controller;

import com.sreenath.bookstore.dto.LoginDTO;
import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.dto.UserRegistrationDTO;
import com.sreenath.bookstore.model.EmailData;
import com.sreenath.bookstore.model.UserRegistrationData;
import com.sreenath.bookstore.service.emailservice.IEmailService;
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

    @Autowired
    private IEmailService iEmailService;

    /***
     * Implemented getUserRegistrationData method to get all the users from the database
     * @return
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> getUserRegistrationData() {
        List<UserRegistrationData> userRegistrationDataList = iUserRegistrationService.getUserRegistrationData();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", userRegistrationDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getUserRegistrationDataById method to get user by id from database
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/get_by_id/{token}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserRegistrationDataByUserId(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getUserByEmailId method to get user by email id from database
     * @param email - passing email as param
     * @return
     */
    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("email") String email) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserByEmailId(email);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Email", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented verifyUser method, while registering user a mail with verification link
     * will send to the registered user email id, by clicking on the link user details is going to display
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable("token") String token) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.verifyUser(token);
        if (userRegistrationData.isVerified()) {
            ResponseDTO responseDTO = new ResponseDTO("User has been verified", userRegistrationData, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
        else {
            ResponseDTO responseDTO = new ResponseDTO("ERROR : Invalid Token", null, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
    }

    /***
     * Implemented createUserRegistrationData to create user registration
     * After registering a mail will send to the registered email id
     * @param userRegistrationDTO - passing userRegistrationDTO as param
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.createUserRegistrationData(userRegistrationDTO);
        String token = tokenUtil.createToken(userRegistrationData.getUserId());
        EmailData emailData = new EmailData(userRegistrationData.getEmail(), "Successfully Registered",
                                      "Hi " + userRegistrationData.getFirstName() + " " +userRegistrationData.getLastName() +
                                            ", Click on the given below link to verify \n" + iEmailService.getLink(token));
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Created User Registration Data", userRegistrationData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented userLogin method for user login
     * @param loginDTO - passing loginDTO as param
     * @return
     */
    @PostMapping("/user_login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO loginDTO) {
        iUserRegistrationService.userLogin(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful", loginDTO);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented updateUserRegistrationData method to update user by id
     * @param token - passing token as param
     * @param userRegistrationDTO - passing userRegistrationDTO as param
     * @return
     */
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationData(@PathVariable("token") String token,
                                                                  @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = iUserRegistrationService.updateUserRegistrationData(tokenId, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Registration Data for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
