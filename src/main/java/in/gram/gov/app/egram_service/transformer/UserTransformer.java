package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.request.UserRequestDTO;
import in.gram.gov.app.egram_service.dto.response.UserResponseDTO;

public class UserTransformer {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        
        if (user.getPanchayat() != null) {
            dto.setPanchayatId(user.getPanchayat().getId());
            dto.setPanchayatName(user.getPanchayat().getPanchayatName());
        }
        
        return dto;
    }

    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }
}

