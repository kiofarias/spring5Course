package br.com.regaliatec.springframework.controllers.v1;

import br.com.regaliatec.springframework.api.v1.model.CategoryDTO;
import br.com.regaliatec.springframework.controllers.RestResponseEntityExceptionHandler;
import br.com.regaliatec.springframework.services.CategoryService;
import br.com.regaliatec.springframework.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Jim";

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCategories() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setName("Jil");

        List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        //when and then
        mockMvc.perform(get(CategoryController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void testGetCategoryByName() throws Exception{
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        //when and then
        mockMvc.perform(get(CategoryController.BASE_URL+"/"+NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }

    @Test
    public void testGetCategoryByNameNotFound() throws Exception{
        //given
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        //when and then
        mockMvc.perform(get(CategoryController.BASE_URL+"/foo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}