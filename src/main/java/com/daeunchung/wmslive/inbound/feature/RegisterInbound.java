package com.daeunchung.wmslive.inbound.feature;

import com.daeunchung.wmslive.inbound.domain.Inbound;
import com.daeunchung.wmslive.inbound.domain.InboundItem;
import com.daeunchung.wmslive.inbound.domain.InboundRepository;
import com.daeunchung.wmslive.product.domain.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
class RegisterInbound {
    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    @PostMapping("/inbounds")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        // TODO 요청을 도메인으로 변경해서 도메인을 저장한다.
        final Inbound inbound = createInbound(request);
        inboundRepository.save(inbound);
    }

    private Inbound createInbound(Request request) {
        return new Inbound(
                request.title,
                request.description,
                request.orderRequestedAt,
                request.estimatedArrivalAt,
                mapToInboundItems(request)
        );
    }

    private List<InboundItem> mapToInboundItems(Request request) {
        return request.inboundItems.stream()
                .map(this::newToInboundItem)
                .toList();
    }

    private InboundItem newToInboundItem(Request.Item item) {
        return new InboundItem(
                productRepository.getBy(item.productNo),
                item.quantity,
                item.unitPrice,
                item.description
        );
    }

    public record Request(
            @NotBlank(message = "입고 제목은 필수입니다.") String title,
            @NotBlank(message = "입고 설명은 필수입니다.") String description,
            @NotNull(message = "입고 요청일은 필수입니다.") LocalDateTime orderRequestedAt,
            @NotNull(message = "입고 예정일은 필수입니다.") LocalDateTime estimatedArrivalAt,
            @NotNull(message = "입고 품목은 필수입니다.") List<Request.Item> inboundItems) {

//        public Request {
//            Assert.hasText(title, "입고 제목은 필수입니다.");
//            Assert.hasText(description, "입고 설명은 필수입니다.");
//            Assert.notNull(orderRequestedAt, "입고 요청일은 필수입니다.");
//            Assert.notNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
//            Assert.notEmpty(inboundItems, "입고 품목은 필수입니다.");
//        }

        public record Item(
                @NotNull(message = "상품 번호는 필수입니다.") Long productNo,
                @NotNull(message = "수량은 필수입니다.") @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") Long quantity,
                @NotNull(message = "단가는 필수입니다.") @Min(value = 0, message = "단가는 0원 이상이어야 합니다.") Long unitPrice,
                @NotBlank(message = "품목 설명은 필수입니다.") String description) {

            // 서비스 함수 구현 단계에서는 Spring Aseert.함수() 를 사용하다가, Api 요청 보내는 부분 개발 할때 jakarta validation 으로 전부 변경해준다.

//            public Item {
//                Assert.notNull(productNo, "상품 번호는 필수입니다.");
//                Assert.notNull(quantity, "수량은 필수입니다.");
//                if (1 > quantity) {
//                    throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
//                }
//                Assert.notNull(unitPrice, "단가는 필수입니다.");
//                if (0 > unitPrice) {
//                    throw new IllegalArgumentException("단가는 0원 이상이어야 합니다.");
//                }
//                Assert.hasText(description, "품목 설명은 필수입니다.");
//            }
        }
    }
}
