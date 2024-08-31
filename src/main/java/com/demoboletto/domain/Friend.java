package com.demoboletto.domain;

import com.demoboletto.type.EProfile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "친구")
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private int friendId;

    @ManyToOne
    @JoinColumn(name = "friend_user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "friend_type")
    @Enumerated(EnumType.STRING)
    private EFriendType friendType;

    @Column(name = "friend_name")
    private String friendName;

    @Column(name = "friend_profile")
    @Enumerated(EnumType.STRING)
    private EProfile friendProfile;
}
