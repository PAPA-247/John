/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 17, 2020
*/

package com.papa247.john.Enumerators;

/*
 * Used to easily determine if a review targets a USER or an ADDRESS
 */
public enum TargetType {
    User,
    Address;
    
    public static TargetType fromNum(int num) {
        return (num==0)? TargetType.User : TargetType.Address; 
    }
    
    public int toNum() {
        return (this == User)? 0 : 1;
    }
}
