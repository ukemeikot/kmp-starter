import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.ukemeikot.starter.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "composeApplication") {
        App()
    }
}
