package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Admin;
import com.atomika.gitByCity.dto.mapper.AdminMapper;
import com.atomika.gitByCity.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;


//    public Admin create (Admin admin) {
//        return adminMapper.entityToDto(
//                adminRepository.save(adminMapper.dtoToEntity(admin)));
//    }
//
//    public Admin update (Admin admin) {
//        return adminMapper.entityToDto(
//                adminRepository.save(adminMapper.dtoToEntity(admin)));
//    }
//
//    public Long delete (Long id) {
//        adminRepository.deleteById(id);
//        return id;
//    }
//
//    public List<Admin> findAll () {
//        return adminMapper.toList(adminRepository.findAll());
//    }
}
