package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Rule-based "AI" helper for doctor recommendations and slot suggestions.
 * Maps symptoms to specializations and recommends appropriate doctors.
 */
public class AIHelper {

    private static final Map<String, Specialization> SYMPTOM_MAP = new HashMap<>();

    static {
        SYMPTOM_MAP.put("chest pain", Specialization.CARDIOLOGIST);
        SYMPTOM_MAP.put("heart", Specialization.CARDIOLOGIST);
        SYMPTOM_MAP.put("palpitations", Specialization.CARDIOLOGIST);
        SYMPTOM_MAP.put("skin rash", Specialization.DERMATOLOGIST);
        SYMPTOM_MAP.put("acne", Specialization.DERMATOLOGIST);
        SYMPTOM_MAP.put("eczema", Specialization.DERMATOLOGIST);
        SYMPTOM_MAP.put("headache", Specialization.NEUROLOGIST);
        SYMPTOM_MAP.put("migraine", Specialization.NEUROLOGIST);
        SYMPTOM_MAP.put("seizure", Specialization.NEUROLOGIST);
        SYMPTOM_MAP.put("bone pain", Specialization.ORTHOPEDICS);
        SYMPTOM_MAP.put("fracture", Specialization.ORTHOPEDICS);
        SYMPTOM_MAP.put("joint pain", Specialization.ORTHOPEDICS);
        SYMPTOM_MAP.put("ear pain", Specialization.ENT);
        SYMPTOM_MAP.put("sore throat", Specialization.ENT);
        SYMPTOM_MAP.put("hearing loss", Specialization.ENT);
        SYMPTOM_MAP.put("fever", Specialization.GENERALPHYSICIAN);
        SYMPTOM_MAP.put("cold", Specialization.GENERALPHYSICIAN);
        SYMPTOM_MAP.put("cough", Specialization.GENERALPHYSICIAN);
        SYMPTOM_MAP.put("tumor", Specialization.ONCOLOGIST);
        SYMPTOM_MAP.put("cancer", Specialization.ONCOLOGIST);
        SYMPTOM_MAP.put("lump", Specialization.ONCOLOGIST);
        SYMPTOM_MAP.put("unexplained weight loss", Specialization.ONCOLOGIST);
        SYMPTOM_MAP.put("persistent fatigue", Specialization.ONCOLOGIST);
        SYMPTOM_MAP.put("abnormal bleeding", Specialization.ONCOLOGIST);

    }

    //Recommends doctors based on patient symptoms using keyword matching.
    public static List<Doctor> recommendDoctors(String symptoms, Collection<Doctor> allDoctors) {
        String lowerSymptoms = symptoms.toLowerCase();

        Set<Specialization> matchedSpecs = SYMPTOM_MAP.entrySet().stream()
                // Use regex \b to ensure the symptom key matches a whole word
                .filter(entry -> lowerSymptoms.matches(".*\\b" + entry.getKey() + "\\b.*"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());

        //fallback
        if (matchedSpecs.isEmpty()) {
            matchedSpecs.add(Specialization.GENERALPHYSICIAN);
        }

        final Set<Specialization> finalSpecs = matchedSpecs;
        return allDoctors.stream()
                .filter(d -> finalSpecs.contains(d.getSpecialization()))
                .sorted(Comparator.comparingDouble(Doctor::getConsultationFee))
                .collect(Collectors.toList());
    }


    //suggest next available slots
    public static List<String> suggestSlots(String date) {
        return Arrays.asList(DateUtil.getAvailableSlots(date));
    }
}
