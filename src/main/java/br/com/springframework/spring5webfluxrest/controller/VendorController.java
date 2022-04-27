package br.com.springframework.spring5webfluxrest.controller;

import br.com.springframework.spring5webfluxrest.domain.Vendor;
import br.com.springframework.spring5webfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Flux<Vendor> createVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream);
    }

    @PutMapping("/{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }
}
