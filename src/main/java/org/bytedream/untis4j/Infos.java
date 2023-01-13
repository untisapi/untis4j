package org.bytedream.untis4j;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Class to store information about the api user
 *
 * @version 1.0
 * @since 1.0
 */
public class Infos {

    private final String username;
    private final String password;
    private final String server;
    private final String schoolName;
    private final String userAgent;

    private final String sessionId;
    private final int personId;
    private final UntisUtils.ElementType personType;
    private final int classId;

    /**
     * Initialize the {@link Infos} class
     *
     * @param username   the username used for the api
     * @param password   the password used for the api
     * @param server     the server used for the api
     * @param schoolName the school name used for the api
     * @param classId    the id of the class
     * @param personId   the id of the person
     * @param sessionId  the id of the session
     * @param personType the person type
     * @param userAgent  the user agent used for the api
     * @since 1.0
     */
    public Infos(String username, String password, String server, String schoolName, String userAgent, String sessionId, int personId, UntisUtils.ElementType personType, int classId) {
        this.username = username;
        this.password = password;
        this.server = server;
        this.schoolName = schoolName;
        this.userAgent = userAgent;

        this.sessionId = sessionId;
        this.personId = personId;
        this.personType = personType;
        this.classId = classId;
    }

    /**
     * Returns the username used for the api
     *
     * @return the username used for the api
     * @since 1.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password used for the api
     *
     * @return the password used for the api
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the server used for the api
     *
     * @return the server used for the api
     * @since 1.0
     */
    public String getServer() {
        return server;
    }

    /**
     * Returns the school name used for the api
     *
     * @return the school name used for the api
     * @since 1.0
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * Returns the user agent used for the api
     *
     * @return the user agent used for the api
     * @since 1.0
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Returns the session id used for the api
     *
     * @return the session id used for the api
     * @since 1.1
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Returns the person id of the user
     *
     * @return the person id of the user
     * @since 1.3
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Returns the server used for the api
     *
     * @return the server used for the api
     * @since 1.1
     */
    public UntisUtils.ElementType getPersonType() {
        return personType;
    }

    /**
     * Returns the class id used for the api
     *
     * @return the class id used for the api
     * @since 1.1
     */
    public int getClassId() {
        return classId;
    }

    @Override
    public String toString() {
        HashMap<String, Object> infosAsMap = new HashMap<>();

        infosAsMap.put("username", username);
        infosAsMap.put("password", password);
        infosAsMap.put("server", server);
        infosAsMap.put("schoolName", schoolName);
        infosAsMap.put("userAgent", userAgent);
        infosAsMap.put("sessionId", sessionId);
        infosAsMap.put("personType", personType);
        infosAsMap.put("classId", classId);

        return new JSONObject(infosAsMap).toString();
    }
}
