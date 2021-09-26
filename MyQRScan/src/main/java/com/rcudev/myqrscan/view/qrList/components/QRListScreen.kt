package com.rcudev.myqrscan.view.qrList.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel

@Composable
fun QRListScreen(
    context: Activity,
    viewModel: QRListViewModel
) {
    val state = viewModel.state.value
    val invalidUrlString = stringResource(id = R.string.qr_item_invalid_url)
    var qrToEdit: QRItem by rememberSaveable { mutableStateOf(QRItem()) }
    var qrToDelete: QRItem by rememberSaveable { mutableStateOf(QRItem()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon_splash),
            contentDescription = null,
            modifier = Modifier
                .width(256.dp)
                .height(256.dp)
                .align(Alignment.Center)
                .alpha(0.1f),
        )
        if (state.error.isBlank()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.Top)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    items(state.qrList.reversed()) { qrItem ->
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
                                    startActivity(context, openURL, null)
                                } else {
                                    Toast.makeText(context, invalidUrlString, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            },
                            onEditQRClick = {
                                qrToEdit = qrItem
                                state.showEditDialog.value = true
                            },
                            onDeleteQRClick = {
                                qrToDelete = qrItem
                                state.showDeleteDialog.value = true
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        val qrScanner = IntentIntegrator(context)
                        qrScanner.setBarcodeImageEnabled(true)
                        qrScanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                        qrScanner.setBeepEnabled(true)
                        qrScanner.initiateScan()
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.Red900),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.Bottom)
                        .fillMaxWidth(), shape = RoundedCornerShape(0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.qr_list_scan),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(5.dp, end = 10.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_qr_icon),
                            contentDescription = null
                        )
                    }
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = stringResource(id = R.string.qr_list_error),
                color = MaterialTheme.colors.error,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        } else if (state.qrList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.qr_list_help_text),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    QREditDialog(viewModel = viewModel, qrToEdit = qrToEdit)
    QRDeleteDialog(viewModel = viewModel, qrToDelete = qrToDelete)
}