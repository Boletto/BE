package com.demoboletto.repository;

import com.demoboletto.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId(Long userId);
    List<Friend> findByFriendNameContainingOrFriendNicknameContaining(String keyword, String keyword2);
    void deleteByFriendUserId(Long friendUserId);
    List<Friend> findByFriendUserId(Long friendUserId);
}
