package transfer.demo.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalRequest {
    @NonNull
    private PayerRequest payerRequest;
    @NonNull
    private ReceiverRequest receiverRequest;
    @NonNull
    private TransactionRequest transactionRequest;
}
