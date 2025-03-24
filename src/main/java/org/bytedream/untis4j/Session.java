/*
 * @author ByteDream
 * @version 1.1
 */

package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.*;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class to control the untis4j API
 *
 * @version 1.1
 * @since 1.0
 */
public class Session {

    private Infos infos;
    private boolean useCache = true;
    private RequestManager requestManager;

    /**
     * Class to do all the Untis stuff.
     *
     * <p>org.bytedream.untis4j.Main class to handle and get all Untis information<p/>
     *
     * @param infos          infos about the user
     * @param requestManager manager that handles all requests
     * @since 1.0
     */
    private Session(Infos infos, RequestManager requestManager) {
        this.infos = infos;
        this.requestManager = requestManager;
    }

    /**
     * Logs in to the server.
     *
     * <p>Send an login request to the server and returns {@link Session} if the login was successful.
     * Throws {@link IOException} if an IO Exception occurs or {@link LoginException} (which inherits from IOException) if login fails</p>
     *
     * @param username   the username used for the api
     * @param password   the password used for the api
     * @param server     the server used for the api
     * @param schoolName the school name used for the api
     * @return a session
     * @throws IOException if an IO Exception occurs
     * @see Session#login(String, String, String, String, String)
     * @since 1.0
     */
    public static Session login(String username, String password, String server, String schoolName) throws IOException {
        return login(username, password, server, schoolName, "");
    }

    /**
     * Logs in to the server.
     *
     * <p>Send an login request to the server and returns {@link Session} if the login was successful.
     * Throws {@link IOException} if an IO Exception occurs or {@link LoginException} (which inherits from IOException) if login fails</p>
     *
     * @param server     the server from your school as URL
     * @param schoolName school name of the school you want to connect to
     * @param username   the username used for the API
     * @param password   the password used for the API
     * @param userAgent  the user agent you want to send with
     * @return a {@link Session} session
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public static Session login(String username, String password, String server, String schoolName, String userAgent) throws IOException {
        if (!server.startsWith("http://") && !server.startsWith("https://")) {
            server = "https://" + server;
        }
        Infos infos = RequestManager.generateUserInfosAndLogin(username, password, server, schoolName, userAgent);

        RequestManager requestManager = new RequestManager(infos);

        return new Session(infos, requestManager);
    }

    /**
     * Logs out from the server.
     *
     * <p>Send an logout request to the server. Throws {@link IOException} if an IO Exception occurs</p>
     *
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public void logout() throws IOException {
        requestManager.POST(UntisUtils.Method.LOGOUT.getMethod(), new HashMap<>());
    }

    /**
     * Reconnects the session.
     *
     * <p>Logs out and then logs in again. If this works the {@link RequestManager} gets refreshed.
     * Throws {@link IOException} if an IO Exception occurs or {@link LoginException} (which extends from IOException) if login fails</p>
     *
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public void reconnect() throws IOException {
        try {
            logout();
        } catch (IOException ignore) {
        }

        infos = RequestManager.generateUserInfosAndLogin(infos.getUsername(), infos.getPassword(), infos.getServer(), infos.getSchoolName(), infos.getUserAgent());
        requestManager = new RequestManager(infos);
    }

    /**
     * Checks if the same request is still in the {@link com.github.benmanes.caffeine.cache.LoadingCache} and if not, the request is sent to the server.
     *
     * @see Session#requestSender(UntisUtils.Method, Map, ResponseConsumer)
     * @since 1.1
     */
    private <T extends BaseResponse> T requestSender(UntisUtils.Method method, ResponseConsumer<? extends T> action) throws IOException {
        return requestSender(method, new HashMap<>(), action);
    }

    /**
     * Checks if the same request is still in the {@link com.github.benmanes.caffeine.cache.LoadingCache} and if not, the request is sent to the server.
     *
     *
     * @param method the POST method
     * @param params params you want to send with the request
     * @param action lambda expression that gets called if the {@code method} is not in the cache manager
     * @return the response in a {@link BaseResponseLists.ResponseList}
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     */
    private <T extends BaseResponse> T requestSender(UntisUtils.Method method, Map<String, ?> params, ResponseConsumer<? extends T> action) throws IOException {
        if (useCache) {
            return action.getResponse(requestManager.CachedPOST(method.getMethod(), params));
        } else {
            return action.getResponse(requestManager.POST(method.getMethod(), params));
        }
    }

