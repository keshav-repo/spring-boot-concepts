package com.example.CriteriaQuery;

import com.example.CriteriaQuery.entity.Address;
import com.example.CriteriaQuery.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping
public class CriteriaQueryApplication {
	public static void main(String[] args) {
		SpringApplication.run(CriteriaQueryApplication.class, args);
	}

	@Autowired
	private EntityManager entityManager;

	@GetMapping("/employee")
	public String getEmployeeAddressData2() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

		Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
		Root<Address> addressRoot = criteriaQuery.from(Address.class);

		Predicate join = criteriaBuilder.equal(employeeRoot.get("employeeId"), addressRoot.get("employeeId"));

		criteriaQuery.multiselect(employeeRoot, addressRoot);
		criteriaQuery.where(join);

		List<Object[]> res =  entityManager.createQuery(criteriaQuery).getResultList();

		return "done";
	}

}
