package com.demoboletto.service;

import com.demoboletto.domain.*;
import com.demoboletto.dto.response.GetUserProfileUpdateDto;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.dto.request.UserProfileUpdateDto;
import com.demoboletto.dto.response.GetUserInfoDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CollectRepository collectRepository;
    private final UserTravelRepository userTravelRepository;
    private final PictureRepository pictureRepository;
    private final FriendRepository friendRepository;
    private final AWSS3Service awsS3Service;

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

        String profileUrl = null;
        if (file != null && !file.isEmpty()) {
            try {
                profileUrl = awsS3Service.uploadFile(file); // 새 파일 업로드
            } catch (IOException e) {
                throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
            }
        }

        user.updateProfile(
                userProfileUpdateDto.nickName(),
                userProfileUpdateDto.name(),
                profileUrl
        );

        userRepository.save(user);

        return GetUserProfileUpdateDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .profileUrl(user.getUserProfile())
                .build();
    }

    @Transactional
    public void deleteUser(Long userId) {
        List<UserTravel> userTravels = userTravelRepository.findByUserId(userId);
        if (!userTravels.isEmpty()) {
            userTravelRepository.deleteAll(userTravels);
        }

        List<Collect> collects = collectRepository.findByUserId(userId);
        if (!collects.isEmpty()) {
            collectRepository.deleteAll(collects);
        }

        List<Picture> pictures = pictureRepository.findByUserId(userId);
        if (!pictures.isEmpty()) {
            pictures.forEach(picture -> {
                picture.setDeleted(true);  // soft delete
                picture.setUser(null);
                pictureRepository.save(picture);
            });
        }

        List<Friend> friends = friendRepository.findByUserId(userId);
        if (!friends.isEmpty()) {
            friendRepository.deleteAll(friends);
        }

        List<Friend> friendsOfOthers = friendRepository.findByFriendUserId(userId);
        if (!friendsOfOthers.isEmpty()) {
            friendRepository.deleteAll(friendsOfOthers);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }

}
