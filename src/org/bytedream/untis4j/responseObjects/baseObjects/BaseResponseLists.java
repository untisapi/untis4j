package org.bytedream.untis4j.responseObjects.baseObjects;

import java.util.ArrayList;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.*;

/**
 * Contains all base response list classes
 *
 * @version 1.0
 * @since 1.0
 */
public class BaseResponseLists {

    /**
     * Base class for all response lists. Has no real use
     *
     * @version 1.0
     * @since 1.0
     */
    public static class ResponseList<E> extends ArrayList<E> {

    }

    /**
     * Base class for almost all response lists
     *
     * @version 1.0
     * @since 1.0
     */
    public static class NILResponseList<E extends NILResponseObject> extends ResponseList<E> {

        /**
         * Finds an element by its name
         *
         * @param name name of the element you want to find
         * @return the element
         *
         * @since 1.0
         */
        public E findByName(String name) {
            return this.stream().filter(e -> e.getName().equals(name)).findAny().orElse(null);
        }

        /**
         * Finds an element by its id
         *
         * @param id id of the element you want to find
         * @return the element
         *
         * @since 1.0
         */
        public E findById(int id) {
            return this.stream().filter(responseObject -> responseObject.getId() == id).findAny().orElse(null);
        }

        /**
         * Finds an element by its long name
         *
         * @param longName long name of the element you want to find
         * @return the element
         *
         * @since 1.0
         */
        public E findByLongName(String longName) {
            return this.stream().filter(e -> e.getLongName().equals(longName)).findAny().orElse(null);
        }

        /**
         * Finds elements that have the {@code name} or a part of it in their name
         *
         * @param name name of the element you want to search
         * @return {@link T} with elements that have the {@code name} or a part of it in their name
         *
         * @since 1.0
         */
        public <T extends NILResponseList<E>> T searchByName(String name) {
            T nameList = (T) new NILResponseList<E>();

            this.stream().filter(e -> e.getName().contains(name)).forEach(nameList::add);

            return nameList;
        }

        /**
         * Finds elements that have the {@code id} or a part of it in their id
         *
         * @param id id of the element you want to search
         * @return {@link T} with elements that have the {@code id} or a part of it in their id
         *
         * @since 1.0
         */
        public <T extends NILResponseList<E>> T searchById(int id) {
            T idList = (T) new NILResponseList<E>();

            this.stream().filter(e -> String.valueOf(e.getId()).contains(String.valueOf(id))).forEach(idList::add);

            return idList;
        }

        /**
         * Finds elements that have the {@code longName} or a part of it in their long name
         *
         * @param longName long name of the element you want to search
         * @return {@link T} with elements that have the {@code longName} or a part of it in their long name
         *
         * @since 1.0
         */
        public <T extends NILResponseList<E>> T searchByLongName(String longName) {
            T longNameList = (T) new NILResponseList<E>();

            this.stream().filter(e -> e.getName().contains(longName)).forEach(longNameList::add);

            return longNameList;
        }

    }

    /**
     * Base class for most of the response lists
     *
     * @version 1.0
     * @since 1.0
     */
    public static class NAILResponseList<E extends NAILResponseObject> extends NILResponseList<E> {

        /**
         * Finds an element if its active or not
         *
         * @param active active state of the element you want to find
         * @return the element
         *
         * @since 1.0
         */
        public E findByActive(boolean active) {
            return this.stream().filter(e -> e.isActive() == active).findAny().orElse(null);
        }

        /**
         * Finds elements that are {@code active}
         *
         * @param active active state of the element you want to search
         * @return {@link T} with elements that are {@code active}
         *
         * @since 1.0
         */
        public <T extends NAILResponseList<E>> T searchByActive(boolean active) {
            T activeList = (T) new NAILResponseList<E>();

            this.stream().filter(e -> e.isActive() == active).forEach(activeList::add);

            return activeList;
        }

    }

}
