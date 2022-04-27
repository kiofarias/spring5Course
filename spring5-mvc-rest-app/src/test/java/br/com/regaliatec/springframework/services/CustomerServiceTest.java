package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapper;
import br.com.regaliatec.springframework.api.v1.mapper.CustomerMapperImpl;
import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.controllers.v1.CustomerController;
import br.com.regaliatec.springframework.domain.Customer;
import br.com.regaliatec.springframework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {


    public static final String FIRST_NAME = "Michale";
    public static final String LAST_NAME = "Weston";
    public static final Long ID = 1l;
    public static final String CUSTOMER_URL = CustomerController.BASE_URL+ "/1";
    CustomerService customerService;

    CustomerMapper customerMapper;

    @Mock
    CustomerRepository customerRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
        customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    public void getAllCustomers() throws Exception{
        //given
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);
        //when
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
        //then
        assertEquals(customerDTOList.size(),2L);

    }

    @Test
    public void getCustomerById() throws Exception{
        //given
        Customer customer1 = new Customer();
        customer1.setId(ID);
        customer1.setFirstName(FIRST_NAME);
        customer1.setLastName(LAST_NAME);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer1));
        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(CUSTOMER_URL, customerDTO.getCustomerUrl());

    }

    @Test
    public void createNewCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer savedCustomer = customerMapper.customerDTOToCustomer(customerDTO);
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(FIRST_NAME, savedDTO.getFirstName());
        assertEquals(LAST_NAME, savedDTO.getLastName());
        assertEquals(CUSTOMER_URL, savedDTO.getCustomerUrl());
    }

    @Test
    public void updateCustomerById() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //when
        CustomerDTO savedDTO = customerService.updateCustomerById(1L, customerDTO);

        //then
        assertEquals(FIRST_NAME, savedDTO.getFirstName());
        assertEquals(LAST_NAME, savedDTO.getLastName());
        assertEquals(CUSTOMER_URL, savedDTO.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() throws Exception{
        Long id=1L;
        customerService.deleteCustomerById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}