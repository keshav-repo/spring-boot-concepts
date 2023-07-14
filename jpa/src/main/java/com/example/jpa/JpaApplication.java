package com.example.jpa;

import com.example.jpa.entity.Address;
import com.example.jpa.entity.ElectronicsProduct;
import com.example.jpa.entity.Employee;
import com.example.jpa.repo.EmployeeRepo;
import com.example.jpa.repo.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class JpaApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private EmployeeRepo employeeRepo;

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<ElectronicsProduct> electronicsProductList = new ArrayList<>();

		ElectronicsProduct electronicsProduct1 = ElectronicsProduct.builder()
				.name("Iphone")
				.brand("Apple")
				.price(100000.00)
				.model("Iphone 8")
				.specifications("50M PX Camera")
				.description("Good phone")
				.build();
		ElectronicsProduct monitor = ElectronicsProduct.builder()
				.name("32 Inch Curved Monitor")
				.brand("Samsung")
				.price(50000.00)
				.model("Model X")
				.specifications("4k Display")
				.description("Nice for photo editing")
				.build();


	//	productRepository.save(electronicsProduct);
		electronicsProductList.add(electronicsProduct1);
		electronicsProductList.add(monitor);

		try{
			productRepository.saveAll(electronicsProductList);
		}catch (Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}

		Address address = Address.builder()
				.city("Bengaluru")
				.state("Karnataka")
				.street("MG ROAD")
				.zipCode("560001 ")
				.build();
		Employee employee = new Employee("John", "Doe", address);
		employeeRepo.save(employee);


	}
}
