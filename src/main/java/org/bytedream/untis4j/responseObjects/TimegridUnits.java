package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.UntisUtils;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class to manage {@link TimegridUnitObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class TimegridUnits extends ResponseList<TimegridUnits.TimegridUnitObject> {

    /**
     * Sorts the given timegrid units by all days and returns the sorted timegrid units
     *
     * @param timegridUnits timegrid units that should be sorted
     * @return the sorted timegrid units
     * @since 1.1
     */
    public static TimegridUnits sortByDays(TimegridUnits timegridUnits) {
        timegridUnits.sortByDays();
        return timegridUnits;
    }

    /**
     * Sorts the given timegrid units by all time units and returns the sorted timegrid units
     *
     * @param timegridUnits timegrid units that should be sorted
     * @return the sorted timegrid units
     * @since 1.1
     */
    public static TimegridUnits sortByTimeUnits(TimegridUnits timegridUnits) {
        timegridUnits.sortByTimeUnits();
        return timegridUnits;
    }

    /**
     * Finds a timegrid unit by its day
     *
     * @param day day of the timegrid unit you want to find
     * @return the time unit
     * @since 1.0
     */
    public TimegridUnitObject findByDay(int day) {
        return this.stream().filter(timegridUnitObject -> timegridUnitObject.getDay() == day).findAny().orElse(null);
    }

    /**
     * Finds a timegrid unit by its name
     *
     * @param timeUnits time units of the timegrid unit you want to find
     * @return the time unit
     * @since 1.0
     */
    public TimegridUnitObject findByTimeUnits(TimeUnits timeUnits) {
        return this.stream().filter(timegridUnitObject -> timegridUnitObject.getTimeUnits().equals(timeUnits)).findAny().orElse(null);
    }

    /**
     * Finds timegrid units that have the {@code day} or a part of it in their day
     *
     * @param day day of the timegrid units you want to search
     * @return {@link TimeUnits} with timegrid units that have the {@code day} or a part of it in their day
     * @since 1.0
     */
    public TimegridUnits searchByDay(int day) {
        TimegridUnits timegridUnits = new TimegridUnits();

        this.stream().filter(timegridUnitObject -> String.valueOf(timegridUnitObject.getDay()).contains(String.valueOf(day))).forEach(timegridUnits::add);

        return timegridUnits;
    }

    /**
     * Finds timegrid units that have the {@code timeUnits} or a part of it in their time units
     *
     * @param timeUnits time units of the timegrid units you want to search
     * @return {@link TimeUnits} with timegrid units that have the {@code timeUnits} or a part of it in their time units
     * @since 1.0
     */
    public TimegridUnits searchByTimeUnits(TimeUnits timeUnits) {
        TimegridUnits timegridUnits = new TimegridUnits();

        this.forEach(timegridUnitObject -> {
            if (UntisUtils.listContainsListItem(timegridUnitObject.getTimeUnits(), timeUnits)) {
                timegridUnits.add(timegridUnitObject);
            }
        });

        return timegridUnits;
    }

    /**
     * Sorts the timegrid units by all days
     *
     * @since 1.1
     */
    public void sortByDays() {
        this.sort(Comparator.comparing(TimegridUnitObject::getDay));
    }

    /**
     * Sorts the timegrid units by all time units
     *
     * @since 1.1
     */
    public void sortByTimeUnits() {
        this.sortByDays();
    }

    /**
     * Returns all days that are saved in the list
     *
     * @return all days
     * @since 1.1
     */
    public ArrayList<Integer> getDays() {
        ArrayList<Integer> days = new ArrayList<>();

        this.stream().map(TimegridUnitObject::getDay).forEach(days::add);

        return days;
    }

    /**
     * Returns all time units that are saved in the list
     *
     * @return all time units
     * @since 1.1
     */
    public TimeUnits getTimeUnits() {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().map(TimegridUnitObject::getTimeUnits).forEach(timeUnits::addAll);

        return timeUnits;
    }

    /**
     * Class to get information about a timegrid unit
     *
     * @version 1.0
     * @since 1.0
     */
    public static class TimegridUnitObject extends ResponseObject {

        private final int day;
        private final TimeUnits timeUnits;

        /**
         * Initialize the {@link TimegridUnitObject} class
         *
         * @param day       day of the timegrid unit
         * @param timeUnits all time untis on the given day
         * @since 1.0
         */
        public TimegridUnitObject(int day, TimeUnits timeUnits) {
            this.day = day;
            this.timeUnits = timeUnits;
        }

        /**
         * Returns the day of the timegrid unit
         *
         * @return the day of the timegrid unit
         * @since 1.0
         */
        public int getDay() {
            return day;
        }

        /**
         * Returns the time units
         *
         * @return the time units
         * @since 1.0
         */
        public TimeUnits getTimeUnits() {
            return timeUnits;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> timegridUnitAsMap = new HashMap<>();

            timegridUnitAsMap.put("day", day);
            timegridUnitAsMap.put("timeUnitObjects", timeUnits);

            return new JSONObject(timegridUnitAsMap).toString();
        }
    }

}
