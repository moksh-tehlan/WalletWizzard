package com.moksh.presentation.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.moksh.presentation.R

val homeIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.home_icon)

val passBookIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.passbook_icon)

val booksIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.books_icon)

val profileIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.profile_icon)

val addIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.add_icon)

val forwardIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.forward_arrow)