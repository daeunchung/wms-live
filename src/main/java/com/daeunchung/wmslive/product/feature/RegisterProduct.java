package com.daeunchung.wmslive.product.feature;

import com.daeunchung.wmslive.product.domain.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterProduct {
    private final ProductRepository productRepository;

    public RegisterProduct(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody final Request request) {
        final Product product = request.toDomain();
        productRepository.save(product);
    }

    public record Request(
            // Request (API 요청값)의 validation check 를 jakarta 로 변경
            @NotBlank(message = "상품명은 필수입니다.")
            String name,
            @NotBlank(message = "상품명은 필수입니다.")
            String code,
            @NotBlank(message = "상품명은 필수입니다.")
            String description,
            @NotBlank(message = "상품명은 필수입니다.")
            String brand,
            @NotBlank(message = "상품명은 필수입니다.")
            String maker,
            @NotBlank(message = "상품명은 필수입니다.")
            String origin,
            @NotNull(message = "카테고리는 필수입니다.")
            Category category,
            @NotNull(message = "온도대는 필수입니다.")
            TemperatureZone temperatureZone,
            @NotNull(message = "상품의 무게는 필수입니다.") @Min(value = 0, message = "상품의 무게는 0보다 작을 수 없습니다.")
            Long weightInGrams,
            @NotNull(message = "상품의 너비는 필수입니다.") @Min(value = 0, message = "상품의 무게는 0보다 작을 수 없습니다.")
            Long widthInMillimeters,
            @NotNull(message = "상품의 높이는 필수입니다.") @Min(value = 0, message = "상품의 무게는 0보다 작을 수 없습니다.")
            Long heightInMillimeters,
            @NotNull(message = "상품의 길이는 필수입니다.") @Min(value = 0, message = "상품의 무게는 0보다 작을 수 없습니다.")
            Long lengthInMillimeters) {

        public Request {
//            Assert.hasText(name, "상품명은 필수입니다.");
//            Assert.hasText(code, "상품코드는 필수입니다.");
//            Assert.hasText(description, "상품설명은 필수입니다.");
//            Assert.hasText(brand, "브랜드는 필수입니다.");
//            Assert.hasText(maker, "제조사는 필수입니다.");
//            Assert.hasText(origin, "원산지는 필수입니다.");
//            Assert.notNull(category, "카테고리는 필수입니다.");
//            Assert.notNull(temperatureZone, "온도대는 필수입니다.");
//            Assert.notNull(weightInGrams, "무게는 필수입니다.");
//            if (0 > weightInGrams) {
//                throw new IllegalArgumentException("무게는 0보다 작을 수 없습니다.");
//            }
//            Assert.notNull(widthInMillimeters, "가로길이는 필수입니다.");
//            if (0 > widthInMillimeters) {
//                throw new IllegalArgumentException("가로길이는 0보다 작을 수 없습니다.");
//            }
//            Assert.notNull(heightInMillimeters, "세로길이는 필수입니다.");
//            if (0 > heightInMillimeters) {
//                throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
//            }
//            Assert.notNull(lengthInMillimeters, "세로길이는 필수입니다.");
//            if (0 > lengthInMillimeters) {
//                throw new IllegalArgumentException("세로길이는 0보다 작을 수 없습니다.");
//            }
        }

        public Product toDomain() {
            return new Product(
                    name,
                    code,
                    description,
                    brand,
                    maker,
                    origin,
                    category,
                    temperatureZone,
                    weightInGrams,
                    new ProductSize(
                            widthInMillimeters,
                            heightInMillimeters,
                            lengthInMillimeters)
            );
        }
    }
}
