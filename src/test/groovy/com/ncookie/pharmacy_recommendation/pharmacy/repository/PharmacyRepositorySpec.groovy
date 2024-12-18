package com.ncookie.pharmacy_recommendation.pharmacy.repository

import com.ncookie.pharmacy_recommendation.AbstractIntegrationContainerBaseTest
import com.ncookie.pharmacy_recommendation.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

// AbstractIntegrationContainerBaseTest에서 Specification을 상속 받고
// @SpringBootTest 어노테이션을 적용했기 때문에 별도로 명시하지 않아도 된다.
class PharmacyRepositorySpec extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository

    def "PharmacyRepository save"() {
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }
}
