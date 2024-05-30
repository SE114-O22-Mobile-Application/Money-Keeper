package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.uit.moneykeeper.transaction.viewmodel.CategoryDropdownViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    viewModel: CategoryDropdownViewModel,
    onSelected: (Int) -> Unit,
    modifier: Modifier
) {
    val categoryList = viewModel.listLoaiGiaoDich

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
                modifier = Modifier.menuAnchor()
                    .background(Color.Transparent),
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

                DropdownMenuItem(
                    text = {
                        Text(text = "Thu", fontSize = 14.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.FileDownload,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Green
                        )
                    },
                    onClick = {
                        selectedItem = "Thu"
                        onSelected((-1))
                        isExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Chi", fontSize = 14.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.FileUpload,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Red
                        )
                    },
                    onClick = {
                        selectedItem = "Chi"
                        onSelected((-2))
                        isExpanded = false
                    }
                )

                categoryList.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(text = category.ten, fontSize = 13.sp)
                        },
                        leadingIcon = {
                            Surface(
                                shape = CircleShape,
                                color = category.mauSac
                            ) {
                                Icon(
                                    imageVector = category.icon.getIcon(),
                                    contentDescription = category.ten,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(all = 4.dp)
                                        .size(16.dp),
                                )
                            }
                        },
                        onClick = {
                            selectedItem = category.ten
                            onSelected(category.id)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}