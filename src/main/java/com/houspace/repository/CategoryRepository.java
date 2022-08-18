package com.houspace.repository;

import com.houspace.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

//    List<CategoryEntity> findByCg(Long cgNum, String cgName);
}
