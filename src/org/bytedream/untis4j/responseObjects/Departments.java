package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NILResponseObject;

/**
 * Class to manage {@link DepartmentObject} objects
 *
 * @version 1.0
 * @since 1.0
 */
public class Departments extends NILResponseList<Departments.DepartmentObject> {

    /**
     * Class to get information about a department
     *
     * @version 1.0
     * @since 1.0
     */
    public static class DepartmentObject extends NILResponseObject {

        /**
         * Initialize the {@link DepartmentObject} class
         *
         * @param name     name of the department
         * @param id       id of the department
         * @param longName long name of the department
         * @since 1.0
         */
        public DepartmentObject(String name, int id, String longName) {
            super(name, id, longName);
        }
    }

}
