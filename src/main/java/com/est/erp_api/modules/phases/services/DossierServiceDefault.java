package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.Dossier;
import com.est.erp_api.modules.phases.models.PhaseCandidat;
import com.est.erp_api.modules.phases.repositories.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Service
public class DossierServiceDefault implements DossierService {

    @Autowired
    private DossierRepository dossierRepository;

    @Override
    public Mono<Dossier> createDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }

    @Override
    public Mono<Dossier> updateDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }

    @Override
    public Mono<Void> deleteDossier(String id) {
        return dossierRepository.deleteById(id).map(dossier -> dossier);
    }

    @Override
    public Flux<Dossier> findByCandidatId(String candidatId) {
        return dossierRepository.findByCandidatId(candidatId);
    }

    @Override
    public Mono<Dossier> getDossier(String id) {
        return dossierRepository.findById(id);
    }

    @Override
    public Flux<Dossier> findAll(int limit, int offset) {
        return dossierRepository.findAll(limit, offset);
    }

    @Override
    public Flux<Dossier> editFromFile(FilePart filePart) {
        return filePart.content()
                .flatMap(dataBuffer -> {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataBuffer.asInputStream()));
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    String content = new String(bytes);
                    String[] lines = content.split("\n");

                    List<String> headers = Arrays.asList(lines[0].split(","));
                    if (!headers.contains("code_massar")) {
                        return Mono.error(new Exception("Le fichier ne contient pas le champ code_massar"));
                    }

                    // Process each line except the header
                    return Flux.fromArray(Arrays.copyOfRange(lines, 1, lines.length))
                            .flatMap(line -> {
                                String[] values = line.split(",");
                                Map<String, String> valueMap = new HashMap<>();
                                for (int i = 0; i < headers.size(); i++) {
                                    valueMap.put(headers.get(i).trim(), values[i]);
                                }

                                String codeMassar = valueMap.get("code_massar");
                                return dossierRepository.findByCodeMassar(codeMassar)
                                        .flatMap(dossier -> {
                                            Class<?> dossierClass = Dossier.class;
                                            for (Field field : dossierClass.getDeclaredFields()) {
                                                field.setAccessible(true);
                                                String fieldName = field.getName();
                                                if (valueMap.containsKey(fieldName)) {
                                                    String fieldValue = valueMap.get(fieldName);
                                                    try {
                                                        Object convertedValue = convertToFieldType(field, fieldValue);
                                                        field.set(dossier, convertedValue);
                                                    } catch (IllegalAccessException e) {
                                                        return Mono.error(e);
                                                    }
                                                }
                                            }
                                            return dossierRepository.save(dossier);
                                        });
                            });
                });
    }

    private Object convertToFieldType(Field field, String value) {
        Class<?> fieldType = field.getType();
        if (fieldType.equals(String.class)) {
            return value;
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.equals(LocalDate.class)) {
            return LocalDate.parse(value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }


}