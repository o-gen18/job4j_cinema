package ru.job4j.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PsqlAccountStore {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(PsqlStore.instOf().showOccupiedSeats());
        System.out.println("Servlet writing:" + json);
    }
}
