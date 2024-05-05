package com.daeunchung.wmslive.product.feature;

import com.daeunchung.wmslive.common.ApiTest;
import com.daeunchung.wmslive.product.domain.ProductRepository;
import com.daeunchung.wmslive.product.feature.api.RegisterProductApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 실무에서는 레이어드 아키텍쳐 형태로 개발을 해야하지만, 예제 실습 코드라서 클래스 형태로 생성하겠습니다. 실무에서는 권장하지 않음을 기억해주세요
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterProductTest extends ApiTest {
    @LocalServerPort
    private int port;
    private RegisterProduct registerProduct;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        if (RestAssured.UNDEFINED_PORT == RestAssured.port) {
            RestAssured.port = port;
        }
        productRepository = new ProductRepository();
        registerProduct = new RegisterProduct(productRepository);
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        //given
        new RegisterProductApi().request();
        //then
        assertThat(productRepository.findAll()).hasSize(1);
    }

}