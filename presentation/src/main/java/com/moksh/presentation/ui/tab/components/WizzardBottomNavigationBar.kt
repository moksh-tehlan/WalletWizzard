package com.moksh.presentation.ui.tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WizzardBottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .blur(radius = 50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp)
                .height(70.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.16f)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colorScheme.background)
                .topBorder(
                    strokeWidth = 2.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.onBackground,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        )
                    ),
                    radius = 30.dp
                )
                .padding(top = 34.dp, bottom = 20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                content()
            }
        }
    }
}

private fun Modifier.topBorder(strokeWidth: Dp, brush: Brush, radius: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val radiusWidthPx = density.run { radius.toPx() }

        Modifier.drawBehind {
            val path = Path().apply {
                moveTo(-5f, radiusWidthPx)
                quadraticTo(
                    x2 = radiusWidthPx,
                    y2 = 0f,
                    x1 = 7f,
                    y1 = 7f
                )
                lineTo(size.width - radiusWidthPx, 0f)
                quadraticTo(
                    x2 = size.width + 5f,
                    y2 = radiusWidthPx,
                    x1 = size.width - 7f,
                    y1 = 7f
                )
            }
            drawPath(
                path = path,
                brush = brush,
                style = Stroke(width = strokeWidthPx)
            )
        }
    }
)
