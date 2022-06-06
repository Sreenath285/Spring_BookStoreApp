package com.sreenath.bookstore.service.userregistrationservice;

import com.sreenath.bookstore.dto.LoginDTO;
import com.sreenath.bookstore.dto.UserRegistrationDTO;
import com.sreenath.bookstore.exceptions.BookStoreCustomException;
import com.sreenath.bookstore.model.UserRegistrationData;
import com.sreenath.bookstore.repository.UserRegistrationRepository;
import com.sreenath.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationService implements IUserRegistrationService{
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public List<UserRegistrationData> getUserRegistrationData() {
        return userRegistrationRepository.findAll();
    }

    @Override
    public UserRegistrationData getUserRegistrationDataByUserId(int tokenId) {
        return userRegistrationRepository.findById(tokenId)
                                         .orElseThrow(() -> new BookStoreCustomException("User not found"));
    }

    @Override
    public UserRegistrationData getUserByEmailId(String email) {
        UserRegistrationData userRegistrationData = userRegistrationRepository.findUserRegistrationDataByEmail(email);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new BookStoreCustomException("User with email id " + email + " not found");
    }

    @Override
    public UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = new UserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    @Override
    public UserRegistrationData updateUserRegistrationData(int tokenId, UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataByUserId(tokenId);
        userRegistrationData.updateUserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    @Override
    public UserRegistrationData userLogin(LoginDTO loginDTO) {
        UserRegistrationData userLoginData = userRegistrationRepository.findByEmailAndPassword(loginDTO.email,
                                                                                               loginDTO.password);
        if (userLoginData != null)
            return userLoginData;
        else
            throw new BookStoreCustomException("User not found");
    }

    @Override
    public UserRegistrationData verifyUser(String token) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataByUserId(tokenUtil.decodeToken(token));
        userRegistrationData.setVerified(true);
        return userRegistrationRepository.save(userRegistrationData);
    }
}
