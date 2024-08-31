package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.PhaseCandidat;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CandidatsExtract {

    public static List<Integer> findIndexes(List<String> header, String[] fields) {
        List<Integer> indexes = new ArrayList<>();
        for (String field : fields) {
            indexes.add(header.indexOf(field));
        }
        return indexes;
    }

    public static Flux<List<PhaseCandidat>> extractCandidats(String phase_id, FilePart file) {
        try {
            return file.content()
                    .map(dataBuffer -> {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataBuffer.asInputStream()));
                        List<PhaseCandidat> candidats = new ArrayList<>();
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        String content = new String(bytes);
                        String[] lines = content.split("\n");
                        String[] seek = {"Code", "nomFR", "prenomFR", "Filière", "score", "rang"};
                        List<Integer> indexes = findIndexes(List.of(lines[0].split(",")), seek);
                        for (int i = 1; i < lines.length; i++) {
                            String[] fields = lines[i].split(",");
                            PhaseCandidat candidat = getPhaseCandidat(phase_id, fields, indexes);
                            candidats.add(candidat);
                        }

                        return candidats;
                    });

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    private static PhaseCandidat getPhaseCandidat(String phase_id, String[] fields, List<Integer> indexes) {
        PhaseCandidat candidat = new PhaseCandidat();
        candidat.setPhase_id(phase_id);
        candidat.setCode(fields[indexes.get(0)]);
        candidat.setNomfr(fields[indexes.get(1)]);
        candidat.setPrenomfr(fields[indexes.get(2)]);
        candidat.setFiliere(fields[indexes.get(3)]);
        candidat.setScore(Float.parseFloat(fields[indexes.get(4)]));
        candidat.setRang(fields[indexes.get(5)]);
        return candidat;
    }
}
