package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.UntisUtils;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage {@link Lesson} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Timetable extends ResponseList<Timetable.Lesson> {

    /**
     * Sorts the given timetable by all dates and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByDate(Timetable timetable) {
        timetable.sortByDate();
        return timetable;
    }

    /**
     * Sorts the given timetable by all start times and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByStartTime(Timetable timetable) {
        timetable.sortByStartTime();
        return timetable;
    }

    /**
     * Sorts the given timetable by all end times and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByEndTime(Timetable timetable) {
        timetable.sortByEndTime();
        return timetable;
    }

    /**
     * Sorts the given timetable by all time unit object and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByTimeUnits(Timetable timetable) {
        timetable.sortByTimeUnitObjects();
        return timetable;
    }

    /**
     * Sorts the given timetable by all classes and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByClasses(Timetable timetable) {
        timetable.sortByClasses();
        return timetable;
    }

    /**
     * Sorts the given timetable by all teachers and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByTeachers(Timetable timetable) {
        timetable.sortByTeachers();
        return timetable;
    }

    /**
     * Sorts the given timetable by all rooms and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByRooms(Timetable timetable) {
        timetable.sortByRooms();
        return timetable;
    }

    /**
     * Sorts the given timetable by all subjects and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortBySubjects(Timetable timetable) {
        timetable.sortBySubjects();
        return timetable;
    }

    /**
     * Sorts the given timetable by all codes and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByCode(Timetable timetable) {
        timetable.sortByCode();
        return timetable;
    }

    /**
     * Sorts the given timetable by all activity types and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByActivityType(Timetable timetable) {
        timetable.sortByActivityType();
        return timetable;
    }

    /**
     * Finds a timetable by its date (basically returns itself, if the date is correct)
     *
     * @param date date of the timetable you want to find
     * @return the timetable
     * @since 1.0
     */
    public Timetable findByDate(LocalDate date) {
        if (this.stream().allMatch(lesson -> lesson.getDate().equals(date))) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Finds a lesson by its start time
     *
     * @param startTime start time of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByStartTime(LocalTime startTime) {
        return this.stream().filter(lesson -> lesson.getStartTime().equals(startTime)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its end time
     *
     * @param endTime end time of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByEndTime(LocalTime endTime) {
        return this.stream().filter(lesson -> lesson.getEndTime().equals(endTime)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its time unit object
     *
     * @param timeUnitObject time unit object of the lesson you want to find
     * @return the lesson
     * @since 1.1
     */
    public Lesson findByTimeUnitObject(TimeUnits.TimeUnitObject timeUnitObject) {
        return this.stream().filter(lesson -> lesson.getTimeUnitObject().equals(timeUnitObject)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its classes ids
     *
     * @param classes classes of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByClasses(Classes classes) {
        return this.stream().filter(lesson -> lesson.getClasses().containsAll(classes)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its teacher ids
     *
     * @param teachers teachers of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByTeachers(Teachers teachers) {
        return this.stream().filter(lesson -> lesson.getTeachers().containsAll(teachers)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its room ids
     *
     * @param rooms rooms of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByRooms(Rooms rooms) {
        return this.stream().filter(lesson -> lesson.getRooms().containsAll(rooms)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its subject ids
     *
     * @param subjects subjects of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findBySubjects(Subjects subjects) {
        return this.stream().filter(lesson -> lesson.getSubjects().containsAll(subjects)).findAny().orElse(null);
    }

    /**
     * Returns a timetable, containing all lessons that have {@code code} as its code
     *
     * @param code code of the lessons you want find
     * @return the timetable
     * @since 1.0
     */
    public Timetable findByCode(UntisUtils.LessonCode code) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getCode().equals(code)).forEach(timetable::add);

        return timetable;
    }

    /**
     * Returns a timetable, containing all lessons that have {@code activityType} as its activityType
     *
     * @param activityType activity type of the lessons you want find
     * @return the timetable
     * @since 1.0
     */
    public Timetable findByActivityType(String activityType) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getActivityType().equalsIgnoreCase(activityType.trim())).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code date} or a part of it in their date
     *
     * @param date date of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code date} or a part of it in their date
     * @since 1.0
     */
    public Timetable searchByDate(LocalDate date) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")).contains(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")))).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code startTime} or a part of it in their start time
     *
     * @param startTime start time of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code startTime} or a part of it in their start time
     * @since 1.0
     */
    public Timetable searchByStartTime(LocalTime startTime) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getStartTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(startTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code endTime} or a part of it in their end time
     *
     * @param endTime end time of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code endTime} or a part of it in their end time
     * @since 1.0
     */
    public Timetable searchByEndTime(LocalTime endTime) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getEndTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(endTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code timeUnitObject} or a part of it in their time unit object
     *
     * @param timeUnitObject time unit object of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code endTime} or a part of it in their end time
     * @since 1.1
     */
    public Timetable searchByTimeUnits(TimeUnits.TimeUnitObject timeUnitObject) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getTimeUnitObject().equals(timeUnitObject)).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code classesIds} or a part of it in their classes
     *
     * @param classes classes of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code classesIds} or a part of it in their classes ids
     * @since 1.0
     */
    public Timetable searchByClasses(Classes classes) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (lesson.getClasses().containsAll(classes)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code teacherIds} or a part of it in their teachers
     *
     * @param teachers teachers of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code teacherIds} or a part of it in their teacher ids
     * @since 1.0
     */
    public Timetable searchByTeachers(Teachers teachers) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (lesson.getTeachers().containsAll(teachers)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code roomIds} or a part of it in their rooms
     *
     * @param rooms rooms of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code roomIds} or a part of it in their room ids
     * @since 1.0
     */
    public Timetable searchByRooms(Rooms rooms) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (lesson.getRooms().containsAll(rooms)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code subjectIds} or a part of it in their subjects
     *
     * @param subjects subjects of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code subjectIds} or a part of it in their subject ids
     * @since 1.0
     */
    public Timetable searchBySubjects(Subjects subjects) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (lesson.getSubjects().containsAll(subjects)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code code} it in their code
     *
     * @param code code of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code code} in their code
     * @since 1.0
     */
    public Timetable searchByCode(UntisUtils.LessonCode code) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getCode().equals(code)).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code activityType} it in their activity type
     *
     * @param activityType activity type of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code activityType} in their activity type
     * @since 1.0
     */
    public Timetable searchByActivityType(String activityType) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getActivityType().toLowerCase().contains(activityType.trim().toLowerCase())).forEach(timetable::add);

        return timetable;
    }

    /**
     * Sorts the timetable by all dates
     *
     * @since 1.1
     */
    public void sortByDate() {
        this.sort(Comparator.comparing(Lesson::getDate));
    }

    /**
     * Sorts the timetable by all start times
     *
     * @since 1.1
     */
    public void sortByStartTime() {
        this.sort(Comparator.comparing(Lesson::getStartTime));
    }

    /**
     * Sorts the timetable by all end times
     *
     * @since 1.1
     */
    public void sortByEndTime() {
        this.sort(Comparator.comparing(Lesson::getEndTime));
    }

    /**
     * Sorts the timetable by all time unit objects
     *
     * @since 1.1
     */
    public void sortByTimeUnitObjects() {
        this.sort(Comparator.comparing(o -> o.getTimeUnitObject().getStartTime()));
    }

    /**
     * Sorts the timetable by all classes
     *
     * @since 1.1
     */
    public void sortByClasses() {
        this.sort((o1, o2) -> {
            Classes o1Classes = o1.getClasses();
            Classes o2Classes = o2.getClasses();

            int o1Index = 0;
            int o2Index = 0;

            if (o1Classes.size() != 0) {
                o1Index = o1Classes.get(0).getId();
            }
            if (o2Classes.size() != 0) {
                o2Index = o2Classes.get(0).getId();
            }

            return o1Index - o2Index;
        });
    }

    /**
     * Sorts the timetable by all teachers
     *
     * @since 1.1
     */
    public void sortByTeachers() {
        this.sort((o1, o2) -> {
            Teachers o1Teachers = o1.getTeachers();
            Teachers o2Teachers = o2.getTeachers();

            int o1Index = 0;
            int o2Index = 0;

            if (o1Teachers.size() != 0) {
                o1Index = o1Teachers.get(0).getId();
            }
            if (o2Teachers.size() != 0) {
                o2Index = o2Teachers.get(0).getId();
            }

            return o1Index - o2Index;
        });
    }

    /**
     * Sorts the timetable by all rooms
     *
     * @since 1.1
     */
    public void sortByRooms() {
        this.sort((o1, o2) -> {
            Teachers o1Teachers = o1.getTeachers();
            Teachers o2Teachers = o2.getTeachers();

            int o1Index = 0;
            int o2Index = 0;

            if (o1Teachers.size() != 0) {
                o1Index = o1Teachers.get(0).getId();
            }
            if (o2Teachers.size() != 0) {
                o2Index = o2Teachers.get(0).getId();
            }

            return o1Index - o2Index;
        });
    }

    /**
     * Sorts the timetable by all subjects
     *
     * @since 1.1
     */
    public void sortBySubjects() {
        this.sort((o1, o2) -> {
            Subjects o1Subjects = o1.getSubjects();
            Subjects o2Subjects = o2.getSubjects();

            int o1Index = 0;
            int o2Index = 0;

            if (o1Subjects.size() != 0) {
                o1Index = o1Subjects.get(0).getId();
            }
            if (o2Subjects.size() != 0) {
                o2Index = o2Subjects.get(0).getId();
            }

            return o1Index - o2Index;
        });
    }

    /**
     * Sorts the timetable by all codes
     *
     * @since 1.1
     */
    public void sortByCode() {
        this.sort(Comparator.comparing(Lesson::getCode));
    }

    /**
     * Sorts the timetable by all activity types
     *
     * @since 1.1
     */
    public void sortByActivityType() {
        this.sort((o1, o2) -> o1.getActivityType().compareToIgnoreCase(o2.getActivityType()));
    }

    /**
     * Returns all dates that are saved in the lessons
     *
     * @return all dates
     * @since 1.1
     */
    public ArrayList<LocalDate> getDates() {
        ArrayList<LocalDate> dates = new ArrayList<>();

        this.stream().map(Lesson::getDate).forEach(dates::add);

        return dates;
    }

    /**
     * Returns all start times that are saved in the lessons
     *
     * @return all start times
     * @since 1.1
     */
    public ArrayList<LocalTime> getStartTimes() {
        ArrayList<LocalTime> startTimes = new ArrayList<>();

        this.stream().map(Lesson::getStartTime).forEach(startTimes::add);

        return startTimes;
    }

    /**
     * Returns all end times that are saved in the lessons
     *
     * @return all end times
     * @since 1.1
     */
    public ArrayList<LocalTime> getEndTimes() {
        ArrayList<LocalTime> endTimes = new ArrayList<>();

        this.stream().map(Lesson::getEndTime).forEach(endTimes::add);

        return endTimes;
    }

    /**
     * Returns all time units that are saved in the lessons
     *
     * @return all time units
     * @since 1.1
     */
    public TimeUnits getTimeUnits() {
        TimeUnits timeUnits = new TimeUnits();

        this.stream().map(Lesson::getTimeUnitObject).forEach(timeUnits::add);

        return timeUnits;
    }

    /**
     * Returns all classes that are saved in the lessons
     *
     * @return all classes
     * @since 1.1
     */
    public Classes getClasses() {
        Classes classes = new Classes();

        this.stream().map(Lesson::getClasses).forEach(classes::addAll);

        return classes;
    }

    /**
     * Returns all teachers that are saved in the lessons
     *
     * @return all teachers
     * @since 1.1
     */
    public Teachers getTeachers() {
        Teachers teachers = new Teachers();

        this.stream().map(Lesson::getTeachers).forEach(teachers::addAll);

        return teachers;
    }

    /**
     * Returns all rooms that are saved in the lessons
     *
     * @return all rooms
     * @since 1.1
     */
    public Rooms getRooms() {
        Rooms rooms = new Rooms();

        this.stream().map(Lesson::getRooms).forEach(rooms::addAll);

        return rooms;
    }

    /**
     * Returns all subjects that are saved in the lessons
     *
     * @return all subjects
     * @since 1.1
     */
    public Subjects getSubjects() {
        Subjects subjects = new Subjects();

        this.stream().map(Lesson::getSubjects).forEach(subjects::addAll);

        return subjects;
    }

    /**
     * Returns all lesson codes that are saved in the lessons
     *
     * @return all lesson codes
     * @since 1.1
     */
    public ArrayList<UntisUtils.LessonCode> getLessonCodes() {
        ArrayList<UntisUtils.LessonCode> codes = new ArrayList<>();

        this.stream().map(Lesson::getCode).forEach(codes::add);

        return codes;
    }

    /**
     * Returns all activity types that are saved in the lessons
     *
     * @return all activity types
     * @since 1.1
     */
    public ArrayList<String> getActivityTypes() {
        ArrayList<String> activityTypes = new ArrayList<>();

        this.stream().map(Lesson::getActivityType).forEach(activityTypes::add);

        return activityTypes;
    }

    public ArrayList<String> getInfos() {
        ArrayList<String> infos = new ArrayList<>();

        this.stream().map(Lesson::getInfo).forEach(infos::add);

        return infos;
    }

    public ArrayList<String> getSubstTexts() {
        ArrayList<String> substText = new ArrayList<>();

        this.stream().map(Lesson::getSubstText).forEach(substText::add);

        return substText;
    }

    public ArrayList<String> getLsTexts() {
        ArrayList<String> lsTexts = new ArrayList<>();

        this.stream().map(Lesson::getLsText).forEach(lsTexts::add);

        return lsTexts;
    }

    public ArrayList<Integer> getLsNumbers() {
        ArrayList<Integer> lsNumbers = new ArrayList<>();

        this.stream().map(Lesson::getLsNumber).forEach(lsNumbers::add);

        return lsNumbers;
    }
    public ArrayList<String> getStudentGroups() {
        ArrayList<String> studentGroups = new ArrayList<>();

        this.stream().map(Lesson::getStudentGroup).forEach(studentGroups::add);

        return studentGroups;
    }

    /**
     * Class to get information about a lesson
     *
     * @version 1.0
     * @since 1.0
     */
    public static class Lesson extends ResponseObject {

        private final LocalDate date;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final TimeUnits.TimeUnitObject timeUnitObject;
        private final Classes classes;
        private final Classes originalClasses;
        private final Teachers teachers;
        private final Teachers originalTeachers;
        private final Rooms rooms;
        private final Rooms originalRooms;
        private final Subjects subjects;
        private final Subjects originalSubjects;
        private final UntisUtils.LessonCode code;
        private final String activityType;
        private final String info;
        private final String substText;
        private final String lsText;
        private final Integer lsNumber;
        private final String studentGroup;

        /**
         * Initialize the {@link Lesson} class
         *
         * @param date              date of the timetable
         * @param startTime         time when the lesson start
         * @param endTime           time when the lesson end
         * @param timeUnitObject    the {@link org.bytedream.untis4j.responseObjects.TimeUnits.TimeUnitObject}
         * @param classes           classes
         * @param originalClasses   the original classes who are represented, if any
         * @param teachers          teachers
         * @param originalTeachers  the original teachers who are represented, if any
         * @param rooms             rooms
         * @param originalRooms     the original rooms if the class was moved to another room
         * @param subjects          subjects
         * @param originalSubjects  the original subjects if the subject has changed
         * @param code              code of the lesson (normally null, {@link UntisUtils.LessonCode#CANCELLED} if the lesson is cancelled, {@code UntisUtils.LessonCode.IRREGULAR} if e.g. a lesson has been moved
         * @param activityType      type of the lesson
         * @param info              info text of the lesson
         * @param substText         substitution text
         * @param lsText            ls text of the lesson
         * @param lsNumber          ls Number of the lesson
         * @param studentGroup      name of the studentGroup
         * @since 1.0
         */
        public Lesson(LocalDate date,
                      LocalTime startTime,
                      LocalTime endTime,
                      TimeUnits.TimeUnitObject timeUnitObject,
                      Classes classes,
                      Classes originalClasses,
                      Teachers teachers,
                      Teachers originalTeachers,
                      Rooms rooms,
                      Rooms originalRooms,
                      Subjects subjects,
                      Subjects originalSubjects,
                      UntisUtils.LessonCode code,
                      String activityType,
                      String info,
                      String substText,
                      String lsText,
                      Integer lsNumber,
                      String studentGroup) {
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.timeUnitObject = timeUnitObject;
            this.classes = classes;
            this.originalClasses = originalClasses;
            this.teachers = teachers;
            this.originalTeachers = originalTeachers;
            this.rooms = rooms;
            this.originalRooms = originalRooms;
            this.subjects = subjects;
            this.originalSubjects = originalSubjects;
            this.code = code;
            this.activityType = activityType;
            this.info = info;
            this.substText = substText;
            this.lsText = lsText;
            this.lsNumber = lsNumber;
            this.studentGroup = studentGroup;
        }

        /**
         * Returns the date of the lesson
         *
         * @return the date of the lesson
         * @since 1.0
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Returns the start time of the lesson
         *
         * @return the start time of the lesson
         * @since 1.0
         */
        public LocalTime getStartTime() {
            return startTime;
        }

        /**
         * Returns the end time of the lesson
         *
         * @return the end time of the lesson
         * @since 1.0
         */
        public LocalTime getEndTime() {
            return endTime;
        }

        /**
         * Returns the time unit
         *
         * @return the time unit
         * @since 1.1
         */
        public TimeUnits.TimeUnitObject getTimeUnitObject() {
            return timeUnitObject;
        }

        /**
         * Returns the classes that have this lesson
         *
         * @return the classes that have this lesson
         * @since 1.0
         */
        public Classes getClasses() {
            return classes;
        }

        /**
         * @return if any changes were made to the lesson, like teachers or rooms have been changed
         * @since 1.4
         */
        public boolean hasChanges() {
            return originalClasses.size() > 0 || originalTeachers.size() > 0 || originalRooms.size() > 0 || originalSubjects.size() > 0;
        }

        /**
         * @return the original classes if the classes have changed
         * @since 1.4
         */
        public Classes getOriginalClasses() {
            return originalClasses;
        }

        /**
         * Returns the teachers that have this lesson
         *
         * @return the teachers that have this lesson
         * @since 1.0
         */
        public Teachers getTeachers() {
            return teachers;
        }

        /**
         * @return  the original teachers who are represented, if any
         * @since 1.4
         */
        public Teachers getOriginalTeachers() {
            return originalTeachers;
        }

        /**
         * Returns the id of the rooms where the lesson is
         *
         * @return the id of the rooms where the lesson is
         * @since 1.0
         */
        public Rooms getRooms() {
            return rooms;
        }

        /**
         * @return the original rooms if the class was moved to another room
         * @since 1.4
         */
        public Rooms getOriginalRooms() {
            return originalRooms;
        }

        /**
         * Returns the lesson subjects
         *
         * @return the lesson subjects
         * @since 1.0
         */
        public Subjects getSubjects() {
            return subjects;
        }

        /**
         * @return the original subjects if the subject has changed
         * @since 1.4
         */
        public Subjects getOriginalSubjects() {
            return originalSubjects;
        }

        /**
         * Returns the lesson code (if cancelled the lesson is cancelled, if irregular the something irregular is with this lesson (it got moved or something)
         *
         * @return the lesson code
         * @since 1.0
         */
        public UntisUtils.LessonCode getCode() {
            return code;
        }

        /**
         * Returns the activity type
         *
         * @return the activity type
         * @since 1.0
         */
        public String getActivityType() {
            return activityType;
        }

        public String getInfo() {
            return info;
        }
        public String getSubstText() {
            return substText;
        }
        public String getLsText() {
            return lsText;
        }
        public Integer getLsNumber() {
            return lsNumber;
        }
        public String getStudentGroup() {
            return studentGroup;
        }
        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            Map<String, Object> classAsMap = new HashMap<>();
            classAsMap.put("date", date);
            classAsMap.put("classes", classes);
            classAsMap.put("hasChanges", hasChanges());
            classAsMap.put("originalClasses", originalClasses);
            classAsMap.put("teachers", teachers);
            classAsMap.put("originalTeachers", originalTeachers);
            classAsMap.put("rooms", rooms);
            classAsMap.put("originalRooms", originalRooms);
            classAsMap.put("subjects", subjects);
            classAsMap.put("originalSubjects", originalSubjects);
            classAsMap.put("code", code);
            classAsMap.put("startTime", startTime.format(DateTimeFormatter.ofPattern("HHmm")));
            classAsMap.put("endTime", endTime.format(DateTimeFormatter.ofPattern("HHmm")));
            classAsMap.put("activityType", activityType);
            classAsMap.put("info", info);
            classAsMap.put("substText", substText);
            classAsMap.put("lsText", lsText);
            classAsMap.put("lsNumber", lsNumber);
            classAsMap.put("studentGroup", studentGroup);
            return new JSONObject(classAsMap).toString();
        }
    }

}
