/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Enumerators;

public enum Extensions {
    CLOSET_COMMON,
    CLOSET_UTILITY,
    CLOSET_WALKIN,
    CLOSET_LARGE,
    CLOSET,
    NONE;

    public String toIcon() {
        return "gmi-checkroom"; // clothes hanger
    }

    public static Extensions fromString(String s) {
    	switch(s) {
    		case "CLOSET_COMMON":
    			return Extensions.CLOSET_COMMON;
			case "CLOSET_UTILITY":
				return Extensions.CLOSET_UTILITY;
			case "CLOSET_WALKIN":
				return Extensions.CLOSET_WALKIN;
			case "CLOSET_LARGE":
				return Extensions.CLOSET_LARGE;
			case "CLOSET":
				return Extensions.CLOSET;
			default:
				return Extensions.NONE;
    	}
    }
}
