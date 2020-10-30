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

    public String toIcon() {
        switch(this) {
            case BEDROOM:
                return "fas-bed";
            case BATHROOM:
                return "fas-toilet";
            case KITCHEN:
                return "gmi-kitchen";
            case LIVING_ROOM:
                return "fas-couch";
            case ENTRY:
                return "fas-door-open";
            case PATIO:
                return "gmi-deck";
            case GUEST:
                return ""; // ???
            case OFFICE:
                return "fas-mail-bulk";
            case DINNING:
                return "fas-utensils";
            case FAMILY:
                return "gmi-family-restroom"; // shhh, I know... its all I could find
        }
        return "gmi-error";
    }

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
