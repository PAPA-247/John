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
