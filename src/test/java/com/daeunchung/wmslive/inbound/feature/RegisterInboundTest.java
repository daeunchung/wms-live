package com.daeunchung.wmslive.inbound.feature;

import com.daeunchung.wmslive.common.ApiTest;
import com.daeunchung.wmslive.inbound.domain.InboundRepository;
import com.daeunchung.wmslive.product.domain.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static com.daeunchung.wmslive.product.fixture.ProductFixture.aProduct;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.http.HttpStatus.CREATED;

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
        Mockito.when(productRepository.getBy(anyLong()))
                .thenReturn(aProduct().build());

        final LocalDateTime orderRequestedAt = LocalDateTime.now();
        final LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        final Long productNo = 1L;
        final Long quantity = 1L;
        final Long unitPrice = 1500L;
        final RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                "description"
        );
        final List<RegisterInbound.Request.Item> inboundItems = List.of(inboundItem);
        final RegisterInbound.Request request = new RegisterInbound.Request(
                "title",
                "description",
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems
        );

        RestAssured.given().log().all() // HTTP 요청이 콘솔에 찍히도록 설정한다
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds")
                .then().log().all().statusCode(CREATED.value());

//        registerInbound.request(request); // ApiTest 로 변경

        // 검증
        Assertions.assertThat(inboundRepository.findAll()).hasSize(1);
    }

}
