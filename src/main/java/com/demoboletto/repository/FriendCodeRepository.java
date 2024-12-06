package com.demoboletto.repository;

import com.demoboletto.domain.FriendCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendCodeRepository extends JpaRepository<FriendCode, Long>, FriendCodeCustomRepository {
    boolean existsByFriendCode(String friendCode);

    Optional<FriendCode> findByFriendCode(String friendCode);
}
