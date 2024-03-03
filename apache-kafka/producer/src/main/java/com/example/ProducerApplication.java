package com.example;

import com.example.model.OrderPlacedEvent;
import com.example.model.ProductPayload;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequestMapping("/order")
public class ProducerApplication implements CommandLineRunner {

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//produceOrderEvent();
	}

	@GetMapping
	public String emitOrderEvent(){
		produceOrderEvent();
		return "SUCCESS";
	}

	void produceOrderEvent(){
		List<ProductPayload> productPayloadList = new ArrayList<>();
		productPayloadList.add(new ProductPayload(10, 5));
		productPayloadList.add(new ProductPayload(11, 7));
		productPayloadList.add(new ProductPayload(12, 6));

		OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
				.orderId("1000")
				.customerId("500")
				.productPurchased(productPayloadList)
				.build();
		orderService.sendOrder(orderPlacedEvent);
	}

	@PostMapping
	public String customOrderEvent(@RequestBody OrderPlacedEvent orderPlacedEvent){
		orderService.sendOrder(orderPlacedEvent);
		return "SUCCESS";
	}
}
