package transfer.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.demo.models.entity.CashBox;

public interface CashBoxRepository extends JpaRepository<CashBox, Long> {
    CashBox findByName (String name);
}
