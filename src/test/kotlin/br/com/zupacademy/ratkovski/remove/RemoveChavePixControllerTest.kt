package br.com.zupacademy.ratkovski.remove

import br.com.zupacademy.ratkovski.PixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.RegisterKeyPixResponse
import br.com.zupacademy.ratkovski.RemoveKeyPixResponse
import br.com.zupacademy.ratkovski.RemovePixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.factory.KeyManagerGrpcFactory
import br.com.zupacademy.ratkovski.registra.NovaChavePixRequest
import br.com.zupacademy.ratkovski.registra.TipoDeChaveRequest
import br.com.zupacademy.ratkovski.registra.TipoDeContaRequest
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
internal class RemoveChavePixControllerTest {
    @field:Inject
    lateinit var removeStub: RemovePixKeyManagerServiceGrpc.RemovePixKeyManagerServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve remover uma chave pix existente`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = RemoveKeyPixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()
        given(removeStub.remove(any())).willReturn(respostaGrpc)


        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)

    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class RemoveStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(RemovePixKeyManagerServiceGrpc.RemovePixKeyManagerServiceBlockingStub::class.java)
    }

}