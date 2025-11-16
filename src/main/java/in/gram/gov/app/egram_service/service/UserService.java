package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.enums.UserRole;
import in.gram.gov.app.egram_service.constants.enums.UserStatus;
import in.gram.gov.app.egram_service.constants.exception.DuplicateResourceException;
import in.gram.gov.app.egram_service.constants.exception.ResourceNotFoundException;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public User findByEmailOrNull(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> findByPanchayatIdAndRole(Long panchayatId, UserRole role) {
        return userRepository.findByPanchayatIdAndRole(panchayatId, role);
    }

    public Long countByPanchayatIdAndRoleAndStatus(Long panchayatId, UserRole role, UserStatus status) {
        return userRepository.countByPanchayatIdAndRoleAndStatus(panchayatId, role, status);
    }

    public Page<User> findByPanchayatId(Long panchayatId, Pageable pageable) {
        return userRepository.findByPanchayatId(panchayatId, pageable);
    }

    public Page<User> findByFilters(Long panchayatId, UserRole role, UserStatus status, Pageable pageable) {
        return userRepository.findByFilters(panchayatId, role, status, pageable);
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateStatus(Long id, UserStatus status) {
        User user = findById(id);
        user.setStatus(status);
        userRepository.save(user);
    }

    public User findByPasswordResetToken(String token) {
        return userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired password reset token"));
    }
}

