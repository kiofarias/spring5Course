package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.model.CategoryDTO;
import br.com.regaliatec.springframework.domain.Category;
import br.com.regaliatec.springframework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "Jimmy";
    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void getAllCategories() throws Exception{
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        //when
        List<CategoryDTO> returnedCategories = categoryService.getAllCategories();
        //then
        assertEquals(3, returnedCategories.size());
    }


    @Test
    public void getCategoryByName() throws Exception{
        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);
        when(categoryRepository.findByName(NAME)).thenReturn(category);
        //when
        CategoryDTO returnedCategory = categoryService.getCategoryByName(NAME);
        //then
        assertEquals(NAME, returnedCategory.getName());
    }
}