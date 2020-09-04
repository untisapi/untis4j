package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.Response;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Class to get information about the time when the last change were made
 *
 * @version 1.0
 * @since 1.0
 */
public class LatestImportTime extends ResponseObject {

    private final int latestImportTime;

    /**
     * Initializes the {@link LatestImportTime} class
     *
     * @param latestImportTime time when the last change were made
     *
     * @since 1.0
     */
    public LatestImportTime(int latestImportTime) {
        this.latestImportTime = latestImportTime;
    }

    /**
     * Returns the time when the last change were made
     *
     * @return the time when the last change were made
     *
     * @since 1.0
     */
    public int getLatestImportTime() {
        return latestImportTime;
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
        return new JSONObject(new HashMap<String, Object>() {{put("latestImportTime", latestImportTime);}}).toString();
    }
}
