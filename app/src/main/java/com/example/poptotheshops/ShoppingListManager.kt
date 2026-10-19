package com.example.poptotheshops

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ShoppingListManager(
    private val shoppingItemDao: ShoppingItemDao,
    private val coroutineScope: CoroutineScope
) {
    var shoppingItems by mutableStateOf<List<ShoppingItem>>(emptyList())
        private set

    init {
        coroutineScope.launch {
            shoppingItemDao.getAllItems().collect { storedItems ->
                shoppingItems = storedItems
            }
        }
    }

    fun addItem(shoppingItem: ShoppingItem) {
        for (listItem in shoppingItems) {
            if (shoppingItem.name == listItem.name) {
                throw IllegalArgumentException(
                    "Duplicate Item already exists in list."
                )
            }
        }
        coroutineScope.launch {
            shoppingItemDao.insertItem(shoppingItem)
        }
    }

    fun removeItem(shoppingItem: ShoppingItem) {
        coroutineScope.launch {
            shoppingItemDao.deleteItem(shoppingItem)
        }
    }
}