package br.com.zupacademy.ratkovski.registra

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class TipoDeChaveRequesTest {

    @Nested
    inner class ChaveAleatoriaTest {
        @Test
        fun `deve ser valido quando o campo de chave aleatoria for nulo ou vazio`() {
            val tipoDeChave = TipoDeChaveRequest.RANDOM
            assertTrue(tipoDeChave.valida(null))
            assertTrue(tipoDeChave.valida(""))
        }
    }

    @Test
    fun `nao deve ser valido quando o campo de chave aleatoria possuir um valor`() {
        val tipoDeChave = TipoDeChaveRequest.RANDOM

        assertFalse(tipoDeChave.valida("teste"))

    }


    @Nested
    inner class CpfTest {
        @Test
        fun `deve ser valido quando o campo cpf for valido`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            assertTrue(tipoDeChave.valida("91103941097"))


        }

        @Test
        fun `nao deve ser valido quando o campo cpf for valido`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            assertFalse(tipoDeChave.valida("9110394109"))


        }

        @Test
        fun `nao deve ser valido quando o campo cpf for nulo ou vazio`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            assertFalse(tipoDeChave.valida(null))
            assertFalse(tipoDeChave.valida(""))

        }

        @Test
        fun `nao deve ser valido quando o campo cpf possuir letras`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            assertFalse(tipoDeChave.valida("9110394102a"))


        }

    }

    @Nested
    inner class CelularTest {
        @Test
        fun `deve ser valido quando o campo celular for um numero valido`() {
            val tipoDeChave = TipoDeChaveRequest.PHONE
            assertTrue(tipoDeChave.valida("+5518918979876"))


        }

        @Test
        fun `nao deve ser valido quando o campo celular for nulo ou vazio`() {
            val tipoDeChave = TipoDeChaveRequest.PHONE
            assertFalse(tipoDeChave.valida(null))
            assertFalse(tipoDeChave.valida(""))

        }

        @Test
        fun `nao deve ser valido quando o numero do celular for invalido`() {
            val tipoDeChave = TipoDeChaveRequest.PHONE
            assertFalse(tipoDeChave.valida("5518918979876090"))
            assertFalse(tipoDeChave.valida("+55189189a"))

        }
    }

    @Nested
    inner class EmailTest {
        @Test
        fun `deve ser valido quando o campo email possuir um formato  valido`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            assertTrue(tipoDeChave.valida("ratkovski@email.com"))


        }

        @Test
        fun `nao deve ser valido quando o campo email for nulo ou vazio`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            assertFalse(tipoDeChave.valida(null))
            assertFalse(tipoDeChave.valida(""))

        }

        @Test
        fun `nao deve ser valido quando o campo email possuir formato invalido`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            assertFalse(tipoDeChave.valida("ratkovskiemail.com"))
            assertFalse(tipoDeChave.valida("ratkovski@email.com."))

        }
    }
}

