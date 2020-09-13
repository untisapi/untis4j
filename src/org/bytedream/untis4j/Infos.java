package org.bytedream.untis4j;

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
    private final UntisUtils.ElementType personType;
    private final int klasseId;

    /**
     * Initialize the {@link Infos} class
     *
     * @param username   the username used for the api
     * @param password   the password used for the api
     * @param server     the server used for the api
     * @param schoolName the school name used for the api
     * @param userAgent  the user agent used for the api
     * @since 1.0
     */
    public Infos(String username, String password, String server, String schoolName, String userAgent, String sessionId, UntisUtils.ElementType personType, int klasseId) {
        this.username = username;
        this.password = password;
        this.server = server;
        this.schoolName = schoolName;
        this.userAgent = userAgent;

        this.sessionId = sessionId;
        this.personType = personType;
        this.klasseId = klasseId;
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
     * Returns the server used for the api
     *
     * @return the server used for the api
     * @since 1.1
     */
    public UntisUtils.ElementType getPersonType() {
        return personType;
    }

    /**
     * Returns the klasse id used for the api
     *
     * @return the klasse id used for the api
     * @since 1.1
     */
    public int getKlasseId() {
        return klasseId;
    }
}
