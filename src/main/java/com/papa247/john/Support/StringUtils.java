/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 30, 2020
*/

package com.papa247.john.Support;

public class StringUtils {
    /**
     * Check if a string is either null (empty, nonexistent), or empty
     * @param s the string to check
     * @return true if the string is null or empty.
     */
    public static boolean isNullOrEmpty(String s) {
        if (s==null)
            return true;
        if (s.replace(" ", "").equals(""))
            return true;
        return false;
    }
}
