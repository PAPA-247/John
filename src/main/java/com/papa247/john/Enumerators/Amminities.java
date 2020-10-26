/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Enumerators;

public enum Amminities {
    PET_FRIENDLY,
    WASHERDRYER,
    DISHWASHER,
    POOL,
    GOLF_COURSE,
    GRILL,
    CLUB_HOUSE,
    PARK,
    PLAYGROUND,
    BACKYARD,
    FRONTYARD,
    GATED,
    SECURITY_SYSTEM,
    CAMERAS,
    SECURITY_PERSONAL,
    GAS_NATURAL,
    GAS_PROPANE,
    ELECTRIC,
    GARBAGE_PICKUP,
    PARKING,
    PARKING_INCLUDED,
    BIKE_RACKS,
    ELECTRIC_CAR_CHARGER,
    FURNISHED_PARTIALLY,
    FURNISHED_FULLY,
    GYM,
    UTILITIES_INCLUDED,
    NONE;

    public static Amminities fromString(String s) {
        switch(s) {
            case "PET_FRIENDLY":
                    return Amminities.PET_FRIENDLY;
            case "WASHERDRYER":
                return Amminities.WASHERDRYER;
            case "DISHWASHER":
                return Amminities.DISHWASHER;
            case "POOL":
                return Amminities.POOL;
            case "GOLF_COURSE":
                return Amminities.GOLF_COURSE;
            case "GRILL":
                return Amminities.GRILL;
            case "CLUB_HOUSE":
                return Amminities.CLUB_HOUSE;
            case "PARK":
                return Amminities.PARK;
            case "PLAYGROUND":
                return Amminities.PLAYGROUND;
            case "BACKYARD":
                return Amminities.BACKYARD;
            case "FRONTYARD":
                return Amminities.FRONTYARD;
            case "GATED":
                return Amminities.GATED;
            case "SECURITY_SYSTEM":
                return Amminities.SECURITY_SYSTEM;
            case "CAMERAS":
                return Amminities.CAMERAS;
            case "SECURITY_PERSONAL":
                return Amminities.SECURITY_PERSONAL;
            case "GAS_NATURAL":
                return Amminities.GAS_NATURAL;
            case "GAS_PROPANE":
                return Amminities.GAS_PROPANE;
            case "ELECTRIC":
                return Amminities.ELECTRIC;
            case "GARBAGE_PICKUP":
                return Amminities.GARBAGE_PICKUP;
            case "PARKING":
                return Amminities.PARKING;
            case "PARKING_INCLUDED":
                return Amminities.PARKING_INCLUDED;
            case "BIKE_RACKS":
                return Amminities.BIKE_RACKS;
            case "ELECTRIC_CAR_CHARGER":
                return Amminities.ELECTRIC_CAR_CHARGER;
            case "FURNISHED_PARTIALLY":
                return Amminities.FURNISHED_PARTIALLY;
            case "FURNISHED_FULLY":
                return Amminities.FURNISHED_FULLY;
            default:
                return Amminities.NONE;

        }
    }
}
