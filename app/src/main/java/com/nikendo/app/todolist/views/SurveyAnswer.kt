package com.nikendo.app.todolist.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.R
import com.nikendo.app.todolist.ui.theme.MyTheme

@Composable
fun SurveyAnswer(answer: Answer, modifier: Modifier = Modifier) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp)
        ) {
            Image(
                painter = answer.image,
                contentDescription = "Image - answer ${answer.text}",
                modifier = Modifier.clip(MaterialTheme.shapes.small)
            )
            Text(text = answer.text, modifier = Modifier
                .weight(1f)
                .padding(8.dp))
            RadioButton(selected = false, onClick = { })

        }
    }
}

@Preview(showBackground = false)
@Composable
fun SurveyAnswerPreview() {
    MyTheme {
        SurveyAnswer(answer = Answer(painterResource(id = R.drawable.ic_weather), text = "Weather"))
    }
}

data class Answer(val image: Painter, val text: String)