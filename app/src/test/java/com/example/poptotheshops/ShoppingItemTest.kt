package com.example.poptotheshops

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ShoppingItemTest {

    @Test
    fun shoppingItemStoresNameCorrectly() {
        // Arrange
        val expectedName = "Milk"

        // Act
        val item = ShoppingItem(name = expectedName)

        // Assert
        assertEquals(expectedName, item.name)
    }

    @Test
    fun shoppingItemRejectsBlankName() {
        assertThrows(IllegalArgumentException::class.java) {
            ShoppingItem(name = "")
        }
    }

    @Test
    fun shoppingItemRejectsSpacesOnly() {
        assertThrows(IllegalArgumentException::class.java) {
            ShoppingItem(name = "   ")
        }
    }

    @Test
    fun shoppingItemAcceptsThirtyCharacterName() {
        // Arrange
        val validName = "This is thirty characters long" // Exactly 30 characters

        // Act
        val item = ShoppingItem(name = validName)

        // Assert
        assertEquals(validName, item.name)
    }

    @Test
    fun shoppingItemRejectsNameLongerThanThirtyCharacters() {
        val invalidName = "Its over thirty characters long" // 31+ characters
        assertThrows(IllegalArgumentException::class.java) {
            ShoppingItem(name = invalidName)
        }
    }
}