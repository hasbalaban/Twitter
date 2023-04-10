package com.fakem.twitter.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.fakem.twitter.ui.theme.BlueTwitter
import com.fakem.twitter.ui.theme.GreyTwitter

data class BottomBarItemInfo(
    @DrawableRes val image : Int?,
    val label : String,
    var isSelected : Boolean = false,
    val contentDescription : String?,
){
    val tint : (Boolean) -> Color = { isSelected->
        if (isSelected) BlueTwitter else GreyTwitter
    }
}
