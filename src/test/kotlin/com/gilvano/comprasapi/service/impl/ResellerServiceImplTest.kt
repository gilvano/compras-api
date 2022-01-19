package com.gilvano.comprasapi.service.impl

import com.gilvano.comprasapi.exception.BadRequestException
import com.gilvano.comprasapi.helper.buildReseller
import com.gilvano.comprasapi.repository.ResellerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.UUID
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
internal class ResellerServiceImplTest{

    @MockK
    private lateinit var resellerRepository: ResellerRepository

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var resellerService: ResellerServiceImpl

    @Test
    fun `should create a reseller and encrypt password`() {
        val initialPassword = java.util.Random().nextInt().toString()
        val reseller = buildReseller(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val resellerEncrypted = reseller.copy(password = fakePassword)

        every { resellerRepository.existsByCpf(reseller.cpf) } returns false
        every { resellerRepository.existsByEmail(reseller.email) } returns false
        every { resellerRepository.save(resellerEncrypted) } returns reseller
        every { bCrypt.encode(initialPassword) } returns fakePassword

        resellerService.create(reseller)

        verify(exactly = 1) { resellerRepository.existsByCpf(reseller.cpf) }
        verify(exactly = 1) { resellerRepository.existsByEmail(reseller.email) }
        verify(exactly = 1) { resellerRepository.save(resellerEncrypted) }
        verify(exactly = 1) { bCrypt.encode(initialPassword) }
    }

    @Test
    fun `should throw bad request when cpf exists`() {
        val cpf = Random.nextInt().toString()
        val email = "email@email.com"
        val reseller = buildReseller(cpf = cpf, email = email)

        every { resellerRepository.existsByCpf(cpf) } returns true
        every { resellerRepository.existsByEmail(email) } returns false

        val error = assertThrows<BadRequestException>{
            resellerService.create(reseller)
        }

        Assertions.assertEquals("CPF already registered", error.message)
        Assertions.assertEquals("CP001", error.errorCode)

        verify(exactly = 1) { resellerRepository.existsByCpf(cpf) }
        verify(exactly = 0) { resellerRepository.existsByEmail(email) }
        verify(exactly = 0) { resellerRepository.save(any()) }
    }

    @Test
    fun `should throw bad request when e-mail exists`() {
        val cpf = Random.nextInt().toString()
        val email = "email@email.com"
        val reseller = buildReseller(cpf = cpf, email = email)

        every { resellerRepository.existsByCpf(cpf) } returns false
        every { resellerRepository.existsByEmail(email) } returns true

        val error = assertThrows<BadRequestException>{
            resellerService.create(reseller)
        }

        Assertions.assertEquals("E-mail already registered", error.message)
        Assertions.assertEquals("CP002", error.errorCode)

        verify(exactly = 1) { resellerRepository.existsByCpf(cpf) }
        verify(exactly = 1) { resellerRepository.existsByEmail(email) }
        verify(exactly = 0) { resellerRepository.save(any()) }
    }
}