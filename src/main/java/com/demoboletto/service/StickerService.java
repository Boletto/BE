//package com.demoboletto.service;
//
//import com.demoboletto.domain.Travel;
//import com.demoboletto.domain.TravelSticker;
//import com.demoboletto.dto.request.CreateStickerDto;
//import com.demoboletto.dto.response.GetStickerDto;
//import com.demoboletto.exception.CommonException;
//import com.demoboletto.exception.ErrorCode;
//import com.demoboletto.repository.TravelStickerRepository;
//import com.demoboletto.repository.travel.TravelRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class StickerService {
//    private final TravelStickerRepository stickerRepository;
//    private final TravelRepository travelRepository;
//
//    public List<GetStickerDto> getStickerList(Long travelId) {
//        List<GetStickerDto> stickerDtoList = new ArrayList<>();
//        stickerRepository.findAllByTravelId(travelId).forEach(travelSticker ->
//                stickerDtoList.add(
//                        GetStickerDto.builder()
//                                .stickerId(travelSticker.getStickerId())
//                                .field(travelSticker.getField())
//                                .locX(travelSticker.getLocX())
//                                .locY(travelSticker.getLocY())
//                                .scale(travelSticker.getScale())
//                                .rotation(travelSticker.getRotation())
//                                .build()
//                ));
//        return stickerDtoList;
//    }
//
//    @Transactional
//    public void createSticker(List<CreateStickerDto> stickerDtoList, Long travelId) {
//        stickerRepository.deleteAllByTravelId(travelId);
//        Travel travel = travelRepository.findById(travelId)
//                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TRAVEL));
//
//        List<TravelSticker> travelStickers = stickerDtoList.stream()
//                .map(createStickerDto -> TravelSticker.create(createStickerDto, travel))
//                .toList();
//        stickerRepository.saveAll(travelStickers);
//    }
//
//    @Transactional
//    public void deleteAllByTravelId(Long travelId) {
//        stickerRepository.deleteAllByTravelId(travelId);
//    }
//}