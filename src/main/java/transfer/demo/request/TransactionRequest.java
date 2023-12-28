package transfer.demo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import transfer.demo.models.entity.User;
import transfer.demo.models.enums.TransactionStatus;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TransactionRequest {

    private User payerId;
    private User receiverId;
    @NonNull
    private double amount;
    @NonNull
    private String currency;
    private int uniqueId;
    @NonNull
    private String comment;
    @NonNull
    private String sendCashBox;
    @NonNull
    private String receiveCashBox;
    private TransactionStatus status;
    private LocalDateTime date;
}
