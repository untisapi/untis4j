package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Class to manage {@link KlasseObject} objects
 *
 * @version 1.0
 * @since 1.0
 */
public class Klassen extends NAILResponseList<Klassen.KlasseObject> {

    /**
     * Class to get information about a klasse
     *
     * <p>Class to get information about a klasse (klasse is the german word for class, but because class is a constructor in like every programming language the untis team named it klass</p>
     *
     * @version 1.0
     * @since 1.0
     */
    public static class KlasseObject extends NAILResponseObject {

        /**
         * Initialize the {@link KlasseObject} class
         *
         * @param name name of the klasse
         * @param active if the klasse is active
         * @param id id of the klasse
         * @param longName long name of the klasse
         *
         * @since 1.0
         */
        public KlasseObject(String name, boolean active, int id, String longName) {
            super(name, active, id, longName);
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
            HashMap<String, String> klasseAsMap = new HashMap<>();

            klasseAsMap.put("name", this.getName());
            klasseAsMap.put("active", String.valueOf(this.isActive()));
            klasseAsMap.put("id", String.valueOf(this.getId()));
            klasseAsMap.put("longName", this.getLongName());

            return new JSONObject(klasseAsMap).toString();
        }
    }

}
