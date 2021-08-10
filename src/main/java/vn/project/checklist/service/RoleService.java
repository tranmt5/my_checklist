package vn.project.checklist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.project.checklist.model.Role;
import vn.project.checklist.repository.RoleRepo;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public List<Role> getListRole() {
        return roleRepo.findAll();
    }

    public void createRole(Role role) {
        roleRepo.save(role);
    }

    public Role getRoleById(int id) {
        return roleRepo.findById(id).get();
    }

    public void deleteRoleById(int id) {
        roleRepo.deleteById(id);
    }

    public List<Role> getRolesByQuery(String name) {
        return roleRepo.findAllByName(name);
    }
}
