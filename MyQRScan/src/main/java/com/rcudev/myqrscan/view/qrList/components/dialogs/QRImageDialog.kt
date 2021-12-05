package com.rcudev.myqrscan.view.qrList.components.dialogs

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.theme.Red600
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


const val SHARE_QR_IMAGE_TYPE = "image/*"
const val DEFAULT_QR_IMAGE_NAME = "QRimage"

@Composable
fun QRImageDialog(
    context: Activity,
    viewModel: QRListViewModel,
    qrImage: Bitmap?,
    qrImageToShow: QRItem
) {
    val state = viewModel.state.value
    val shareQRImageString = stringResource(id = R.string.qr_item_share_qr_accessibility)
    val scope = rememberCoroutineScope()

    if (state.showQRImageDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showQRImageDialog.value = false
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.qr_image_dialog_description),
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    qrImage?.let {
                        Image(
                            bitmap = qrImage.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        val bytes = ByteArrayOutputStream()
                                        qrImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
                                        val path: String = MediaStore.Images.Media.insertImage(
                                            context.contentResolver,
                                            qrImage,
                                            qrImageToShow.name ?: DEFAULT_QR_IMAGE_NAME,
                                            null
                                        )
                                        ShareCompat
                                            .IntentBuilder(context)
                                            .setType(SHARE_QR_IMAGE_TYPE)
                                            .setChooserTitle(shareQRImageString)
                                            .setStream(Uri.parse(path))
                                            .startChooser()
                                    }
                                }
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    qrImageToShow.url?.apply {
                        Text(
                            text = this,
                            fontSize = 12.sp,
                            maxLines = 3,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        state.showQRImageDialog.value = false
                    }) {
                    Text(
                        text = stringResource(id = R.string.qr_dialog_close),
                        fontSize = 14.sp,
                        color = Red600
                    )
                }
            }
        )
    }

}