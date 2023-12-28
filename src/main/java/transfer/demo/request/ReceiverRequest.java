package transfer.demo.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiverRequest {
    @NonNull
    private String name;
    @NonNull
    private String lastname;
    @NonNull
    private String surname;
    @NonNull
    private String phoneNumber;
}
