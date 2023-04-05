package br.com.projetointegrador.wine.context.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfUnicoValidator.class)
public @interface CpfUnico {

    String message() default "CPF já cadastrado!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}