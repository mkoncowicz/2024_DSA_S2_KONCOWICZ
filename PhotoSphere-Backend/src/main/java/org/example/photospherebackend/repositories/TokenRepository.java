package org.example.photospherebackend.repositories;

import org.example.photospherebackend.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String Token);
}
