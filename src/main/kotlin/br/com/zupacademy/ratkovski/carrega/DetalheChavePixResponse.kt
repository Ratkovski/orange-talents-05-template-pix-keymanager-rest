package br.com.zupacademy.ratkovski.carrega

import br.com.zupacademy.ratkovski.AccountType
import br.com.zupacademy.ratkovski.DetailsKeyPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChavePixResponse(chaveResponse: DetailsKeyPixResponse) {
    val pixId = chaveResponse.pixId
    val tipo = chaveResponse.chave.tipo
    val chave = chaveResponse.chave.chave

    val criadaEm = chaveResponse.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds,it.nanos.toLong()), ZoneOffset.UTC)

    }
    val tipoConta = when (chaveResponse.chave.conta.tipo){
        AccountType.CONTA_CORRENTE -> "CONTA_CORRENTE"
        AccountType.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "NAO_RECONHECIDA"

    }
    val conta = mapOf(Pair("tipo",tipoConta),
        Pair("instituicao",chaveResponse.chave.conta.instituicao),
        Pair("nomeDoTitular",chaveResponse.chave.conta.nomeDoTitular),
        Pair("cpfDoTitular",chaveResponse.chave.conta.cpfDoTitular),
        Pair("agencia",chaveResponse.chave.conta.agencia),
        Pair("numero",chaveResponse.chave.conta.numeroDaConta)
    )

}
