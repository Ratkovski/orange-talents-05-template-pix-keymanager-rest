package br.com.zupacademy.ratkovski.registra

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import br.com.caelum.stella.validation.CPFValidator

import br.com.zupacademy.ratkovski.AccountType
import br.com.zupacademy.ratkovski.KeyType
import br.com.zupacademy.ratkovski.RegisterKeyPixRequest
import br.com.zupacademy.ratkovski.validation.ValidPixKey
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@ValidPixKey
@Introspected
data class NovaChavePixRequest(
    @field:NotNull val tipoDeConta: TipoDeContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoDeChave: TipoDeChaveRequest?
) {


    fun paraModeloGrpc(clienteId: UUID): RegisterKeyPixRequest {

        return RegisterKeyPixRequest.newBuilder()

            .setClienteId(clienteId.toString())
            .setTipoConta(tipoDeConta?.atributoGrpc ?: AccountType.UNKNOWN_ACCOUNT)
            .setChave(chave ?: "")
            .setTipoChave(tipoDeChave?.atributoGrpc ?: KeyType.UNKNOWN_KEY)


            .build()

    }

}

enum class TipoDeChaveRequest(val atributoGrpc: KeyType) {

    CPF(KeyType.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            if (!chave.matches("^[0-9]{11}\$+".toRegex())) {
                return false
            }
//                return CPFValidator().run{
//                    initialize(null)
//                    isValid(chave,null)
            return CPFValidator(false).invalidMessagesFor(chave).isEmpty()
        }
    },
    PHONE(KeyType.PHONE) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(KeyType.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    RANDOM(KeyType.RANDOM) {
        override fun valida(chave: String?) = chave.isNullOrBlank()

    };

    abstract fun valida(chave: String?): Boolean
}


enum class TipoDeContaRequest(val atributoGrpc: AccountType) {
    CONTA_CORRENTE(AccountType.CONTA_CORRENTE),
    CONTA_POUPANCA(AccountType.CONTA_POUPANCA)


}



