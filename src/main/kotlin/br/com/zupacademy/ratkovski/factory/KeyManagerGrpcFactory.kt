package br.com.zupacademy.ratkovski.factory

import br.com.zupacademy.ratkovski.DetailsPixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.ListaPixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.PixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.RemovePixKeyManagerServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager")val channel: ManagedChannel) {

    @Singleton
    fun registraChave()= PixKeyManagerServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave()= RemovePixKeyManagerServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave()= ListaPixKeyManagerServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave()= DetailsPixKeyManagerServiceGrpc.newBlockingStub(channel)

}