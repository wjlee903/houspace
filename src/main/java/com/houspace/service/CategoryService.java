package com.houspace.service;

import com.houspace.dto.CategoryDTO;
import com.houspace.entity.CategoryEntity;

import java.util.List;

/*
*   작성자 : 오채영
*   작성일 : 22-06-27
* */

public interface CategoryService {

    // DB의 전체 data를 불러오는 method, paging 처리가 안된 data는 List로 받는다.
    public List<CategoryDTO> getCgList();


    public void registerCgList(CategoryDTO categoryDTO);


    default CategoryDTO entityToDto(CategoryEntity categoryEntity) {
       CategoryDTO categoryDTO = CategoryDTO.builder()
               .cgNum(categoryEntity.getCgNum())
//               .cgName(categoryEntity.getCgName())
                .build();

        return categoryDTO;
    }

    // dto 객체를 DB에 저장할수 있는 DB(table)객체로 변환
    // 사용 예 - DB로 data 저장, 수정 method
    default CategoryEntity dtoToEntity(CategoryDTO dto) {
        CategoryEntity entity = CategoryEntity.builder()
                .cgNum(dto.getCgNum())
                .cgName(dto.getCgName())
                .build();

        return entity;
    }

}
