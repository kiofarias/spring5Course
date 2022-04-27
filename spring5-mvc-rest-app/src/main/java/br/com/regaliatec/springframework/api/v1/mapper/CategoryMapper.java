package br.com.regaliatec.springframework.api.v1.mapper;

import br.com.regaliatec.springframework.api.v1.model.CategoryDTO;
import br.com.regaliatec.springframework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDTO categoryToCategoryDTO(Category category);
}
