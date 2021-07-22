package br.com.zupacademy.ratkovski.carrega

import br.com.zupacademy.ratkovski.*
import br.com.zupacademy.ratkovski.factory.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CarregaChavePixControllerTest {

    @field:Inject
    lateinit var carregaChaveStub: DetailsPixKeyManagerServiceGrpc.DetailsPixKeyManagerServiceBlockingStub

    @field:Inject
    lateinit var listaChaveStub: ListaPixKeyManagerServiceGrpc.ListaPixKeyManagerServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    val CHAVE_EMAIL = "teste@teste.com.br"
    val CHAVE_CELULAR = "+5511912345678"
    val CONTA_CORRENTE = AccountType.CONTA_CORRENTE
    val TIPO_DE_CHAVE_EMAIL = KeyType.EMAIL
    val TIPO_DE_CHAVE_CELULAR = KeyType.PHONE
    val INSTITUICAO = "Itau"
    val TITULAR = "Ratkovski"
    val DOCUMENTO_DO_TITULAR = "34597563067"
    val AGENCIA = "0001"
    val NUMERO_DA_CONTA = "1010-1"
    val CHAVE_CRIADA_EM = LocalDateTime.now()

    @Test
    internal fun `deve carregar uma chave pix existente`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        given(carregaChaveStub.carrega(Mockito.any())).willReturn(carregaChavePixResponse(clienteId, pixId))

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertNotNull(response.body())
    }

    private fun carregaChavePixResponse(clienteId: String, pixId: String)=
            DetailsKeyPixResponse.newBuilder()
                .setClienteId(clienteId)
                .setPixId(pixId)
                .setChave(DetailsKeyPixResponse.ChavePix.newBuilder()
                    .setTipo(TIPO_DE_CHAVE_EMAIL)
                    .setChave(CHAVE_EMAIL)
                    .setConta(DetailsKeyPixResponse.ChavePix.ContaInfo.newBuilder()
                        .setTipo(CONTA_CORRENTE)
                        .setInstituicao(INSTITUICAO)
                        .setNomeDoTitular(TITULAR)
                        .setCpfDoTitular(DOCUMENTO_DO_TITULAR)
                        .setAgencia(AGENCIA)
                        .setNumeroDaConta(NUMERO_DA_CONTA)
                        .build())
                    .setCriadaEm(CHAVE_CRIADA_EM.let {
                        val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createdAt.epochSecond)
                            .setNanos(createdAt.nano)
                            .build()
                    }))
                    .build()








    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubListaMock() = Mockito.mock(ListaPixKeyManagerServiceGrpc.ListaPixKeyManagerServiceBlockingStub::class.java)

        @Singleton
        fun stubDetalhesMock() = Mockito.mock(DetailsPixKeyManagerServiceGrpc.DetailsPixKeyManagerServiceBlockingStub::class.java)
    }

}