package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.VendorMapper;
import br.com.regaliatec.springframework.api.v1.model.VendorDTO;
import br.com.regaliatec.springframework.controllers.v1.VendorController;
import br.com.regaliatec.springframework.domain.Vendor;
import br.com.regaliatec.springframework.repositories.VendorRepository;
import br.com.regaliatec.springframework.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService{

    @Resource
    private VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = VendorMapper.INSTANCE;
    }


    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long Id) {
        return vendorRepository.findById(Id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public VendorDTO updateVendorById(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendorById(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName()!=null){
                        vendor.setName(vendorDTO.getName());
                    }
                    VendorDTO savedVendorDTO =saveAndReturnDTO(vendor);
                    return savedVendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        savedVendorDTO.setVendorUrl(getVendorUrl(savedVendor));
        return savedVendorDTO;
    }

    private String getVendorUrl(Vendor vendor) {
        return VendorController.BASE_URL+"/" + vendor.getId();
    }
}
