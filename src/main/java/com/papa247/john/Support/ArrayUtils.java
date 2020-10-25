/**
* PAPA-247: Project JOHN
*
*   Simple class used to shorten other files by providing a simple method to add/remove elements from an array
*/

package com.papa247.john.Support;


public class ArrayUtils {
    /**
     * Clones an array to a new array and appends data
     * @param <T> The array type
     * @param array The existing array
     * @param newArray A new array who's length is one more than the existing array (we can't create generic arrays)
     * @param data The object you wish to add to the array
     * @return the new array with the added object
     */
    public static <T> T[] add(T[] array, T[] newArray, T data) {
        if (newArray.length!=array.length+1)
            throw new IllegalArgumentException("New array length not one more than existing array.");
        
        if (array.length==0) {
            newArray[0] = data;
            return newArray;
        }
        
        if (array[0]==null)
            array[0] = data;
        
        for (T obj : array)
            if (obj.equals(data))
                return array; // Already there.
        
        for (int i = 0; i<array.length; i++) {
            if (array[i]!=null)
                newArray[i] = array[i];
        }
        
        newArray[array.length] = data;
        return newArray;
    }
    
    /**
     * Removes a data object from an array by not coppying it to a new array
     * @param <T> Array type
     * @param array existing array
     * @param newArray new array (who's length is one smaller than the existing array (we can't create generic arrays))
     * @param data object you wish to remove
     * @return newArray if object removed, <array> if object not removed.
     */
    public static <T> T[] remove(T[] array, T[] newArray, T data) {
        if (newArray.length!=array.length-1)
            throw new IllegalArgumentException("New array length not one more than existing array.");
        
        if (array.length==0)
            return array; // Empty
        
        if (array.length==1)
            return newArray;
        
        int shift = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i].equals(data)) {
                shift = 1;
            } else {
                newArray[i-shift] = array[i];
            }
        }
        if (shift==0) // Wait a minute... we never found our data???
            return array;
        return newArray;
    }
    
    public static <T> boolean contains(T[] array, T data) {
        if (array.length == 0)
            return false;
        for (T obj : array)
            if (obj.equals(data))
                return true;
        return false;
    }
}
