package org.bytedream.untis4j;

import org.json.JSONObject;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Support class
 *
 * @version 1.1
 * @since 1.0
 */
public class UntisUtils {

    /**
     * Processes the given params to a well-formatted string
     *
     * @param method method you want to process
     * @return a string with the processed params
     * @see UntisUtils#processParams(String, Map)
     * @since 1.0
     */
    public static String processParams(String method) {
        return processParams(method, new HashMap<>());
    }

    /**
     * Processes the given params to a well-formatted string
     *
     * @param method         method you want to process
     * @param optionalParams params you want to process
     * @return a string with the processed params
     * @since 1.0
     */
    public static String processParams(String method, Map<String, ?> optionalParams) {
        JSONObject paramsJSONObject = new JSONObject(optionalParams);
        return "{\"id\":\"ID\",\"method\":\"" + method + "\",\"jsonrpc\":\"2.0\",\"params\":" + paramsJSONObject + "}";
    }

    /**
     * Returns a well formatted {@code java.util.HashMap} with date params in it, which can be used to send date information to the untis server
     *
     * @param start the beginning of the time period
     * @param end   the end of the time period
     * @return the well parsed dates in a {@code java.util.HashMap}
     * @since 1.0
     */
    public static HashMap<String, String> localDateToParams(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new DateTimeException("The end date must end after or on the same day as the start date");
        }

        return new HashMap<String, String>() {{
            put("startDate", start.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            put("endDate", end.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }};
    }

    /**
     * Checks if two lists have a same item
     *
     * @param list1 list 1 to check
     * @param list2 list 2 to check
     * @return {@code true} if the two lists contains a same item, {@code false} if not
     * @since 1.0
     */
    public static boolean listContainsListItem(List<?> list1, List<?> list2) {
        for (Object o : list1) {
            if (list2.contains(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two sets have a same item
     *
     * @param set1 set 1 to check
     * @param set2 set 2 to check
     * @return {@code true} if the two sets contains a same item, {@code false} if not
     * @since 1.0
     */
    public static boolean setContainsSetItem(Set<?> set1, Set<?> set2) {
        for (Object o : set1) {
            if (set2.contains(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * All element types.
     *
     * <p>This enum contains all element types that can be used while POST a method to the untis server</p>
     *
     * @since 1.0
     */
    public enum ElementType {
        CLASS(1),
        TEACHER(2),
        SUBJECT(3),
        ROOM(4),
        PERSON(5),
        OTHER(6);

        private final int elementType;

        /**
         * Private {@link ElementType} initializer
         *
         * @param elementType element type int
         * @since 1.0
         */
        ElementType(int elementType) {
            this.elementType = elementType;
        }

        /**
         * Returns an {@link ElementType} based on the given value
         *
         * @param elementType element type int from which you want to get the {@link ElementType}
         * @return the {@link ElementType}
         * @since 1.1
         */
        public static ElementType of(int elementType) {
            if (1 > elementType) {
                throw new EnumConstantNotPresentException(ElementType.class, "Invalid value for ElementType: " + elementType);
            }
            if(elementType > 6) {
                return OTHER;
            }
            return ElementType.values()[elementType - 1];
        }

        /**
         * Returns the id of the element type
         *
         * @return the id of the element type
         * @since 1.0
         */
        public int getElementType() {
            return elementType;
        }
    }

    /**
     * All (supported) untis POST methods.
     *
     * <p>This enum contains all methods that can be POSTed to the untis server and support units4j</p>
     *
     * @since 1.0
     */
    public enum Method {
        LOGIN("authenticate"),
        LOGOUT("logout"),

        GETCLASSREGCATEGORIES("getClassregCategories"),
        GETCLASSREGCATEGORYGROUPS("getClassregCategoryGroups"),
        GETCLASSREGEVENTS("getClassregEvents"),
        GETCURRENTSCHOOLYEAR("getCurrentSchoolyear"),
        GETDEPARTMENTS("getDepartments"),
        GETEXAMS("getExams"),
        GETEXAMTYPES("getExamTypes"),
        GETHOLIDAYS("getHolidays"),
        GETCLASSES("getKlassen"),
        GETLATESTIMPORTTIME("getLatestImportTime"),
        GETROOMS("getRooms"),
        GETSCHOOLYEARS("getSchoolyears"),
        GETSTATUSDATA("getStatusData"),
        GETSUBJECTS("getSubjects"),
        GETTEACHERS("getTeachers"),
        GETTIMEGRIDUNTIS("getTimegridUnits"),
        GETTIMETABLE("getTimetable"),
        GETTIMETABLEWITHABSENCE("getTimetableWithAbsences");

        private final String method;

        /**
         * Private {@link Method} initializer
         *
         * @param method method string
         * @since 1.0
         */
        Method(String method) {
            this.method = method;
        }

        /**
         * Returns the string of the method
         *
         * @return the string of the method
         * @since 1.0
         */
        public String getMethod() {
            return method;
        }
    }

    /**
     * All lesson codes
     *
     * @since 1.0
     */
    public enum LessonCode {
        CANCELLED("cancelled"),
        IRREGULAR("irregular"),
        REGULAR("regular");

        private final String lessonCode;

        LessonCode(String timetableCode) {
            this.lessonCode = timetableCode;
        }

        public String getLessonCode() {
            return lessonCode;
        }
    }

}
