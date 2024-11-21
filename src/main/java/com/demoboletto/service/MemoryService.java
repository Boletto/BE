//package com.demoboletto.service;
//
//import com.demoboletto.domain.Travel;
//import com.demoboletto.dto.request.MemoryEditDto;
//import com.demoboletto.dto.response.GetMemoryDto;
//import com.demoboletto.exception.CommonException;
//import com.demoboletto.exception.ErrorCode;
//import com.demoboletto.repository.travel.TravelRepository;
//import com.demoboletto.type.EStatusType;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class MemoryService {
////    private final PictureService pictureService;
//    private final StickerService stickerService;
//    private final SpeechService speechService;
//    private final TravelRepository travelRepository;
//
//    public GetMemoryDto getMemoryByTravelId(Long travelId) {
//        // get picture data from db & get sticker data from db & get speech data from db
//        return GetMemoryDto.builder()
//                .travelId(travelId)
//                .pictureList(pictureService.getPictureList(travelId))
//                .stickerList(stickerService.getStickerList(travelId))
//                .speechList(speechService.getSpeechList(travelId))
//                .status(travelRepository.findStatusByTravelId(travelId))
//                .fourCutList(pictureService.getFourCutList(travelId))
//                .build();
//    }
//
//    @Transactional
//    public void memoryEditMode(MemoryEditDto memoryEditDto) {
//        // mode 변경시 unlock이 될때 스티커랑 말풍선 데이터도 넘어오고 같이 저장. 벌크 형식으로 다 삭제하고 다시 넣는 구조로//
//
//        Travel travel = travelRepository.findById(memoryEditDto.travelId())
//                .orElseThrow(() -> new IllegalArgumentException("travel not found"));
//        try {
//            switch (memoryEditDto.status()) {
//                case LOCK:
//                    if (travel.getStatus() == EStatusType.LOCK) {
//                        throw new IllegalArgumentException("already locked");
//                    }
//                    travel.setStatus(EStatusType.LOCK);
//                    travelRepository.save(travel);
//                case UNLOCK:
//                    // save sticker data to db & save speech data to db
//                    stickerService.createSticker(memoryEditDto.stickerList(), memoryEditDto.travelId());
//                    speechService.createSpeech(memoryEditDto.speechList(), memoryEditDto.travelId());
//
//                    travel.setStatus(EStatusType.UNLOCK);
//                    travelRepository.save(travel);
//
//            }
//
//        } catch (Exception e) {
//            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
//}