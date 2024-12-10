package com.demoboletto.service;

import com.demoboletto.domain.*;
import com.demoboletto.dto.request.UserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserInfoDto;
import com.demoboletto.dto.response.GetUserProfileUpdateDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.*;
import com.demoboletto.repository.friend.FriendRepository;
import com.demoboletto.repository.travel.TravelRepository;
import com.demoboletto.repository.travel.UserTravelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserFrameRepository userFrameRepository;
    private final UserCustomFrameRepository userCustomFrameRepository;
    private final PictureRepository pictureRepository;

    private final UserRepository userRepository;
    private final UserTravelRepository userTravelRepository;
    private final FriendRepository friendRepository;
    private final ObjectStorageService objectStorageService;
    private final UserAlarmRepository userAlarmRepository;
    private final TravelRepository travelRepository;
    private final FriendCodeRepository friendCodeRepository;

    // 유저의 이름과 닉네임을 조회
    public GetUserInfoDto getUserNameAndNickname(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return GetUserInfoDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public GetUserProfileUpdateDto updateUserProfile(Long userId, UserProfileUpdateDto userProfileUpdateDto, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if (userProfileUpdateDto.profileDefault()) {
            user.updateProfile(null);
        } else {
            String profileUrl = null;
            if (file != null && !file.isEmpty()) {
                try {
                    profileUrl = objectStorageService.uploadFile(file, userId);
                } catch (IOException e) {
                    throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
                }
            }
            user.updateProfile(profileUrl);
        }
        if (userProfileUpdateDto.name() != null) {
            user.updateName(userProfileUpdateDto.name());
        }
        if (userProfileUpdateDto.nickName() != null) {
            user.updateNickname(userProfileUpdateDto.nickName());
        }

        userRepository.save(user);

        return GetUserProfileUpdateDto.builder()
                .nickname(user.getNickname())
                .name(user.getName())
                .profileUrl(user.getUserProfile())
                .build();
    }


    @Transactional
    public void deleteUser(Long userId) {
        log.info("User ID: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // UserTravel 삭제 (User는 삭제해도 되지만 Travel은 삭제되면 안 됨)
        List<UserTravel> userTravels = userTravelRepository.findByUserId(userId);

        if (!userTravels.isEmpty()) {
            userTravelRepository.deleteAll(userTravels);
        }

        // 현재 수정중인 Travel에서 유저 제거
        travelRepository.detachUser(userId);

        // Picture soft delete
        pictureRepository.detachPicturesByUserId(userId);

        // Friend 관련 데이터 삭제 (친구 목록 삭제)
        List<Friend> friends = friendRepository.findByUserId(userId);
        if (!friends.isEmpty()) {
            friendRepository.deleteAll(friends);
        }

        List<FriendCode> friendCodes = friendCodeRepository.findFriendCodesByUserId(userId);

        friendCodeRepository.deleteAll(friendCodes);

        // 다른 유저의 친구 목록에서 해당 유저 삭제
        List<Friend> friendsOfOthers = friendRepository.findByFriendUserId(userId);
        if (!friendsOfOthers.isEmpty()) {
            friendRepository.deleteAll(friendsOfOthers);
        }

        // 유저 알림 데이터 삭제
        List<UserAlarm> userAlarms = userAlarmRepository.findByUserId(userId);
        if (!userAlarms.isEmpty()) {
            userAlarmRepository.deleteAll(userAlarms);
        }

        // 유저 커스텀 프레임 삭제
        userFrameRepository.deleteUserFramesByUserId(userId);

        userRepository.delete(user);
    }

    public void updateDeviceToken(Long userId, String token) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.updateDeviceToken(token);
        userRepository.save(user);
    }

    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.invalidateRefreshToken();
        user.invalidateDeviceToken();
        userRepository.save(user);
    }
}
