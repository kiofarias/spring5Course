package br.com.regaliatec.springframework.api.v1.mapper;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRSTNAME = "Jonh";
    public static final String LASTNAME = "Wick";
    public static final long ID = 1L;
    public static final String CUSTOMERURL= "/api/customer/1";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        //then
        assertEquals(customerDTO.getFirstName(),FIRSTNAME);
        assertEquals(customerDTO.getLastName(),LASTNAME);
    }
}