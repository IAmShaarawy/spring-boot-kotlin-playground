package dev.shaarawy.todo.service

import dev.shaarawy.todo.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class BankServiceTest {

    private val dataSource = mockk<BankDataSource>(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should cal its data source to retrieve data`() {
        // given

        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { dataSource.retrieveBanks() }


    }
}