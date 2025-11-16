package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.enums.SchemeStatus;
import in.gram.gov.app.egram_service.constants.exception.ResourceNotFoundException;
import in.gram.gov.app.egram_service.domain.entity.Scheme;
import in.gram.gov.app.egram_service.domain.repository.SchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchemeService {
    private final SchemeRepository schemeRepository;

    @Transactional
    public Scheme create(Scheme scheme) {
        return schemeRepository.save(scheme);
    }

    public Scheme findById(Long id) {
        return schemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scheme", id));
    }

    public Page<Scheme> findByPanchayatId(Long panchayatId, Pageable pageable) {
        return schemeRepository.findByPanchayatId(panchayatId, pageable);
    }

    public Page<Scheme> findByPanchayatIdAndStatus(Long panchayatId, SchemeStatus status, Pageable pageable) {
        return schemeRepository.findByPanchayatIdAndStatus(panchayatId, status, pageable);
    }

    public Page<Scheme> findActiveBySlug(String slug, Pageable pageable) {
        return schemeRepository.findActiveBySlug(slug, pageable);
    }

    @Transactional
    public Scheme update(Scheme scheme) {
        return schemeRepository.save(scheme);
    }

    @Transactional
    public void updateStatus(Long id, SchemeStatus status) {
        Scheme scheme = findById(id);
        scheme.setStatus(status);
        schemeRepository.save(scheme);
    }

    @Transactional
    public void delete(Long id) {
        schemeRepository.deleteById(id);
    }
}

