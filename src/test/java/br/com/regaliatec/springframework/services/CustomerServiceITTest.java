package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.bootstrap.Bootstrap;
import br.com.regaliatec.springframework.domain.Customer;
import br.com.regaliatec.springframework.repositories.CategoryRepository;
import br.com.regaliatec.springframework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception{
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void pathCustomerFirstName() throws Exception{
        //given
        String updateFirstName = "updateFirstName";
        Long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updateFirstName);
        //when
        CustomerDTO patchCustomerDTO =customerService.patchCustomerById(id,customerDTO);
        //then
        assertNotNull(patchCustomerDTO);
        assertEquals(updateFirstName,patchCustomerDTO.getFirstName());
        assertThat(originalFirstName,not(equalTo(patchCustomerDTO.getFirstName())));
        assertThat(originalLastName,equalTo(patchCustomerDTO.getLastName()));
    }

    @Test
    public void pathCustomerLastName() throws Exception{

        //given
        String updateLastName = "updateLastName";
        Long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updateLastName);
        //when
        CustomerDTO patchCustomerDTO =customerService.patchCustomerById(id,customerDTO);
        //then
        assertNotNull(patchCustomerDTO);
        assertEquals(updateLastName,patchCustomerDTO.getLastName());
        assertThat(originalLastName,not(equalTo(patchCustomerDTO.getLastName())));
        assertThat(originalFirstName,equalTo(patchCustomerDTO.getFirstName()));

    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }

}
