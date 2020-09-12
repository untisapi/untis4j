package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    public static Subjects sortByForeColor(Subjects subjects) {
        subjects.sortByForeColor();
        return subjects;
    }

    /**
     * Sorts the given subjects by all back colors and returns the sorted subjects
     *
     * @param subjects subjects that should be sorted
     * @return the sorted subjects
     * @since 1.1
     */
    public static Subjects sortByBackColor(Subjects subjects) {
        subjects.sortByBackColor();
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
        return this.stream().filter(subjectObject -> subjectObject.getAlternateName().equals(alternateName)).findAny().orElse(null);
    }

    /**
     * Finds a subject by its back color
     *
     * @param backColor back color of the subject you want to find
     * @return the subject
     * @since 1.0
     */
    public SubjectObject findByBackColor(String backColor) {
        return this.stream().filter(subjectObject -> subjectObject.getBackColor().equals(backColor)).findAny().orElse(null);
    }

    /**
     * Finds a subject by its fore color
     *
     * @param foreColor fore color of the subject you want to find
     * @return the subject
     * @since 1.0
     */
    public SubjectObject findByForeColor(String foreColor) {
        return this.stream().filter(subjectObject -> subjectObject.getForeColor().equals(foreColor)).findAny().orElse(null);
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

        this.stream().filter(subjectObject -> subjectObject.getAlternateName().contains(alternateName)).forEach(subjects::add);

        return subjects;
    }

    /**
     * Finds subjects that have the {@code backColor} or a part of it in their back color
     *
     * @param backColor back color of the subjects you want to search
     * @return {@link Subjects} with subjects that have the {@code backColor} or a part of it in their back color
     * @since 1.0
     */
    public Subjects searchByBackColor(Color backColor) {
        Subjects subjects = new Subjects();

        this.stream().filter(subjectObject -> subjectObject.getBackColor().toString().contains(backColor.toString())).forEach(subjects::add);

        return subjects;
    }

    /**
     * Finds subjects that have the {@code foreColor} or a part of it in their fore color
     *
     * @param foreColor fore color of the subjects you want to search
     * @return {@link Subjects} with subjects that have the {@code foreColor} or a part of it in their fore color
     * @since 1.0
     */
    public Subjects searchByForeColor(Color foreColor) {
        Subjects subjects = new Subjects();

        this.stream().filter(subjectObject -> subjectObject.getBackColor().toString().contains(foreColor.toString())).forEach(subjects::add);

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
    public void sortByForeColor() {
        this.sort((o1, o2) -> o1.getAlternateName().compareToIgnoreCase(o2.getAlternateName()));
    }

    /**
     * Sorts the subjects by all back colors
     *
     * @since 1.1
     */
    public void sortByBackColor() {
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
    public ArrayList<Color> getBackColors() {
        ArrayList<Color> backColors = new ArrayList<>();

        this.stream().map(SubjectObject::getBackColor).forEach(backColors::add);

        return backColors;
    }

    /**
     * Returns all fore colors that are saved in the list
     *
     * @return all fore colors
     * @since 1.1
     */
    public ArrayList<Color> getForeColors() {
        ArrayList<Color> foreColors = new ArrayList<>();

        this.stream().map(SubjectObject::getForeColor).forEach(foreColors::add);

        return foreColors;
    }

    /**
     * Class to get information about a subject
     *
     * @version 1.1
     * @since 1.0
     */
    public static class SubjectObject extends NAILResponseObject {

        private final String alternateName;
        private final Color backColor;
        private final Color foreColor;

        /**
         * Initialize the {@link SubjectObject} class
         *
         * @param name          name of the subject
         * @param active        if the subject is active
         * @param id            id of the subject
         * @param longName      long name of the subject
         * @param alternateName alternate name for the subject
         * @param backColor     backColor
         * @param foreColor     foreColor
         * @since 1.0
         */
        public SubjectObject(String name, boolean active, int id, String longName, String alternateName, String backColor, String foreColor) {
            super(name, active, id, longName);
            this.alternateName = alternateName;
            this.backColor = Color.decode(backColor);
            this.foreColor = Color.decode(foreColor);
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
        public Color getBackColor() {
            return backColor;
        }

        /**
         * Returns the fore color
         *
         * @return fore color
         * @since 1.0
         */
        public Color getForeColor() {
            return foreColor;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> subjectAsMap = new HashMap<>();

            subjectAsMap.put("name", this.getName());
            subjectAsMap.put("isActive", this.isActive());
            subjectAsMap.put("id", this.getId());
            subjectAsMap.put("longName", this.getLongName());
            subjectAsMap.put("alternateName", alternateName);
            subjectAsMap.put("backColor", backColor);
            subjectAsMap.put("foreColor", foreColor);

            return new JSONObject(subjectAsMap).toString();
        }
    }

}
