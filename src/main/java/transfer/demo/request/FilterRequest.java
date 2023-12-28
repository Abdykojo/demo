package transfer.demo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import transfer.demo.models.enums.TransactionStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRequest {
    private TransactionStatus status;
    private String sendCashBoxName;
    private String receiveCashBoxName;
    private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.SSS")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.SSS")
    private LocalDateTime endDate;
    private String filterField;
    private int pageNumber;
    private int pageSize;

}