package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {


    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
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
        customer1.setId(1l);
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer1));
        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        //then
        assertEquals("Michale", customerDTO.getFirstName());

    }
}