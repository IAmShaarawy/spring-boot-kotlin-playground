package dev.shaarawy.todo.datasource.mock

import dev.shaarawy.todo.datasource.BankDataSource
import dev.shaarawy.todo.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    private val banks = listOf(
            Bank("1", 3.14, 17),
            Bank("2", 17.0, 0),
            Bank("3", 0.0, 100),
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull { it.accountNumber == accountNumber } ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
    }
}