package dev.lynx.secure.method.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class SecuredUserController {

    /** With @Secured ROLE_ prefix is mandatory **/
    @Secured(value = {"ADMIN"})
    /** With @PreAuthorize ROLE_ prefix is optional @PreAuthorize("hasRole('ROLE_ADMIN')") and @PreAuthorize("hasRole('ADMIN')") both works. **/
//    @PreAuthorize("hasRole('ACCT')")
    @GetMapping("/access-error")
    public String adminRoleWithoutRolePrefix(HttpServletRequest httpServletRequest) {
        log.info("SessionId is: {} ", httpServletRequest.getSession().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object : {} ", authentication);
        return authentication.getName();
    }

    @GetMapping("/admin-role")
    @Secured(value = {"ROLE_ADMIN"})
    public String adminRole(HttpServletRequest httpServletRequest) {
        log.info("SessionId is: {} ", httpServletRequest.getSession().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object : {} ", authentication);
        return authentication.getName();
    }

    @GetMapping("/user-role")
    @Secured({"ROLE_USER"})
    public String userRole(HttpServletRequest httpServletRequest) {
        log.info("SessionId is: {} ", httpServletRequest.getSession().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object : {} ", authentication);
        return authentication.getName();
    }


    @GetMapping("/acct-role")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_ACCT"})  // equates to (ROLE_ADMIN || ROLE_USER || ROLE_ACCT)
    public String acctRole(HttpServletRequest httpServletRequest) {
        log.info("SessionId is: {} ", httpServletRequest.getSession().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object : {} ", authentication);
        return authentication.getName();
    }
}
