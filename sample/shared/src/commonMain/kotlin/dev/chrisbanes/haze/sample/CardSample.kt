// Copyright 2023, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0

package dev.chrisbanes.haze.sample

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun CreditCardSample(
  navController: NavHostController,
  blurEnabled: Boolean = HazeDefaults.blurEnabled(),
) {
  val hazeState = rememberHazeState(blurEnabled = blurEnabled)

  Box {
    // Background content
    Box(
      modifier = Modifier
        .fillMaxSize()
        .hazeSource(state = hazeState),
    ) {
      Spacer(
        Modifier
          .fillMaxSize()
          .background(brush = Brush.linearGradient(colors = listOf(Color.Black, Color.DarkGray))),
      )

      Text(
        text = LorumIspum,
        color = Color.White.copy(alpha = 0.2f),
        modifier = Modifier.padding(24.dp),
      )
    }

    // Card 1

    val cardStyle = HazeStyle(
      backgroundColor = Color.Black,
      tints = listOf(HazeTint(Color.Yellow.copy(alpha = 0.4f))),
      blurRadius = 8.dp,
      noiseFactor = HazeDefaults.noiseFactor,
    )

    repeat(3) { index ->
      // Our card
      val reverseIndex = (2 - index)
      val cardOffset = remember { mutableFloatStateOf(0f) }
      val draggableState = rememberDraggableState { cardOffset.value += it }

      Box(
        modifier = Modifier
          .testTag("credit_card_$index")
          .fillMaxWidth(0.7f - (reverseIndex * 0.05f))
          .aspectRatio(16 / 9f)
          .align(Alignment.Center)
          .offset { IntOffset(x = 0, y = reverseIndex * -100) }
          .offset { IntOffset(x = 0, y = cardOffset.value.toInt()) }
          .draggable(
            state = draggableState,
            orientation = Orientation.Vertical,
            onDragStopped = { velocity ->
              animate(
                initialValue = cardOffset.value,
                targetValue = 0f,
                initialVelocity = velocity,
                animationSpec = spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow),
              ) { value, _ ->
                cardOffset.value = value
              }
            },
          )
          // We add 1 to the zIndex as the background content is zIndex 0f
          .hazeSource(hazeState, zIndex = 1f + index)
          .clip(RoundedCornerShape(16.dp))
          .hazeEffect(state = hazeState, style = cardStyle),
      ) {
        Column(Modifier.padding(32.dp)) {
          Text("Bank of Haze")
        }
      }
    }

    FloatingActionButton(
      onClick = navController::navigateUp,
      modifier = Modifier
        .windowInsetsPadding(WindowInsets.statusBars)
        .padding(24.dp),
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = null,
      )
    }
  }
}

val LorumIspum by lazy {
  """
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit amet congue mauris, iaculis accumsan eros. Aliquam pulvinar est ac elit vulputate egestas. Vestibulum consequat libero at sem varius, vitae semper urna rhoncus. Aliquam mollis, ipsum a convallis scelerisque, sem dui consequat leo, in tempor risus est ac mi. Nam vel tellus dolor. Nunc lobortis bibendum fermentum. Mauris sed mollis justo, eu tristique elit. Cras semper augue a tortor tempor, vitae vestibulum eros convallis. Curabitur id justo eget tortor iaculis lobortis. Integer pharetra augue ac elit porta iaculis non vitae libero. Nam eros turpis, suscipit at iaculis vitae, malesuada vel arcu. Donec tincidunt porttitor iaculis. Pellentesque non augue magna. Mauris mattis purus vitae mi maximus, id molestie ipsum facilisis. Donec bibendum gravida dolor nec suscipit. Pellentesque tempus felis iaculis, porta diam sed, tristique tortor.

Sed vel tellus vel augue pulvinar semper sit amet eu est. In porta arcu eu sapien luctus scelerisque. In hac habitasse platea dictumst. Aenean varius lobortis malesuada. Sed vitae ornare arcu. Nunc maximus lectus purus, vel aliquet velit facilisis a. Nulla maximus bibendum magna id vulputate. Mauris volutpat lorem et risus porta dignissim. In at elit a est vulputate tincidunt.

Nulla facilisi. Curabitur gravida quam nec massa tempus, sed placerat nunc hendrerit. Duis sit amet cursus ipsum. Phasellus eget congue lacus. Duis vehicula venenatis posuere. Morbi non tempor risus. Aenean bibendum efficitur tortor, eu interdum velit gravida rutrum. Sed tempus elementum libero. Suspendisse dapibus lorem vitae justo congue pellentesque. Phasellus et tellus sagittis, blandit nibh a, porta felis. Proin ornare eget odio eget laoreet. Cras id augue fringilla, molestie ligula sit amet, sollicitudin neque.

Suspendisse vitae bibendum justo, nec egestas mauris. Mauris id metus mi. Morbi ut maximus ex, eu consequat elit. Sed malesuada pellentesque mauris vel molestie. Nulla facilisi. Cras pellentesque metus id nibh sodales gravida. Vivamus a feugiat felis. Vivamus et justo libero. Maecenas ac augue viverra, blandit diam sed, porttitor sapien. Proin eu eros mollis, commodo lectus nec, imperdiet nisi. Proin nulla nulla, vehicula a faucibus sit amet, auctor sed lorem. Mauris ut ipsum sit amet massa posuere maximus eget porttitor nisl. Quisque nunc dolor, pharetra id nunc sit amet, maximus convallis nunc.

Ut magna diam, ullamcorper vel imperdiet at, dignissim sit amet turpis. Duis ut enim eu sapien fringilla placerat. Integer at dui eget leo tincidunt iaculis. Fusce nec elementum turpis. Aenean gravida, ipsum sit amet varius hendrerit, elit nisi hendrerit ex, et porta enim lorem eget mi. Duis convallis dolor a lacinia aliquam. Aliquam erat volutpat.
""".trim()
}
