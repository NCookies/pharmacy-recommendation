package com.ncookie.pharmacy_recommendation.pharmacy.service;

import com.ncookie.pharmacy_recommendation.api.dto.DocumentDto;
import com.ncookie.pharmacy_recommendation.api.dto.KakaoApiResponseDto;
import com.ncookie.pharmacy_recommendation.api.service.KakaoAddressSearchService;
import com.ncookie.pharmacy_recommendation.direction.dto.OutputDto;
import com.ncookie.pharmacy_recommendation.direction.entity.Direction;
import com.ncookie.pharmacy_recommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService.recommendPharmacyList failed] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::converToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto converToOutputDto(Direction direction) {
        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("TODO") // TODO: 추후 구현 예정
                .roadViewUrl("TODO")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }

}
