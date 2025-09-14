package compose

import ScreenSaverContent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import com.theapache64.klokk.CLOCKS_CONTAINER_HEIGHT
import com.theapache64.klokk.CLOCKS_CONTAINER_WIDTH
import com.theapache64.klokk.PADDING
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AppKit.NSApp

// A standalone main function to run the Compose content on native macOS
@OptIn(ExperimentalForeignApi::class)
fun main() {
    Window(
        "Compose macOS native",
        size = DpSize(width = (CLOCKS_CONTAINER_WIDTH + PADDING).dp, height = (CLOCKS_CONTAINER_HEIGHT + PADDING).dp)
    ) {
        ScreenSaverContent()
    }

    NSApp?.run()
}
