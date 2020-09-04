package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.UntisUtils;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.ResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;

public class Timetable extends ResponseList<Timetable.Lesson> {

    /**
     * Finds a timetable by its date (basically returns itself, if the date is correct)
     *
     * @param date date of the timetable you want to find
     * @return the timetable
     *
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
     *
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
     *
     * @since 1.0
     */
    public Lesson findByEndTime(LocalTime endTime) {
        return this.stream().filter(lesson -> lesson.getEndTime().equals(endTime)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its klassen ids
     *
     * @param klassenIds klassen ids of the lesson you want to find
     * @return the lesson
     *
     * @since 1.0
     */
    public Lesson findByKlassenIds(Set<Integer> klassenIds) {
        return this.stream().filter(lesson -> lesson.getKlassenIds().containsAll(klassenIds)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its teacher ids
     *
     * @param teacherIds teacher ids of the lesson you want to find
     * @return the lesson
     *
     * @since 1.0
     */
    public Lesson findByTeacherIds(Set<Integer> teacherIds) {
        return this.stream().filter(lesson -> lesson.getTeacherIds().containsAll(teacherIds)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its room ids
     *
     * @param roomIds room ids of the lesson you want to find
     * @return the lesson
     *
     * @since 1.0
     */
    public Lesson findByRoomIds(Set<Integer> roomIds) {
        return this.stream().filter(lesson -> lesson.getRoomIds().containsAll(roomIds)).findAny().orElse(null);
    }

    /**
     * Finds a lesson by its subject ids
     *
     * @param subjectIds subject ids of the lesson you want to find
     * @return the lesson
     *
     * @since 1.0
     */
    public Lesson findBySubjectIds(Set<Integer> subjectIds) {
        return this.stream().filter(lesson -> lesson.getSubjectIds().containsAll(subjectIds)).findAny().orElse(null);
    }

    /**
     * Returns a timetable, containing all lessons that have {@code code} as its code
     *
     * @param code code of the lessons you want find
     * @return the timetable
     *
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
     *
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
     *
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
     *
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
     *
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
     * @param klassenIds klassen ids of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code klassenIds} or a part of it in their klassen ids
     *
     * @since 1.0
     */
    public Timetable searchByKlassenIds(Set<Integer> klassenIds) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (UntisUtils.setContainsSetItem(lesson.getKlassenIds(), klassenIds)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code teacherIds} or a part of it in their teacher ids
     *
     * @param teacherIds teacher ids of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code teacherIds} or a part of it in their teacher ids
     *
     * @since 1.0
     */
    public Timetable searchByTeacherIds(Set<Integer> teacherIds) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (UntisUtils.setContainsSetItem(lesson.getTeacherIds(), teacherIds)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code roomIds} or a part of it in their room ids
     *
     * @param roomIds room ids of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code roomIds} or a part of it in their room ids
     *
     * @since 1.0
     */
    public Timetable searchByRoomIds(Set<Integer> roomIds) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (UntisUtils.setContainsSetItem(lesson.getRoomIds(), roomIds)) {
                timetable.add(lesson);
            }
        });

        return timetable;
    }

    /**
     * Finds lessons that have the {@code subjectIds} or a part of it in their subject ids
     *
     * @param subjectIds subject ids of the lessons you want to search
     * @return {@link Timetable} with lessons that have the {@code subjectIds} or a part of it in their subject ids
     *
     * @since 1.0
     */
    public Timetable searchBySubjectIds(Set<Integer> subjectIds) {
        Timetable timetable = new Timetable();

        this.forEach(lesson -> {
            if (UntisUtils.setContainsSetItem(lesson.getSubjectIds(), subjectIds)) {
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
     *
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
     *
     * @since 1.0
     */
    public Timetable searchByActivityType(String activityType) {
        Timetable timetable = new Timetable();

        this.stream().filter(lesson -> lesson.getActivityType().equals(activityType)).forEach(timetable::add);

        return timetable;
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
        private final Set<Integer> klassenIds;
        private final Set<Integer> teacherIds;
        private final Set<Integer> roomIds;
        private final Set<Integer> subjectIds;
        private final UntisUtils.LessonCode code;
        private final String activityType;

        /**
         * Initialize the {@link Lesson} class
         *
         * @param date date of the timetable
         * @param startTime time when the lesson start
         * @param endTime time when the lesson end
         * @param klassenIds ids of the classes
         * @param teacherId ids of the teachers
         * @param roomIds ids of the rooms
         * @param subjectIds ids of the subjects
         * @param code code of the lesson (normally null, {@link UntisUtils.LessonCode#CANCELLED} if the lesson is cancelled, {@code UntisUtils.LessonCode.IRREGULAR} if e.g. a lesson has been moved
         * @param activityType type of the lesson
         *
         * @since 1.0
         */
        public Lesson(LocalDate date,
                      LocalTime startTime,
                      LocalTime endTime,
                      Set<Integer> klassenIds,
                      Set<Integer> teacherId,
                      Set<Integer> roomIds,
                      Set<Integer> subjectIds,
                      UntisUtils.LessonCode code,
                      String activityType) {
            this.date = date;
            this.klassenIds = klassenIds;
            this.teacherIds = teacherId;
            this.roomIds = roomIds;
            this.subjectIds = subjectIds;
            this.code = code;
            this.startTime = startTime;
            this.endTime = endTime;
            this.activityType = activityType;
        }

        /**
         * Returns the date of the lesson
         *
         * @return the date of the lesson
         *
         * @since 1.0
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Returns the start time of the lesson
         *
         * @return the start time of the lesson
         *
         * @since 1.0
         */
        public LocalTime getStartTime() {
            return startTime;
        }

        /**
         * Returns the end time of the lesson
         *
         * @return the end time of the lesson
         *
         * @since 1.0
         */
        public LocalTime getEndTime() {
            return endTime;
        }

        /**
         * Returns the id of the klassen that have this lesson
         *
         * @return the id of the klassen that have this lesson
         *
         * @since 1.0
         */
        public Set<Integer> getKlassenIds() {
            return klassenIds;
        }

        /**
         * Returns the id of the teachers that have this lesson
         *
         * @return the id of the teachers that have this lesson
         *
         * @since 1.0
         */
        public Set<Integer> getTeacherIds() {
            return teacherIds;
        }

        /**
         * Returns the id of the rooms where the lesson is
         *
         * @return the id of the rooms where the lesson is
         *
         * @since 1.0
         */
        public Set<Integer> getRoomIds() {
            return roomIds;
        }

        /**
         * Returns the id of the lesson subjects
         *
         * @return the id of the lesson subjects
         *
         * @since 1.0
         */
        public Set<Integer> getSubjectIds() {
            return subjectIds;
        }

        /**
         * Returns the lesson code (if cancelled the lesson is cancelled, if irregular the something irregular is with this lesson (it got moved or something)
         *
         * @return the lesson code
         *
         * @since 1.0
         */
        public UntisUtils.LessonCode getCode() {
            return code;
        }

        /**
         * Returns the activity type
         *
         * @return the activity type
         *
         * @since 1.0
         */
        public String getActivityType() {
            return activityType;
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
            HashMap<String, String> klasseAsMap = new HashMap<>();

            klasseAsMap.put("date", date.toString());
            klasseAsMap.put("kl", klassenIds.toString());
            klasseAsMap.put("te", teacherIds.toString());
            klasseAsMap.put("su", subjectIds.toString());
            if (code != null) {
                klasseAsMap.put("code", code.getLessonCode());
            }
            klasseAsMap.put("startTime", startTime.format(DateTimeFormatter.ofPattern("HHmm")));
            klasseAsMap.put("endTime", endTime.format(DateTimeFormatter.ofPattern("HHmm")));
            klasseAsMap.put("activityType", activityType);
            klasseAsMap.put("ro", roomIds.toString());

            return new JSONObject(klasseAsMap).toString();
        }
    }

}
