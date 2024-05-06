package com.daeunchung.wmslive.inbound.feature;

import com.daeunchung.wmslive.inbound.domain.InboundRepository;
import com.daeunchung.wmslive.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static com.daeunchung.wmslive.product.fixture.ProductFixture.aProduct;
import static org.mockito.ArgumentMatchers.anyLong;

class RegisterInboundTest {

    private RegisterInbound registerInbound;
    private ProductRepository productRepository;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInbound = new RegisterInbound(productRepository, inboundRepository);
    }

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
        registerInbound.request(request);
    }

}
