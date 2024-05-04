package com.daeunchung.wmslive.product.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 실무에서는 레이어드 아키텍쳐 형태로 개발을 해야하지만, 예제 실습 코드라서 클래스 형태로 생성하겠습니다. 실무에서는 권장하지 않음을 기억해주세요
 */
class RegisterProductTest {

    private RegisterProduct registerProduct;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        registerProduct = new RegisterProduct(productRepository);
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        //given
        final String name = "name";
        final String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        final Category category = Category.ELECTRONICS;
        final TemperatureZone temperatureZone = TemperatureZone.ROOM_TEMPERATURE;
        final Long weightInGrams = 1000L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;
        final RegisterProduct.Request request = new RegisterProduct.Request(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                category,
                temperatureZone,
                weightInGrams, // gram
                widthInMillimeters, // 너비 mm
                heightInMillimeters, // 높이 mm
                lengthInMillimeters // 길이 mm
        );
        //when
        registerProduct.request(request);

        //then
        assertThat(productRepository.findAll()).hasSize(1);
    }
}