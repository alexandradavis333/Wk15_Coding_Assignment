package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.Entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
