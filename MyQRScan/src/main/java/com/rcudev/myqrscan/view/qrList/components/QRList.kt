package com.rcudev.myqrscan.view.qrList.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
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
import com.rcudev.myqrscan.view.qrList.components.listItems.RecentScanItem

@Composable
fun QRList(
    context: Activity,
    viewModel: QRListViewModel,
    onOpenUrlFailed: () -> Unit,
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
            RecentScanItem(
                qrItem = qrItem,
                onCardClick = {
                    if (URLUtil.isValidUrl(qrItem.url)) {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(qrItem.url)
                        ContextCompat.startActivity(context, openURL, null)
                    } else {
                        onOpenUrlFailed()
                    }
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