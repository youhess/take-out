package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 添加
     *
     * @return
     */
    @PostMapping()
    @ApiOperation("新增员工")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);

        employeeService.addEmployee(employeeDTO);


        return Result.success();
    }


    /**
     * 添加
     *
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> employeePage(EmployeePageQueryDTO employeePageQueryDTO) {
        // 不是json格式的 直接用employeePageQueryDto就可以了 springboot会自己安装好
        log.info("员工分页查询参数：{}", employeePageQueryDTO);

      PageResult pageResult =  employeeService.employeePageQuery(employeePageQueryDTO);
        return Result.success(pageResult);

    }

    /**
     * 启用 和 禁用
     *
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用和禁用")
    public Result employeeStatus(@PathVariable Integer status, long id) {
        // 不是json格式的 直接用employeePageQueryDto就可以了 springboot会自己安装好
        log.info("启用参数：{},{}", status,id);

        employeeService.employeeStatus(status,id);

        return Result.success();

    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("查询员工信息")
    public Result<Employee> employeeGetById (@PathVariable Integer id) {
        // 不是json格式的 直接用employeePageQueryDto就可以了 springboot会自己安装好
        log.info("查询id：{}", id);

        Employee employee = employeeService.employeeGetById(id);

        return Result.success(employee);

    }

    /**
     *
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result employeeUpdate (@RequestBody EmployeeDTO employeeDTO) {
        // requestbody 是json格式的数据
        log.info("修改员工的信息：{}", employeeDTO);

        employeeService.employeeUpdate(employeeDTO);

        return Result.success();

    }

}
