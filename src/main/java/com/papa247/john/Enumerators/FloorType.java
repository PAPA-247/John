/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Enumerators;

public enum FloorType {
    CARPET,
    HARDWOOD,
    LAMINATE,
    TILE,
    GRASS,
    ROCK,
    PAVED,
    CONCRETE,
    HEATED,
    NONE;

    public String toIcon() {
    	// TODO: FloorType icons
    	switch(this) {
			case CARPET:
				return "";
			case HARDWOOD:
				return "";
			case LAMINATE:
				return "";
			case TILE:
				return "";
			case GRASS:
				return "";
			case ROCK:
				return "";
			case PAVED:
				return "";
			case CONCRETE:
				return "";
			case HEATED:
				return "";
    	}
    	return "gmi-error";
    }

    public static FloorType fromString(String s) {
    	switch(s) {
    		case "CARPET":
    			return FloorType.CARPET;
			case "HARDWOOD":
				return FloorType.HARDWOOD;
			case "LAMINATE":
				return FloorType.LAMINATE;
			case "TILE":
				return FloorType.TILE;
			case "GRASS":
				return FloorType.GRASS;
			case "ROCK":
				return FloorType.ROCK;
			case "PAVED":
				return FloorType.PAVED;
			case "CONCRETE":
				return FloorType.CONCRETE;
			case "HEATED":
				return FloorType.HEATED;
			default:
				return FloorType.NONE;
    	}
    }
}
