package br.com.zupacademy.ratkovski.validation


import br.com.zupacademy.ratkovski.registra.NovaChavePixRequest

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext

import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidator::class])
annotation class ValidPixKey(
    val message: String = "chave Pix inválida (\${validatedValue.tipoDeChave})",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)


@Singleton
class ValidPixKeyValidator: ConstraintValidator<ValidPixKey, NovaChavePixRequest> {
    override fun isValid(
        value: NovaChavePixRequest?,
        //annotationMetadata: AnnotationValue<ValidPixKey>,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext,
    ): Boolean {

        // must be validated with @NotNull
        if (value?.tipoDeChave == null) {
            return true
        }

        return value.tipoDeChave.valida(value.chave)
    }
}
