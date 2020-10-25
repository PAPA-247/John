/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Enumerators;

public enum Appliances {
    WASHER,
    DRYER,
    DISHWASHER,
    STOVE,
    STOVETOP,
    FRIDGE,
    FRIDGEFREEZER,
    FRIDGEFREEZERICE,
    MICROWAVE,
    DINNINGWARE,
    CABINETS,
    COFFEE_MAKER,
    RANGE_HOOD,
    GARBAGE_DISPOSAL,
    TRASH_COMPACTOR,
    TRASH_BIN,
    AC,
    HEAT_GENERIC,
    HEAT_PUMP,
    TV,
    TV_SMART,
    NONE;

    public static Appliances fromString(String s) {
        switch(s) {
            case "WASHER":
                return Appliances.WASHER;
            case "DRYER":
                return Appliances.DRYER;
            case "DISHWASHER":
                return Appliances.DISHWASHER;
            case "STOVE":
                return Appliances.STOVE;
            case "STOVETOP":
                return Appliances.STOVETOP;
            case "FRIDGE":
                return Appliances.FRIDGE;
            case "FRIDGEFREEZER":
                return Appliances.FRIDGEFREEZER;
            case "FRIDGEFREEZERICE":
                return Appliances.FRIDGEFREEZERICE;
            case "MICROWAVE":
                return Appliances.MICROWAVE;
            case "DINNINGWARE":
                return Appliances.DINNINGWARE;
            case "CABINETS":
                return Appliances.CABINETS;
            case "COFFEE_MAKER":
                return Appliances.COFFEE_MAKER;
            case "RANGE_HOOD":
                return Appliances.RANGE_HOOD;
            case "GARBAGE_DISPOSAL":
                return Appliances.GARBAGE_DISPOSAL;
            case "TRASH_COMPACTOR":
                return Appliances.TRASH_COMPACTOR;
            case "TRASH_BIN":
                return Appliances.TRASH_BIN;
            case "AC":
                return Appliances.AC;
            case "HEAT_GENERIC":
                return Appliances.HEAT_GENERIC;
            case "HEAT_PUMP":
                return Appliances.HEAT_PUMP;
            case "TV":
                return Appliances.TV;
            case "TV_SMART":
                return Appliances.TV_SMART;

            default:
                return Appliances.NONE;
        }
    }
}
