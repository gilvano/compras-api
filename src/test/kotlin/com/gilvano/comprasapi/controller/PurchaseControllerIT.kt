package com.gilvano.comprasapi.controller

import com.gilvano.comprasapi.helper.buildReseller
import com.gilvano.comprasapi.repository.PurchaseRepository
import com.gilvano.comprasapi.repository.ResellerRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gilvano.comprasapi.controller.response.LoginResponse


@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PurchaseControllerIT{

    @Autowired
    private lateinit var resellerRepository: ResellerRepository

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var bCrypt: BCryptPasswordEncoder

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val resellerCpf = "02718404043"
    private val resellerName = "Reseller"
    private val resellerEmail = "r1@mail.com"
    private val resellerPassword = "123456"
    private var token: String = ""

    @BeforeAll
    fun setup() {
        resellerRepository.save(
            buildReseller(
                name = resellerName,
                cpf = resellerCpf,
                email = resellerEmail,
                password = bCrypt.encode(resellerPassword),
            )
        )
        token = login(resellerEmail, resellerPassword)
    }

    @AfterAll
    fun tearDown() {
        resellerRepository.deleteAll()
    }

    @AfterEach
    fun tearDownEach() {
        purchaseRepository.deleteAll()
    }

    @Test
    fun `Assert that the purchase is created with reseller cpf`() {
        mockMvc.perform(
            post("/api/v1/purchases")
                .contentType("application/json")
                .content(
                    """
                    {
                        "id": 1,
                        "date": "2022-01-02",
                        "value": 1000.0
                    }
                    """.trimIndent()
                )
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isCreated)

        purchaseRepository.findById(1).get().let {
            assert(it.cpf == resellerCpf)
            assert(it.cashback == 100.0)
            assert(it.cashbackPercentage == 10.0)
        }
    }

    @Test
    fun `Assert that two purchases are created and that the cashback is fifteen percent`() {
        mockMvc.perform(
            post("/api/v1/purchases")
                .contentType("application/json")
                .content(
                    """
                    {
                        "id": 1,
                        "date": "2022-01-02",
                        "value": 1000.0
                    }
                    """.trimIndent()
                )
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isCreated)

        mockMvc.perform(
            post("/api/v1/purchases")
                .contentType("application/json")
                .content(
                    """
                    {
                        "id": 2,
                        "date": "2022-01-02",
                        "value": 200.0
                    }
                    """.trimIndent()
                )
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isCreated)

        purchaseRepository.findById(1).get().let {
            assert(it.cpf == resellerCpf)
            assert(it.cashback == 150.0)
            assert(it.cashbackPercentage == 15.0)
        }

        purchaseRepository.findById(2).get().let {
            assert(it.cpf == resellerCpf)
            assert(it.cashback == 30.0)
            assert(it.cashbackPercentage == 15.0)
        }
    }

    @Test
    fun `Assert that two purchases are created in different months and that their cashback is different`() {
        mockMvc.perform(
            post("/api/v1/purchases")
                .contentType("application/json")
                .content(
                    """
                    {
                        "id": 1,
                        "date": "2022-01-02",
                        "value": 2000.0
                    }
                    """.trimIndent()
                )
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isCreated)

        mockMvc.perform(
            post("/api/v1/purchases")
                .contentType("application/json")
                .content(
                    """
                    {
                        "id": 2,
                        "date": "2022-02-02",
                        "value": 200.0
                    }
                    """.trimIndent()
                )
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isCreated)

        purchaseRepository.findById(1).get().let {
            assert(it.cpf == resellerCpf)
            assert(it.cashback == 400.0)
            assert(it.cashbackPercentage == 20.0)
        }

        purchaseRepository.findById(2).get().let {
            assert(it.cpf == resellerCpf)
            assert(it.cashback == 20.0)
            assert(it.cashbackPercentage == 10.0)
        }
    }

    private fun login(username: String, password: String): String{
        val response = mockMvc.perform(
            post("/api/v1/resellers/login")
                .contentType("application/json")
                .content(
                    """
                    {
                        "email": "$username",
                        "senha": "$password"
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        val loginResponse = jacksonObjectMapper().readValue(response, LoginResponse::class.java)

        return loginResponse.token
    }
}