package com.daeunchung.wmslive.inbound.feature;

import com.daeunchung.wmslive.common.ApiTest;
import com.daeunchung.wmslive.common.Scenario;
import com.daeunchung.wmslive.inbound.domain.InboundRepository;
import com.daeunchung.wmslive.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RegisterInboundTest extends ApiTest { // 7.API TEST 로 변경

//    private RegisterInbound registerInbound;

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private InboundRepository inboundRepository;

//    @BeforeEach
//    void setUp2() {
//        // ApiTest 에 있는 @BeforeEach 도 함수명이 setUp() 인 바람에 메서드 오버라이딩이 일어나서 ApiTest 의 setUp 이 RegisterInboundTest 의 setUp 으로 바뀌어서 덮어써진다 -> 랜덤포트 적용이 되지 않는다. 따라서 함수명을 변경해주었음
//        productRepository = Mockito.mock(ProductRepository.class);
//        inboundRepository = new InboundRepository();
////        registerInbound = new RegisterInbound(productRepository, inboundRepository);
//    }

    @Test
    @DisplayName("입고를 등록한다.")
    void registerInbound() {
//        Mockito.when(productRepository.findById(anyLong())) // anyLong() : [mockito] ArgumentMatchers
//                .thenReturn(Optional.of(aProduct().build()));
//        Mockito.when(productRepository.getBy(anyLong()))
//                .thenReturn(aProduct().build());

        // 시나리오 기반 테스트로 변경 (기존애 mock repository 객체를 사용하다가, 시나리오 테스트때는 실제 요청을 생성해서 DB 스프링 빈에 등록된 repository 객체를 통해 읽고 쓰도록 한다)
        Scenario.registerProduct().request()
                .registerInbound().request();

//        registerInbound.request(request); // 서비스 요청 -> ApiTest 로 변경 -> Scenario Test 로 변경
        // 검증
        Assertions.assertThat(inboundRepository.findAll()).hasSize(1);
    }
}