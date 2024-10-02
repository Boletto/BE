package com.demoboletto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "alarm_settings")
public class AlarmSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "all_notifications")
    private Boolean allNotifications;

    @Column(name = "sticker_frame_notifications")
    private Boolean stickerFrameNotifications;

    @Column(name = "friend_request_notifications")
    private Boolean friendRequestNotifications;

    @Column(name = "travel_invitation_notifications")
    private Boolean travelInvitationNotifications;

    @Column(name = "location_sharing_consent")
    private Boolean locationSharingConsent;

    public AlarmSettings(User user, Boolean allNotifications, Boolean stickerFrameNotifications,
                         Boolean friendRequestNotifications, Boolean travelInvitationNotifications,
                         Boolean locationSharingConsent) {
        this.user = user;
        this.allNotifications = allNotifications;
        this.stickerFrameNotifications = stickerFrameNotifications;
        this.friendRequestNotifications = friendRequestNotifications;
        this.travelInvitationNotifications = travelInvitationNotifications;
        this.locationSharingConsent = locationSharingConsent;
    }
}
