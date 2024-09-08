package com.example.navigo.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimationLoader(modifier: Modifier, animationId: Int) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            animationId
        )
    )
    LottieAnimation(
        composition = preloaderLottieComposition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )
}