package com.E_com.E_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.E_com.E_commerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name=:name AND c.parentcategory.name=:parentCategoryName")
     Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);

}
