package dev.shaarawy.todo

import com.fasterxml.jackson.annotation.JsonIgnore

data class Error(
    @JsonIgnore val throwable: Throwable
) {
    val message = throwable.message
}