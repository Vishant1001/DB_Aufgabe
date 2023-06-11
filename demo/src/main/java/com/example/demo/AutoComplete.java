package com.example.demo;

import java.util.List;

public class AutoComplete {
        private List<String> stationList;
        private long timeTaken;
        private int numberOfStationsFound;

        public AutoComplete(List<String> stationList, long timeTaken, int numberOfStationsFound) {
            this.stationList = stationList;
            this.timeTaken = timeTaken;
            this.numberOfStationsFound = numberOfStationsFound;
        }

        public List<String> getStationList() {
            return stationList;
        }

        public long getTimeTaken() {
            return timeTaken;
        }

        public int getNumberOfStationsFound() {
            return numberOfStationsFound;
        }
    }
