/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Enumerators;

public enum RoomType {
    BEDROOM,
    BATHROOM,
    KITCHEN,
    LIVING_ROOM,
    ENTRY,
    PATIO,
    GUEST,
    OFFICE,
    DINNING,
    FAMILY,
    NONE;

    public static RoomType fromString(String s) {
        switch(s) {
            case "BEDROOM":
                return RoomType.BEDROOM;
            case "BATHROOM":
                return RoomType.BATHROOM;
            case "KITCHEN":
                return RoomType.KITCHEN;
            case "LIVING_ROOM":
                return RoomType.LIVING_ROOM;
            case "ENTRY":
                return RoomType.ENTRY;
            case "PATIO":
                return RoomType.PATIO;
            case "GUEST":
                return RoomType.GUEST;
            case "OFFICE":
                return RoomType.OFFICE;
            case "DINNING":
                return RoomType.DINNING;
            case "FAMILY":
                return RoomType.FAMILY;
            default:
                return RoomType.NONE;
        }
    }
}
