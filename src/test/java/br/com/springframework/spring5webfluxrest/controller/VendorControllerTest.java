package br.com.springframework.spring5webfluxrest.controller;

import br.com.springframework.spring5webfluxrest.domain.Vendor;
import br.com.springframework.spring5webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {
        vendorRepository= Mockito.mock(VendorRepository.class);
        vendorController= new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void getAllVendors() {
        given(vendorRepository.findAll()).willReturn(Flux.just(
                Vendor.builder().firstName("firstName1").lastName("lastName1").build(),
                Vendor.builder().firstName("firstName2").lastName("lastName2").build()
        ));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() {
        given(vendorRepository.findById("someid")).willReturn(Mono.just(
                Vendor.builder().firstName("firstName1").lastName("lastName1").build()
        ));

        webTestClient.get().uri("/api/v1/vendors/someid")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(1);
    }
}