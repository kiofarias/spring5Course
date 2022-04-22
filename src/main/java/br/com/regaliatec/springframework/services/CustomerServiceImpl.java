package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapper;
import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.controllers.v1.CustomerController;
import br.com.regaliatec.springframework.domain.Customer;
import br.com.regaliatec.springframework.repositories.CustomerRepository;
import br.com.regaliatec.springframework.services.exceptions.ResourceNotFoundException;
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
                    customerDTO.setCustomerUrl(getCustomerUrl(customer));
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
                    customerDTO.setCustomerUrl(getCustomerUrl(customer));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
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
                    Customer savedCustomer = customerRepository.save(customer);
                    CustomerDTO savedcustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
                    savedcustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer));
                    return savedcustomerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedcustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedcustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer));
        return savedcustomerDTO;
    }

    private String getCustomerUrl(Customer customer) {
        return CustomerController.BASE_URL +"/"+ customer.getId();
    }

}
