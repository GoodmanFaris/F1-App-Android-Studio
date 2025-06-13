package com.example.f1app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.drawWithContent
import com.example.f1app.R

@Composable
fun GradientImage(painter: Painter, modifier: Modifier = Modifier) {
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxHeight()
            .height(120.dp)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White, Color.White.copy(alpha = 0.65f) , Color.Transparent),
                        startX = 0f,
                        endX = size.width
                    )
                )
            }
    )
}


fun getTrackImageRes(circuitName: String): Int {
    return when (circuitName) {
        "Monza" -> R.drawable.monza
        "Spa-Francorchamps" -> R.drawable.spa_francorchamps
        "Monaco" -> R.drawable.monaco
        "Sakhir" -> R.drawable.sakhir
        "Jeddah" -> R.drawable.jeddah
        "Melbourne" -> R.drawable.melbourne
        "Baku" -> R.drawable.baku
        "Miami" -> R.drawable.miami
        "Barcelona" -> R.drawable.barcelona
        "Montréal" -> R.drawable.montreal
        "Spielberg" -> R.drawable.spielberg
        "Silverstone" -> R.drawable.silverstone
        "Budapest" -> R.drawable.budapest
        "Zandvoort" -> R.drawable.zandvoort
        "Marina Bay" -> R.drawable.marina
        "Suzuka" -> R.drawable.suzuka
        "Lusail"-> R.drawable.lusail
        "Austin" -> R.drawable.austin
        "Mexico City" -> R.drawable.mexico_city
        "São Paulo" -> R.drawable.sao_paulo
        "Las Vegas" -> R.drawable.las_vegas
        "Yas Island" -> R.drawable.yas_marina
        "Shanghai" -> R.drawable.jeddah
        "Imola" -> R.drawable.jeddah
        else -> R.drawable.logo_red
    }
}