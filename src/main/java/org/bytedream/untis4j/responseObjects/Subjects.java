package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage {@link SubjectObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Subjects extends NAILResponseList<Subjects.SubjectObject> {

    /**
     * Sorts the given subjects by all alternative names and returns the sorted subjects
     *
     * @param subjects subjects that should be sorted
     * @return the sorted subjects
     * @since 1.1
     */
    public static Subjects sortByAlternateName(Subjects subjects) {
        subjects.sortByAlternateName();
        return subjects;
    }

    /**
     * Sorts the given subjects by all fore colors and returns the sorted subjects
     *
     * @param subjects subjects that should be sorted
     * @return the sorted subjects
     * @since 1.1
     */
    public static Subjects sortByForeColorHex(Subjects subjects) {
        subjects.sortByForeColorHex();
        return subjects;
    }

    /**
     * Sorts the given subjects by all back colors and returns the sorted subjects
     *
     * @param subjects subjects that should be sorted
     * @return the sorted subjects
     * @since 1.1
     */
    public static Subjects sortByBackColorHex(Subjects subjects) {
        subjects.sortByBackColorHex();
        return subjects;
    }

    /**
     * Finds a subject by its alternative name
     *
     * @param alternateName alternative name of the subject you want to find
     * @return the subject
     * @since 1.0
     */
    public SubjectObject findByAlternateName(String alternateName) {
        return this.stream().filter(subjectObject -> subjectObject.getAlternateName().equalsIgnoreCase(alternateName.trim())).findAny().orElse(null);
    }

    /**
     * Finds a subject by its back color
     *
     * @param backColorHex back color of the subject you want to find
     * @return the subject
     * @since 1.0
     */
    public SubjectObject findByBackColorHex(String backColorHex) {
        return this.stream().filter(subjectObject -> subjectObject.getBackColorHex().equals(backColorHex)).findAny().orElse(null);
    }

    /**
     * Finds a subject by its fore color
     *
     * @param foreColorHex fore color of the subject you want to find
     * @return the subject
     * @since 1.0
     */
    public SubjectObject findByForeColorHex(String foreColorHex) {
        return this.stream().filter(subjectObject -> subjectObject.getForeColorHex().equals(foreColorHex)).findAny().orElse(null);
    }

    /**
     * Finds subjects that have the {@code alternateName} or a part of it in their alternative name
     *
     * @param alternateName alternative name of the subjects you want to search
     * @return {@link Subjects} with subjects that have the {@code alternateName} or a part of it in their alternative name
     * @since 1.0
     */
    public Subjects searchByAlternateName(String alternateName) {
        Subjects subjects = new Subjects();

        this.stream().filter(subjectObject -> subjectObject.getAlternateName().toLowerCase().contains(alternateName.trim().toLowerCase())).forEach(subjects::add);

        return subjects;
    }

    /**
     * Finds subjects that have the {@code backColorHex} or a part of it in their back color
     *
     * @param backColorHex back color of the subjects you want to search
     * @return {@link Subjects} with subjects that have the {@code backColorHex} or a part of it in their back color
     * @since 1.0
     */
    public Subjects searchByBackColorHex(String backColorHex) {
        Subjects subjects = new Subjects();

        this.stream().filter(subjectObject -> subjectObject.getBackColorHex().equals(backColorHex)).forEach(subjects::add);

        return subjects;
    }

    /**
     * Finds subjects that have the {@code foreColorHex} or a part of it in their fore color
     *
     * @param foreColorHex fore color of the subjects you want to search
     * @return {@link Subjects} with subjects that have the {@code foreColorHex} or a part of it in their fore color
     * @since 1.0
     */
    public Subjects searchByForeColorHex(String foreColorHex) {
        Subjects subjects = new Subjects();

        this.stream().filter(subjectObject -> subjectObject.getBackColorHex().equals(foreColorHex)).forEach(subjects::add);

        return subjects;
    }

    /**
     * Sorts the subjects by all alternative names
     *
     * @since 1.1
     */
    public void sortByAlternateName() {
        this.sort((o1, o2) -> o1.getAlternateName().compareToIgnoreCase(o2.getAlternateName()));
    }

    /**
     * Sorts the subjects by all fore colors
     *
     * @since 1.1
     */
    public void sortByForeColorHex() {
        this.sort((o1, o2) -> o1.getAlternateName().compareToIgnoreCase(o2.getAlternateName()));
    }

    /**
     * Sorts the subjects by all back colors
     *
     * @since 1.1
     */
    public void sortByBackColorHex() {
        this.sort((o1, o2) -> o1.getAlternateName().compareToIgnoreCase(o2.getAlternateName()));
    }

    /**
     * Returns all alternate names that are saved in the list
     *
     * @return all alternate names
     * @since 1.1
     */
    public ArrayList<String> getAlternateNames() {
        ArrayList<String> alternateNames = new ArrayList<>();

        this.stream().map(SubjectObject::getAlternateName).forEach(alternateNames::add);

        return alternateNames;
    }

    /**
     * Returns all back colors that are saved in the list
     *
     * @return all back colors
     * @since 1.1
     */
    public ArrayList<String> getBackColorHex() {
        ArrayList<String> backColorHex = new ArrayList<>();

        this.stream().map(SubjectObject::getBackColorHex).forEach(backColorHex::add);

        return backColorHex;
    }

    /**
     * Returns all fore colors that are saved in the list
     *
     * @return all fore colors
     * @since 1.1
     */
    public ArrayList<String> getForeColorHex() {
        ArrayList<String> foreColorHex = new ArrayList<>();

        this.stream().map(SubjectObject::getForeColorHex).forEach(foreColorHex::add);

        return foreColorHex;
    }

    /**
     * Class to get information about a subject
     *
     * @version 1.1
     * @since 1.0
     */
    public static class SubjectObject extends NAILResponseObject {

        private final String alternateName;
        private final String backColorHex;
        private final String foreColorHex;

        /**
         * Initialize the {@link SubjectObject} class
         *
         * @param name          name of the subject
         * @param active        if the subject is active
         * @param id            id of the subject
         * @param longName      long name of the subject
         * @param alternateName alternate name for the subject
         * @param backColorHex     backColorHex
         * @param foreColorHex     foreColorHex
         * @since 1.0
         */
        public SubjectObject(String name, boolean active, int id, String longName, String alternateName, String backColorHex, String foreColorHex) {
            super(name, active, id, longName);
            this.alternateName = alternateName;
            this.backColorHex = "#" + backColorHex;
            this.foreColorHex = "#" + foreColorHex;
        }

        /**
         * Returns the alternate name for the subject
         *
         * @return the alternate name for the subject
         * @since 1.0
         */
        public String getAlternateName() {
            return alternateName;
        }

        /**
         * Returns the back color
         *
         * @return the back color
         * @since 1.0
         */
        public String getBackColorHex() {
            return backColorHex;
        }

        /**
         * Returns the fore color
         *
         * @return fore color
         * @since 1.0
         */
        public String getForeColorHex() {
            return foreColorHex;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            Map<String, Object> subjectAsMap = new HashMap<>();
            subjectAsMap.put("name", this.getName());
            subjectAsMap.put("isActive", this.isActive());
            subjectAsMap.put("id", this.getId());
            subjectAsMap.put("longName", this.getLongName());
            subjectAsMap.put("alternateName", alternateName);
            subjectAsMap.put("backColorHex", backColorHex);
            subjectAsMap.put("foreColorHex", foreColorHex);
            return new JSONObject(subjectAsMap).toString();
        }
    }

}