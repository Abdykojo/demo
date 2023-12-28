package transfer.demo.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    @NonNull
    private int code;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String cashBoxName;
}
