package fastcampus.spring.batch.springbatchexample.part4;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderStatistics {

    private String amount;  // 일별 주문 금액 합계

    private LocalDate date;

    @Builder
    public OrderStatistics(String amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }
}
