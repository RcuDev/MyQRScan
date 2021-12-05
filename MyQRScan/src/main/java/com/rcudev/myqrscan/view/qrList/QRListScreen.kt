package com.rcudev.myqrscan.view.qrList

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanOptions
import com.rcudev.myqrscan.MyQRScanApplication
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.components.QRBottomBar
import com.rcudev.myqrscan.view.qrList.components.QRList
import com.rcudev.myqrscan.view.qrList.components.QRScanFloatingButton
import com.rcudev.myqrscan.view.qrList.components.QRTopBar
import com.rcudev.myqrscan.view.qrList.components.dialogs.*

const val SHARE_QR_TYPE = "text/plain"

@Composable
fun QRListScreen(
    application: MyQRScanApplication,
    context: Activity,
    viewModel: QRListViewModel,
    onThemeChanged: (Boolean) -> Unit,
    barcodeLauncher: ActivityResultLauncher<ScanOptions>
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    var qrImage: Bitmap? by rememberSaveable { mutableStateOf(null) }
    var qrImageToShow: QRItem by rememberSaveable { mutableStateOf(QRItem(category = viewModel.recentCategoryText.value)) }
    var qrToEdit: QRItem by rememberSaveable { mutableStateOf(QRItem(category = viewModel.recentCategoryText.value)) }
    var qrToDelete: QRItem by rememberSaveable { mutableStateOf(QRItem(category = viewModel.recentCategoryText.value)) }
    var qrCategoryToDelete: QRCategory by rememberSaveable { mutableStateOf(QRCategory("")) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            QRTopBar(
                recentCategory = context.resources.getString(R.string.qr_recent_category),
                viewModel = viewModel,
                onAddQRCategoryClick = {
                    state.showAddCategoryDialog.value = true
                },
                onCategoryDeleteClicked = {
                    qrCategoryToDelete = it
                    state.showDeleteCategoryDialog.value = true
                }
            )
        },
        bottomBar = { QRBottomBar() },
        floatingActionButton = {
            QRScanFloatingButton(
                application = application,
                onCreateQRClick = {
                    state.showCreateQRDialog.value = true
                },
                onThemeChanged = onThemeChanged,
                barcodeLauncher = barcodeLauncher
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon_splash),
                contentDescription = null,
                modifier = Modifier
                    .width(256.dp)
                    .height(256.dp)
                    .align(Alignment.Center)
                    .alpha(if (application.isDarkTheme.value) 0.4f else 0.1f),
            )
            if (state.error.isBlank()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    QRList(
                        context = context,
                        viewModel = viewModel,
                        onOpenUrlFailed = {
                            val barcodeEncoder = BarcodeEncoder()
                            val bitmap = barcodeEncoder.encodeBitmap(
                                it.url,
                                BarcodeFormat.QR_CODE,
                                400,
                                400
                            )

                            qrImage = bitmap
                            qrImageToShow = it
                            state.showQRImageDialog.value = true
                        },
                        onViewQRImageClick = {
                            val barcodeEncoder = BarcodeEncoder()
                            val bitmap = barcodeEncoder.encodeBitmap(
                                it.url,
                                BarcodeFormat.QR_CODE,
                                400,
                                400
                            )

                            qrImage = bitmap
                            qrImageToShow = it
                            state.showQRImageDialog.value = true
                        },
                        onEditButtonClick = {
                            qrToEdit = it
                            state.showEditDialog.value = true
                        },
                        onDeleteButtonClick = {
                            qrToDelete = it
                            state.showDeleteDialog.value = true
                        }
                    )
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
            } else if (viewModel.qrList.value.isEmpty()) {
                Text(
                    text = stringResource(
                        id = if (viewModel.selectedCategory.value.categoryName == viewModel.recentCategoryText.value)
                            R.string.qr_list_help_text else R.string.qr_list_category_empty
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }

        QRImageDialog(
            context = context,
            viewModel = viewModel,
            qrImage = qrImage,
            qrImageToShow = qrImageToShow
        )
        QREditDialog(viewModel = viewModel, qrToEdit = qrToEdit)
        QRDeleteDialog(viewModel = viewModel, qrToDelete = qrToDelete)
        QRCreateQRDialog(viewModel = viewModel)
        QRAddCategoryDialog(viewModel = viewModel)
        QRCategoryDeleteDialog(viewModel = viewModel, qrCategoryToDelete = qrCategoryToDelete)
    }
}