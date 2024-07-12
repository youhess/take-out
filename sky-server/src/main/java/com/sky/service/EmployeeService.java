package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * 新增员工方法
     * @param employeeDTO
     * @return
     */
    void addEmployee(EmployeeDTO employeeDTO);

    PageResult employeePageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     *
     * @param status
     * @param id
     */

    void employeeStatus(Integer status, long id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee employeeGetById(Integer id);

    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    void employeeUpdate(EmployeeDTO employeeDTO);
}
