package br.com.regaliatec.springframework.api.v1.mapper;

import br.com.regaliatec.springframework.api.v1.model.CustomerDTO;
import br.com.regaliatec.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
