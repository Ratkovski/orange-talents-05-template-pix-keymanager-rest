package br.com.zupacademy.ratkovski.carrega

import br.com.zupacademy.ratkovski.ListaKeyPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ChavePixResponse (chavePix:ListaKeyPixResponse.ChavePix){
    val pixId = chavePix.pixId
    val chave = chavePix.chave
    val tipo = chavePix.tipo
    val tipoDeConta = chavePix.tipoDeConta
    val criadaEm = chavePix.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds,it.nanos.toLong()),ZoneOffset.UTC)
    }
}