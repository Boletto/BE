package com.demoboletto.repository.friend;

import com.demoboletto.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, FriendCustomRepository {

    List<Friend> findByUserId(Long userId);

    void deleteByFriendUserId(Long friendUserId);

    List<Friend> findByFriendUserId(Long friendUserId);

    boolean existsByUserIdAndFriendUserId(Long userId, Long friendUserId);
}
