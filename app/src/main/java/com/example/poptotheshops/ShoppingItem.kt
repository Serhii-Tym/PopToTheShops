package com.example.poptotheshops

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException(
                "Please enter a shopping item."
            )
        }
        if (name.length > 30) {
            throw IllegalArgumentException(
                "Shopping item must be 30 characters or fewer."
            )
        }
    }
}