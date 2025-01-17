package com.demoboletto.domain;

import com.demoboletto.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_travel")
@NoArgsConstructor
@Getter
public class UserTravel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "travel_id", referencedColumnName = "travel_id")
    private Travel travel;

    @Column(name = "accepted")
    private Boolean accepted;

    @Builder
    public UserTravel(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
        this.accepted = false;
    }

    public static UserTravel create(User user, Travel travel) {
        return UserTravel.builder()
                .travel(travel)
                .user(user)
                .build();
    }

    public void acceptInvite() {
        this.accepted = true;
    }

    public boolean isAccepted() {
        return this.accepted;
    }
}
