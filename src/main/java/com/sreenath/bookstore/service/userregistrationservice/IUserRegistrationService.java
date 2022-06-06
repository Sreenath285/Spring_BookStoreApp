package com.sreenath.bookstore.service.userregistrationservice;

import com.sreenath.bookstore.dto.LoginDTO;
import com.sreenath.bookstore.dto.UserRegistrationDTO;
import com.sreenath.bookstore.model.UserRegistrationData;

import java.util.List;

public interface IUserRegistrationService {
    List<UserRegistrationData> getUserRegistrationData();

    UserRegistrationData getUserRegistrationDataByUserId(int tokenId);

    UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData updateUserRegistrationData(int tokenId, UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData getUserByEmailId(String email);

    UserRegistrationData userLogin(LoginDTO loginDTO);

    UserRegistrationData verifyUser(String token);
}
