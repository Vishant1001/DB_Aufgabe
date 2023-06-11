package com.example.demo;

import com.opencsv.CSVParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StationController {
    private List<Station> stations;
    public StationController() {
        stations = loadCsv();
    }

    @GetMapping("/api/v1/auto-complete/{query}")
    public ResponseEntity<Object> autocomplete(@PathVariable String query) {
        long startTime = System.currentTimeMillis();

        // Error: Alphanumeric Characters Not Allowed
        if (!query.matches("[a-zA-Z]+")) {
            ErrorMessage errorResponse = new ErrorMessage("001", "Alphanumeric characters are not allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Error: Numeric Characters Not Allowed
        if (query.matches(".*\\d+.*")) {
            ErrorMessage errorResponse = new ErrorMessage("002", "Numeric characters are not allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Error: Empty Query Parameter
        if (query.isEmpty()) {
            ErrorMessage errorResponse = new ErrorMessage("003", "Query parameter cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        List<String> matchingStations = findMatchingStations(query);
        long endTime = System.currentTimeMillis();

        AutoComplete autoCompleteResponse = new AutoComplete(matchingStations, endTime - startTime, matchingStations.size());
        return ResponseEntity.ok(autoCompleteResponse);
    }


    private List<Station> loadCsv() {
        List<Station> stations = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/D_Bahnhof_2016_01_alle.csv");
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(1)
                     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                     .build()) {

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length >= 6) {
                    String evaNr = nextRecord[0];
                    String ds100 = nextRecord[1];
                    String name = nextRecord[2];
                    String verkehr = nextRecord[3];
                    String laenge = nextRecord[4];
                    String breite = nextRecord[5];

                    Station station = new Station(evaNr, ds100, name, verkehr, laenge, breite);
                    stations.add(station);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return stations;
    }

    private List<String> findMatchingStations(String partialName) {
        List<String> matchingStations = new ArrayList<>();

        for (Station station : stations) {
            if (station.getName().toLowerCase().contains(partialName.toLowerCase())) {
                String stationInfo = station.getEvaNr() + " - " + station.getDs100() + " - " + station.getName();
                matchingStations.add(stationInfo);
            }
        }

        return matchingStations;
    }
}
