package org.themoviedb.example.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerGridItem(brush: Brush) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(149.dp)
        .padding(all = 10.dp), verticalAlignment = Alignment.Top) {
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(modifier = Modifier
            .size(500.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}