package pet.store.controller.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.App.PetStoreCustomer;
import pet.store.App.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addEmployee(Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Adding employee {}", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomer(Long petStoreId, @RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding customer {}", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@GetMapping("/pet_store")
	public List<PetStoreData> retrieveAllPetStoreData() {
		log.info("Retrieve pet store data {}");
		return PetStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/pet_store/[petStoreId]")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store with id = ", petStoreId );
		return PetStoreService.retrievePetStoreById(petStoreId);
	}
	
	@GetMapping()
	public List<PetStoreData> retrievePetStoreInfo(){
		log.info("Retrieving pet store information ");
		return PetStoreService.retrieveAllPetStores();
	}
	
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId){
		log.info("Delete Pet store with id =", petStoreId);
		PetStoreService.deletePetStoreById(petStoreId);
		return Map.of("message", "Pet store with id = " + petStoreId + " was successful.");
	}
}
