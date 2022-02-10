package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@PreAuthorize("isAuthenticated()")
@Retention(RUNTIME)
@Target(METHOD)
public @interface PodeConsultarCozinhas {
}
