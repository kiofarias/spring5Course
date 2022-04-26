package br.com.springframework.spring5webfluxrest.controller;

import br.com.springframework.spring5webfluxrest.domain.Category;
import br.com.springframework.spring5webfluxrest.domain.Vendor;
import br.com.springframework.spring5webfluxrest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository= Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAllCategories() {
        given(categoryRepository.findAll()).willReturn(Flux.just(
                Category.builder().description("Cat1").build()
                ,Category.builder().description("Cat2").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
        then(categoryRepository).should(times(1)).findAll();

    }

    @Test
    public void getCategoryById() {
        given(categoryRepository.findById(any(String.class))).willReturn(Mono.just(
                Category.builder().description("Cat1").build()));

        webTestClient.get().uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class);
        then(categoryRepository).should(times(1)).findById(any(String.class));

    }

    @Test
    public void create(){
        given(categoryRepository.saveAll(any(Publisher.class))).willReturn(
                Flux.just(Category.builder().description("descr").build())
        );

        Mono<Category> categoryMono = Mono.just(Category.builder().description("test").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isCreated();

        then(categoryRepository).should(times(1)).saveAll(any(Publisher.class));
    }
}