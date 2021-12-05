package com.rcudev.myqrscan.view.qrList.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.qrList.SHARE_QR_TYPE
import com.rcudev.myqrscan.view.qrList.components.listItems.QRListItem

@Composable
fun QRList(
    context: Activity,
    viewModel: QRListViewModel,
    onOpenUrlFailed: (QRItem) -> Unit,
    onViewQRImageClick: (QRItem) -> Unit,
    onEditButtonClick: (QRItem) -> Unit,
    onDeleteButtonClick: (QRItem) -> Unit,
) {
    val shareQRString = stringResource(id = R.string.qr_item_share_qr_accessibility)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        items(viewModel.qrList.value.reversed()) { qrItem ->
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            QRListItem(
                qrItem = qrItem,
                onCardClick = {
                    try {
                        if (Patterns.WEB_URL.matcher(qrItem.url ?: "").matches()) {
                            var urlToOpen = qrItem.url
                            if (urlToOpen?.contains("http://") == false && !urlToOpen.contains("https://")) {
                                urlToOpen = "http://${urlToOpen}"
                            }

                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data = Uri.parse(urlToOpen)
                            ContextCompat.startActivity(context, openURL, null)
                        } else {
                            onOpenUrlFailed(qrItem)
                        }
                    } catch (e: Exception) {
                        onOpenUrlFailed(qrItem)
                    }
                },
                onViewQRImageClick = {
                    onViewQRImageClick(qrItem)
                },
                onEditQRClick = {
                    onEditButtonClick(qrItem)
                },
                onShareQRClick = {
                    ShareCompat.IntentBuilder(context)
                        .setType(SHARE_QR_TYPE)
                        .setChooserTitle(shareQRString)
                        .setText(qrItem.url)
                        .startChooser()
                },
                onDeleteQRClick = {
                    onDeleteButtonClick(qrItem)
                }
            )
        }
    }
}