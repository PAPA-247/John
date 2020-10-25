/**
* PAPA-247: Project JOHN
*
*   Used to represent a range of values (numerical, but hey)
*
*/

package com.papa247.john.Support;

public class Range<type> {
    public type lowerLimit;
    public type upperLimit;
    public boolean within(type i) {
        double lL = Double.parseDouble(lowerLimit.toString());
        double uL = Double.parseDouble(upperLimit.toString());
        double val = Double.parseDouble(i.toString());
        if (val>=lL && val<=uL)
            return true;
        return false;
    }
}
