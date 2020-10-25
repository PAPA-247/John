/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Enumerators;

public enum Fixtures {
    STALLS,
    TOILET,
    SHOWER,
    BATHTUB,
    BATHTUB_JETS,
    SINK_SINGLE,
    SINK_DOUBLE,
    MIRROR,
    TOWEL_HOLDER,
    HANDTOWEL_HOLDER,
    CELIN_FAN,
    LIGHT_ROUND,
    LIGHT_RACKSTRIP,
    LIGHT_LEDSTRIP,
    LIGHT_SQUARE,
    FAN,
    VENT,
    HVAC_RETURN,
    NONE;

    public static Fixtures fromString(String s) {
        switch(s) {
            case "STALLS":
                return Fixtures.STALLS;
            case "TOILET":
                return Fixtures.TOILET;
            case "SHOWER":
                return Fixtures.SHOWER;
            case "BATHTUB":
                return Fixtures.BATHTUB;
            case "BATHTUB_JETS":
                return Fixtures.BATHTUB_JETS;
            case "SINK_SINGLE":
                return Fixtures.SINK_SINGLE;
            case "SINK_DOUBLE":
                return Fixtures.SINK_DOUBLE;
            case "MIRROR":
                return Fixtures.MIRROR;
            case "TOWEL_HOLDER":
                return Fixtures.TOWEL_HOLDER;
            case "HANDTOWEL_HOLDER":
                return Fixtures.HANDTOWEL_HOLDER;
            case "CELIN_FAN":
                return Fixtures.CELIN_FAN;
            case "LIGHT_ROUND":
                return Fixtures.LIGHT_ROUND;
            case "LIGHT_RACKSTRIP":
                return Fixtures.LIGHT_RACKSTRIP;
            case "LIGHT_LEDSTRIP":
                return Fixtures.LIGHT_LEDSTRIP;
            case "LIGHT_SQUARE":
                return Fixtures.LIGHT_SQUARE;
            case "FAN":
                return Fixtures.FAN;
            case "VENT":
                return Fixtures.VENT;
            case "HVAC_RETURN":
                return Fixtures.HVAC_RETURN;
            default:
                return Fixtures.NONE;
        }
    }
}
