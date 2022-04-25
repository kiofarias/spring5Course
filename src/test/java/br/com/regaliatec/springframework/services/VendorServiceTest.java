package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.VendorMapper;
import br.com.regaliatec.springframework.api.v1.model.VendorDTO;
import br.com.regaliatec.springframework.controllers.v1.CustomerController;
import br.com.regaliatec.springframework.controllers.v1.VendorController;
import br.com.regaliatec.springframework.domain.Vendor;
import br.com.regaliatec.springframework.repositories.VendorRepository;
import br.com.regaliatec.springframework.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorMapper vendorMapper;

    public static final String NAME1 = "Teste Inc.";
    public static final Long ID1 = 1l;
    public static final String VENDOR_URL1 = VendorController.BASE_URL+"/"+ID1;
    public static final String NAME2 = "Teste2 Inc.";
    public static final Long ID2 = 2l;
    public static final String VENDOR_URL2 = VendorController.BASE_URL+"/"+ID2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository);
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void getAllVendors() {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setName(NAME1);
        vendor1.setId(ID1);
        Vendor vendor2 = new Vendor();
        vendor2.setName(NAME2);
        vendor2.setId(ID2);
        List<Vendor> vendorList = Arrays.asList(vendor1,vendor2);
        when(vendorRepository.findAll()).thenReturn(vendorList);
        //when
        List<VendorDTO> vendorDTOS= vendorService.getAllVendors();
        //then
        assertEquals(vendorDTOS.size(),2);
        assertEquals(vendorDTOS.get(0).getName(),NAME1);
        assertEquals(vendorDTOS.get(0).getVendorUrl(),VENDOR_URL1);
        assertEquals(vendorDTOS.get(1).getName(),NAME2);
        assertEquals(vendorDTOS.get(1).getVendorUrl(),VENDOR_URL2);
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setName(NAME1);
        vendor1.setId(ID1);
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor1));
        //when
        VendorDTO vendorDTO= vendorService.getVendorById(ID1);
        //then
        assertEquals(vendorDTO.getVendorUrl(),VENDOR_URL1);
        assertEquals(vendorDTO.getName(),NAME1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {
        //given
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        VendorDTO vendorDTO= vendorService.getVendorById(ID1);
        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME1);
        Vendor savedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        savedVendor.setId(ID1);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        //when
        VendorDTO savedVendorDTO= vendorService.createNewVendor(vendorDTO);
        //then
        assertEquals(savedVendorDTO.getVendorUrl(),VENDOR_URL1);
        assertEquals(savedVendorDTO.getName(),NAME1);
    }


    @Test
    public void updateVendorById() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME1);
        Vendor savedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        savedVendor.setId(ID1);
        savedVendor.setName(vendorDTO.getName());
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        //when
        VendorDTO savedVendorDTO= vendorService.updateVendorById(ID1,vendorDTO);
        //then
        assertEquals(savedVendorDTO.getVendorUrl(),VENDOR_URL1);
        assertEquals(savedVendorDTO.getName(),NAME1);
    }

    @Test
    public void patchVendorById() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME1);
        Vendor savedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        savedVendor.setId(ID1);
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(savedVendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        //when
        VendorDTO savedVendorDTO= vendorService.patchVendorById(ID1,vendorDTO);
        //then
        assertEquals(savedVendorDTO.getVendorUrl(),VENDOR_URL1);
        assertEquals(savedVendorDTO.getName(),NAME1);
        then(vendorRepository).should(times(1)).findById(anyLong());
        then(vendorRepository).should(times(1)).save(any(Vendor.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchVendorByIdNotFound() {
        //given
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        VendorDTO vendorDTO= vendorService.patchVendorById(ID1,new VendorDTO());
        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

    }

    @Test
    public void deleteVendorById() {
        //given
        //when
        vendorService.deleteVendorById(ID1);
        //then
        then(vendorRepository).should().deleteById(anyLong());
    }

}