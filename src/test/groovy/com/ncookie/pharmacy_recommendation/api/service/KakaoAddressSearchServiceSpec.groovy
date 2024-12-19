package com.ncookie.pharmacy_recommendation.api.service

import com.ncookie.pharmacy_recommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Description

@Description("실제 카카오 API를 호출해서 진행하는 통합 테스트")
class KakaoAddressSearchServiceSpec extends AbstractIntegrationContainerBaseTest {
    
    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다"() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid 하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다"() {
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)
        
        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

}
