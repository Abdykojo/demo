package transfer.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transfer.demo.models.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
