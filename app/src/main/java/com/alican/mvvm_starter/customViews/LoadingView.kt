package com.alican.mvvm_starter.customViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.ui.theme.Blue

@Composable
fun LoadingView(
    isBackgroundFullColor: Boolean = false
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        restartOnPlay = true,
        speed = 1f

    )

    Column(
        Modifier
            .background(if (isBackgroundFullColor) Color.White else Color.Transparent)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.29f)
                .fillMaxHeight(0.18f),
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = if (!isBackgroundFullColor) Color.White else Color.Transparent),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .padding(top = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.loading),
                    color = Blue,
                    fontSize = 11.sp,
                    lineHeight = 13.sp,
                    maxLines = 1,
                )
            }
        }


    }
}