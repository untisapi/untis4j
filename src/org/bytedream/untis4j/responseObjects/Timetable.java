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
     * Sorts the given timetable by all klassen and returns the sorted timetable
     *
     * @param timetable timetable that should be sorted
     * @return the sorted timetable
     * @since 1.1
     */
    public static Timetable sortByKlassen(Timetable timetable) {
        timetable.sortByKlassen();
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
     * Finds a lesson by its klassen ids
     *
     * @param klassen klassen of the lesson you want to find
     * @return the lesson
     * @since 1.0
     */
    public Lesson findByKlassen(Klassen klassen) {
        return this.stream().filter(lesson -> lesson.getKlassen().containsAll(klassen)).findAny().orElse(null);
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

        this.stream().filter(lesson -> lesson.getActivityType().equals(activityType)).forEach(timetable::add);

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

        this.stream().filter(lesson -> lesson.getStartTime().format(DateTimeFormatter.ofPattern("HHmmss")).contains(endTime.format(DateTimeFormatter.ofPattern("HHmmss")))).forEach(timetable::add);

        return timetable;
    }

    /**
     * Finds lessons that have the {@code klassenIds} or a part of it in their klassen ids
     *
     * @param klassen klassen of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code klassenIds} or a part of it in their klassen ids
     * @since 1.0
     */
    public Timetable searchByKlassen(Klassen klassen) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (lesson.getKlassen().containsAll(klassen)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code teacherIds} or a part of it in their teacher ids
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
     * Finds lessons that have the {@code roomIds} or a part of it in their room ids
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
     * Finds lessons that have the {@code subjectIds} or a part of it in their subject ids
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

        this.stream().filter(lesson -> lesson.getActivityType().equals(activityType)).forEach(timetable::add);

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
     * Sorts the timetable by all klassen
     *
     * @since 1.1
     */
    public void sortByKlassen() {
        this.sort((o1, o2) -> {
            Klassen o1Klassen = o1.getKlassen();
            Klassen o2Klassen = o2.getKlassen();

            int o1Index = 0;
            int o2Index = 0;

            if (o1Klassen.size() != 0) {
                o1Index = o1Klassen.get(0).getId();
            }
            if (o2Klassen.size() != 0) {
                o2Index = o2Klassen.get(0).getId();
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

        this.stream().map(Lesson::getStartTime).forEach(endTimes::add);

        return endTimes;
    }

    /**
     * Returns all klassen that are saved in the lessons
     *
     * @return all klassen
     * @since 1.1
     */
    public Klassen getKlassen() {
        Klassen klassen = new Klassen();

        this.stream().map(Lesson::getKlassen).forEach(klassen::addAll);

        return klassen;
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
        private final Klassen klassen;
        private final Teachers teachers;
        private final Rooms rooms;
        private final Subjects subjects;
        private final UntisUtils.LessonCode code;
        private final String activityType;

        /**
         * Initialize the {@link Lesson} class
         *
         * @param date         date of the timetable
         * @param startTime    time when the lesson start
         * @param endTime      time when the lesson end
         * @param klassen      klassen
         * @param teachers     teachers
         * @param rooms        rooms
         * @param subjects     subjects
         * @param code         code of the lesson (normally null, {@link UntisUtils.LessonCode#CANCELLED} if the lesson is cancelled, {@code UntisUtils.LessonCode.IRREGULAR} if e.g. a lesson has been moved
         * @param activityType type of the lesson
         * @since 1.0
         */
        public Lesson(LocalDate date,
                      LocalTime startTime,
                      LocalTime endTime,
                      Klassen klassen,
                      Teachers teachers,
                      Rooms rooms,
                      Subjects subjects,
                      UntisUtils.LessonCode code,
                      String activityType) {
            this.date = date;
            this.klassen = klassen;
            this.teachers = teachers;
            this.rooms = rooms;
            this.subjects = subjects;
            this.code = code;
            this.startTime = startTime;
            this.endTime = endTime;
            this.activityType = activityType;
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
         * Returns the klassen that have this lesson
         *
         * @return the klassen that have this lesson
         * @since 1.0
         */
        public Klassen getKlassen() {
            return klassen;
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
         * Returns the id of the rooms where the lesson is
         *
         * @return the id of the rooms where the lesson is
         * @since 1.0
         */
        public Rooms getRooms() {
            return rooms;
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

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> klasseAsMap = new HashMap<>();

            klasseAsMap.put("date", date);
            klasseAsMap.put("klassen", klassen);
            klasseAsMap.put("teachers", teachers);
            klasseAsMap.put("rooms", rooms);
            klasseAsMap.put("subjects", subjects);
            klasseAsMap.put("code", code);
            klasseAsMap.put("startTime", startTime.format(DateTimeFormatter.ofPattern("HHmm")));
            klasseAsMap.put("endTime", endTime.format(DateTimeFormatter.ofPattern("HHmm")));
            klasseAsMap.put("activityType", activityType);

            return new JSONObject(klasseAsMap).toString();
        }
    }

}
