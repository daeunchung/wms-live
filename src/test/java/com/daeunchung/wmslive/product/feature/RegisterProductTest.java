package com.daeunchung.wmslive.product.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;

/**
 * 실무에서는 레이어드 아키텍쳐 형태로 개발을 해야하지만, 예제 실습 코드라서 클래스 형태로 생성하겠습니다. 실무에서는 권장하지 않음을 기억해주세요
 */
class RegisterProductTest {

    private RegisterProduct registerProduct;

    @BeforeEach
    void setUp() {
        registerProduct = new RegisterProduct();

    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() {
        // given
        final Long weightInGrams = 1000L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;
        final String name = "name";
        final String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        RegisterProduct.Request request = new RegisterProduct.Request(
                name, code, description, brand, maker, origin,
                Category.ELECTRONICS, TemperatureZone.ROOM_TEMPERATURE
                , weightInGrams // gram
                , widthInMillimeters,// 너비 mm
                heightInMillimeters,// 높이 mm
                lengthInMillimeters // 길이 mm
        );
        // when
        registerProduct.request(request);

        // then
//        Assertions.assertThat(productRepository.findAll).hasSize(1);
    }

    public enum Category {
        ELECTRONICS("전자 제품");

        private final String description;

        Category(String description) {
            this.description = description;
        }
    }

    public enum TemperatureZone {
        ROOM_TEMPERATURE("상온");

        private final String description;

        TemperatureZone(String description) {
            this.description = description;
        }
    }

    @Controller
    public static class RegisterProduct {
        public void request(Request request) {
        }

        public record Request(String name, String code, String description, String brand, String maker, String orgin,
                              Category electronics, TemperatureZone roomTemperature, Long weightInGrams,
                              Long widthInMillimeters, Long heightInMillimeters, Long lengthInMillimeters) {
        }
    }
}
