package com.est.erp_api.modules.phases.services;

import com.est.erp_api.modules.phases.models.Parcours;
import com.est.erp_api.modules.phases.repositories.ParcoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ParcoursServiceDefault implements ParcoursService {

    @Autowired
    private ParcoursRepository parcoursRepository;

    public ParcoursServiceDefault(ParcoursRepository parcoursRepository) {
        this.parcoursRepository = parcoursRepository;
    }

    @Override
    public Flux<Parcours> getParcours(String dossier_id) {
        return parcoursRepository.findByDossierId(dossier_id);
    }

    @Override
    public Mono<Parcours> addParcours(Parcours parcours) {
        return parcoursRepository.save(parcours);
    }

    @Override
    public Mono<Void> deleteParcours(String parcours_id) {
        return parcoursRepository.deleteById(parcours_id);
    }

    @Override
    public Flux<String> findAllFilieres() {
        return parcoursRepository.findAllFilieres();
    }

    @Override
    public Flux<String> findAllAnneesUniversitaires() {
        return parcoursRepository.findAllAnneesUniversitaires();
    }

    @Override
    public Flux<Parcours> getAllBy(String filiere, String annee) {
        return parcoursRepository.findByFiliereAndAnnee(filiere, annee);
    }


    @Override
    public Flux<Parcours> processFile(FilePart filePart, String filiere, String annee) {
        return filePart.content()
                .flatMap(dataBuffer -> {
                    try {
                        InputStream inputStream = dataBuffer.asInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                        InputSource inputSource = new InputSource(inputStreamReader);

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(inputSource);
                        doc.getDocumentElement().normalize();

                        // Extraire le numéro de la session
                        String sessionNumber = doc.getElementsByTagName("C_COD_ANU").item(0).getTextContent();
                        boolean isSession1 = sessionNumber.contains("Session 1");

                        // Extraire les informations des étudiants
                        NodeList studentList = doc.getElementsByTagName("G_IND");
                        List<Node> studentNodes = new ArrayList<>();
                        for (int i = 0; i < studentList.getLength(); i++) {
                            studentNodes.add(studentList.item(i));
                        }

                        return Flux.fromIterable(studentNodes)
                                .filter(studentNode -> studentNode.getNodeType() == Node.ELEMENT_NODE)
                                .flatMap(studentNode -> {
                                    Element studentElement = (Element) studentNode;
                                    String codeApogee = studentElement.getElementsByTagName("COD_ETU_TPW_IND").item(0).getTextContent().split(":")[1].trim();
                                    String statut_inscription = studentElement.getElementsByTagName("COD_TRE_TPW").item(0).getTextContent();

                                    return parcoursRepository.findByFiliereAndAnneeAndCode(filiere, annee, codeApogee)
                                            .flatMap(parcours -> {
                                                if (isSession1) {
                                                    parcours.setResultat_session1(statut_inscription);
                                                } else {
                                                    parcours.setResultat_session2(statut_inscription);
                                                }
                                                parcours.setStatut_inscription("EN COURS");
                                                return parcoursRepository.save(parcours);

                                            });
                                });

                    } catch (Exception e) {
                        return Flux.error(e);
                    }
                });
    }

    // Extract année, se trouve en deuxième position dans le champ de la session
    @Override
    public Flux<String> extractAnnee(FilePart filePart) {
        return filePart.content()
                .map(dataBuffer -> {
                    try {
                        InputStream inputStream = dataBuffer.asInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                        InputSource inputSource = new InputSource(inputStreamReader);

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(inputSource);
                        doc.getDocumentElement().normalize();

                        String session = doc.getElementsByTagName("C_COD_ANU").item(0).getTextContent();
                        String[] sessionParts = session.split(" ");
                        return sessionParts[sessionParts.length - 1];
                    } catch (Exception e) {
                        return null;
                    }
                });
    }






    @Override
    public Flux<Parcours> reintegrate(String filiere, String annee) {
        return parcoursRepository.findByFiliereAndAnnee(filiere, annee)
                .flatMap(parcours -> {
                    if (parcours.getStatut_inscription().equals("inscrit") && (parcours.getResultat_final().equals("ADM") || parcours.getResultat_final().equals("ADMC") || parcours.getResultat_final().equals("ADMD"))) {
                        Parcours newParcours = new Parcours();
                        newParcours.setDossier_id(parcours.getDossier_id());
                        newParcours.setNiveau("2");
                        newParcours.setFiliere(parcours.getFiliere());
                        newParcours.setCode_apogee(parcours.getCode_apogee());
                        newParcours.setOption("");
                        newParcours.setNouvellement_inscrit(true);
                        newParcours.setAnnee_universitaire(String.valueOf(Integer.parseInt(annee.substring(0, 4)) + 1) + "/" + String.valueOf(Integer.parseInt(annee.substring(5, 9)) + 1));
                        newParcours.setStatut_inscription("inscrit");
                        newParcours.setResultat_session1("");
                        newParcours.setResultat_session2("");
                        newParcours.setResultat_final("");
                        parcours.setStatut_inscription("achevé");
                        parcours.setResultat_final("AJ");
                        return Flux.concat(parcoursRepository.save(parcours), parcoursRepository.save(newParcours));
                    } else if (parcours.getStatut_inscription().equals("inscrit") && !parcours.getResultat_final().equals("ADM") && !parcours.getResultat_final().equals("ADMC") && !parcours.getResultat_final().equals("ADMD")) {
                        Parcours newParcours = new Parcours();
                        newParcours.setDossier_id( parcours.getDossier_id());
                        newParcours.setNiveau(parcours.getNiveau());
                        newParcours.setFiliere(parcours.getFiliere());
                        newParcours.setCode_apogee(parcours.getCode_apogee());
                        newParcours.setOption(parcours.getOption());
                        newParcours.setNouvellement_inscrit(false);
                        newParcours.setAnnee_universitaire(Integer.parseInt(annee.substring(0, 4)) + 1 + "/" + (Integer.parseInt(annee.substring(5, 9)) + 1));
                        newParcours.setStatut_inscription("inscrit");
                        newParcours.setResultat_session1("");
                        newParcours.setResultat_session2("");
                        newParcours.setResultat_final("");
                        parcours.setStatut_inscription("année blanche");
                        return parcoursRepository.save(newParcours);
                    } else if (parcours.getStatut_inscription().equals("annulé") || parcours.getStatut_inscription().equals("abandonné") || parcours.getStatut_inscription().equals("exclu")) {
                        return Flux.empty();
                    } else {
                        return Flux.empty();
                    }
                });
    }

    @Override
    public Mono<Void> updateOption(String option, String parcours_id) {
        return parcoursRepository.updateOption(option, parcours_id);
    }







}
