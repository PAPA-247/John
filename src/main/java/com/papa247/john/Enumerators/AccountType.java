/**
 * Signifies what type of account a user is. (This is how we manage what they can/can not do)
 * 
 *
 */
package com.papa247.john.Enumerators;

public enum AccountType {
	STUDENT,
	PROPERTYMANAGER,
	REALTOR,
	ADMINISTRATOR,
	INVALID;
    
    public static AccountType fromNum(int num) {
        switch (num) {
            case 0:
                return AccountType.STUDENT;                
            case 1:
                return AccountType.PROPERTYMANAGER;
            case 2:
                return AccountType.REALTOR;
            case 3:
                return AccountType.ADMINISTRATOR;
                
            default:
                return AccountType.INVALID;
        }
    }
    public int toNum() {
        switch (this) {
            case INVALID:
                return -1;
            case STUDENT:
                return 0;
            case PROPERTYMANAGER:
                return 1;
            case REALTOR:
                return 2;
            case ADMINISTRATOR:
                return 3;

            default:
                return -1;
        }
    }
}
