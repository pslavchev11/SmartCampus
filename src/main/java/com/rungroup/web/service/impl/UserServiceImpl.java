package com.rungroup.web.service.impl;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.dto.UserDto;
import com.rungroup.web.mapper.UserMapper;
import com.rungroup.web.models.Role;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.repository.RoleRepository;
import com.rungroup.web.repository.UserRepository;
import com.rungroup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setUniqueNumber(registrationDto.getUniqueNumber());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName(registrationDto.getRole());
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void updateUser(RegistrationDto dto) {
        UserEntity user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getId()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setUniqueNumber(dto.getUniqueNumber());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByName(dto.getRole());
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByUniqueNumber(Integer uniqueNumber) {
        return userRepository.findByUniqueNumber(uniqueNumber);
    }

    @Override
    public Optional<UserEntity> findById(Long studentId) {
        return userRepository.findById(studentId);
    }

    @Override
    public List<UserDto> findByRole(String roleName) {

        return userRepository.findByRolesName(roleName)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
