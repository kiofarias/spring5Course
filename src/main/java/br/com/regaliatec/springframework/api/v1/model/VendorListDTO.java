package br.com.regaliatec.springframework.api.v1.model;

import br.com.regaliatec.springframework.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorListDTO {
    private List<VendorDTO> vendors;
}
