package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class to manage {@link TimeUnitObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class TimeUnits extends ResponseList<TimeUnits.TimeUnitObject> {

    /**
     * Sorts the given time units by all names and returns the sorted time units
     *
     * @param timeUnits time units that should be sorted
     * @return the sorted time units
     * @since 1.1
     */
    public static TimeUnits sortByName(TimeUnits timeUnits) {
        timeUnits.sortByName();
        return timeUnits;
    }

    /**
     * Sorts the given time units by all start times and returns the sorted time units
     *
     * @param timeUnits time units that should be sorted
     * @return the sorted time units
     * @since 1.1
     */
    public static TimeUnits sortByStartTime(TimeUnits timeUnits) {
        timeUnits.sortByStartTime();
        return timeUnits;
    }

    /**
     * Sorts the given time units by all end times and returns the sorted time units
     *
     * @param timeUnits time units that should be sorted
     * @return the sorted time units
     * @since 1.1
     */
    public static TimeUnits sortByEndTime(TimeUnits timeUnits) {
        timeUnits.sortByEndTime();
        return timeUnits;
    }

    /**
     * Finds a time unit by its name
     *
     * @param name name of the time unit you want to find
     * @return the time unit
     * @since 1.0
     */
    public TimeUnitObject findByName(String name) {
        return this.stream().filter(timeUnitObject -> timeUnitObject.getName().equalsIgnoreCase(name.trim())).findAny().orElse(null);
    }

    /**
     * Finds a time unit by its start time
     *
     * @param startTime start time of the time unit you want to find
     * @return the time unit
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
     * @since 1.0
     */
    public TimeUnits searchByName(String name) {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().filter(timeUnitObject -> timeUnitObject.getName().toLowerCase().contains(name.trim().toLowerCase())).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Finds time units that have the {@code startTime} or a part of it in their start time
     *
     * @param startTime start time of the time units you want to search
     * @return {@link TimeUnits} with time units that have the {@code startTime} or a part of it in their start time
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
     * @since 1.0
     */
    public TimeUnits searchByEndTime(LocalTime endTime) {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().filter(timeUnitObject -> timeUnitObject.getEndTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(endTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Sorts the time units by all names
     *
     * @since 1.1
     */
    public void sortByName() {
        this.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }

    /**
     * Sorts the time units by all start times
     *
     * @since 1.1
     */
    public void sortByStartTime() {
        this.sort(Comparator.comparing(TimeUnitObject::getStartTime));
    }

    /**
     * Sorts the time units by all end times
     *
     * @since 1.1
     */
    public void sortByEndTime() {
        this.sort(Comparator.comparing(TimeUnitObject::getEndTime));
    }

    /**
     * Returns all names that are saved in the list
     *
     * @return all names
     * @since 1.1
     */
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        this.stream().map(TimeUnitObject::getName).forEach(names::add);

        return names;
    }

    /**
     * Returns all start times that are saved in the list
     *
     * @return all start times
     * @since 1.1
     */
    public ArrayList<LocalTime> getStartTimes() {
        ArrayList<LocalTime> startTimes = new ArrayList<>();

        this.stream().map(TimeUnitObject::getStartTime).forEach(startTimes::add);

        return startTimes;
    }

    /**
     * Returns all end times that are saved in the list
     *
     * @return all end times
     * @since 1.1
     */
    public ArrayList<LocalTime> getEndTimes() {
        ArrayList<LocalTime> endTimes = new ArrayList<>();

        this.stream().map(TimeUnitObject::getEndTime).forEach(endTimes::add);

        return endTimes;
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
         * @param name      name of the time unit
         * @param startTime start time of the time unit
         * @param endTime   end time of the time unit
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
         * @since 1.0
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the start time of the time unit
         *
         * @return the start time of the time unit
         * @since 1.0
         */
        public LocalTime getStartTime() {
            return startTime;
        }

        /**
         * Returns the end time of the time unit
         *
         * @return the end time of the time unit
         * @since 1.0
         */
        public LocalTime getEndTime() {
            return endTime;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
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
