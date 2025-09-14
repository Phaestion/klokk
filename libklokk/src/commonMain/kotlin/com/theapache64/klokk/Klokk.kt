package com.theapache64.klokk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.theapache64.klokk.composable.Clock
import com.theapache64.klokk.movement.core.Movement
import com.theapache64.klokk.theme.KlokkTheme
import kotlinx.coroutines.delay

@Composable
fun KlokkApp(
    enableBottomToolBar: Boolean = true,
    activeMovement: Movement = Movement.Time()
) {
    // To hold and control movement transition
    var activeMovement by remember { mutableStateOf(activeMovement) }

    // To control the auto playing animation
    var shouldPlayAutoAnim by remember { mutableStateOf(true) }


    // Generating degree matrix using the active movement
    val degreeMatrix = activeMovement.getMatrixGenerator().getVerifiedMatrix()

    LaunchedEffect(Unit) {
        if (activeMovement is Movement.StandBy) {
            shouldPlayAutoAnim = true
            return@LaunchedEffect
        } else {
            shouldPlayAutoAnim = false
            while (true) {
                activeMovement = Movement.Time()
                delay(activeMovement.durationInMillis.toLong())
            }
        }
    }

    KlokkTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND_COLOR),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Building clock matrix
            repeat(ROWS) { i ->
                Row {
                    repeat(COLUMNS) { j ->
                        val clockData = degreeMatrix[i][j]
                        Clock(
                            needleOneDegree = clockData.degreeOne,
                            needleTwoDegree = clockData.degreeTwo,
                            durationInMillis = activeMovement.durationInMillis,
                            modifier = Modifier.requiredSize(CLOCK_SIZE.dp)
                        )
                    }
                }
            }

            // The animation loop
            LaunchedEffect(shouldPlayAutoAnim) {
                println("Animation loop created and started -> will run? $shouldPlayAutoAnim")
                val defaultWaitTime = activeMovement.durationInMillis.toLong() + ENJOY_TIME_IN_MILLIS
                val mediumDelay = defaultWaitTime - ENJOY_TIME_IN_MILLIS

                while (shouldPlayAutoAnim) {
                    delay(ENJOY_TIME_IN_MILLIS)

                    activeMovement = Movement.Trance(Movement.Trance.To.SQUARE) // Show square
                    delay(defaultWaitTime)

                    activeMovement = Movement.Trance(to = Movement.Trance.To.FLOWER) // Then flower
                    delay(mediumDelay) // flower doesn't have enjoy time

                    activeMovement = Movement.Trance(to = Movement.Trance.To.STAR) // To star, through circle (auto)
                    delay(mediumDelay)

                    activeMovement = Movement.Trance(to = Movement.Trance.To.FLY) // then fly
                    delay(defaultWaitTime)

                    activeMovement = Movement.Wave(Movement.Wave.State.START)
                    delay(mediumDelay)

                    activeMovement = Movement.Wave(Movement.Wave.State.END)
                    delay(mediumDelay)

                    // Ripple
                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.START) // then ripple start
                    delay(defaultWaitTime)

                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.END) // then ripple end
                    delay(defaultWaitTime)

                    // Time table
                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.TIME_TABLE) // then ripple start
                    delay(defaultWaitTime)

                    // Ripple again
                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.START) // then ripple start
                    delay(defaultWaitTime)

                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.END) // then ripple end
                    delay(defaultWaitTime)

                    activeMovement = Movement.Time() // then show time
                    delay(defaultWaitTime)
                }

            }
        }
    }
}