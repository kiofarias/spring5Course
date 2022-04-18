package br.com.regaliatec.springframework.api.v1.mapper;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRSTNAME = "Jonh";
    public static final String LASTNAME = "Wick";
    public static final long ID = 1L;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        //then
        assertEquals(customerDTO.getId(),Long.valueOf(ID));
        assertEquals(customerDTO.getFirstName(),FIRSTNAME);
        assertEquals(customerDTO.getLastName(),LASTNAME);
    }
}