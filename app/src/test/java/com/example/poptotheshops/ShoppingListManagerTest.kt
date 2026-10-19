package com.example.poptotheshops

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ShoppingListManagerTest {

    private lateinit var shoppingListManager: ShoppingListManager

    @Before
    fun setUp() {
        shoppingListManager = ShoppingListManager()
    }

    @Test
    fun addItem_validItem_addsToList() {
        val item = ShoppingItem("Apples",)
        shoppingListManager.addItem(item)

        assertEquals(1, shoppingListManager.shoppingItems.size)
        assertEquals("Apples", shoppingListManager.shoppingItems[0].name)
    }

    @Test(expected = IllegalArgumentException::class)
    fun addItem_emptyItemName_throwsException() {
        val item = ShoppingItem("",)
        shoppingListManager.addItem(item)
    }

    @Test(expected = IllegalArgumentException::class)
    fun addItem_blankItemName_throwsException() {
        val item = ShoppingItem("   ",)
        shoppingListManager.addItem(item)
    }

    @Test
    fun removeItem_existingItem_removesFromList() {
        val item1 = ShoppingItem("Apples",)
        val item2 = ShoppingItem("Milk",)
        shoppingListManager.addItem(item1)
        shoppingListManager.addItem(item2)

        shoppingListManager.removeItem(item1)

        assertEquals(1, shoppingListManager.shoppingItems.size)
        assertFalse(shoppingListManager.shoppingItems.contains(item1))
        assertTrue(shoppingListManager.shoppingItems.contains(item2))
    }
}
