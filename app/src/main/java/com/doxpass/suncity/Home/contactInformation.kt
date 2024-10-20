import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dox.profile.Home.NotificationViewModel
import com.google.firebase.database.FirebaseDatabase

data class NotificationItem(val title: String, val body: String)


@Composable
fun NotificationBoardView(navController: NavController) {
    Column {
        Column() {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back arrow",
                    modifier = Modifier.align(Alignment.Start))
            }
            Text(text = "Notification", modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0085A0))
        }

        val viewModel = remember { NotificationViewModel() }
        LaunchedEffect(Unit) {
            viewModel.fetchNotifications()
        }

        val notifications by viewModel.notifications.observeAsState(listOf())

        LazyColumn {
            items(notifications) { notification ->
                NotificationItem(notification)
            }
        }
    }
}
@Composable
fun NotificationItem(notification: NotificationItem) {
    Card (elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)){


        Text(
            text = notification.title,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, end = 8.dp, top = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = notification.body,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewNotificationBoardView() {
    NotificationBoardView(navController = rememberNavController())
}
