package transfer.demo.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import transfer.demo.models.entity.CashBox;
import transfer.demo.models.entity.Transaction;
import java.util.List;
import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Boolean existsTransactionByUniqueId (int number);

    Transaction findByUniqueIdAndReceiverPhoneNumberAndReceiveCashBoxId (int uniqueId, String phoneNumber, CashBox id);

    @Query(value ="select * from tb_transaction order by =?1", nativeQuery = true)
    List<Transaction> findAllTransaction(String filterParam);

    @Query("from Transaction")
    List<Transaction> findAllByPageRequest(PageRequest request);
}
