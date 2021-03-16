package company.carsProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.carsProject.model.UserAccount;

public interface UserAccountRepository  extends JpaRepository<UserAccount,Long>{

}
