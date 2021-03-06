/**
* PAPA-247: Project JOHN
*
*
*   Used to keep track of custom exceptions
*
*/

package com.papa247.john.Support;

public class Exceptions  {
    
    /**
     * Thrown whenever a listings search result turns up nothing
     * @author cnewb
     *
     */
    public static class NoListingsFound extends RuntimeException {
        private static final long serialVersionUID = 1001;

        public NoListingsFound(String errorMessage) {
            super(errorMessage);
        }
    }
    
    public static class NoSuchUser extends RuntimeException {
        private static final long serialVersionUID = 1002;

        public NoSuchUser(String errorMessage) {
            super(errorMessage);
        }
    }
    
    public static class NoSuchUserFound extends RuntimeException {
        private static final long serialVersionUID = 1003;

        public NoSuchUserFound(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
    

    public static class NoSuchAddressFound extends RuntimeException {
        private static final long serialVersionUID = 1004;

        public NoSuchAddressFound(String errorMessage) {
            super(errorMessage);
        }
    }

    
    public static class NoListingFound extends RuntimeException {
        private static final long serialVersionUID = 1005;

        public NoListingFound(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
    
    
    public static class PasswordComplexityNotMet extends RuntimeException {
        private static final long serialVersionUID = 1006;

        public PasswordComplexityNotMet(String errorMessage) {
            super(errorMessage);
        }
    }
    
    public static class InvalidUserJSON extends InvalidJSON {
        private static final long serialVersionUID = 1007;

        public InvalidUserJSON(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
    public static class InvalidJSON extends RuntimeException {
        private static final long serialVersionUID = 1008;

        public InvalidJSON(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
    
    public static class LoadFailed extends RuntimeException {
        private static final long serialVersionUID = 1009;

        public LoadFailed(String errorMessage) {
            super(errorMessage);
        }
    }
    
    public static class SaveFailed extends RuntimeException {
        private static final long serialVersionUID = 1010;

        public SaveFailed(String errorMessage) {
            super(errorMessage);
        }
    }
    
    
}
