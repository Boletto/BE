package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@Table(name = "friend")
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_id")
    private User friendUser;

    @Column(name = "friend_nickname")
    private String friendNickname;

    @Column(name = "friend_name")
    private String friendName;

    @Column(name = "friend_profile")
    private String friendProfile;

    @Builder
    public Friend(User user, User friendUser, String friendName, String friendNickname, String friendProfile) {
        this.user = user;
        this.friendUser = friendUser;
        this.friendProfile = friendProfile;
        this.friendName = friendName;
        this.friendNickname = friendNickname;
    }
}
