package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem

@Composable
fun RecentScanItem(
    qrItem: QRItem,
    onCardClick: (QRItem) -> Unit,
    onEditQRClick: () -> Unit,
    onShareQRClick: () -> Unit,
    onDeleteQRClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(qrItem) },
        elevation = 6.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = qrItem.name ?: stringResource(id = R.string.qr_item_empty_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
                    .wrapContentWidth(Alignment.Start)
                    .align(Alignment.CenterVertically),
            )

            Box(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
            ) {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Localized description")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    IconButton(
                        onClick = {
                            expanded = false
                            onEditQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_edit_button),
                            stringResource(id = R.string.qr_item_edit_qr_accessibility),
                        )
                    }
                    IconButton(
                        onClick = {
                            expanded = false
                            onShareQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_share_button),
                            stringResource(id = R.string.qr_item_edit_qr_accessibility),
                        )
                    }
                    Divider()
                    IconButton(
                        onClick = {
                            expanded = false
                            onDeleteQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_delete_button),
                            stringResource(id = R.string.qr_item_delete_qr_accessibility),
                        )
                    }
                }
            }

        }
    }
}