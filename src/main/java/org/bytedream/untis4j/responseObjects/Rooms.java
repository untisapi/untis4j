package org.bytedream.untis4j.responseObjects;

import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseLists.NAILResponseList;
import org.bytedream.untis4j.responseObjects.baseObjects.BaseResponseObjects.NAILResponseObject;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage {@link RoomObject} objects
 *
 * @version 1.1
 * @since 1.0
 */
public class Rooms extends NAILResponseList<Rooms.RoomObject> {

    /**
     * Sorts the given rooms by all end dates and returns the sorted rooms
     *
     * @param rooms rooms that should be sorted
     * @return the sorted rooms
     * @since 1.1
     */
    public static Rooms sortByBuilding(Rooms rooms) {
        rooms.sortByBuilding();
        return rooms;
    }

    /**
     * Finds a building by its name
     *
     * @param building name of the building you want to find
     * @return the building
     * @since 1.0
     */
    public RoomObject findByBuilding(String building) {
        return this.stream().filter(roomObject -> roomObject.getBuilding().equalsIgnoreCase(building.trim())).findAny().orElse(null);
    }

    /**
     * Finds buildings that have the {@code building} name or a part of it in their name
     *
     * @param building name of the buildings you want to search
     * @return {@link Rooms} with buildings that have the {@code building} name or a part of it in their name
     * @since 1.0
     */
    public Rooms searchByBuilding(String building) {
        Rooms rooms = new Rooms();

        this.stream().filter(roomObject -> roomObject.getBuilding().toLowerCase().contains(building.trim().toLowerCase())).forEach(rooms::add);

        return rooms;
    }

    /**
     * Sorts the list by all buildings
     *
     * @since 1.1
     */
    public void sortByBuilding() {
        this.sort((o1, o2) -> o1.getBuilding().compareToIgnoreCase(o2.getBuilding()));
    }

    /**
     * Returns all buildings that are saved in the list
     *
     * @return all buildings
     * @since 1.1
     */
    public ArrayList<String> getBuildings() {
        ArrayList<String> buildings = new ArrayList<>();

        this.stream().map(RoomObject::getBuilding).forEach(buildings::add);

        return buildings;
    }

    /**
     * Class to get information about a room
     *
     * @version 1.1
     * @since 1.0
     */
    public static class RoomObject extends NAILResponseObject {

        private final String building;

        /**
         * Initialize the {@link RoomObject} class
         *
         * @param name     name of the room
         * @param active   if the room is active
         * @param id       id of the room
         * @param longName long name of the room
         * @param building building in which the room is
         * @since 1.0
         */
        public RoomObject(String name, boolean active, int id, String longName, String building) {
            super(name, active, id, longName);
            this.building = building;
        }

        /**
         * Returns the building in which the room is
         *
         * @return the building in which the room is
         * @since 1.0
         */
        public String getBuilding() {
            return building;
        }

        /**
         * Returns a json parsed string with all information
         *
         * @return a json parsed string with all information
         * @since 1.0
         */
        @Override
        public String toString() {
            Map<String, Object> teacherAsMap = new HashMap<>();
            teacherAsMap.put("name", this.getName());
            teacherAsMap.put("isActive", this.isActive());
            teacherAsMap.put("id", this.getId());
            teacherAsMap.put("longName", this.getLongName());
            teacherAsMap.put("building", building);
            return new JSONObject(teacherAsMap).toString();
        }
    }

}
