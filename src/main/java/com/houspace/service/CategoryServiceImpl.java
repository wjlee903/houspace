package com.houspace.service;

import com.houspace.dto.CategoryDTO;
import com.houspace.entity.CategoryEntity;
import com.houspace.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getCgList() {

        List<CategoryEntity> entityList = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (CategoryEntity entity : entityList) {

            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .cgNum(entity.getCgNum())
                    .cgName(entity.getCgName())
                    .build();

            dtoList.add(categoryDTO);
        }

        return dtoList;
    }

    @Override
    public void registerCgList(CategoryDTO categoryDTO) {

        CategoryEntity categoryEntity = dtoToEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }
}
