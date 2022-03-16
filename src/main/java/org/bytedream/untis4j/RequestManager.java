package org.bytedream.untis4j;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to manage all requests
 *
 * @version 1.0
 * @since 1.0
 */
public class RequestManager {

    private static final String baseURL = "/WebUntis/jsonrpc.do";
    private final Infos infos;
    private final String url;
    private boolean loggedIn = true;

    /**
     * Initialize the {@link RequestManager} class
     *
     * @param infos user information
     * @since 1.0
     */
    public RequestManager(Infos infos) {
        this.infos = infos;

        url = infos.getServer() + baseURL + "?school=" + infos.getSchoolName();
    }

    /**
     * A method to generate user infos and logging in
     *
     * @param username   the username used for the api
     * @param password   the password used for the api
     * @param server     the server used for the api
     * @param schoolName the school name used for the api
     * @param userAgent  the user agent used for the api
     * @return the generated infos
     * @throws IOException if an IO Exception occurs
     * @since 1.1
     */
    public static Infos generateUserInfosAndLogin(String username, String password, String server, String schoolName, String userAgent) throws IOException {
        URL url = new URL(server + baseURL + "?school=" + schoolName);
        String requestBody = UntisUtils.processParams(UntisUtils.Method.LOGIN.getMethod(), new HashMap<String, String>() {{
            put("user", username);
            put("password", password);
            put("client", userAgent);
        }});
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setInstanceFollowRedirects(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("Content-Type", "application/json");

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(requestBody);

        BufferedReader input;

        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (NullPointerException e) {
            input = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null) {
            stringBuilder.append(line);
        }

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            if (jsonObject.has("error")) {
                JSONObject errorObject = jsonObject.getJSONObject("error");
                throw new LoginException("The response contains an error (" + errorObject.getInt("errorObject") + "): " + errorObject.getString("message"));
            }
        } catch (JSONException e) {
            throw new IOException("An unexpected exception occurred: " + stringBuilder.toString());
        }

        JSONObject result = jsonObject.getJSONObject("result");

        UntisUtils.ElementType elementType = UntisUtils.ElementType.of(result.getInt("personType"));

        return new Infos(username, password, server, schoolName, userAgent, result.getString("sessionId"), result.getInt("personId"), elementType, result.getInt("klasseId"));
    }

    /**
     * Sends a POST request to the server
     *
     * @see RequestManager#POST(String, Map)
     * @since 1.0
     */
    public Response POST(String method) throws IOException {
        return this.POST(method, new HashMap<>());
    }

    /**
     * Sends a POST request to the server
     *
     * @param method the POST method
     * @param params params you want to send with the request
     * @return {@link Response} with all information about the response
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response POST(String method, Map<String, ?> params) throws IOException {

        if (loggedIn || method.equals(UntisUtils.Method.LOGIN.getMethod())) {
            boolean error;
            URL url = new URL(this.url);
            String requestBody = UntisUtils.processParams(method, params);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("User-Agent", infos.getUserAgent());
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Cookie", "JSESSIONID=" + infos.getSessionId() + "; schoolname=" + infos.getSchoolName());

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);

            BufferedReader input;

            try {
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                error = false;
            } catch (NullPointerException e) {
                error = true;
                input = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(stringBuilder.toString());

                if (jsonObject.has("error")) {
                    JSONObject errorObject = jsonObject.getJSONObject("error");
                    throw new ConnectException("The response contains an error (" + errorObject.getInt("errorObject") + "): " + errorObject.getString("message"));
                }
            } catch (JSONException e) {
                throw new ConnectException("An unexpected exception occurred: " + stringBuilder.toString());
            }

            if (method.equals(UntisUtils.Method.LOGOUT.getMethod()) && loggedIn && !error) {
                loggedIn = false;
            }

            return new Response(connection.getResponseCode(), jsonObject);
        } else {
            throw new LoginException("Not logged in");
        }

    }

    /**
     * Returns the url, generated out of the server address and school name which were set in the info parameter in {@link RequestManager#infos}
     *
     * @return the url, generated out of the server address and school name which were set in the info parameter in {@link RequestManager#infos}
     * @since 1.0
     */
    public String getURL() {
        return url;
    }

}
