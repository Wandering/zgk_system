package cn.thinkjoy.jx.k12system.util;

import cn.thinkjoy.security.UserCredentials;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


public class AbstractRestfulService {
    public static UserCredentials getUserCredentials() {
        Authentication authentication = getContext().getAuthentication();
        if (authentication != null) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
            return new UserCredentials(details.getUsername(), "[PROTECTED]", details.isEnabled(), details.isAccountNonExpired(), details.isCredentialsNonExpired(), details.isAccountNonLocked(), details.getAuthorities());
        } else {
            return null;
        }
    }
}
