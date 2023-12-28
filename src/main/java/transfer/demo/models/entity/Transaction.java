package transfer.demo.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import transfer.demo.models.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_transaction")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "payer_phone_number")
    private String payerPhoneNumber;
    @Column(name = "receiver_phone_number")
    private String receiverPhoneNumber;
    @Column(name = "amount")
    private double amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "comment")
    private String comment;
    @Column(name = "unique_id")
    private int uniqueId;
    @ManyToOne
    @JoinColumn(name = "send_cashBox_id_id")
    private CashBox sendCashBoxId;
    @ManyToOne
    @JoinColumn(name = "receive_cashBox_id_id")
    private CashBox receiveCashBoxId;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm.SSS")
    private LocalDateTime date;
}
