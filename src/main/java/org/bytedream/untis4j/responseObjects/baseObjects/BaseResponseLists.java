package org.bytedream.untis4j.responseObjects.baseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NILResponseObject;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.ResponseObject;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Contains all base response list classes
 *
 * @version 1.1
 * @since 1.0
 */
public class BaseResponseLists {

    /**
     * Base class for all response lists. Has no real use
     *
     * @version 1.1
     * @since 1.0
     */
    public static class ResponseList<E extends ResponseObject> extends ArrayList<E> implements BaseResponse {
    }

    /**
     * Base class for almost all response lists
     *
     * @version 1.1
     * @since 1.0
     */
    public static class NILResponseList<E extends NILResponseObject> extends ResponseList<E> {

        /**
         * Sorts the given list by all names and returns the sorted list
         *
         * @param <T> a class that extends {@link NILResponseList}
         * @param list the list that should be sorted
         * @return the sorted list
         * @since 1.1
         */
        public static <T extends NILResponseList<? extends NILResponseObject>> T sortByName(T list) {
            list.sortByName();
            return list;
        }

        /**
         * Sorts the given list by all ids and returns the sorted list
         *
         * @param <T> a class that extends {@link NILResponseList}
         * @param list the list that should be sorted
         * @return the sorted list
         * @since 1.1
         */
        public static <T extends NILResponseList<? extends NILResponseObject>> T sortById(T list) {
            list.sortById();
            return list;
        }

        /**
         * Sorts the given list by all long names and returns the sorted list
         *
         * @param list the list that should be sorted
         * @param <T> a class which extends {@link NILResponseList}
         * @return the sorted list
         * @since 1.1
         */
        public static <T extends NILResponseList<? extends NILResponseObject>> T sortByLongName(T list) {
            list.sortByLongName();
            return list;
        }

        /**
         * Finds an element by its name
         *
         * @param name name of the element you want to find
         * @return the element
         * @since 1.0
         */
        public E findByName(String name) {
            return this.stream().filter(e -> e.getName().equalsIgnoreCase(name.trim())).findAny().orElse(null);
        }

        /**
         * Finds an element by its id
         *
         * @param id id of the element you want to find
         * @return the element
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
         * @since 1.0
         */
        public E findByLongName(String longName) {
            return this.stream().filter(e -> e.getLongName().equalsIgnoreCase(longName.trim())).findAny().orElse(null);
        }

        /**
         * Finds elements that have the {@code name} or a part of it in their name
         *
         * @param name name of the element you want to search
         * @param <T> a class which extends {@link NILResponseList}
         * @return {@link T} with elements that have the {@code name} or a part of it in their name
         * @since 1.0
         */
        public <T extends NILResponseList<E>> T searchByName(String name) {
            T nameList = (T) new NILResponseList<E>();

            this.stream().filter(e -> e.getName().toLowerCase().contains(name.trim().toLowerCase())).forEach(nameList::add);

            return nameList;
        }

        /**
         * Finds elements that have the {@code id} or a part of it in their id
         *
         * @param id id of the element you want to search
         * @param <T> a class which extends {@link NILResponseList}
         * @return {@link T} with elements that have the {@code id} or a part of it in their id
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
         * @param <T> a class which extends {@link NILResponseList}
         * @return {@link T} with elements that have the {@code longName} or a part of it in their long name
         * @since 1.0
         */
        public <T extends NILResponseList<E>> T searchByLongName(String longName) {
            T longNameList = (T) new NILResponseList<E>();

            this.stream().filter(e -> e.getName().toLowerCase().contains(longName.trim().toLowerCase())).forEach(longNameList::add);

            return longNameList;
        }

        /**
         * Sorts the list by all names
         *
         * @since 1.1
         */
        public void sortByName() {
            this.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        }

        /**
         * Sorts the list by all ids
         *
         * @since 1.1
         */
        public void sortById() {
            this.sort(Comparator.comparingInt(NILResponseObject::getId));
        }

        /**
         * Sorts the list by all long names and returns itself
         *
         * @since 1.1
         */
        public void sortByLongName() {
            this.sort((o1, o2) -> o1.getLongName().compareToIgnoreCase(o2.getLongName()));
        }

        /**
         * Returns all names that are saved in the list
         *
         * @return all names
         * @since 1.1
         */
        public ArrayList<String> getNames() {
            ArrayList<String> names = new ArrayList<>();

            this.stream().map(E::getName).forEach(names::add);

            return names;
        }

        /**
         * Returns all ids that are saved in the list
         *
         * @return all ids
         * @since 1.1
         */
        public ArrayList<Integer> getIds() {
            ArrayList<Integer> ids = new ArrayList<>();

            this.stream().map(E::getId).forEach(ids::add);

            return ids;
        }

        /**
         * Returns all long names that are saved in the list
         *
         * @return all long names
         * @since 1.1
         */
        public ArrayList<String> getLongNames() {
            ArrayList<String> longNames = new ArrayList<>();

            this.stream().map(E::getLongName).forEach(longNames::add);

            return longNames;
        }

    }

    /**
     * Base class for most of the response lists
     *
     * @version 1.1
     * @since 1.0
     */
    public static class NAILResponseList<E extends NAILResponseObject> extends NILResponseList<E> {

        /**
         * Sorts the given list by all active elements and returns the sorted list
         *
         * @param list the list that should be sorted
         * @param <T> a class which extends {@link NILResponseList}
         * @return the sorted list
         * @since 1.1
         */
        public static <T extends NILResponseList<? extends NILResponseObject>> T sortByActive(T list) {
            list.sortByLongName();
            return list;
        }

        /**
         * Finds an element if its active or not
         *
         * @param active active state of the element you want to find
         * @return the element
         * @since 1.0
         */
        public E findByActive(boolean active) {
            return this.stream().filter(e -> e.isActive() == active).findAny().orElse(null);
        }

        /**
         * Finds elements that are {@code active}
         *
         * @param active active state of the element you want to search
         * @param <T> a class which extends {@link NAILResponseList}
         * @return {@link T} with elements that are {@code active}
         * @since 1.0
         */
        public <T extends NAILResponseList<E>> T searchByActive(boolean active) {
            T activeList = (T) new NAILResponseList<E>();

            this.stream().filter(e -> e.isActive() == active).forEach(activeList::add);

            return activeList;
        }

        /**
         * Sorts the list by all active elements
         *
         * @since 1.1
         */
        public void sortByActive() {
            this.sort((o1, o2) -> Boolean.compare(o1.isActive(), o2.isActive()));
        }

        /**
         * Returns all activity types that are saved in the list
         *
         * @return all activity types
         * @since 1.1
         */
        public ArrayList<Boolean> getActiveTypes() {
            ArrayList<Boolean> activeTypes = new ArrayList<>();

            this.stream().map(E::isActive).forEach(activeTypes::add);

            return activeTypes;
        }

    }

}
