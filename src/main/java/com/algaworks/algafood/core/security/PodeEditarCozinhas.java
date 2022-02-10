package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
@Retention(RUNTIME)
@Target(METHOD)
public @interface PodeEditarCozinhas {
}
