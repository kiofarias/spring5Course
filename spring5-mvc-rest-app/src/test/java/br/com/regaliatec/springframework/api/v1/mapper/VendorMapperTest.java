package br.com.regaliatec.springframework.api.v1.mapper;

import br.com.regaliatec.springframework.api.v1.model.VendorDTO;
import br.com.regaliatec.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final String NAME = "Test Inc.";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        //then
        assertEquals(vendorDTO.getName(),NAME);
    }

    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        //then
        assertEquals(vendor.getName(),NAME);
    }
}