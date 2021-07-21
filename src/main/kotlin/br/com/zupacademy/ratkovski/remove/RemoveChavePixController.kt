package br.com.zupacademy.ratkovski.remove

import br.com.zupacademy.ratkovski.DetailsKeyPixRequest
import br.com.zupacademy.ratkovski.PixKeyManagerServiceGrpc
import br.com.zupacademy.ratkovski.RemoveKeyPixRequest
import br.com.zupacademy.ratkovski.RemovePixKeyManagerServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.LoggerFactory

import java.util.*


@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(
    private val deletaChavePixClient:
    RemovePixKeyManagerServiceGrpc.RemovePixKeyManagerServiceBlockingStub
) {
    private val Logger = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun deleta(clienteId:UUID,
               pixId:UUID):HttpResponse<Any>{
        Logger.info("[$clienteId] removendo uma chave pix com $pixId")
        deletaChavePixClient.remove(RemoveKeyPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build())
        return HttpResponse.ok()
    }
}