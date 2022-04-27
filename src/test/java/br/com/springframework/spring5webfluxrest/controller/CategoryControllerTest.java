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

import java.util.ArrayList;
import java.util.List;

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
        Flux<Category> categories = Flux.just(Category.builder().description("Cat1").build(),Category.builder().description("Cat2").build());
        given(categoryRepository.findAll()).willReturn(categories);

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .isEqualTo(categories.collectList().block())
                .hasSize(2);
        then(categoryRepository).should(times(1)).findAll();

    }

    @Test
    public void getCategoryById() {

        Mono<Category> categoryMono = Mono.just(
                Category.builder().description("Cat1").build());
        given(categoryRepository.findById(any(String.class))).willReturn(categoryMono);

        webTestClient.get().uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(categoryMono.block());
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
    @Test
    public void update(){
        Mono<Category> categoryMono = Mono.just(Category.builder().description("test").build());
        Mono<Category> categoryMonoSaved = Mono.just(Category.builder().description("descr").build());
        given(categoryRepository.save(any(Category.class))).willReturn(categoryMonoSaved);

        webTestClient.put()
                .uri("/api/v1/categories/someId")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Category.class)
                .isEqualTo(categoryMonoSaved.block());

        then(categoryRepository).should(times(1)).save(any(Category.class));
    }

    @Test
    public void patch(){
        Mono<Category> categoryMono = Mono.just(Category.builder().description("test").build());
        Mono<Category> categoryMonoSaved = Mono.just(Category.builder().description("Saved").build());

        given(categoryRepository.findById(any(String.class))).willReturn(categoryMono);
        given(categoryRepository.save(any(Category.class))).willReturn(categoryMonoSaved);

        webTestClient.patch()
                .uri("/api/v1/categories/someId")
                .body(categoryMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Category.class)
                .isEqualTo(categoryMonoSaved.block());

        then(categoryRepository).should(times(1)).findById(any(String.class));
        then(categoryRepository).should(times(1)).save(any(Category.class));
    }

}