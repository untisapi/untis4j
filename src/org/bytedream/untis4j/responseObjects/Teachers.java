package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Class to manage {@link TeacherObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Teachers extends NAILResponseList<Teachers.TeacherObject> {

    /**
     * Sorts the given teachers by all titles and returns the sorted teachers
     *
     * @param teachers teachers that should be sorted
     * @return the sorted teachers
     * @since 1.1
     */
    public static Teachers sortByTitle(Teachers teachers) {
        teachers.sortByTitle();
        return teachers;
    }

    /**
     * Sorts the given teachers by all fore names and returns the sorted teachers
     *
     * @param teachers teachers that should be sorted
     * @return the sorted teachers
     * @since 1.1
     */
    public static Teachers sortByForeName(Teachers teachers) {
        teachers.sortByForeName();
        return teachers;
    }

    /**
     * Sorts the given teachers by all full names and returns the sorted teachers
     *
     * @param teachers teachers that should be sorted
     * @return the sorted teachers
     * @since 1.1
     */
    public static Teachers sortByFullName(Teachers teachers) {
        teachers.sortByFullName();
        return teachers;
    }

    /**
     * Finds a teacher by its title name
     *
     * @param title title of his teacher you want to find
     * @return the subject
     * @since 1.0
     */
    public TeacherObject findByTitle(String title) {
        return this.stream().filter(subject -> subject.getTitle().equals(title)).findAny().orElse(null);
    }

    /**
     * Finds a teacher by his fore name
     *
     * @param foreName fore name of the teacher you want to find
     * @return the subject
     * @since 1.0
     */
    public TeacherObject findByForeName(String foreName) {
        return this.stream().filter(subject -> subject.getForename().equals(foreName)).findAny().orElse(null);
    }

    /**
     * Finds a teacher by his full name
     *
     * @param fullName full name of the teacher you want to find
     * @return the subject
     * @since 1.0
     */
    public TeacherObject findByFullName(String fullName) {
        return this.stream().filter(subject -> subject.getForename().equals(fullName)).findAny().orElse(null);
    }

    /**
     * Finds teachers that have the {@code title} or a part of it in their title
     *
     * @param title title of the teachers you want to search
     * @return {@link Teachers} with teachers that have the {@code title} or a part of it in their title
     * @since 1.0
     */
    public Teachers searchByTitle(String title) {
        Teachers teachers = new Teachers();

        this.stream().filter(subject -> subject.getTitle().contains(title)).forEach(teachers::add);

        return teachers;
    }

    /**
     * Finds teachers that have the {@code foreName} or a part of it in their fore name
     *
     * @param foreName fore name of the teachers you want to search
     * @return {@link Teachers} with teachers that have the {@code foreName} or a part of it in their fore name
     * @since 1.0
     */
    public Teachers searchByForeName(String foreName) {
        Teachers teachers = new Teachers();

        this.stream().filter(subject -> subject.getForename().contains(foreName)).forEach(teachers::add);

        return teachers;
    }

    /**
     * Finds teachers that have the {@code fullName} or a part of it in their full name
     *
     * @param fullName full name of the teachers you want to search
     * @return {@link Teachers} with teachers that have the {@code fullName} or a part of it in their full name
     * @since 1.0
     */
    public Teachers searchByFullName(String fullName) {
        Teachers teachers = new Teachers();

        this.stream().filter(subject -> subject.getForename().contains(fullName)).forEach(teachers::add);

        return teachers;
    }

    /**
     * Sorts the teachers by all titles
     *
     * @since 1.1
     */
    public void sortByTitle() {
        this.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
    }

    /**
     * Sorts the teachers by all fore names
     *
     * @since 1.1
     */
    public void sortByForeName() {
        this.sort((o1, o2) -> o1.getForename().compareToIgnoreCase(o2.getForename()));
    }

    /**
     * Sorts the teachers by all full names
     *
     * @since 1.1
     */
    public void sortByFullName() {
        this.sort((o1, o2) -> o1.getFullName().compareToIgnoreCase(o2.getFullName()));
    }

    /**
     * Class to get information about a teacher
     *
     * @version 1.0
     * @since 1.0
     */
    public static class TeacherObject extends NAILResponseObject {

        private final String title;
        private final String forename;
        private final String fullName;

        /**
         * Initialize the {@link TeacherObject} class
         *
         * @param name     name of the teacher
         * @param active   if the teacher is active
         * @param id       id of the teacher
         * @param title    title of the teacher
         * @param foreName fore name of the teacher
         * @param longName long name of the teacher
         * @since 1.0
         */
        public TeacherObject(String name, boolean active, int id, String title, String foreName, String longName) {
            super(name, active, id, longName);
            this.title = title;
            this.forename = foreName;

            fullName = title + foreName + longName;
        }

        /**
         * Returns the title of the teacher
         *
         * @return the title of the teacher
         * @since 1.0
         */
        public String getTitle() {
            return title;
        }

        /**
         * Returns the fore name of the teacher
         *
         * @return the fore name of the teacher
         * @since 1.0
         */
        public String getForename() {
            return forename;
        }

        /**
         * Returns the full name of the teacher
         *
         * @return the full name of the teacher
         * @since 1.0
         */
        public String getFullName() {
            return fullName;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, String> teacherAsMap = new HashMap<>();

            teacherAsMap.put("name", this.getName());
            teacherAsMap.put("isActive", String.valueOf(this.isActive()));
            teacherAsMap.put("id", String.valueOf(this.getId()));
            teacherAsMap.put("title", title);
            teacherAsMap.put("foreName", title);
            teacherAsMap.put("longName", this.getLongName());
            teacherAsMap.put("fullName", fullName);

            return new JSONObject(teacherAsMap).toString();
        }
    }

}
