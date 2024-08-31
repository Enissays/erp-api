package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import com.est.erp_api.modules.phases.repositories.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidatServiceDefault implements CandidatService {
    @Autowired
    private CandidatRepository candidatRepository;

    @Override
    public Mono<PhaseCandidat> addCandidat(PhaseCandidat candidat) {
        return candidatRepository.save(candidat);
    }

    @Override
    public Mono<Void> deleteCandidat(String phase_id, String candidat_id) {
        return candidatRepository.deleteById(candidat_id);
    }

    @Override
    public Mono<Void> updateCandidat(PhaseCandidat candidat) {
        return candidatRepository.save(candidat).then();
    }

    @Override
    public Flux<PhaseCandidat> getCandidats(String phase_id) {
        return candidatRepository.findByPhaseId(phase_id);
    }

    @Override
    public Mono<Void> importCandidats(FilePart file, String phase_id) {
        try {
            Flux<List<PhaseCandidat>> candidats = CandidatsExtract.extractCandidats(phase_id, file);
            assert candidats != null;
            return candidats.flatMap(candidatRepository::saveAll).then();
        }
        catch (Exception e) {
            return Mono.error(e);
        }
    }
}
