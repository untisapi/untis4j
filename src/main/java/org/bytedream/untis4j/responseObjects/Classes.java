package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;

/**
 * Class to manage {@link ClassObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Classes extends NAILResponseList<Classes.ClassObject> {

    /**
     * Class to get information about a class
     *
     * <p>Class to get information about a class (class is the german word for class, but because class is a constructor in like every programming language the untis team named it klass</p>
     *
     * @version 1.1
     * @since 1.0
     */
    public static class ClassObject extends NAILResponseObject {

        /**
         * Initialize the {@link ClassObject} class
         *
         * @param name     name of the class
         * @param active   if the class is active
         * @param id       id of the class
         * @param longName long name of the class
         * @since 1.0
         */
        public ClassObject(String name, boolean active, int id, String longName) {
            super(name, active, id, longName);
        }
    }

}
