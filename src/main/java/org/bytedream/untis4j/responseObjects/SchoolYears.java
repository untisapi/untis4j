package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class to manage {@link SchoolYearObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class SchoolYears extends ResponseList<SchoolYears.SchoolYearObject> {

    /**
     * Sorts the given school years by all names and returns the sorted school years
     *
     * @param schoolYears school years that should be sorted
     * @return the sorted school years
     * @since 1.1
     */
    public static SchoolYears sortByName(SchoolYears schoolYears) {
        schoolYears.sortByName();
        return schoolYears;
    }

    /**
     * Sorts the given school years by all start dates and returns the sorted school years
     *
     * @param schoolYears school years that should be sorted
     * @return the sorted school years
     * @since 1.1
     */
    public static SchoolYears sortByStartDate(SchoolYears schoolYears) {
        schoolYears.sortByStartDate();
        return schoolYears;
    }

    /**
     * Sorts the given school years by all end dates and returns the sorted school years
     *
     * @param schoolYears school years that should be sorted
     * @return the sorted school years
     * @since 1.1
     */
    public static SchoolYears sortByEndDate(SchoolYears schoolYears) {
        schoolYears.sortByEndDate();
        return schoolYears;
    }

    /**
     * Sorts the given school years by all ids and returns the sorted school years
     *
     * @param schoolYears school years that should be sorted
     * @return the sorted school years
     * @since 1.1
     */
    public static SchoolYears sortById(SchoolYears schoolYears) {
        schoolYears.sortById();
        return schoolYears;
    }

    /**
     * Finds a school year by its name
     *
     * @param name name of the school year you want to find
     * @return the school year
     * @since 1.0
     */
    public SchoolYearObject findByName(String name) {
        return this.stream().filter(schoolYearObject -> schoolYearObject.getName().equalsIgnoreCase(name.trim())).findAny().orElse(null);
    }

    /**
     * Finds a school year by its start date
     *
     * @param startDate start date of the school year you want to find
     * @return the school year
     * @since 1.0
     */
    public SchoolYearObject findByStartDate(LocalDate startDate) {
        return this.stream().filter(schoolYearObject -> schoolYearObject.getStartDate().isEqual(startDate)).findAny().orElse(null);
    }

    /**
     * Finds a school year by its end date
     *
     * @param endDate end date of the school year you want to find
     * @return the school year
     * @since 1.0
     */
    public SchoolYearObject findByEndDate(LocalDate endDate) {
        return this.stream().filter(schoolYearObject -> schoolYearObject.getStartDate().isEqual(endDate)).findAny().orElse(null);
    }

    /**
     * Finds a school year by its id
     *
     * @param id id of the school year you want to find
     * @return the school year
     * @since 1.0
     */
    public SchoolYearObject findById(int id) {
        return this.stream().filter(schoolYearObject -> schoolYearObject.getId() == id).findAny().orElse(null);
    }

    /**
     * Finds school years that have the {@code name} or a part of it in their name
     *
     * @param name name of the school years you want to search
     * @return {@link SchoolYears} with school years that have the {@code name} or a part of it in their name
     * @since 1.0
     */
    public SchoolYears searchByName(String name) {
        SchoolYears schoolYears = new SchoolYears();

        this.stream().filter(schoolYearObject -> schoolYearObject.getName().toLowerCase().contains(name.trim().toLowerCase())).forEach(schoolYears::add);

        return schoolYears;
    }

    /**
     * Finds school years that have the {@code startDate} or a part of it in their start date
     *
     * @param startDate start date of the school years you want to search
     * @return {@link SchoolYears} with school years that have the {@code startDate} or a part of it in their start date
     * @since 1.0
     */
    public SchoolYears searchByStartDate(LocalDate startDate) {
        SchoolYears schoolYears = new SchoolYears();

        this.stream().filter(schoolYearObject -> schoolYearObject.getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).contains(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))).forEach(schoolYears::add);

        return schoolYears;
    }

    /**
     * Finds school years that have the {@code endDate} or a part of it in their end date
     *
     * @param endDate end date of the school years you want to search
     * @return {@link SchoolYears} with school years that have the {@code endDate} or a part of it in their end date
     * @since 1.0
     */
    public SchoolYears searchByEndDate(LocalDate endDate) {
        SchoolYears schoolYears = new SchoolYears();

        this.stream().filter(schoolYearObject -> schoolYearObject.getEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).contains(endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))).forEach(schoolYears::add);

        return schoolYears;
    }

    /**
     * Finds school years that have the {@code id} or a part of it in their id
     *
     * @param id id of the school years you want to search
     * @return {@link SchoolYears} with school years that have the {@code id} or a part of it in their id
     * @since 1.0
     */
    public SchoolYears searchById(int id) {
        SchoolYears schoolYears = new SchoolYears();

        this.stream().filter(schoolYearObject -> String.valueOf(schoolYearObject.getId()).contains(String.valueOf(id))).forEach(schoolYears::add);

        return schoolYears;
    }

    public void sortByName() {
        this.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }

    /**
     * Sorts the school years by all start dates
     *
     * @since 1.1
     */
    public void sortByStartDate() {
        this.sort(Comparator.comparing(SchoolYearObject::getStartDate));
    }

    /**
     * Sorts the school years by all end dates
     *
     * @since 1.1
     */
    public void sortByEndDate() {
        this.sort(Comparator.comparing(SchoolYearObject::getEndDate));
    }

    /**
     * Sorts the list by ids
     *
     * @since 1.1
     */
    public void sortById() {
        this.sort(Comparator.comparingInt(SchoolYearObject::getId));
    }

    /**
     * Returns all names that are saved in the list
     *
     * @return all names
     * @since 1.1
     */
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        this.stream().map(SchoolYearObject::getName).forEach(names::add);

        return names;
    }

    /**
     * Returns all ids that are saved in the list
     *
     * @return all ids
     * @since 1.1
     */
    public ArrayList<Integer> getIds() {
        ArrayList<Integer> ids = new ArrayList<>();

        this.stream().map(SchoolYearObject::getId).forEach(ids::add);

        return ids;
    }

    /**
     * Returns all start dates that are saved in the list
     *
     * @return all start dates
     * @since 1.1
     */
    public ArrayList<LocalDate> getStartDates() {
        ArrayList<LocalDate> startDates = new ArrayList<>();

        this.stream().map(SchoolYearObject::getStartDate).forEach(startDates::add);

        return startDates;
    }

    /**
     * Returns all end dates that are saved in the list
     *
     * @return all end dates
     * @since 1.1
     */
    public ArrayList<LocalDate> getEndDates() {
        ArrayList<LocalDate> endDates = new ArrayList<>();

        this.stream().map(SchoolYearObject::getStartDate).forEach(endDates::add);

        return endDates;
    }

    /**
     * Class to get information about the school year
     *
     * @version 1.1
     * @since 1.0
     */
    public static class SchoolYearObject extends ResponseObject {

        private final String name;
        private final int id;
        private final LocalDate startDate;
        private final LocalDate endDate;

        /**
         * Initialize the {@link SchoolYearObject} class
         *
         * @param name      name of the school year
         * @param id        id of the school year
         * @param startDate date when the school year began
         * @param endDate   date when the school year will end
         * @since 1.0
         */
        public SchoolYearObject(String name, int id, LocalDate startDate, LocalDate endDate) {
            this.name = name;
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        /**
         * Returns the name of the school year
         *
         * @return the name of the school year
         * @since 1.0
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the id of the school year
         *
         * @return the id of the school year
         * @since 1.0
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the start day of the school year
         *
         * @return the start day of the school year
         * @since 1.0
         */
        public LocalDate getStartDate() {
            return startDate;
        }

        /**
         * Returns the end day of the school year
         *
         * @return the end day of the school year
         * @since 1.0
         */
        public LocalDate getEndDate() {
            return endDate;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> currentSchoolYearAsMap = new HashMap<>();

            currentSchoolYearAsMap.put("name", name);
            currentSchoolYearAsMap.put("id", id);
            currentSchoolYearAsMap.put("startDate", startDate);
            currentSchoolYearAsMap.put("endDate", endDate);

            return new JSONObject(currentSchoolYearAsMap).toString();
        }
    }

}

