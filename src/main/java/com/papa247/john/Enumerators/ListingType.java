/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Enumerators;

public enum ListingType {
    APARTMENT,
    CONDO,
    STUDIO,
    DUPLEX,
    TOWNHOME,
    LOFT,
    HOUSE,
    CO_OP,
    SINGLE_ROOM,
    NONE;

    public static ListingType fromString(String s) {
    	switch(s) {
    		case "APARTMENT":
    			return ListingType.APARTMENT;
			case "CONDO":
				return ListingType.CONDO;
			case "STUDIO":
				return ListingType.STUDIO;
			case "DUPLEX":
				return ListingType.DUPLEX;
			case "TOWNHOME":
				return ListingType.TOWNHOME;
			case "LOFT":
				return ListingType.LOFT;
			case "HOUSE":
				return ListingType.HOUSE;
			case "CO_OP":
				return ListingType.CO_OP;
			case "SINGLE_ROOM":
				return ListingType.SINGLE_ROOM;
			default:
				return ListingType.NONE;
    	}
    }
}
