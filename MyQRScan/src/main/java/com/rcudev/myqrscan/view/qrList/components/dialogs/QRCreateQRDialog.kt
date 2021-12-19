package com.rcudev.myqrscan.view.qrList.components.dialogs

import android.app.Activity
import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.qrList.components.QRAutofocusTextField
import com.rcudev.myqrscan.view.theme.Green600
import com.rcudev.myqrscan.view.theme.Red600

@Composable
fun QRCreateQRDialog(
    context: Activity,
    viewModel: QRListViewModel,
    saveCreatedQR: (String) -> Unit
) {
    val state = viewModel.state.value
    val adRequest = AdRequest.Builder().build()
    var newQRContent by remember { mutableStateOf("") }
    var isLoadingAd by remember { mutableStateOf(false) }
    var interstitialAd: InterstitialAd? = null

    InterstitialAd.load(
        context,
        "ca-app-pub-8389040971985833/8121118352",
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
            }
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
            }
        })

    if (state.resetCreateQRText.value) {
        newQRContent = ""
        state.resetCreateQRText.value = false
    }

    if (state.showCreateQRDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showCreateQRDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.qr_create_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.qr_create_description),
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    if (isLoadingAd) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Red600)
                    } else {
                        QRAutofocusTextField(value = newQRContent, onValueChanged = {
                            newQRContent = it
                        })
                    }
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                }
            },
            confirmButton = {
                if (!TextUtils.isEmpty(newQRContent)) {
                    TextButton(
                        enabled = !isLoadingAd,
                        onClick = {
                            isLoadingAd = true
                            if (interstitialAd != null) {
                                interstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                        saveCreatedQR(newQRContent)
                                        newQRContent = ""
                                        state.showCreateQRDialog.value = false
                                        isLoadingAd = false
                                    }

                                    override fun onAdShowedFullScreenContent() {
                                        interstitialAd = null
                                        saveCreatedQR(newQRContent)
                                        state.showCreateQRDialog.value = false
                                        isLoadingAd = false
                                    }
                                }
                                interstitialAd?.show(context)
                            } else {
                                saveCreatedQR(newQRContent)
                                state.showCreateQRDialog.value = false
                                isLoadingAd = false
                            }
                        }) {
                        Text(
                            text = stringResource(id = R.string.qr_dialog_confirm),
                            fontSize = 14.sp,
                            color = Green600
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !isLoadingAd,
                    onClick = {
                        state.showCreateQRDialog.value = false
                        newQRContent = ""
                    }) {
                    Text(
                        text = stringResource(id = R.string.qr_dialog_cancel),
                        fontSize = 14.sp,
                        color = Red600
                    )
                }
            }
        )
    }
}