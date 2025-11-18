package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.dto.request.PanchayatRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;

public class PanchayatTransformer {

    public static PanchayatResponseDTO toDTO(Panchayat panchayat) {
        if (panchayat == null) {
            return null;
        }
        
        PanchayatResponseDTO dto = new PanchayatResponseDTO();
        dto.setPanchayatId(panchayat.getId());
        dto.setPanchayatName(panchayat.getPanchayatName());
        dto.setSlug(panchayat.getSlug());
        dto.setDistrict(panchayat.getDistrict());
        dto.setState(panchayat.getState());
        dto.setAddress(panchayat.getAddress());
        dto.setContactPhone(panchayat.getContactPhone());
        dto.setContactEmail(panchayat.getContactEmail());
        dto.setLogoUrl(panchayat.getLogoUrl());
        dto.setHeroImageUrl(panchayat.getHeroImageUrl());
        dto.setDescription(panchayat.getDescription());
        dto.setAboutText(panchayat.getAboutText());
        dto.setOfficeAddress(panchayat.getOfficeAddress());
        dto.setOfficePhone(panchayat.getOfficePhone());
        dto.setOfficeEmail(panchayat.getOfficeEmail());
        dto.setMapCoordinates(panchayat.getMapCoordinates());
        dto.setOfficeHours(panchayat.getOfficeHours());
        dto.setStatus(panchayat.getStatus());
        dto.setCreatedAt(panchayat.getCreatedAt());
        dto.setUpdatedAt(panchayat.getUpdatedAt());
        
        return dto;
    }

    public static Panchayat toEntity(PanchayatRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Panchayat.builder()
                .panchayatName(dto.getPanchayatName())
                .slug(dto.getSlug())
                .district(dto.getDistrict())
                .state(dto.getState())
                .address(dto.getAddress())
                .contactPhone(dto.getContactPhone())
                .contactEmail(dto.getContactEmail())
                .description(dto.getDescription())
                .aboutText(dto.getAboutText())
                .officeAddress(dto.getOfficeAddress())
                .officePhone(dto.getOfficePhone())
                .officeEmail(dto.getOfficeEmail())
                .mapCoordinates(dto.getMapCoordinates())
                .officeHours(dto.getOfficeHours())
                .build();
    }

    public static void updateEntity(Panchayat panchayat, PanchayatRequestDTO dto) {
        if (panchayat == null || dto == null) {
            return;
        }
        
        panchayat.setPanchayatName(dto.getPanchayatName());
        panchayat.setSlug(dto.getSlug());
        panchayat.setDistrict(dto.getDistrict());
        panchayat.setState(dto.getState());
        panchayat.setAddress(dto.getAddress());
        panchayat.setContactPhone(dto.getContactPhone());
        panchayat.setContactEmail(dto.getContactEmail());
        panchayat.setDescription(dto.getDescription());
        panchayat.setAboutText(dto.getAboutText());
        panchayat.setOfficeAddress(dto.getOfficeAddress());
        panchayat.setOfficePhone(dto.getOfficePhone());
        panchayat.setOfficeEmail(dto.getOfficeEmail());
        panchayat.setMapCoordinates(dto.getMapCoordinates());
        panchayat.setOfficeHours(dto.getOfficeHours());
    }
}

