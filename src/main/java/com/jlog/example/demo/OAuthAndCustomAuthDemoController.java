package com.jlog.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OAuthAndCustomAuthDemoController {

    @PreAuthorize("hasRole('USER_ROLE')")
    @GetMapping("/me")
    public Mono<Authentication> index() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication);
    }
}
