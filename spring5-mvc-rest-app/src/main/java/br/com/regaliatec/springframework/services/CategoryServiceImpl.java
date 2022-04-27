package br.com.regaliatec.springframework.services;

import br.com.regaliatec.springframework.api.v1.mapper.CategoryMapper;
import br.com.regaliatec.springframework.api.v1.model.CategoryDTO;
import br.com.regaliatec.springframework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Resource
    private CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryMapper = CategoryMapper.INSTANCE;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository
                .findByName(name));
    }
}
