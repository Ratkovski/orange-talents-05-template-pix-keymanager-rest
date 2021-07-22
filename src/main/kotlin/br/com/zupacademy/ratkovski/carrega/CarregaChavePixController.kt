package br.com.zupacademy.ratkovski.carrega


import br.com.zupacademy.ratkovski.*

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory

import java.util.*


@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController (
    val carregaChavePixClient: DetailsPixKeyManagerServiceGrpc.DetailsPixKeyManagerServiceBlockingStub,
    val listaChavePixClient: ListaPixKeyManagerServiceGrpc.ListaPixKeyManagerServiceBlockingStub) {

    private val Logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun carrega(
        clienteId: UUID,
        pixId: UUID
    ): HttpResponse<Any> {
        Logger.info("[$clienteId]carrega chave pix por id: $pixId")
        val chaveResponse = carregaChavePixClient.carrega(
            DetailsKeyPixRequest.newBuilder()
                .setPixId(
                    DetailsKeyPixRequest.FiltroPorPixId.newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId.toString())

                .build())
                .build())

          return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
    }

  
}
