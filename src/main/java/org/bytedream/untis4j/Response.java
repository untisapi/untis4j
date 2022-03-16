package org.bytedream.untis4j;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponse;
import org.json.JSONObject;

/**
 * Base class for every response
 *
 * @version 1.0
 * @since 1.0
 */
public class Response implements BaseResponse {

    private final int code;
    private final JSONObject response;

    private final Object[] error = new Object[2];

    /**
     * Initialize the {@code Response} class
     *
     * @param code     response code
     * @param response response content
     * @since 1.0
     */
    public Response(int code, JSONObject response) {
        this.code = code;
        this.response = response;

        if (response.has("error")) {
            JSONObject errorResponse = response.getJSONObject("error");
            error[0] = errorResponse.getInt("code");
            error[1] = errorResponse.getString("message");
        } else {
            error[0] = -1;
            error[1] = null;
        }
    }

    /**
     * Returns if the response code represents an error
     *
     * @return if the response code represents an error
     * @since 1.0
     */
    public boolean isError() {
        return (int) error[0] != -1;
    }

    /**
     * Returns the response code
     *
     * @return the response code
     * @since 1.0
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the error code (is -1 if there is no error code)
     *
     * @return the error code
     * @since 1.0
     */
    public int getErrorCode() {
        return (int) error[0];
    }

    /**
     * Returns the error message (is null if there is no error message)
     *
     * @return the error message
     * @since 1.0
     */
    public String getErrorMessage() {
        return (String) error[1];
    }

    /**
     * Returns the response
     *
     * @return the response
     * @since 1.0
     */
    public JSONObject getResponse() {
        return response;
    }

    /**
     * Returns the response as string
     *
     * @return the response as string
     * @since 1.0
     */
    @Override
    public String toString() {
        return response.toString();
    }
}
