package com.rcudev.myqrscan.view.qrList

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.MyQRScanApplication
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.components.QRBottomBar
import com.rcudev.myqrscan.view.qrList.components.QRList
import com.rcudev.myqrscan.view.qrList.components.QRScanFloatingButton
import com.rcudev.myqrscan.view.qrList.components.QRTopBar
import com.rcudev.myqrscan.view.qrList.components.dialogs.QRAddCategoryDialog
import com.rcudev.myqrscan.view.qrList.components.dialogs.QRDeleteDialog
import com.rcudev.myqrscan.view.qrList.components.dialogs.QREditDialog
import kotlinx.coroutines.launch

const val SHARE_QR_TYPE = "text/plain"

@Composable
fun QRListScreen(
    application: MyQRScanApplication,
    context: Activity,
    viewModel: QRListViewModel,
    onThemeChanged: (Boolean) -> Unit
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val invalidUrlString = stringResource(id = R.string.qr_item_invalid_url)
    var qrToEdit: QRItem by rememberSaveable { mutableStateOf(QRItem(category = viewModel.recentCategoryValue.value)) }
    var qrToDelete: QRItem by rememberSaveable { mutableStateOf(QRItem(category = viewModel.recentCategoryValue.value)) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            QRTopBar(
                recentCategory = context.resources.getString(R.string.qr_topbar_recent_category),
                viewModel = viewModel
            )
        },
        bottomBar = { QRBottomBar() },
        floatingActionButton = {
            QRScanFloatingButton(
                application = application,
                context = context,
                onAddQRCategoryClick = {
                    state.showAddCategoryDialog.value = true
                },
                onThemeChanged
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                            scope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar(
                                        message = invalidUrlString
                                    )
                            }
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
                    text = stringResource(id = R.string.qr_list_help_text),
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

        QREditDialog(viewModel = viewModel, qrToEdit = qrToEdit)
        QRDeleteDialog(viewModel = viewModel, qrToDelete = qrToDelete)
        QRAddCategoryDialog(viewModel = viewModel)
    }
}