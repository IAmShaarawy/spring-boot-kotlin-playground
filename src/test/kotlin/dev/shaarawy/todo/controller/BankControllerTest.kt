package dev.shaarawy.todo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.shaarawy.todo.model.Bank
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET /api/banks")

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // given / when
            mockMvc.get("/api/banks")
                .andDo { log() }
                // then
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with the given account number`() {
            // given
            val accountNumber = "1"

            // when
            mockMvc.get("/api/banks/$accountNumber")
                // then
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(3.14) }
                    jsonPath("$.transactionFee") { value(17) }
                }


        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // given
            val accountNumber = 12

            // when
            mockMvc.get("/api/banks/$accountNumber")

                // then
                .andExpect { status { isNotFound() } }

        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        private lateinit var newBank: Bank
        lateinit var newBankJsonString: String

        @BeforeAll
        fun setup() {
            newBank = Bank("acc123", 31.415, 2)
            newBankJsonString = objectMapper.writeValueAsString(newBank)
        }

        @Test
        fun `should add new bank`() {
            // when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = newBankJsonString
            }
            // then
            performPost.andExpect {
                status { isCreated() }
                content {
                    json(newBankJsonString)
                }
            }

        }

        @Test
        fun `should get the added bank with id`() {
            // given
            mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = newBankJsonString
            }

            // when
            val performGetAddedBank = mockMvc.get("/api/banks/${newBank.accountNumber}")

            // then
            performGetAddedBank.andExpect {
                status { isOk() }
                content { json(newBankJsonString) }
            }

        }
        
        @Test
        fun `should return BAD REQUEST if account number already exits`(){
            // given
            mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = newBankJsonString
            }
            
            // when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = newBankJsonString
            }

            
            // then
            performPost.andExpect {
                status { isBadRequest() }
            }

            
        }
    }
}