package com.example.app_dev.service.impl;

import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import com.example.app_dev.model.UserRole;
import com.example.app_dev.repository.RoleRepository;
import com.example.app_dev.repository.UserRoleRepository;
import com.example.app_dev.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void createUserRole(User user, Employee employee) {
        UserRole userRole = new UserRole();

        userRole.setUser(user);
        int positionId = employee.getPosition().getPositionId();
        int roleId = getRoleIdByPositionId(positionId);
        this.roleRepository.findById(roleId).ifPresent(userRole::setRole);

        save(userRole);
    }

    @Override
    public void updateUserRole(Employee employee) {
        int positionId = employee.getPosition().getPositionId();
        int roleId = getRoleIdByPositionId(positionId);
        UserRole userRole = new UserRole();
        this.roleRepository.findById(roleId).ifPresent(userRole::setRole);
        userRole.setUser(employee.getUser());

        List<UserRole> list = this.userRoleRepository.findByUser(employee.getUser());
        for(UserRole ur : list) {
            if (ur.getUser().getUserId().equals(employee.getUser().getUserId())){
                userRole.setId(ur.getId());
                break;
            }
        }
        save(userRole);
    }

    public int getRoleIdByPositionId(int positionId){
        int roleId;
        switch (positionId){
            case 1: // Receptionist
            case 2: // Serve
                roleId = 4;     // employee
                break;
            case 3: // Expert
            case 4: // Supervision
                roleId = 1;     // admin
                break;
            case 5: // Manager
                roleId = 2;     // manager
                break;
            case 6: // Director
                roleId = 3;     // director
                break;
            default: // Guest
                roleId = 5;     // guest
                break;
        }
        return roleId;
    }


    @Override
    public boolean isDirectorRole(User user) {
        for(UserRole u : userRoleRepository.findByUser(user)){
            if (u.getRole().getRoleName().equals("ROLE_DIRECTOR")) {
                return true;
            }
        }
        return false;
    }
}
