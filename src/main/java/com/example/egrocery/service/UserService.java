package com.example.egrocery.service;


import com.example.egrocery.dto.ResponseDto;
import com.example.egrocery.dto.SignInResponseDto;
import com.example.egrocery.dto.user.SignInDto;
import com.example.egrocery.dto.user.SignupDto;
import com.example.egrocery.exception.AuthenticationFailException;
import com.example.egrocery.exception.CustomException;
import com.example.egrocery.model.User;
import com.example.egrocery.repository.UserRepo;
import com.example.egrocery.token.TokenConfirmation;
import com.example.egrocery.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenService tokenService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        //check if user exists
        if ( Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
            //we have a user
            throw new CustomException("user already exist");
        }
        //hash the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword =hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getUserName(), signupDto.getEmail(), encryptedPassword);
        userRepo.save(user);

        //create and save token
        final TokenConfirmation tokenConfirmation = new TokenConfirmation(user);
        tokenService.saveToken(tokenConfirmation);

        ResponseDto responseDto = new ResponseDto("success", "user created successfully");
        return responseDto;


    }

    //password encryption
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        mDigest.update(password.getBytes());
        byte[] digest = mDigest.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;



    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //find user by email

        User user = userRepo.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new  AuthenticationFailException("user does not exist");
        }

        //hash password
        //confirm password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword())))
                throw new AuthenticationFailException("wrong password");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //sign in

        TokenConfirmation token = tokenService.getToken(user);
        if (Objects.isNull(token)) {
            throw new CustomException("token not present");
        }
        return new SignInResponseDto("success", token.getToken());
    }
}