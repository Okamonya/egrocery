package com.example.egrocery.token;

import com.example.egrocery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<TokenConfirmation, Long> {
    TokenConfirmation findByUser(User user);

    TokenConfirmation findByToken(String token);
}
