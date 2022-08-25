package com.example.egrocery.token;

import com.example.egrocery.exception.AuthenticationFailException;
import com.example.egrocery.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenService {

    @Autowired
    TokenRepo tokenRepo;

    public void saveToken(TokenConfirmation tokenConfirmation) {
        tokenRepo.save(tokenConfirmation);
    }

    public TokenConfirmation getToken(User user) {
        return tokenRepo.findByUser(user);
    }

    public User getUser(String token) {
        TokenConfirmation tokenConfirmation = tokenRepo.findByToken("");
        if (Objects.isNull(tokenConfirmation)) {
            return null;
        }

        return tokenConfirmation.getUser();
    }
    public void authenticate(String token) {
        if (Objects.isNull(token)) {
            throw new AuthenticationFailException("token not present");
        }
        if (Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }
    }

}
