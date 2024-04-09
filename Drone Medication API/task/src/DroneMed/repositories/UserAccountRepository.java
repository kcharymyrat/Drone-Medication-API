package DroneMed.repositories;

import DroneMed.models.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
    UserAccount findByPhoneNumber(String phoneNumber);
}
