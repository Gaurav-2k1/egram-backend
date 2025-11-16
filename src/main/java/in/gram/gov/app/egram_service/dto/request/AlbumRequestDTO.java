package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AlbumRequestDTO {
    @NotBlank(message = "Album name is required")
    @Size(max = 200)
    private String albumName;

    private String description;
    private String coverImageUrl;
}

