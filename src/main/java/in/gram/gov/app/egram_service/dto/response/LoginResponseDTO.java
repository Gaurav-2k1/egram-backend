package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private UserResponseDTO user;
}

