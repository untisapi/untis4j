package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NILResponseObject;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class to manage {@link HolidaysObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Holidays extends NILResponseList<Holidays.HolidaysObject> {

    /**
     * Finds a holidays by its start date
     *
     * @param startDate start date of the holidays you want to find
     * @return the holidays
     *
     * @since 1.0
     */
    public HolidaysObject findByStartDate(LocalDate startDate) {
        return this.stream().filter(holidaysObject -> holidaysObject.getStartDate().isEqual(startDate)).findAny().orElse(null);
    }

    /**
     * Finds holidays by its end date
     *
     * @param endDate end date of the holidays you want to find
     * @return the holidays
     *
     * @since 1.0
     */
    public HolidaysObject findByEndDate(LocalDate endDate) {
        return this.stream().filter(holidaysObject -> holidaysObject.getStartDate().isEqual(endDate)).findAny().orElse(null);
    }

    /**
     * Finds holidays that have the {@code startDate} or a part of it in their start date
     *
     * @param startDate start date of the holidays you want to search
     * @return {@link Holidays} with holidays that have the {@code startDate} or a part of it in their start date
     *
     * @since 1.0
     */
    public Holidays searchByStartDate(LocalDate startDate) {
        Holidays holidays = new Holidays();

        this.stream().filter(holidaysObject -> holidaysObject.getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).contains(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))).forEach(holidays::add);

        return holidays;
    }

    /**
     * Finds holidays that have the {@code endDate} or a part of it in their end date
     *
     * @param endDate end date of the holidays you want to search
     * @return {@link Holidays} with holidays that have the {@code endDate} or a part of it in their end date
     *
     * @since 1.0
     */
    public Holidays searchByEndDate(LocalDate endDate) {
        Holidays holidays = new Holidays();

        this.stream().filter(schoolYearObject -> schoolYearObject.getEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).contains(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))).forEach(holidays::add);

        return holidays;
    }

    /**
     * Sorts the holidays by all start dates
     *
     * @since 1.1
     */
    public void sortByStartDate() {
        this.sort(Comparator.comparing(HolidaysObject::getStartDate));
    }

    /**
     * Sorts the holidays by all end dates
     *
     * @since 1.1
     */
    public void sortByEndDate() {
        this.sort(Comparator.comparing(HolidaysObject::getEndDate));
    }

    /**
     * Sorts the given holidays by all start dates and returns the sorted holidays
     *
     * @param holidays holidays that should be sorted
     * @return the sorted holidays
     *
     * @since 1.1
     */
    public static Holidays sortByStartDate(Holidays holidays) {
        holidays.sortByStartDate();
        return holidays;
    }

    /**
     * Sorts the given holidays by all end dates and returns the sorted holidays
     *
     * @param holidays holidays that should be sorted
     * @return the sorted holidays
     *
     * @since 1.1
     */
    public static Holidays sortByEndDate(Holidays holidays) {
        holidays.sortByEndDate();
        return holidays;
    }

    /**
     * Class to get information about holidays
     *
     * @version 1.0
     * @since 1.0
     */
    public static class HolidaysObject extends NILResponseObject {

        private final LocalDate endDate;
        private final LocalDate startDate;

        /**
         * Initialize the {@link HolidaysObject} class
         *
         * @param endDate end of the holidays
         * @param name name of the teacher
         * @param id id of the teacher
         * @param startDate start of the holidays
         * @param longName long name of the teacher
         *
         * @since 1.0
         */
        public HolidaysObject(String name, LocalDate startDate, LocalDate endDate, int id, String longName) {
            super(name, id, longName);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        /**
         * Returns the start date of the holidays
         *
         * @return the start date of the holidays
         *
         * @since 1.0
         */
        public LocalDate getStartDate() {
            return startDate;
        }

        /**
         * Returns the end date of the holidays
         *
         * @return the end date of the holidays
         *
         * @since 1.0
         */
        public LocalDate getEndDate() {
            return endDate;
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
            HashMap<String, Object> holidaysAsMap = new HashMap<>();

            holidaysAsMap.put("name", this.getName());
            holidaysAsMap.put("startDate", startDate);
            holidaysAsMap.put("endDate", endDate);
            holidaysAsMap.put("id", this.getId());
            holidaysAsMap.put("longName", this.getLongName());

            return new JSONObject(holidaysAsMap).toString();
        }
    }

}
