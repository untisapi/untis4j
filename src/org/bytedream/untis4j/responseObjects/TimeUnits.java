package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TimeUnits extends ResponseList<TimeUnits.TimeUnitObject> {

    /**
     * Finds a time unit by its name
     *
     * @param name name of the time unit you want to find
     * @return the time unit
     *
     * @since 1.0
     */
    public TimeUnitObject findByName(String name) {
        return this.stream().filter(timeUnitObject -> timeUnitObject.getName().equals(name)).findAny().orElse(null);
    }

    /**
     * Finds a time unit by its start time
     *
     * @param startTime start time of the time unit you want to find
     * @return the time unit
     *
     * @since 1.0
     */
    public TimeUnitObject findByStartTime(LocalTime startTime) {
        return this.stream().filter(timeUnitObject -> timeUnitObject.getStartTime().equals(startTime)).findAny().orElse(null);
    }

    /**
     * Finds a time unit by its end time
     *
     * @param endTime end time of the time unit you want to find
     * @return the time unit
     *
     * @since 1.0
     */
    public TimeUnitObject findByEndTime(LocalTime endTime) {
        return this.stream().filter(timeUnitObject -> timeUnitObject.getEndTime().equals(endTime)).findAny().orElse(null);
    }

    /**
     * Finds time units that have the {@code name} or a part of it in their name
     *
     * @param name name of the time units you want to search
     * @return {@link TimeUnits} with time units that have the {@code name} or a part of it in their name
     *
     * @since 1.0
     */
    public TimeUnits searchByName(String name) {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().filter(timeUnitObject -> timeUnitObject.getName().contains(name)).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Finds time units that have the {@code startTime} or a part of it in their start time
     *
     * @param startTime start time of the time units you want to search
     * @return {@link TimeUnits} with time units that have the {@code startTime} or a part of it in their start time
     *
     * @since 1.0
     */
    public TimeUnits searchByStartTime(LocalTime startTime) {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().filter(timeUnitObject -> timeUnitObject.getStartTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(startTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Finds time units that have the {@code endTime} or a part of it in their end time
     *
     * @param endTime end time of the time units you want to search
     * @return {@link TimeUnits} with time units that have the {@code endTime} or a part of it in their end time
     *
     * @since 1.0
     */
    public TimeUnits searchByEndTime(LocalTime endTime) {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().filter(timeUnitObject -> timeUnitObject.getEndTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(endTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Class to get information about a time unit
     *
     * @version 1.0
     * @since 1.0
     */
    public static class TimeUnitObject extends ResponseObject {

        private final String name;
        private final LocalTime startTime;
        private final LocalTime endTime;


        /**
         * Initialize the {@link TimeUnitObject} class
         *
         * @param name name of the time unit
         * @param startTime start time of the time unit
         * @param endTime end time of the time unit
         *
         * @since 1.0
         */
        public TimeUnitObject(String name, LocalTime startTime, LocalTime endTime) {
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /**
         * Returns the name of the time unit
         *
         * @return the name of the time unit
         *
         * @since 1.0
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the start time of the time unit
         *
         * @return the start time of the time unit
         *
         * @since 1.0
         */
        public LocalTime getStartTime() {
            return startTime;
        }

        /**
         * Returns the end time of the time unit
         *
         * @return the end time of the time unit
         *
         * @since 1.0
         */
        public LocalTime getEndTime() {
            return endTime;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         *
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> timeUnitAsMap = new HashMap<>();

            timeUnitAsMap.put("name", name);
            timeUnitAsMap.put("startTime", startTime);
            timeUnitAsMap.put("endTime", endTime);

            return new JSONObject(timeUnitAsMap).toString();
        }
    }

}
