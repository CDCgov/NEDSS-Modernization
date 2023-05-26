package gov.cdc.nbs.authentication;

import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    private SecurityUtil() {
    }

    public static NbsUserDetails getUserDetails() {
        return (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static class Operations {
        private Operations() {
        }

        public static final String ADD = "ADD";
        public static final String ANY = "ANY";
        public static final String VIEW = "VIEW";
        public static final String EDIT = "EDIT";
        public static final String FIND = "FIND";
        public static final String MERGE = "MERGE";
        public static final String CREATE = "CREATE";
        public static final String DELETE = "DELETE";
        public static final String REVIEW = "REVIEW";
        public static final String MANAGE = "MANAGE";
    }


    public static class BusinessObjects {
        private BusinessObjects() {
        }

        public static final String QUESTION= "QUESTION";
    }

}
