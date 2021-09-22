package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem

@Composable
fun RecentScanItem(
    qrItem: QRItem,
    onCardClick: (QRItem) -> Unit,
    onEditQRClick: (QRItem) -> Unit,
    onDeleteQRClick: (QRItem) -> Unit
) {

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
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
                    .wrapContentWidth(Alignment.Start)
                    .align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = { onEditQRClick(qrItem) },
                modifier = Modifier.wrapContentWidth(Alignment.End)
            ) {
                Icon(
                    Icons.Filled.Edit,
                    stringResource(id = R.string.qr_item_edit_qr_accessibility),
                    tint = Color.Blue
                )
            }
            IconButton(
                onClick = { onDeleteQRClick(qrItem) },
                modifier = Modifier.wrapContentWidth(Alignment.End)
            ) {
                Icon(
                    Icons.Filled.Delete,
                    stringResource(id = R.string.qr_item_delete_qr_accessibility),
                    tint = Color.Red
                )
            }
        }
    }
}