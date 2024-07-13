package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Page<Category> categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteById(long id);

    List<Category> selectCategoryListByType(Integer type);
}
