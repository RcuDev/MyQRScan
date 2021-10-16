package com.rcudev.myqrscan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class QRItem(
    @PrimaryKey(autoGenerate = true)
    val pk: Int? = 0,
    var name: String? = null,
    val url: String? = null,
    val category: String
) : Serializable
