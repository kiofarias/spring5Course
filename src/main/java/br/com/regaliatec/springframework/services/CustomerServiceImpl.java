package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapper;
import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapperImpl;
import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Resource
    private CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;


    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = CustomerMapper.INSTANCE;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerMapper
                .customerToCustomerDTO(
                        customerRepository.findById(id).get());
    }
}
