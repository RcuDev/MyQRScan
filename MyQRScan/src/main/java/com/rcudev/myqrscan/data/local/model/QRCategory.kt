package com.rcudev.myqrscan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class QRCategory(
    @PrimaryKey
    val categoryName: String? = null
) : Serializable