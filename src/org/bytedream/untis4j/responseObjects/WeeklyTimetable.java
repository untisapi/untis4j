package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Contains the information about the timetable of a specific week
 *
 * @version 1.0
 * @since 1.1
 */
public class WeeklyTimetable implements BaseResponse {

    private final LocalDate startOfWeek;
    private final Timetable[] timetables = new Timetable[7];

    /**
     * Initialize the {@link WeeklyTimetable} class
     *
     * @param startOfWeek start of the week of which the timetables are
     * @param monday      timetable for tuesday
     * @param tuesday     timetable for tuesday
     * @param wednesday   timetable for wednesday
     * @param thursday    timetable for thursday
     * @param friday      timetable for friday
     * @param saturday    timetable for saturday
     * @param sunday      timetable for sunday
     * @since 1.1
     */
    public WeeklyTimetable(LocalDate startOfWeek, Timetable monday, Timetable tuesday, Timetable wednesday, Timetable thursday, Timetable friday, Timetable saturday, Timetable sunday) {
        this.startOfWeek = startOfWeek;

        timetables[0] = monday;
        timetables[1] = tuesday;
        timetables[2] = wednesday;
        timetables[3] = thursday;
        timetables[4] = friday;
        timetables[5] = saturday;
        timetables[6] = sunday;
    }

    /**
     * Returns the date for a specific weekday
     *
     * @param dayOfWeek day from which you want to receive the date
     * @return the date
     * @since 1.1
     */
    public LocalDate getWeekday(DayOfWeek dayOfWeek) {
        return startOfWeek.plusDays(dayOfWeek.getValue() - 1);
    }

    /**
     * Returns the timetable for a specific weekday
     *
     * @param dayOfWeek day from which you want to receive the timetable
     * @return the timetable
     * @since 1.1
     */
    public Timetable getTimetable(DayOfWeek dayOfWeek) {
        return timetables[dayOfWeek.getValue() - 1];
    }

    /**
     * Returns the size of the weekly timetable
     *
     * @return size
     * @since 1.1
     */
    public int size() {
        return timetables.length;
    }

    @Override
    public String toString() {
        HashMap<String, Object> weeklyTimetableAsMap = new HashMap<>();

        weeklyTimetableAsMap.put("1", timetables[0]);
        weeklyTimetableAsMap.put("2", timetables[1]);
        weeklyTimetableAsMap.put("3", timetables[2]);
        weeklyTimetableAsMap.put("4", timetables[3]);
        weeklyTimetableAsMap.put("5", timetables[4]);
        weeklyTimetableAsMap.put("6", timetables[5]);
        weeklyTimetableAsMap.put("7", timetables[6]);

        return new JSONObject(weeklyTimetableAsMap).toString();
    }
}
