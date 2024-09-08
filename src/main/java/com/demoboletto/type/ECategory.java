package com.demoboletto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 일단 enum으로 하긴 했는데 2개 이상 선택 가능해서 바뀌어야 할듯..
@Getter
@RequiredArgsConstructor
public enum ECategory {
    VOLUNTEER,            // 봉사
    COUNTRYSIDE_TRIP,      // 시골여행
    LUXURY,                // 럭셔리
    PILGRIMAGE,            // 순례
    WORKSHOP,              // 워크숍
    SOLO,                  // 나홀로
    GRADUATION_TRIP,       // 졸업여행
    RELAXATION,            // 휴양
    WALKING,               // 뚜벅이
    ADVENTURE,             // 모험
    FANDOM,                // 덕질
    HISTORICAL_SITE,       // 유적지
    SHOPPING,              // 쇼핑
    FAMILY,                // 가족
    SLOW_TRAVEL,           // 느린여행
    HOTEL_STAY,            // 호캉스
    SPORTS,                // 운동
    COUPLE,                // 커플
    ACTIVITY,              // 액티비티
    SIGHTSEEING,           // 관광
    GASTRONOMY,            // 식도락
    BACKPACKING,           // 배낭여행
    FRIENDSHIP             // 우정
}
