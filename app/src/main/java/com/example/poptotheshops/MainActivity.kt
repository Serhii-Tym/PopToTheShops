package com.example.poptotheshops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.poptotheshops.ui.theme.PopToTheShopsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            ShoppingDatabase::class.java,
            "shopping_database"
        ).build()

        setContent {
            PopToTheShopsTheme {
                ShoppingListScreen(database)
            }
        }
    }
}

@Composable
fun ShoppingListScreen(
    database: ShoppingDatabase
) {
    var itemName by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val unknownErrorMessage = stringResource(R.string.unknown_error)
    val coroutineScope = rememberCoroutineScope()

    val shoppingListManager = remember {
        ShoppingListManager(
            shoppingItemDao = database.shoppingItemDao(),
            coroutineScope = coroutineScope
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        // Step 4: Title styling[cite: 89]
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
        )

        // Step 5: Spacing rhythm[cite: 94]
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = itemName,
            onValueChange = {
                itemName = it
            },
            label = {
                Text(
                    text = stringResource(id = R.string.shopping_item),
                )
            },
            isError = errorMessage.isNotEmpty(),
            supportingText = {
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                try {
                    shoppingListManager.addItem(
                        ShoppingItem(
                            name = itemName
                        )
                    )
                    itemName = ""
                    errorMessage = ""
                } catch (exception: IllegalArgumentException) {
                    errorMessage =
                        exception.message ?: unknownErrorMessage
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.add),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        val count = shoppingListManager.shoppingItems.size
        val countText = if (count == 1) "1 item" else "$count items"
        Text(
            text = countText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (shoppingListManager.shoppingItems.isEmpty()) {
            // Step 6: Designed Empty State[cite: 96, 97]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.empty_list_title),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null, // Decorative icon
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.empty_list_message),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        } else {
            // Step 7: LazyColumn with spaced items[cite: 103]
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(shoppingListManager.shoppingItems) { shoppingItem ->
                    ShoppingListItem(
                        shoppingItem = shoppingItem,
                    ) {
                        shoppingListManager.removeItem(shoppingItem)
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    shoppingItem: ShoppingItem,
    onDelete: () -> Unit,
) {
    // Step 7: Cards & IconButton[cite: 101, 104]
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = shoppingItem.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDelete,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}