package org.bytedream.untis4j;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
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
    private final boolean useCache;
    private LoadingCache<String, Response> requests;

    /**
     * Initialize the {@link RequestManager} class
     *
     * @param infos user information
     * @since 1.0
     */
    public RequestManager(Infos infos, boolean useCache) {
        this.infos = infos;
        this.url = infos.getServer() + baseURL + "?school=" + infos.getSchoolName();
        this.useCache = useCache;
        
        if (this.useCache) {
            requests = Caffeine.newBuilder().maximumSize(1_000).expireAfterWrite(Duration.ofMinutes(10)).refreshAfterWrite(Duration.ofMinutes(1)).build(this::POST);
        }
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
        StringBuilder stringBuilder = getStringBuilder(userAgent, url, requestBody);

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            if (jsonObject.has("error")) {
                JSONObject errorObject = jsonObject.getJSONObject("error");
                throw new LoginException("The response contains an error (" + errorObject.getInt("errorObject") + "): " + errorObject.getString("message"));
            }
        } catch (JSONException e) {
            throw new IOException("An unexpected exception occurred: " + stringBuilder);
        }

        JSONObject result = jsonObject.getJSONObject("result");

        UntisUtils.ElementType elementType = UntisUtils.ElementType.of(result.getInt("personType"));

        return new Infos(username, password, server, schoolName, userAgent, result.getString("sessionId"), result.getInt("personId"), elementType, result.getInt("klasseId"));
    }

    private static StringBuilder getStringBuilder(String userAgent, URL url, String requestBody) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setInstanceFollowRedirects(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        OutputStreamWriter utf8Writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
        utf8Writer.write(requestBody);
        utf8Writer.close();

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
        return stringBuilder;
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
            Response response = POST(UntisUtils.processParams(method, params));
            if (method.equals(UntisUtils.Method.LOGOUT.getMethod()) && loggedIn && response != null) {
                loggedIn = false;
            }
            return response;
        } else {
            throw new LoginException("Not logged in");
        }
    }

    /**
     * Sends a POST request to the server, but only if it is not in the cache
     *
     * @param method the POST method
     * @param params params you want to send with the request
     * @return {@link Response} with all information about the response
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    public Response CachedPOST(String method, Map<String, ?> params) throws IOException {

        if (loggedIn) {
            Response response;
            if (useCache) {
                response = requests.get(UntisUtils.processParams(method, params));
            } else {
                response = POST(UntisUtils.processParams(method, params));
            }
            if (method.equals(UntisUtils.Method.LOGOUT.getMethod()) && loggedIn && response != null) {
                loggedIn = false;
            }
            return response;
        } else {
            throw new LoginException("Not logged in");
        }
    }

    /**
     * Sends a POST request to the server
     *
     * @return {@link Response} with all information about the response
     * @throws IOException if an IO Exception occurs
     * @since 1.0
     */
    private Response POST(String request) throws IOException
    {
        boolean error;
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setInstanceFollowRedirects(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("User-Agent", infos.getUserAgent());
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Cookie", "JSESSIONID=" + infos.getSessionId() + "; schoolname=" + infos.getSchoolName());
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request);
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
            throw new ConnectException("An unexpected exception occurred: " + stringBuilder);
        }
        if (error) return null;
        return new Response(connection.getResponseCode(), jsonObject);
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
    
    /**
     * Gets if every request response should be saved in cache
     *
     * @return gets if every request response should be saved in cache
     * @since 1.1
     */
    public boolean isCacheUsed() {
        return useCache;
    }
}
