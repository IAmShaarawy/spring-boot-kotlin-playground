package dev.shaarawy.todo.controller

import dev.shaarawy.todo.Error
import dev.shaarawy.todo.model.Bank
import dev.shaarawy.todo.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class BankController(private val bankService: BankService) {

    @ExceptionHandler(NoSuchElementException::class)// can be replaced by returning null from the end point method
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<Error> =
        ResponseEntity(Error(e), HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)// can be replaced by returning null from the end point method
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<Error> =
        ResponseEntity(Error(e), HttpStatus.BAD_REQUEST)


    @GetMapping("/banks")
    fun getBanks(): Collection<Bank> = bankService.getBanks()

    @GetMapping("/banks/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank = bankService.getBank(accountNumber)

    @PostMapping("/banks")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank): Bank = bankService.addBank(bank)
}