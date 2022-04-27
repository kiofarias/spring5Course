package br.com.springframework.spring5webfluxrest.controller;

import br.com.springframework.spring5webfluxrest.domain.Vendor;
import br.com.springframework.spring5webfluxrest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

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

    @Test
    public void createVendor(){
        Flux<Vendor> vendorFluxSaved = Flux.just(Vendor.builder().firstName("saved1").build()
                ,Vendor.builder().firstName("saved2").build());
        given(vendorRepository.saveAll(any(Flux.class))).willReturn(vendorFluxSaved);

        Flux<Vendor> vendorFlux =  Flux.just(Vendor.builder().firstName("send1").build(),
                Vendor.builder().firstName("send2").build());

        webTestClient.post().uri("/api/v1/vendors")
                .body(vendorFlux,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBodyList(Vendor.class)
                .isEqualTo(vendorFluxSaved.collectList().block())
                .hasSize(2);

        then(vendorRepository).should(times(1)).saveAll(any(Publisher.class));
    }
}