package transfer.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.demo.models.entity.Balance;
import transfer.demo.models.entity.CashBox;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Balance findByCashBoxIdAndCurrency (CashBox name, String currency);
}
