//package uz.quickly;
//
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Optional;
//
//public final class SecurityUtils {
//
//    private SecurityUtils() {
//    }
//
//    private static String extractPrincipal(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        } else if (authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
//            return springSecurityUser.getUsername();
//        } else if (authentication.getPrincipal() instanceof String) {
//            return (String) authentication.getPrincipal();
//        }
//        return null;
//    }
//
//    public static String  findCurrentCustomer() {
//        String something = SecurityUtils
//                .getCurrentUserLogin()
//                .orElseThrow(() -> new InternalAuthenticationServiceException("Current user login not found"));
//    return something;
//    }
//
//    public static Optional<String> getCurrentUserLogin() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
//    }
//
//}
