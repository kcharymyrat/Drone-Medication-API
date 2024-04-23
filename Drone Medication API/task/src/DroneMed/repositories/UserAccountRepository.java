package DroneMed.repositories;

import DroneMed.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    UserAccount findByPhoneNumber(String phoneNumber);
}
