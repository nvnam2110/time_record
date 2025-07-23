package com.poc.time_report.utils;

import static com.poc.time_report.utils.Constants.SAMPLE_ROLE_ADMIN;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

  public static boolean isCurrentUserAdmin() {
    return SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities()
        .stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
            .equals("ROLE_" + SAMPLE_ROLE_ADMIN));
  }
}
