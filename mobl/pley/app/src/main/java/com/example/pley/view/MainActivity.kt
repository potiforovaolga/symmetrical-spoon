
package example.pley.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import example.pley.model.GameModel
import example.pley.ui.theme.PleyTheme

class MainActivity : ComponentActivity() {
    private val gameModel = GameModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PleyTheme {
                GameScreen(gameModel)
            }
        }
    }
}

@Composable
fun GameScreen(gameModel: GameModel) {
    var sliderValue by remember { mutableStateOf(1) }
    var pointsEarned by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    if (gameOver) {
        Text(text = "Игра окончена! Ваши очки: ${gameModel.getScore()}")
        Button(onClick = {
            gameModel.resetGame()
            gameOver = false
            sliderValue = 1
            pointsEarned = 0
        }) {
            Text("Начать заново")
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Загаданное число: ${gameModel.getTargetNumber()}")
            Spacer(modifier = Modifier.height(20.dp))
            Slider(
                value = sliderValue.toFloat(),
                onValueChange = { sliderValue = it.toInt() },
                valueRange = 1f..50f,
                steps = 49
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                pointsEarned = gameModel.guess(sliderValue)
                if (gameModel.isGameOver()) {
                    gameOver = true
                }
            }) {
                Text("Подтвердить")
            }
            if (pointsEarned > 0) {
                Text(text = "Вы заработали $pointsEarned очков!")
            }
            Text(text = "Текущий счет: ${gameModel.getScore()}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    PleyTheme {
        GameScreen(GameModel())
    }
}

