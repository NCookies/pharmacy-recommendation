package com.ncookie.pharmacy_recommendation.direction.service;

import com.ncookie.pharmacy_recommendation.api.dto.DocumentDto;
import com.ncookie.pharmacy_recommendation.direction.entity.Direction;
import com.ncookie.pharmacy_recommendation.pharmacy.dto.PharmacyDto;
import com.ncookie.pharmacy_recommendation.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private final PharmacySearchService pharmacySearchService;

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        // 약국 데이터 조회
        List<PharmacyDto> pharmacyDtos = pharmacySearchService.searchPharmacyDtoList();

        // 거리계산 알고리즘을 이용하여, 고객과 약국 사이의 거리를 계산하고 sort

        // 다음 강의에서 진행
        return Collections.emptyList();
    }

    // Haversine formula 사용
    /**
     * @param lat1 : 사용자 위도
     * @param lon1 : 사용자 경도
     * @param lat2 : 약국 위도
     * @param lon2 : 약국 경도
     * @return : 사용자와 약국 간의 거리
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

}
