package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapper;
import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.domain.Customer;
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
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    public CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedcustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedcustomerDTO.setCustomerUrl("/api/v1/customers/"+savedCustomer.getId());
        return savedcustomerDTO;
    }

    @Override
    public CustomerDTO updateCustomerById(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomerById(Long id, CustomerDTO customerDTO) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstName() != null){
                        customer.setFirstName(customerDTO.getFirstName());
                    }
                    if (customerDTO.getLastName() != null){
                        customer.setLastName(customerDTO.getLastName());
                    }
                    return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                })
                .orElseThrow(RuntimeException::new);
    }

}
