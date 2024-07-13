package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult categoryPageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(CategoryDTO categoryDTO);

    void categoryStatus(Integer status, long id);

    void categoryDelete(long id);

    void categoryUpdate(CategoryDTO categoryDTO);

    List<Category> categoryList(Integer type);
}
