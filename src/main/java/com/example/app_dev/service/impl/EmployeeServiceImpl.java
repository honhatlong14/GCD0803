package com.example.app_dev.service.impl;


import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import com.example.app_dev.repository.EmployeeRepository;
import com.example.app_dev.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Employee> findAllList() {
        return repository.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Employee findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public void saveCrypt(Employee employee) {
        String strPw = employee.getUser().getPassword();
        String bCryptPw = BCrypt.hashpw(strPw, BCrypt.gensalt());
        employee.getUser().setPassword(bCryptPw);

        repository.save(employee);
    }

    @Override
    public void save(Employee employee) {
        repository.save(employee);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean checkPassword(String oldPw, Employee employee) {
        String bCryptCurrentPw = employee.getUser().getPassword(); // get password đã hash và lưu ở trên database về
        return BCrypt.checkpw(oldPw, bCryptCurrentPw); // kiểm tra đoạn mã bcrypt của oldPw với đoạn mã bcrypt password lấy về
    }
}
