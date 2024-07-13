package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理相关接口")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> categoryPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        // 不是json格式的 直接用employeePageQueryDto就可以了 springboot会自己安装好
        log.info("分类分页查询参数：{}", categoryPageQueryDTO);

        PageResult pageResult =  categoryService.categoryPageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);

    }

    @PostMapping
    @ApiOperation("添加菜品和套餐分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        // 获取目录的数据后用到service
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result categoryStatus(@PathVariable Integer status,long id){

        log.info("启用禁用参数:{},{}",status,id);
        categoryService.categoryStatus(status,id);

        return Result.success();

    }


    /**
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result categoryUpdate(@RequestBody CategoryDTO categoryDTO){

        log.info("修改分类:{}",categoryDTO);
        categoryService.categoryUpdate(categoryDTO);

        return Result.success();

    }

    /**
     * 删除目录信息
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除")
    public Result categoryDelete(long id){

        log.info("删除参数：{}",id);
        categoryService.categoryDelete(id);

        return Result.success();
    }


    @GetMapping("/list")
    public Result<List<Category>> categoryList(Integer type){

        List<Category> list =  categoryService.categoryList(type);

        return Result.success(list);

    }
}
