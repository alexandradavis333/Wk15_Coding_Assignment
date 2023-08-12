package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import pet.store.controller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.*;
import pet.store.App.PetStoreCustomer;
import pet.store.App.PetStoreEmployee;
import pet.store.Entity.Customer;
import pet.store.Entity.Employee;
import pet.store.Entity.PetStore;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;

@Service
public class PetStoreService {
	
	@Autowired
	private static PetStoreDao petStoreDao;
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = null;
		
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		}
		else {
		petStore = findOrCreatePetStoreById(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
	}
		return new PetStoreData(petStoreDao.save(petStore));

	}
	
	private static PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet store id cannot be found."));	
		}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStore.getPetStoreId());
		petStore.setPetStoreName(petStore.getPetStoreName());
		petStore.setPetStoreAddress(petStore.getPetStoreAddress());
		petStore.setPetStoreCity(petStore.getPetStoreCity());
		petStore.setPetStoreState(petStore.getPetStoreState());
		petStore.setPetStoreZip(petStore.getPetStoreZip());
		petStore.setPetStorePhone(petStore.getPetStorePhone());
		
	}

	private PetStore findOrCreatePetStoreById(Long petStoreId) {
		PetStore petStore;
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		}
		else {
		petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}

	@Transactional(readOnly = true)
	public static List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			result.add(psd);
		}
		return result;
	}

	public static PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}

	@Autowired
	private static EmployeeDao employeeDao;
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		return new PetStoreEmployee(employeeDao.save(employee));
	}
	
	@Transactional(readOnly = true)
	public static List<PetStoreEmployee> retrieveAllEmployees() {
		List<Employee> employees = employeeDao.findAll();
		List<PetStoreEmployee> result = new LinkedList<>();
		
		for(Employee employee : employees) {
			result.add(new PetStoreEmployee(employee));
		}
		return result;
	}
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(employee.getEmployeeId());
		employee.setEmployeeFirstName(employee.getEmployeeFirstName());
		employee.setEmployeeLastName(employee.getEmployeeLastName());
		employee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
		employee.setEmployeePhone(employee.getEmployeePhone());
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;
		if(Objects.isNull(employeeId)) {
			employee = new Employee();
		}
		else {
		employee = findEmployeeById(employeeId);
		}
		return employee;
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee id cannot be found."));
	}
	
	@Autowired
	private static CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);
		
		copyCustomerFields(customer, petStoreCustomer);
		
		return new PetStoreCustomer(customerDao.save(customer));
	}
	
	@Transactional(readOnly = true)
	public static List<PetStoreCustomer> retrieveAllCustomers() {
		List<Customer> customers = customerDao.findAll();
		List<PetStoreCustomer> result = new LinkedList<>();
		
		for(Customer customer : customers) {
			result.add(new PetStoreCustomer(customer));
		}
		return result;
	}
	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(customer.getCustomerId());
		customer.setCustomerFirstName(customer.getCustomerFirstName());
		customer.setCustomerLastName(customer.getCustomerLastName());
		customer.setCustomerEmail(customer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;
		if(Objects.isNull(customerId)) {
			customer = new Customer();
		}
		else {
		customer = findCustomerById(customerId);
		}
		return customer;
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer id cannot be found."));
	}

	public static void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
	
	
}
