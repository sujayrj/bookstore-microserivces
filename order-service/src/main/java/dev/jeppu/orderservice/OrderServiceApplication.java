package dev.jeppu.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

/*
orders
id
order_ref
username
customer_name
customer_email
customer_phone
delivery_address_line1
delivery_address_line2
delivery_address_city
delivery_address_state
delivery_address_country
delivery_address_zip_code
status
created_at
updated_at
comments

order_items
id
code
name
price
quantity
*/
