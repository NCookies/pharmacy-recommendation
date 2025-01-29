package com.ncookie.pharmacy_recommendation.pharmacy.service;

import com.ncookie.pharmacy_recommendation.pharmacy.cache.PharmacyRedisTemplateService;
import com.ncookie.pharmacy_recommendation.pharmacy.dto.PharmacyDto;
import com.ncookie.pharmacy_recommendation.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    public List<PharmacyDto> searchPharmacyDtoList() {

        // Redis
        // 기본적으로 약국 데이터를 redis에서 읽어오되, 그 과정에서 문제가 발생하면 DB에서 읽어들인다.
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if (!pharmacyDtoList.isEmpty()) {
            log.info("redis findAll success!");
            return pharmacyDtoList;
        }

        // DB
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(this::convertToPharmacyDto)
                .collect(Collectors.toList());
    }

    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }

}
