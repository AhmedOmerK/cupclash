package com.cupclash.service;

import com.cupclash.model.Bracket;
import com.cupclash.repository.BracketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class BracketService {

    // Slot keys for the 31 knockout matches (Round of 32 through Final)
    public static final String[] ROUND_OF_32  = generateKeys("R32",  16);
    public static final String[] ROUND_OF_16  = generateKeys("R16",   8);
    public static final String[] QUARTER_FINAL = generateKeys("QF",   4);
    public static final String[] SEMI_FINAL    = generateKeys("SF",   2);
    public static final String   FINAL_SLOT    = "F_1";
    public static final String   WINNER_SLOT   = "WINNER";

    private final BracketRepository bracketRepository;
    private final ObjectMapper objectMapper;

    public BracketService(BracketRepository bracketRepository, ObjectMapper objectMapper) {
        this.bracketRepository = bracketRepository;
        this.objectMapper = objectMapper;
    }

    // Returns the current active bracket, or creates a blank one if none exists
    public Map<String, String> loadBracket() {
        return bracketRepository.findTopByOrderByUpdatedAtDesc()
                .map(b -> deserialize(b.getBracketJson()))
                .orElseGet(this::emptyBracket);
    }

    // Saves the bracket map as JSON to the database
    public Bracket saveBracket(Map<String, String> bracketData) {
        String json = serialize(bracketData);
        return bracketRepository.findTopByOrderByUpdatedAtDesc()
                .map(existing -> {
                    existing.setBracketJson(json);
                    return bracketRepository.save(existing);
                })
                .orElseGet(() -> bracketRepository.save(new Bracket(json)));
    }

    // Builds a blank bracket — all 31 slots set to empty string
    public Map<String, String> emptyBracket() {
        Map<String, String> slots = new LinkedHashMap<>();
        for (String key : ROUND_OF_32)   slots.put(key, "");
        for (String key : ROUND_OF_16)   slots.put(key, "");
        for (String key : QUARTER_FINAL) slots.put(key, "");
        for (String key : SEMI_FINAL)    slots.put(key, "");
        slots.put(FINAL_SLOT,  "");
        slots.put(WINNER_SLOT, "");
        return slots;
    }

    // ─── JSON helpers ──────────────────────────────────────────────────────────

    private String serialize(Map<String, String> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize bracket", e);
        }
    }

    private Map<String, String> deserialize(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize bracket", e);
        }
    }

    private static String[] generateKeys(String prefix, int count) {
        String[] keys = new String[count];
        for (int i = 0; i < count; i++) keys[i] = prefix + "_" + (i + 1);
        return keys;
    }
}
