package br.com.regaliatec.springframework.controllers.v1;

import br.com.regaliatec.springframework.api.v1.model.VendorDTO;
import br.com.regaliatec.springframework.services.VendorService;
import br.com.regaliatec.springframework.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest extends AbstractRestControllerTest{

    @MockBean //provided by Spring Context
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc; //provided by Spring Context

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;

    @Before
    public void setUp() {
        vendorDTO_1 = new VendorDTO("Vendor 1", VendorController.BASE_URL + "/1");
        vendorDTO_2 = new VendorDTO("Vendor 2", VendorController.BASE_URL + "/2");
    }


    @Test
    public void getVendorList() throws Exception {
        //given
        List<VendorDTO> vendorListDTO = Arrays.asList(vendorDTO_1,vendorDTO_2);
        when(vendorService.getAllVendors()).thenReturn(vendorListDTO);
        //when and then
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        //given
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO_1);
        //when and then
        mockMvc.perform(get(VendorController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void createNewVendor() throws Exception{
        //given
        given(vendorService.createNewVendor(vendorDTO_1)).willReturn(vendorDTO_1);
        //when and then
        mockMvc.perform(post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void updateVendor() throws Exception{
        //given
        given(vendorService.updateVendorById(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);
        //when and then
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void patchVendor() throws Exception{
        //given
        given(vendorService.patchVendorById(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);
        //when and then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        then(vendorService).should().deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}