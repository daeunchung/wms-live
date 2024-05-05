package com.daeunchung.wmslive.common;

import com.daeunchung.wmslive.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }
}
