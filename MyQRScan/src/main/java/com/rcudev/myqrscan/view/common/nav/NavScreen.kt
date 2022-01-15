package com.rcudev.myqrscan.view.common.nav

sealed class NavScreen(val route: String){
    object QRListScreen: NavScreen("qr_list_screen")
    object QRConfigScreen: NavScreen("qr_config_screen")
}
