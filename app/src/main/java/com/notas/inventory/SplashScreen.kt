package com.notas.inventory

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.notas.inventory.ui.navigation.NavigationDestination
import kotlinx.coroutines.delay


object SplashDestination : NavigationDestination {
    override val route = "Splash"
    override val titleRes = R.string.app_splash
}


@Composable
fun SplashScreen(
    navigateTome: () -> Unit,

    modifier: Modifier = Modifier

){
    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }


   suspend fun loadWeather(): () -> Unit {

       delay(37500L)

        return navigateTome
    }

    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f,
        animationSpec = tween(durationMillis = 800,
            easing = {
                OvershootInterpolator(8f)
                    .getInterpolation(it)

            }
            ),

       )
     //   loadWeather()

    }


    // if (iScoreRunning) navigateToSplash else navigateToEntry
    val color = MaterialTheme.colors.primary
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(343.dp)
            .scale(scale.value),

        shape = CircleShape,
        color = MaterialTheme.colors.secondaryVariant,
        border = BorderStroke(width = 2.dp, color = color)


        ) {

        Card(
            modifier = Modifier
                .width(343.dp)
                .height(343.dp)
                .padding(1.dp)
                .clickable { navigateTome() },
            elevation = 10.dp)
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Image(painter = painterResource(id = R.drawable.appreciation),
                    contentDescription = null,
                    modifier = Modifier
                        .size(199.dp)
                        .padding(16.dp)
                        .clip(CircleShape)
                )

                Text("rainbowbuy",
                    style = MaterialTheme.typography.h5,
                    color = color.copy(alpha = 0.5f))

            }
        }


    }
}

