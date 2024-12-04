package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_travel")
@NoArgsConstructor
@Getter
public class UserTravel {
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

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Builder
    public UserTravel(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
        this.isAccepted = false;
    }

    public static UserTravel create(User user, Travel travel) {
        return UserTravel.builder()
                .travel(travel)
                .user(user)
                .build();
    }

    public void acceptInvite() {
        this.isAccepted = true;
    }
}
