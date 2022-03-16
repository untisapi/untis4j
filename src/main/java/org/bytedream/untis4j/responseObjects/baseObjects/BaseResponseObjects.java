package org.bytedream.untis4j.responseObjects.baseObjects;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Contains all base response object classes
 *
 * @version 1.1
 * @since 1.0
 */
public class BaseResponseObjects {

    /**
     * Base class for all response objects. Has no real use
     *
     * @version 1.0
     * @since 1.0
     */
    public static class ResponseObject implements BaseResponse {

        @Override
        public boolean equals(Object obj) {
            return obj.toString().equals(this.toString());
        }
    }

    /**
     * Base class for almost all response objects
     *
     * @version 1.1
     * @since 1.0
     */
    public static class NILResponseObject extends ResponseObject {

        private final String name;
        private final int id;
        private final String longName;

        /**
         * Initializes the {@link NILResponseObject} class
         *
         * @param name     name of the response object
         * @param id       id of the response object
         * @param longName long name of the response object
         * @since 1.0
         */
        public NILResponseObject(String name, int id, String longName) {
            this.name = name;
            this.id = id;
            this.longName = longName;
        }

        /**
         * Returns the name of the response object
         *
         * @return the name of the response object
         * @since 1.0
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the id of the response object
         *
         * @return the id of the response object
         * @since 1.0
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the long name of the response object
         *
         * @return the long name of the response object
         * @since 1.0
         */
        public String getLongName() {
            return longName;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> nilResponseObjectAsMap = new HashMap<>();

            nilResponseObjectAsMap.put("name", this.getName());
            nilResponseObjectAsMap.put("id", this.getId());
            nilResponseObjectAsMap.put("longName", this.getLongName());

            return new JSONObject(nilResponseObjectAsMap).toString();
        }
    }

    /**
     * Base class for most of the response objects
     *
     * @version 1.1
     * @since 1.0
     */
    public static class NAILResponseObject extends NILResponseObject {

        private final boolean active;

        /**
         * Initializes the {@link NAILResponseObject} class
         *
         * @param name     name of the response object
         * @param active   if the response object is active
         * @param id       id of the response object
         * @param longName long name of the response object
         * @since 1.0
         */
        public NAILResponseObject(String name, boolean active, int id, String longName) {
            super(name, id, longName);
            this.active = active;
        }

        /**
         * Returns if the response object is active
         *
         * @return if the response object is active
         * @since 1.0
         */
        public boolean isActive() {
            return active;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            HashMap<String, Object> nailResponseObjectAsMap = new HashMap<>();

            nailResponseObjectAsMap.put("name", this.getName());
            nailResponseObjectAsMap.put("active", active);
            nailResponseObjectAsMap.put("id", this.getId());
            nailResponseObjectAsMap.put("longName", this.getLongName());

            return new JSONObject(nailResponseObjectAsMap).toString();
        }
    }
}
