package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.exception.BadRequestException
import com.gilvano.comprasapi.helper.buildRevendedor
import com.gilvano.comprasapi.repository.RevendedorRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
internal class RevendedorServiceImplTest{

    @MockK
    private lateinit var revendedorRepository: RevendedorRepository

    @InjectMockKs
    private lateinit var revendedorService: RevendedorServiceImpl

    @Test
    fun `deve criar um novo revendedor`() {
        val revendedor = buildRevendedor()

        every { revendedorRepository.existsByCpf(revendedor.cpf) } returns false
        every { revendedorRepository.existsByEmail(revendedor.email) } returns false
        every { revendedorRepository.save(revendedor) } returns revendedor

        revendedorService.create(revendedor)

        verify(exactly = 1) { revendedorRepository.existsByCpf(revendedor.cpf) }
        verify(exactly = 1) { revendedorRepository.existsByEmail(revendedor.email) }
        verify(exactly = 1) { revendedorRepository.save(revendedor) }
    }

    @Test
    fun `deve retornar uma BadRequestException quando o CPF existir`() {
        val cpf = Random.nextInt().toString()
        val email = "email@email.com"
        val revendedor = buildRevendedor(cpf = cpf, email = email)

        every { revendedorRepository.existsByCpf(cpf) } returns true
        every { revendedorRepository.existsByEmail(email) } returns false

        val error = assertThrows<BadRequestException>{
            revendedorService.create(revendedor)
        }

        Assertions.assertEquals("CPF já cadastrado", error.message)
        Assertions.assertEquals("CP001", error.errorCode)

        verify(exactly = 1) { revendedorRepository.existsByCpf(cpf) }
        verify(exactly = 0) { revendedorRepository.existsByEmail(email) }
        verify(exactly = 0) { revendedorRepository.save(any()) }
    }

    @Test
    fun `deve retornar uma BadRequestException quando o E-mail existir`() {
        val cpf = Random.nextInt().toString()
        val email = "email@email.com"
        val revendedor = buildRevendedor(cpf = cpf, email = email)

        every { revendedorRepository.existsByCpf(cpf) } returns false
        every { revendedorRepository.existsByEmail(email) } returns true

        val error = assertThrows<BadRequestException>{
            revendedorService.create(revendedor)
        }

        Assertions.assertEquals("E-mail já cadastrado", error.message)
        Assertions.assertEquals("CP002", error.errorCode)

        verify(exactly = 1) { revendedorRepository.existsByCpf(cpf) }
        verify(exactly = 1) { revendedorRepository.existsByEmail(email) }
        verify(exactly = 0) { revendedorRepository.save(any()) }
    }
}