    /**
     * Information about the Request remark categories <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * <p>Requests all class reg categories and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getClassregCategories())</p>
     *
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getClassRegCategories() throws IOException {
        return requestSender(UntisUtils.Method.GETCLASSREGCATEGORIES, response -> response);
    }

    /**
     * Information about the Request remark categories groups <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * <p>Requests all class reg categories and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getClassregCategoryGroups())</p>
     *
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getClassRegCategoryGroups() throws IOException {
        return requestSender(UntisUtils.Method.GETCLASSREGCATEGORYGROUPS, response -> response);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific school class and time period <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * <p>Requests all class reg categories and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getClassregEvents())</p>
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param elementType type on which the timetable should be oriented
     * @param id          id of the {@code elementType}
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getClassRegEvents(LocalDate start, LocalDate end, UntisUtils.ElementType elementType, Integer id) throws IOException {
        if (start.isBefore(end)) {
            throw new DateTimeException("The start date must end after or on the same day as the end date");
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("startDate", start.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        params.put("endDate", end.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        if (elementType != null && id != null) {
            params.put("type", elementType.getElementType());
            params.put("id", id);
        }

        return requestSender(UntisUtils.Method.GETCLASSREGEVENTS, params, response -> response);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific time period and class id <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param id          id of the class
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @see Session#getClassRegEvents(LocalDate, LocalDate, UntisUtils.ElementType, Integer)
     * @since 1.0
     */
    public Response getAllClassRegEventsFromClassId(LocalDate start, LocalDate end, int id) throws IOException {
        return this.getClassRegEvents(start, end, UntisUtils.ElementType.CLASS, id);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific time period and teacher id <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param id          id of the teacher
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @see Session#getClassRegEvents(LocalDate, LocalDate, UntisUtils.ElementType, Integer)
     * @since 1.0
     */
    public Response getAllClassRegEventsFromTeacherId(LocalDate start, LocalDate end, int id) throws IOException {
        return this.getClassRegEvents(start, end, UntisUtils.ElementType.TEACHER, id);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific time period and subject id <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param id          id of the subject
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @see Session#getClassRegEvents(LocalDate, LocalDate, UntisUtils.ElementType, Integer)
     * @since 1.0
     */
    public Response getAllClassRegEventsFromSubjectId(LocalDate start, LocalDate end, int id) throws IOException {
        return this.getClassRegEvents(start, end, UntisUtils.ElementType.SUBJECT, id);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific time period and room id <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param id          id of the room
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getClassRegEvents(LocalDate, LocalDate, UntisUtils.ElementType, Integer)
     * @since 1.0
     */
    public Response getAllClassRegEventsFromRoomId(LocalDate start, LocalDate end, int id) throws IOException {
        return this.getClassRegEvents(start, end, UntisUtils.ElementType.ROOM, id);
    }

    /**
     * Get the Information about the ClassRegEvents for a specific time period and person id <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param personId    the id of the person
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getClassRegEvents(LocalDate, LocalDate, UntisUtils.ElementType, Integer)
     * @since 1.0
     */
    public Response getAllClassRegEventsFromPersonId(LocalDate start, LocalDate end, int personId) throws IOException {
        return this.getClassRegEvents(start, end, UntisUtils.ElementType.PERSON, personId);
    }

    /**
     * Returns all departments.
     *
     * <p>Returns {@link Departments} with all information about the departments which are registered on the given server</p>
     *
     * @return {@link Departments} with all departments
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Departments getDepartments() throws IOException {
        return requestSender(UntisUtils.Method.GETDEPARTMENTS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Departments departments = new Departments();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject departmentInfo = jsonArray.getJSONObject(i);
                departments.add(new Departments.DepartmentObject(departmentInfo.getString("name"),
                        departmentInfo.getInt("id"),
                        departmentInfo.getString("longName")));
            }

            return departments;
        });
    }

    /**
     * Information about the Exams. <a href="https://github.com/python-webuntis/python-webuntis">Python WebUntis</a>.
     *
     * <p>Requests all exams and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getExams())</p>
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param id    id of the exam
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getExams(LocalDate start, LocalDate end, int id) throws IOException {
        Map<String, String> params = UntisUtils.localDateToParams(start, end);
        params.put("examTypeId", String.valueOf(id));

        return requestSender(UntisUtils.Method.GETEXAMS, params, response -> response);
    }

    /**
     * Requests all exam types.
     *
     * <p>Requests all exam types and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getExamTypes())</p>
     *
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getExamTypes() throws IOException {
        return requestSender(UntisUtils.Method.GETEXAMTYPES, response -> response);
    }

    /**
     * Returns all holidays registered on the given server.
     *
     * <p>Returns {@link Holidays} with all information about the holidays which are registered on the given server</p>
     *
     * @return {@link Holidays} with all information about the holidays
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Holidays getHolidays() throws IOException {
        return requestSender(UntisUtils.Method.GETHOLIDAYS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Holidays holidays = new Holidays();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject holidayInfo = jsonArray.getJSONObject(i);
                holidays.add(new Holidays.HolidaysObject(holidayInfo.getString("name"),
                        holidayInfo.getInt("id"),
                        holidayInfo.getString("longName"),
                        LocalDate.parse(String.valueOf(holidayInfo.getInt("startDate")), DateTimeFormatter.ofPattern("yyyyMMdd")),
                        LocalDate.parse(String.valueOf(holidayInfo.getInt("endDate")), DateTimeFormatter.ofPattern("yyyyMMdd"))));
            }

            return holidays;
        });
    }

    /**
     * Returns all classes registered on the given server.
     *
     * <p>Returns {@link Classes} with all information about the class which are registered on the given server</p>
     *
     * @return {@link Classes} with all information about the class which are registered on the given server
     * @throws IOException if an IO Exception occurs
     * @see Session#getClasses(Integer)
     * @since 1.0
     */
    public Classes getClasses() throws IOException {
        return getClasses(null);
    }

    /**
     * Returns all classes from the given school year registered on the given server.
     *
     * <p>Returns {@link Classes} with all information about the classes from the given school year which are registered on the given server</p>
     *
     * @param schoolYearId number of the school year from which you want to get the classes
     * @return {@link Classes} with all information about the classes
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Classes getClasses(Integer schoolYearId) throws IOException {
        ResponseConsumer<Classes> responseConsumer = response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Classes classes = new Classes();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject classesInfo = jsonArray.getJSONObject(i);
                classes.add(new Classes.ClassObject(classesInfo.getString("name"),
                        classesInfo.getBoolean("active"),
                        classesInfo.getInt("id"),
                        classesInfo.getString("longName")));
            }

            return classes;
        };

        if (schoolYearId != null) {
            return requestSender(UntisUtils.Method.GETCLASSES, new HashMap<String, Integer>() {{
                put("schoolyearId", schoolYearId);
            }}, responseConsumer);
        } else {
            return requestSender(UntisUtils.Method.GETCLASSES, responseConsumer);
        }
    }

    /**
     * Returns {@link LatestImportTime} with information about the time when the last change were made.
     *
     * <p>Returns {@link LatestImportTime} with all information about the time when the last change were made on the server</p>
     *
     * @return {@link LatestImportTime} with all information about the time when the last change were made
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public LatestImportTime getLatestImportTime() throws IOException {
        Response response = requestManager.POST(UntisUtils.Method.GETLATESTIMPORTTIME.getMethod(), new HashMap<>());

        JSONObject jsonResponse = response.getResponse();

        if (response.isError()) {
            throw new IOException(response.getErrorMessage());
        }

        return new LatestImportTime(jsonResponse.getLong("result"));
    }

    /**
     * Returns all rooms.
     *
     * <p>Returns {@link Rooms} with all information about the rooms which are registered on the given server</p>
     *
     * @return {@link Rooms} with all information about the rooms
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Rooms getRooms() throws IOException {
        return requestSender(UntisUtils.Method.GETROOMS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Rooms rooms = new Rooms();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject roomInfo = jsonArray.getJSONObject(i);
                rooms.add(new Rooms.RoomObject(roomInfo.getString("name"),
                        roomInfo.getBoolean("active"),
                        roomInfo.getInt("id"),
                        roomInfo.getString("longName"),
                        roomInfo.getString("building")));
            }

            return rooms;
        });
    }

    /**
     * Returns all school years.
     *
     * <p>Returns {@link SchoolYears} with all information about the school years which are registered on the given server</p>
     *
     * @return {@link SchoolYears} with all information about the school years
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public SchoolYears getSchoolYears() throws IOException {
        return requestSender(UntisUtils.Method.GETSCHOOLYEARS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            SchoolYears schoolYears = new SchoolYears();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject schoolYearInfo = jsonArray.getJSONObject(i);
                schoolYears.add(new SchoolYears.SchoolYearObject(schoolYearInfo.getString("name"),
                        schoolYearInfo.getInt("id"),
                        LocalDate.parse(String.valueOf(schoolYearInfo.getInt("startDate")), DateTimeFormatter.ofPattern("yyyyMMdd")),
                        LocalDate.parse(String.valueOf(schoolYearInfo.getInt("endDate")), DateTimeFormatter.ofPattern("yyyyMMdd"))));
            }

            return schoolYears;
        });
    }

    /**
     * Requests all status data.
     *
     * <p>Requests all status data and give {@link Response} with all information about from the request back</p>
     *
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getStatusData() throws IOException {
        return requestSender(UntisUtils.Method.GETSTATUSDATA, response -> response);
    }

    /**
     * Returns all subjects.
     *
     * <p>Returns {@link Subjects} with all information about the subjects which are registered on the given server</p>
     *
     * @return {@link Subjects} with all information about the subjects
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Subjects getSubjects() throws IOException {
        return requestSender(UntisUtils.Method.GETSUBJECTS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Subjects subjects = new Subjects();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subjectInfo = jsonArray.getJSONObject(i);

                String backColor = "b1b3b4";
                String foreColor = "b1b3b4";

                try {
                    backColor = subjectInfo.getString("backColor");
                } catch (JSONException ignore) {
                }
                try {
                    foreColor = subjectInfo.getString("foreColor");
                } catch (JSONException ignore) {
                }

                subjects.add(new Subjects.SubjectObject(subjectInfo.getString("name"),
                        subjectInfo.getBoolean("active"),
                        subjectInfo.getInt("id"),
                        subjectInfo.getString("longName"),
                        subjectInfo.getString("alternateName"),
                        backColor,
                        foreColor));
            }

            return subjects;
        });
    }

    /**
     * Returns all registered teachers on the given server.
     *
     * <p>Returns {@link Teachers} with all information about the teachers which are registered on the given server</p>
     *
     * @return {@link Teachers} with all information about the teachers
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Teachers getTeachers() throws IOException {
        return requestSender(UntisUtils.Method.GETTEACHERS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Teachers teachers = new Teachers();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject teacherInfo = jsonArray.getJSONObject(i);
                teachers.add(new Teachers.TeacherObject(teacherInfo.getString("name"),
                        teacherInfo.getBoolean("active"),
                        teacherInfo.getInt("id"),
                        teacherInfo.getString("longName"),
                        teacherInfo.getString("title"),
                        teacherInfo.getString("foreName")));
            }

            return teachers;
        });
    }

    /**
     * Returns all registered timegrid units on the given server.
     *
     * <p>Returns {@link TimegridUnits} with all information about the timegrid units which are registered on the given server</p>
     *
     * @return {@link TimegridUnits} with all information about the timegrid units
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public TimegridUnits getTimegridUnits() throws IOException {
        return requestSender(UntisUtils.Method.GETTIMEGRIDUNTIS, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            TimegridUnits timegridUnits = new TimegridUnits();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject timegridUnitInfo = jsonArray.getJSONObject(i);
                JSONArray timegridUnitInfoArray = timegridUnitInfo.getJSONArray("timeUnits");

                TimeUnits timeUnits = new TimeUnits();

                LocalTime startTime;
                LocalTime endTime;

                for (int j = 0; j < timegridUnitInfoArray.length(); j++) {
                    JSONObject timegridUntisObject = timegridUnitInfoArray.getJSONObject(j);

                    try {
                        startTime = LocalTime.parse(String.valueOf(timegridUntisObject.getInt("startTime")), DateTimeFormatter.ofPattern("HHmm"));
                    } catch (DateTimeParseException e) {
                        startTime = LocalTime.parse(String.valueOf(timegridUntisObject.getInt("startTime")), DateTimeFormatter.ofPattern("Hmm"));
                    }

                    try {
                        endTime = LocalTime.parse(String.valueOf(timegridUntisObject.getInt("endTime")), DateTimeFormatter.ofPattern("HHmm"));
                    } catch (DateTimeParseException e) {
                        endTime = LocalTime.parse(String.valueOf(timegridUntisObject.getInt("endTime")), DateTimeFormatter.ofPattern("Hmm"));
                    }

                    timeUnits.add(new TimeUnits.TimeUnitObject(timegridUntisObject.getString("name"), startTime, endTime));
                }

                timegridUnits.add(new TimegridUnits.TimegridUnitObject(timegridUnitInfo.getInt("day"), timeUnits));
            }

            return timegridUnits;
        });
    }

    /**
     * Returns information about the current school year.
     *
     * <p>Returns {@link SchoolYears.SchoolYearObject} with all information about the current school year</p>
     *
     * @return {@link SchoolYears.SchoolYearObject} with all information about the current school year
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public SchoolYears.SchoolYearObject getCurrentSchoolYear() throws IOException {
        return requestSender(UntisUtils.Method.GETCURRENTSCHOOLYEAR, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }
            JSONObject jsonObject = jsonResponse.getJSONObject("result");

            return new SchoolYears.SchoolYearObject(jsonObject.getString("name"),
                    jsonObject.getInt("id"),
                    LocalDate.parse(String.valueOf(jsonObject.getInt("startDate")), DateTimeFormatter.ofPattern("yyyyMMdd")),
                    LocalDate.parse(String.valueOf(jsonObject.getInt("endDate")), DateTimeFormatter.ofPattern("yyyyMMdd")));
        });
    }

    /**
     * Returns the lessons / timetable for a specific time period.
     *
     * <p> Returns the lessons / timetable for a specific time period and class / teacher / subject / room / student id</p>
     *
     * @param start       the beginning of the time period
     * @param end         the end of the time period
     * @param elementType type on which the timetable should be oriented
     * @param id          id of the {@code elementType}
     * @return {@link Timetable} with all information about the lessons
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Timetable getTimetable(LocalDate start, LocalDate end, UntisUtils.ElementType elementType, int id) throws IOException {
        if (end.isBefore(start)) {
            throw new DateTimeException("The end date must end after or on the same day as the start date");
        }
        Map<String, ?> element = new HashMap<>(){{
            put("type", elementType.getElementType());
            put("id", id);
        }};
        Map<String, ?> options = new HashMap<>() {{
            put("startDate", start.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            put("endDate", end.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            put("element", element);
            put("onlyBaseTimetable", "False");
            put("showInfo","True");
            put("showSubstText", "True");
            put("showLsText", "True");
            put("showLsNumber", "True");
            put("showStudentgroup", "True");
        }};
        Map<String, Map<String, ?>> params = new HashMap<>();
        params.put("options", options);

        return requestSender(UntisUtils.Method.GETTIMETABLE, params, response -> {
            JSONObject jsonResponse = response.getResponse();

            if (response.isError()) {
                throw new IOException(response.getErrorMessage());
            }

            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Timetable timetable = new Timetable();

            Classes c;
            Teachers t;
            Subjects s;
            Rooms r;

            try {
                c = getClasses();
            } catch (IOException e) {
                e.printStackTrace();
                c = null;
            }
            try {
                t = getTeachers();
            } catch (IOException e) {
                e.printStackTrace();
                t = null;
            }
            try {
                s = getSubjects();
            } catch (IOException e) {
                e.printStackTrace();
                s = null;
            }
            try {
                r = getRooms();
            } catch (IOException e) {
                e.printStackTrace();
                r = null;
            }




            TimeUnits timeUnits = getTimegridUnits().get(0).getTimeUnits();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject timetableInfos = jsonArray.getJSONObject(i);

                Classes classes = new Classes();
                Classes originalClasses = new Classes();
                Teachers teachers = new Teachers();
                Teachers originalTeachers = new Teachers();
                Subjects subjects = new Subjects();
                Subjects originalSubjects = new Subjects();
                Rooms rooms = new Rooms();
                Rooms originalRooms = new Rooms();

                String[] arrayKeys = {"kl", "te", "su", "ro"};

                for (String currentStringArray : arrayKeys) {
                    if (timetableInfos.has(currentStringArray)) { // some schools haven't specified their rooms or teachers
                        JSONArray arrayJSONArray = timetableInfos.getJSONArray(currentStringArray);

                        for (int j = 0; j < arrayJSONArray.length(); j++) {
                            JSONObject obj = (JSONObject) arrayJSONArray.get(j);
                            int objId = obj.getInt("id");
                            Integer orgId = obj.has("orgid") ? obj.getInt("orgid") : null;
                            try {
                                switch (currentStringArray) {
                                    case "kl":
                                        Classes.ClassObject classObject;
                                        if ((classObject = c.findById(objId)) != null) classes.add(classObject);
                                        if (orgId != null) originalClasses.add(c.findById(orgId));
                                        break;
                                    case "te":
                                        Teachers.TeacherObject teacherObject;
                                        if ((teacherObject = t.findById(objId)) != null) teachers.add(teacherObject);
                                        if (orgId != null) originalTeachers.add(t.findById(orgId));
                                        break;
                                    case "su":
                                        Subjects.SubjectObject subjectObject;
                                        if ((subjectObject = s.findById(objId)) != null) subjects.add(subjectObject);
                                        if (orgId != null) originalSubjects.add(s.findById(orgId));
                                        break;
                                    case "ro":
                                        Rooms.RoomObject roomObject;
                                        if ((roomObject = r.findById(objId)) != null) rooms.add(roomObject);
                                        if (orgId != null) originalRooms.add(r.findById(orgId));
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + currentStringArray);
                                }
                            } catch (NullPointerException ignore) {}
                        }
                    }
                }

                String startString = String.valueOf(timetableInfos.getInt("startTime"));
                String endString = String.valueOf(timetableInfos.getInt("endTime"));

                String startPattern;
                String endPattern;

                switch (startString.length()) {
                    case 4:
                        startPattern = "HHmm";
                        break;
                    case 3:
                        startPattern = "Hmm";
                        break;
                    case 2:
                        startString = "00" + startString;
                        startPattern = "HHmm";
                        break;
                    case 1:
                        startString = "000" + startString;
                        startPattern = "HHmm";
                        break;
                    default:
                        startPattern = null;
                }

                switch (endString.length()) {
                    case 4:
                        endPattern = "HHmm";
                        break;
                    case 3:
                        endPattern = "Hmm";
                        break;
                    case 2:
                        endString = "00" + endString;
                        endPattern = "HHmm";
                        break;
                    case 1:
                        endString = "000" + endString;
                        endPattern = "HHmm";
                        break;
                    default:
                        endPattern = null;
                }

                LocalTime startTime = LocalTime.parse(startString, DateTimeFormatter.ofPattern(Objects.requireNonNull(startPattern)));
                LocalTime endTime = LocalTime.parse(endString, DateTimeFormatter.ofPattern(endPattern));

                UntisUtils.LessonCode code = UntisUtils.LessonCode.REGULAR;
                if (timetableInfos.has("code")) {
                    code = UntisUtils.LessonCode.valueOf(timetableInfos.getString("code").toUpperCase());
                }

                String activityType = null;
                if (timetableInfos.has("activityType")) activityType = timetableInfos.getString("activityType");

                String info = null;
                if (timetableInfos.has("info")) info = timetableInfos.getString("info");

                String substText = null;
                if (timetableInfos.has("substText")) substText = timetableInfos.getString("substText");

                String lsText = null;
                if (timetableInfos.has("lstext")) lsText = timetableInfos.getString("lstext");

                Integer lsNumber = null;
                if (timetableInfos.has("lsnumber")) lsNumber  = timetableInfos.getInt("lsnumber");

                String studentGroup = null;
                if (timetableInfos.has("sg")) studentGroup = timetableInfos.getString("sg");

                timetable.add(new Timetable.Lesson(
                        LocalDate.parse(String.valueOf(timetableInfos.getInt("date")), DateTimeFormatter.ofPattern("yyyyMMdd")),
                        startTime,
                        endTime,
                        timeUnits.findByStartTime(startTime),
                        classes,
                        originalClasses,
                        teachers,
                        originalTeachers,
                        rooms,
                        originalRooms,
                        subjects,
                        originalSubjects,
                        code,
                        activityType,
                        info,
                        substText,
                        lsText,
                        lsNumber,
                        studentGroup
                ));
            }

            return timetable;
        });
    }

    /**
     * Returns the lessons / timetable for a specific time period and class id.
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param classId the id of the class
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getTimetable(LocalDate, LocalDate, UntisUtils.ElementType, int)
     * @since 1.0
     */
    public Timetable getTimetableFromClassId(LocalDate start, LocalDate end, int classId) throws IOException {
        return this.getTimetable(start, end, UntisUtils.ElementType.CLASS, classId);
    }

    /**
     * Returns the lessons / timetable for a specific time period and teacher id.
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param teacherId the id of the teacher
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getTimetable(LocalDate, LocalDate, UntisUtils.ElementType, int)
     * @since 1.0
     */
    public Timetable getTimetableFromTeacherId(LocalDate start, LocalDate end, int teacherId) throws IOException {
        return this.getTimetable(start, end, UntisUtils.ElementType.TEACHER, teacherId);
    }

    /**
     * Returns the lessons / timetable for a specific time period and subject id.
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param subjectId the id of the subject
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getTimetable(LocalDate, LocalDate, UntisUtils.ElementType, int)
     * @since 1.0
     */
    public Timetable getTimetableFromSubjectId(LocalDate start, LocalDate end, int subjectId) throws IOException {
        return this.getTimetable(start, end, UntisUtils.ElementType.SUBJECT, subjectId);
    }

    /**
     * Returns the lessons / timetable for a specific time period and room id.
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param roomId the id of the room
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getTimetable(LocalDate, LocalDate, UntisUtils.ElementType, int)
     * @since 1.0
     */
    public Timetable getTimetableFromRoomId(LocalDate start, LocalDate end, int roomId) throws IOException {
        return this.getTimetable(start, end, UntisUtils.ElementType.ROOM, roomId);
    }

    /**
     * Returns the lessons / timetable for a specific time period and person id.
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @param personId the id of the person
     * @return {@link Timetable} with all information about the events
     * @throws IOException if an IO Exception occurs
     * @see Session#getTimetable(LocalDate, LocalDate, UntisUtils.ElementType, int)
     * @since 1.0
     */
    public Timetable getTimetableFromPersonId(LocalDate start, LocalDate end, int personId) throws IOException {
        return this.getTimetable(start, end, UntisUtils.ElementType.PERSON, personId);
    }

    /**
     * Requests timetable with absence for a specific time period
     *
     * <p>Requests timetable with absence for a specific time period and give {@link Response} with all information about from the request back (i got an error while testing this method: no right for getTimetableWithAbsences())</p>
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getTimetableWithAbsence(LocalDate start, LocalDate end) throws IOException {
        return requestSender(UntisUtils.Method.GETTIMETABLEWITHABSENCE, new HashMap<String, Object>() {{
            put("options", UntisUtils.localDateToParams(start, end));
        }}, response -> response);
    }

    /**
     * Requests the timetable for a whole week
     *
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @param elementType   type on which the timetable should be oriented
     * @param id            id of the {@code elementType}
     * @return the weekly timetable
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetable(LocalDate anyDateOfWeek, UntisUtils.ElementType elementType, int id) throws IOException {
        LocalDate monday = anyDateOfWeek.minusDays(anyDateOfWeek.getDayOfWeek().getValue() - 1);
        LocalDate sunday = monday.plusDays(DayOfWeek.SUNDAY.getValue());

        Timetable[] timetables = {new Timetable(), new Timetable(), new Timetable(), new Timetable(), new Timetable(), new Timetable(), new Timetable()};

        this.getTimetable(monday, sunday, elementType, id).forEach(lesson -> timetables[lesson.getDate().getDayOfWeek().getValue() - 1].add(lesson));

        return new WeeklyTimetable(monday, timetables[0], timetables[1], timetables[2], timetables[3], timetables[4], timetables[5], timetables[6]);
    }

    /**
     * Requests the timetable for a whole week and a class id
     *
     * @param classId the id of the specific class
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @return the weekly timetable
     * @throws IOException if an IO Exception occurs
     * @see Session#getWeeklyTimetable(LocalDate, UntisUtils.ElementType, int)
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetableFromClassId(LocalDate anyDateOfWeek, int classId) throws IOException {
        return this.getWeeklyTimetable(anyDateOfWeek, UntisUtils.ElementType.CLASS, classId);
    }

    /**
     * Requests the timetable for a whole week and a teacher id
     *
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @param teacherId the id of the teacher
     * @return the weekly timetable
     * @throws IOException if an IO Exception occurs
     * @see Session#getWeeklyTimetable(LocalDate, UntisUtils.ElementType, int)
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetableFromTeacherId(LocalDate anyDateOfWeek, int teacherId) throws IOException {
        return this.getWeeklyTimetable(anyDateOfWeek, UntisUtils.ElementType.CLASS, teacherId);
    }

    /**
     * Requests the timetable for a whole week and a subject id
     *
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @param subjectId the id of the subject
     * @return the weekly timetable
     * @throws IOException if an IO Exception occurs
     * @see Session#getWeeklyTimetable(LocalDate, UntisUtils.ElementType, int)
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetableFromSubjectId(LocalDate anyDateOfWeek, int subjectId) throws IOException {
        return this.getWeeklyTimetable(anyDateOfWeek, UntisUtils.ElementType.CLASS, subjectId);
    }

    /**
     * Requests the timetable for a whole week and a room id
     *
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @param roomId        the id of the room
     * @return the weekly timetable
     * @throws IOException – if an IO Exception occurs
     * @see Session#getWeeklyTimetable(LocalDate, UntisUtils.ElementType, int)
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetableFromRoomId(LocalDate anyDateOfWeek, int roomId) throws IOException {
        return this.getWeeklyTimetable(anyDateOfWeek, UntisUtils.ElementType.CLASS, roomId);
    }

    /**
     * Requests the timetable for a whole week and a person id
     *
     * @param anyDateOfWeek any day of the week you want to get the timetable from
     * @param personId the id of a person
     * @throws IOException if an IO Exception occurs
     * @return the weekly timetable
     * @see Session#getWeeklyTimetable(LocalDate, UntisUtils.ElementType, int)
     * @since 1.1
     */
    public WeeklyTimetable getWeeklyTimetableFromPersonId(LocalDate anyDateOfWeek, int personId) throws IOException {
        return this.getWeeklyTimetable(anyDateOfWeek, UntisUtils.ElementType.PERSON, personId);
    }

    /**
     * Allows to request custom data.
     *
     * @param method the POST methode
     * @throws IOException if an IO Exception occurs
     * @return {@link Response} with the response from the request
     * @see Session#getCustomData(String, Map)
     * @since 1.0
     */
    public Response getCustomData(String method) throws IOException {
        return this.getCustomData(method, null);
    }

    /**
     * Allows to request custom data.
     *
     * <p>Requests custom data and give {@link Response} with all information about from the request back</p>
     *
     * @param method the POST methode
     * @param params params you want to send with the request
     * @return {@link Response} with the response from the request
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response getCustomData(String method, Map<String, ?> params) throws IOException {
        return requestManager.POST(method, Objects.requireNonNullElseGet(params, HashMap::new));
    }

    /**
     * Returns the user infos
     *
     * @return the user infos
     * @since 1.1
     */
    public Infos getInfos() {
        return infos;
    }

    /**
     * Gets if every request response should be saved in cache
     *
     * @return gets if every request response should be saved in cache
     * @since 1.1
     */
    public boolean isCacheUsed() {
        return useCache;
    }

    /**
     * Sets if every request response should be saved in cache what returns in better performance
     *
     * @param useCache sets if every request response should be saved in cache
     * @since 1.1
     */
    public void useCache(boolean useCache) {
        this.useCache = useCache;
    }

}
