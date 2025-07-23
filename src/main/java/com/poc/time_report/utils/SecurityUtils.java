package com.poc.time_report.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

  public static boolean isCurrentUserAdmin() {
    return SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
  }
}
