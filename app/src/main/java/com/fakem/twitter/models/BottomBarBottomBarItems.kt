package com.fakem.twitter.models

import com.fakem.twitter.R

fun bottomBarBottomBarItems() = listOf(
        BottomBarItemInfo(image = R.drawable.home_solid_icon, label = "Home", isSelected = true, contentDescription = null ),
        BottomBarItemInfo(image = R.drawable.search_stroke_icon, label = "Search", isSelected = false, contentDescription = null),
        BottomBarItemInfo(image = R.drawable.bell_stroke_icon, label = "Notification", isSelected = false, contentDescription = null),
        BottomBarItemInfo(image = R.drawable.mail_stroke_icon, label = "Message", isSelected = false, contentDescription = null)
    )