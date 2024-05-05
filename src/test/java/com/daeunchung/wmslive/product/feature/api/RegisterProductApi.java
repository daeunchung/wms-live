package com.daeunchung.wmslive.product.feature.api;

import com.daeunchung.wmslive.product.domain.Category;
import com.daeunchung.wmslive.product.domain.TemperatureZone;
import com.daeunchung.wmslive.product.feature.RegisterProduct;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

public class RegisterProductApi {
    private String name = "name";
    private final String code = "code";
    private final String description = "description";
    private final String brand = "brand";
    private final String maker = "maker";
    private final String origin = "origin";
    private final Category category = Category.ELECTRONICS;
    private final TemperatureZone temperatureZone = TemperatureZone.ROOM_TEMPERATURE;
    private final Long weightInGrams = 1000L;
    private final Long widthInMillimeters = 100L;
    private final Long heightInMillimeters = 100L;
    private final Long lengthInMillimeters = 100L;

    public RegisterProductApi name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * ... builder 전부 필드마다 생성해줘야 하a
     */

    public void request() {
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
//        registerProduct.request(request);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value()); // 201
    }
}