import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.theapache64.klokk.CLOCKS_CONTAINER_HEIGHT
import com.theapache64.klokk.CLOCKS_CONTAINER_WIDTH
import com.theapache64.klokk.PADDING

fun main() {
    application {
        val windowState = rememberWindowState(
            width = (CLOCKS_CONTAINER_WIDTH + PADDING).dp, height = (CLOCKS_CONTAINER_HEIGHT + PADDING).dp
        )
        Window(
            title = "KotlinLogo JVM",
            onCloseRequest = ::exitApplication,
            state = windowState,
            alwaysOnTop = true,
        ) {
            ScreenSaverContent()
        }
    }
}
