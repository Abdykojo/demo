package transfer.demo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PayerRequest {
    @NonNull
    private String name;
    @NonNull
    private String lastname;
    @NonNull
    private String surname;
    @NonNull
    private String phoneNumber;
}
