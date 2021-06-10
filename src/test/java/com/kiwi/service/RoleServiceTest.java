package com.kiwi.service;

import com.kiwi.entities.Role;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.RoleRepository;
import com.kiwi.services.implementation.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("Role Service Tests")
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl service;

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1L)
                .name("test")
                .build();
    }

    @DisplayName("Role Find All")
    @Test
    void findAll() {
        //given
        given(roleRepository.findAll()).willReturn((List.of(role)));
        //when
        List<Role> foundRole = service.findAll();
        //then
        then(roleRepository).should().findAll();
        assertThat(foundRole).hasSize(1);
    }

    @DisplayName("Role Find By Id")
    @Test
    void findById() {
        //given
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));
        //when
        Optional<Role> foundRole = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(roleRepository).should().findById(anyLong());
        assertThat(foundRole).isPresent();
    }

    @DisplayName("Role Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(roleRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Role Create")
    @Test
    void create() {
        //given
        given(roleRepository.save(any(Role.class))).willReturn(role);
        //when
        Role savedRole = service.save(new Role());
        //then
        then(roleRepository).should().save(any(Role.class));
        assertThat(savedRole).isNotNull();
    }

    @DisplayName("Role Update")
    @Test
    void update() {
        //given
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));
        given(roleRepository.save(any(Role.class))).willReturn(role);
        //when
        Optional<Role> savedRole = service.update(role, anyLong());
        //then
        then(roleRepository).should().findById(anyLong());
        then(roleRepository).should().save(any(Role.class));
        assertThat(savedRole).isPresent();
    }
}
