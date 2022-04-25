package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.model.VendorDTO;
import br.com.regaliatec.springframework.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long Id);
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorDTO updateVendorById(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendorById(Long id, VendorDTO vendorDTO);
    void deleteVendorById(Long id);

}
