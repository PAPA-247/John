/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Enumerators;

public enum Furniture {
    TWINBED,
    TWINXLBED,
    FULLBED,
    QUEENBED,
    KINGBED,
    DESK,
    NIGHTSTAND,
    DRESSER,
    CHAIR,
    CHAIR_OFFICE,
    CURTAINS,
    CURTAINS_SHOWER,
    CURTAINS_ROD,
    BLINDS,
    BEDDING,
    SOFA,
    SOFA_CHAIR,
    RECLINER,
    COFFE_TABLE,
    SIDE_TABLE,
    CHAIRS,
    TABLE,
    UMBRELLA,
    GRILL,
    FENCE,
    CANOPY,
    HAMIC,
    NONE;

    public String toIcon() {
        // TODO: Furniture icons (I don't think we can actually do this.)
        switch(this) {
            case TWINBED:
                return "";
            case TWINXLBED:
                return "";
            case FULLBED:
                return "";
            case QUEENBED:
                return "";
            case KINGBED:
                return "";
            case DESK:
                return "";
            case NIGHTSTAND:
                return "";
            case DRESSER:
                return "";
            case CHAIR:
                return "";
            case CHAIR_OFFICE:
                return "";
            case CURTAINS:
                return "";
            case CURTAINS_SHOWER:
                return "";
            case CURTAINS_ROD:
                return "";
            case BLINDS:
                return "";
            case BEDDING:
                return "";
            case SOFA:
                return "";
            case SOFA_CHAIR:
                return "";
            case RECLINER:
                return "";
            case COFFE_TABLE:
                return "";
            case SIDE_TABLE:
                return "";
            case CHAIRS:
                return "";
            case TABLE:
                return "";
            case UMBRELLA:
                return "";
            case GRILL:
                return "";
            case FENCE:
                return "";
            case CANOPY:
                return "";
            case HAMIC:
                return "";
        }
        return "gmi-error";
    }

    public static Furniture fromString(String s) {
        switch(s) {
            case "TWINBED":
                return Furniture.TWINBED;
            case "TWINXLBED":
                return Furniture.TWINXLBED;
            case "FULLBED":
                return Furniture.FULLBED;
            case "QUEENBED":
                return Furniture.QUEENBED;
            case "KINGBED":
                return Furniture.KINGBED;
            case "DESK":
                return Furniture.DESK;
            case "NIGHTSTAND":
                return Furniture.NIGHTSTAND;
            case "DRESSER":
                return Furniture.DRESSER;
            case "CHAIR":
                return Furniture.CHAIR;
            case "CHAIR_OFFICE":
                return Furniture.CHAIR_OFFICE;
            case "CURTAINS":
                return Furniture.CURTAINS;
            case "CURTAINS_SHOWER":
                return Furniture.CURTAINS_SHOWER;
            case "CURTAINS_ROD":
                return Furniture.CURTAINS_ROD;
            case "BLINDS":
                return Furniture.BLINDS;
            case "BEDDING":
                return Furniture.BEDDING;
            case "SOFA":
                return Furniture.SOFA;
            case "SOFA_CHAIR":
                return Furniture.SOFA_CHAIR;
            case "RECLINER":
                return Furniture.RECLINER;
            case "COFFE_TABLE":
                return Furniture.COFFE_TABLE;
            case "SIDE_TABLE":
                return Furniture.SIDE_TABLE;
            case "CHAIRS":
                return Furniture.CHAIRS;
            case "TABLE":
                return Furniture.TABLE;
            case "UMBRELLA":
                return Furniture.UMBRELLA;
            case "GRILL":
                return Furniture.GRILL;
            case "FENCE":
                return Furniture.FENCE;
            case "CANOPY":
                return Furniture.CANOPY;
            case "HAMIC":
                return Furniture.HAMIC;
            default:
                return Furniture.NONE;
        }
    }
}
