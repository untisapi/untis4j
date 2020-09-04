package org.bytedream.untis4j;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
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

    private final Infos infos;

    private boolean loggedIn = false;
    private final String baseURL = "/WebUntis/jsonrpc.do";

    private final String url;
    private String sessionId = null;

    /**
     * Initialize the {@link RequestManager} class
     *
     * @param infos user information
     *
     * @since 1.0
     */
    public RequestManager(Infos infos) {
        this.infos = infos;

        url = infos.getServer() + baseURL + "?school=" + infos.getSchoolName();
    }

    /**
     * Sends a POST request to the server
     *
     * @see RequestManager#POST(String, Map)
     *
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
     *
     * @since 1.0
     */
    public Response POST(String method, Map<String, ?> params) throws IOException {

        if (loggedIn || method.equals(UntisUtils.Methods.LOGIN.getMethod())) {
            boolean error;
            URL url = new URL(this.url);
            String requestBody = UntisUtils.processParams(method, params);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("User-Agent", infos.getUserAgent());
            connection.setRequestProperty("Content-Type", "application/json");

            if (sessionId != null && !method.equals(UntisUtils.Methods.LOGIN.getMethod())) {
                connection.setRequestProperty("Cookie", "JSESSIONID=" + sessionId + "; schoolname=" + infos.getSchoolName());
            }

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);

            BufferedReader input;

            if (connection.getResponseCode() > 299) {
                error = true;
                input = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                error = false;
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

            if (method.equals(UntisUtils.Methods.LOGIN.getMethod()) && !loggedIn && !error) {
                sessionId = jsonObject.getJSONObject("result").getString("sessionId");
                loggedIn = true;
            } else if (method.equals(UntisUtils.Methods.LOGOUT.getMethod()) && loggedIn && !error) {
                loggedIn = false;
            }

            return new Response(connection.getResponseCode(), jsonObject);
        } else {
            throw new ConnectException("Not logged in");
        }

    }

    /**
     * Returns the url, generated out of the server address and school name which were set in the info parameter in {@link RequestManager#infos}
     *
     * @return the url, generated out of the server address and school name which were set in the info parameter in {@link RequestManager#infos}
     *
     * @since 1.0
     */
    public String getURL() {
        return url;
    }

}
