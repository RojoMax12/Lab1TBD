package com.lab.backend.Services;

import com.lab.backend.Entities.AdminEntity;
import com.lab.backend.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    private AdminRepository adminRepository;

    public AdminServices(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminEntity createAdmin(AdminEntity admin) {
        return adminRepository.CreateAdmin(admin);
    }

    public List<AdminEntity> getAllAdmins() {
        return adminRepository.getAllAdmins();
    }

    public AdminEntity getAdminById(Long id) {
        return adminRepository.getAdminById(id);
    }

    public AdminEntity getAdminByEmail(String email) {
        return adminRepository.getAdminByEmail(email);
    }

    public void updateAdmin(Long id, AdminEntity admin) {
        adminRepository.updateAdmin(id, admin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteAdminById(id);
    }
}
