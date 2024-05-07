package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.transaction.viewmodel.WalletListDropdownViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletListDropdown(
    viewModel: WalletListDropdownViewModel,
    onSelected: (Int) -> Unit,
    modifier: Modifier
) {
    val viList = viewModel.walletList

    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Tất cả") }

    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor(),
                textStyle = TextStyle(fontSize = 14.sp)
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = "Tất cả", fontSize = 14.sp)
                    },
                    onClick = {
                        selectedItem = "Tất cả"
                        onSelected(0)
                        isExpanded = false
                    }
                )

                viList.forEach { viModel ->
                    DropdownMenuItem(
                        text = {
                            Text(text = viModel.ten, fontSize = 14.sp)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.AccountBalanceWallet,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                        },
                        onClick = {
                            selectedItem = viModel.ten
                            onSelected(viModel.id)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}