package company.carsProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import company.carsProject.model.AdminAccount;

public interface AdminAccountRepository extends JpaRepository<AdminAccount,Long>{

}
