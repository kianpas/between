package com.wb.between.role.service;

import com.wb.between.permission.domain.Permission;
import com.wb.between.permission.dto.AdminPermissionResDto;
import com.wb.between.permission.repository.AdminPermissionRepository;
import com.wb.between.role.domain.Role;
import com.wb.between.role.dto.AdminRoleEditReqDto;
import com.wb.between.role.dto.AdminRoleRegistReqDto;
import com.wb.between.role.dto.AdminRoleResDto;
import com.wb.between.role.repository.AdminRoleRepository;
import com.wb.between.common.exception.CustomException;
import com.wb.between.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRoleService {

    private final AdminRoleRepository adminRoleRepository;

    private final AdminPermissionRepository adminPermissionRepository;

    /**
     * 관리자 > 역할 조회
     * @param pageable
     * @param searchRoleName
     * @return
     */
    @Transactional(readOnly = true)
    public Page<AdminRoleResDto> findAdminRoleList(Pageable pageable, String searchRoleName) {
        Page<Role> adminRoleList = adminRoleRepository.findRoleWithFilter(pageable, searchRoleName);

        return adminRoleList.map(AdminRoleResDto::from);
    }

    /**
     * 권한 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<AdminPermissionResDto> findAdminPermissionList(){
        List<Permission> permissionList = adminPermissionRepository.findAll();
        return permissionList.stream().map(AdminPermissionResDto::from).toList();
    }

    /**
     * 역할 신규 등록
     */
    @Transactional
    public void roleRegist(AdminRoleRegistReqDto adminRoleRegistReqDto) {
        Role role = Role.builder()
                .roleCode(adminRoleRegistReqDto.getRoleCode())
                .roleName(adminRoleRegistReqDto.getRoleName())
                .description(adminRoleRegistReqDto.getDescription())
                .createDt(LocalDateTime.now())
                .build();

        adminRoleRepository.save(role);
    }

    /**
     * 역할 단일 조회
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    public AdminRoleResDto findRoleById(Long roleId) {
        Role role = adminRoleRepository.findById(roleId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        return AdminRoleResDto.from(role);
    }

    /**
     * 역할 수정
     */
    @Transactional
    public void editRole(Long roleId, AdminRoleEditReqDto adminRoleEditReqDto) {

        Role role = adminRoleRepository.findById(roleId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        role.setRoleCode(adminRoleEditReqDto.getRoleCode());
        role.setRoleName(adminRoleEditReqDto.getRoleName());
        role.setDescription(adminRoleEditReqDto.getDescription());

    }

    /**
     * 역할 삭제
     */
    @Transactional
    public void deleteRole(Long roleId) {
        adminRoleRepository.deleteById(roleId);
    }



}
