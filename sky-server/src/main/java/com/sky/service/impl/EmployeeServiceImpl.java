package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对 springboot 自带的工具类
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {

            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);


        //private Integer status;
        employee.setStatus(StatusConstant.ENABLE);

        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        //  private LocalDateTime createTime;
        employee.setCreateTime(LocalDateTime.now());
        //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        //private LocalDateTime updateTime;
        employee.setUpdateTime(LocalDateTime.now());
        Long currentId = BaseContext.getCurrentId();
        //private Long createUser;
        employee.setCreateUser(currentId);
        //private Long updateUser;
        employee.setUpdateUser(currentId);

        employeeMapper.addEmployee(employee);
    }

    @Override
    public PageResult employeePageQuery(EmployeePageQueryDTO employeePageQueryDTO) {

        // select * from employee limit 0,10
// 开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.empolyeePageQuery(employeePageQueryDTO);

        //处理page对象成为 pageresult
        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    @Override
    public void employeeStatus(Integer status, long id) {
        // update employee set status = ? where id = ?
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.employeeUpdate(employee);
    }

    @Override
    public Employee employeeGetById(Integer id) {
        Employee employee = employeeMapper.employeeGetById(id);
        //employee.setPassword("******");
        return employee;
    }


    @Override
    public void employeeUpdate(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        employee.setUpdateTime(LocalDateTime.now());
        Long currentId = BaseContext.getCurrentId();

        employee.setUpdateUser(currentId);

        employeeMapper.employeeUpdate(employee);
    }
}
