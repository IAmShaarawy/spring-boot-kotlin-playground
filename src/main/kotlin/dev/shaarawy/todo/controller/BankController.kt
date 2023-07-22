package dev.shaarawy.todo.controller

import dev.shaarawy.todo.model.Bank
import dev.shaarawy.todo.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class BankController(private val bankService: BankService) {

    @ExceptionHandler(NoSuchElementException::class)// can be replaced by returning null from the end point method
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping("/banks")
    fun getBanks(): Collection<Bank> = bankService.getBanks()

    @GetMapping("/banks/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank = bankService.getBank(accountNumber)
}