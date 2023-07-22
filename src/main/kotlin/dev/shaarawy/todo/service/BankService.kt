package dev.shaarawy.todo.service

import dev.shaarawy.todo.datasource.BankDataSource
import dev.shaarawy.todo.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank {
        return dataSource.retrieveBank(accountNumber)
    }

    fun addBank(bank: Bank): Bank {
        return dataSource.addBank(bank)
    }
}