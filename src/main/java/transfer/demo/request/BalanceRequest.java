package transfer.demo.request;

import lombok.*;
import transfer.demo.models.entity.CashBox;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceRequest {
    @NonNull
    private CashBox cashBoxId;
    @NonNull
    private String currency;
    @NonNull
    private double balance;
}
