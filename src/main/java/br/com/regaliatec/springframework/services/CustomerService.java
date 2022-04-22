package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomerById(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomerById(Long id, CustomerDTO customerDTO);
}
