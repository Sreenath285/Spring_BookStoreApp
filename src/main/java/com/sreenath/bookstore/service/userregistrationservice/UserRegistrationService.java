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

    /***
     * Implemented getUserRegistrationData to find all users
     * @return
     */
    @Override
    public List<UserRegistrationData> getUserRegistrationData() {
        return userRegistrationRepository.findAll();
    }

    /***
     * Implemented getUserRegistrationDataByUserId method to find user by id
     * @param userId - passing userId param
     * @return
     */
    @Override
    public UserRegistrationData getUserRegistrationDataByUserId(int userId) {
        return userRegistrationRepository.findById(userId)
                                         .orElseThrow(() -> new BookStoreCustomException("User not found"));
    }

    /***
     * Implemented getUserByEmailId method to find user by email
     * @param email - passing email param
     * @return
     */
    @Override
    public UserRegistrationData getUserByEmailId(String email) {
        UserRegistrationData userRegistrationData = userRegistrationRepository.findUserRegistrationDataByEmail(email);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new BookStoreCustomException("User with email id " + email + " not found");
    }

    /***
     * Implemented createUserRegistrationData to create user
     * @param userRegistrationDTO - passing userRegistrationDTO param
     * @return
     */
    @Override
    public UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = new UserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    /***
     * Implemented updateUserRegistrationData to update user
     * @param userId - passing userId param
     * @param userRegistrationDTO - passing userRegistrationDTO param
     * @return
     */
    @Override
    public UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataByUserId(userId);
        userRegistrationData.updateUserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    /***
     * Implemented userLogin to login user
     * @param loginDTO - passing loginDTO param
     * @return
     */
    @Override
    public UserRegistrationData userLogin(LoginDTO loginDTO) {
        UserRegistrationData userLoginData = userRegistrationRepository.findByEmailAndPassword(loginDTO.email,
                                                                                               loginDTO.password);
        if (userLoginData != null)
            return userLoginData;
        else
            throw new BookStoreCustomException("User not found");
    }

    /***
     * Implemented verifyUser method to verify user
     * @param token - passing token param
     * @return
     */
    @Override
    public UserRegistrationData verifyUser(String token) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataByUserId(tokenUtil.decodeToken(token));
        userRegistrationData.setVerified(true);
        return userRegistrationRepository.save(userRegistrationData);
    }
}
