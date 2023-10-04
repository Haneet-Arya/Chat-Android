package com.haneetarya.mychat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.haneetarya.mychat.R


val poppins = FontFamily(
    listOf(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_black, FontWeight.Black)
    )

)


val Typography = Typography(
    bodyLarge = TextStyle(
        color = TextWhite,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = poppins
    ),
    headlineLarge = TextStyle(
        color = TextWhite,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppins
    ),
    headlineMedium = TextStyle(
        color = TextWhite,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppins
    ),
    bodyMedium = TextStyle(
        color = TextWhite,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = poppins
    )
)
// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)