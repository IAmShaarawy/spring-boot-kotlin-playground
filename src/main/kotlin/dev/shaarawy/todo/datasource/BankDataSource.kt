package dev.shaarawy.todo.datasource

import dev.shaarawy.todo.model.Bank

interface BankDataSource {
    fun retrieveBanks():Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
}