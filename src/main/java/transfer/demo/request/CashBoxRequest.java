package transfer.demo.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashBoxRequest {
    @NonNull
    private String name;
}
