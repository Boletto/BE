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

    @Builder
    public UserTravel(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
    }
}
