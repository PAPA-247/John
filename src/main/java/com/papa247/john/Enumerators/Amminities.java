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
    INTERNET,
    WIFI,
    NONE;

    public String toIcon() {
        switch(this) {
            case PET_FRIENDLY:
                return "fas-paw";
            case WASHERDRYER:
                return "gmi-local-laundry-service";
            case DISHWASHER: // uhHH
                return "na";
            case POOL:
                return "gmi-pool";
            case GOLF_COURSE:
                return "gmi-golf-course";
            case GRILL:
                return "gmi-outdoor-grill";
            case CLUB_HOUSE:
                return "fas-campground"; // eh
            case PARK:
                return "fas-tree"; // eh
            case PLAYGROUND:
                return "na"; // ???
            case BACKYARD:
                return "gmi-fence"; // ???
            case FRONTYARD:
                return "gmi-grass"; // ???
            case GATED:
                return "fas-dungeon";
            case SECURITY_SYSTEM:
                return "fas-fingerprint"; // eh
            case CAMERAS:
                return "fas-video";
            case SECURITY_PERSONAL:
                return "fas-user-secret"; // eh
            case GAS_NATURAL: // Remove?
                return "fas-burn";
            case GAS_PROPANE: // Remove?
                return "fas-burn";
            case ELECTRIC:
                return "fas-bolt";
            case GARBAGE_PICKUP:
                return "fas-trash-alt";
            case PARKING:
                return "gmi-local-parking";
            case PARKING_INCLUDED:
                return "fas-parking";
            case BIKE_RACKS:
                return "fas-bicycle";
            case ELECTRIC_CAR_CHARGER:
                return "gmi-ev-station";
            case FURNISHED_PARTIALLY:
                return "fas-chair";
            case FURNISHED_FULLY:
                return "fas-couch";
            case GYM:
                return "gmi-fitness-center";
            case UTILITIES_INCLUDED:
                return "";
            case INTERNET:
                return "";
            case WIFI:
                return "gmi-wifi";
            default:
                return "gmi-error";
        }
    }
    
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
