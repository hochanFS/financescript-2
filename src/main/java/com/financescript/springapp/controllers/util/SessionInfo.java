package com.financescript.springapp.controllers.util;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * This class intends to assist comparing the user in current session
 * with the author's
 */

@NoArgsConstructor
@Component
public class SessionInfo {

    public static String retrieveUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userName = userDetails.getUsername();
        }
        return userName;
    }
}
