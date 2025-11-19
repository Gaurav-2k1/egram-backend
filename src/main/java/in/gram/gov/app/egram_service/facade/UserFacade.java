package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.enums.UserRole;
import in.gram.gov.app.egram_service.constants.enums.UserStatus;
import in.gram.gov.app.egram_service.constants.exception.MaxAdminsExceededException;
import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.request.UserRequestDTO;
import in.gram.gov.app.egram_service.dto.response.UserResponseDTO;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.service.UserService;
import in.gram.gov.app.egram_service.transformer.UserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final PanchayatService panchayatService;
    private final PasswordEncoder passwordEncoder;
    private static final int MAX_ADMINS = 4;

    @Transactional
    public UserResponseDTO addTeamMember(UserRequestDTO request) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);

        // Check max admins constraint
        Long activeAdminCount = userService.countByPanchayatIdAndRoleAndStatus(
                tenantId, UserRole.PANCHAYAT_ADMIN, UserStatus.ACTIVE);
        if (activeAdminCount >= MAX_ADMINS) {
            throw new MaxAdminsExceededException("Maximum " + MAX_ADMINS + " admins allowed per panchayat");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.PANCHAYAT_ADMIN)
                .status(UserStatus.ACTIVE)
                .panchayat(panchayat)
                .build();

        user = userService.create(user);
        return UserTransformer.toDTO(user);
    }

    public Page<UserResponseDTO> getTeamMembers(Pageable pageable) {
        Long tenantId = TenantContext.getTenantId();
        Page<User> users = userService.findByPanchayatId(tenantId, pageable);
        return users.map(UserTransformer::toDTO);
    }

    @Transactional
    public void removeTeamMember(Long userId) {
        userService.updateStatus(userId, UserStatus.INACTIVE);
    }

    @Transactional
    public void updateTeamMemberStatus(Long userId, UserStatus status) {
        userService.updateStatus(userId, status);
    }

    public Page<UserResponseDTO> getAll(Long panchayatId, UserRole role, UserStatus status, Pageable pageable) {
        Page<User> users = userService.findByFilters(panchayatId, role, status, pageable);
        return users.map(UserTransformer::toDTO);
    }

    public Page<UserResponseDTO> getTeamMembersBySlug(String slug, Pageable pageable) {
        Panchayat panchayat = panchayatService.findBySlug(slug);
        Page<User> users = userService.findByPanchayatId(panchayat.getId(), pageable);
        return users.map(UserTransformer::toDTO);
    }
}

