package com.dox.suncity.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doxpass.suncity.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

@Composable
fun AuthScreen(navController: NavController) {


    val context = LocalContext.current
    var userFirstName by remember { mutableStateOf("") }
    var userlastName by remember { mutableStateOf("") }
    var userAmountPaid by remember { mutableStateOf("") }
    val auth = Firebase.auth
    val user = auth.currentUser
    val userUID = user?.uid

    LaunchedEffect(userUID) {
        if (userUID != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userUID)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        userFirstName = it.firstName
                        userlastName = it.lastName
                        userAmountPaid = it.gender


                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
    var totalMonthsPaid = userAmountPaid.toIntOrNull() ?: 0

    val monthlyDue = 10000
    val totalDue = totalMonthsPaid * monthlyDue
    Column {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp)) {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    Icons.Outlined.ArrowBack, contentDescription = "Back arrow",
                    modifier = Modifier
                )
            }
            Text(
                text = "Personal Wallet     ", modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
                .height(200.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0085A0)
            )
        )
        {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Row {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.suncity1),
                        contentDescription = "dpk logo"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = "Suncity Gardens Estate",
                            style = MaterialTheme.typography.h6
                        )
                        Row {
                            Text(
                                text = userFirstName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = userlastName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total Months Paid: $totalMonthsPaid",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Column {
                        Text(
                            text = "Monthly Due Amount: ₦$monthlyDue",
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total Amount Paid: ₦$totalDue",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(id = R.drawable.suncity11),
                        contentDescription = "reader"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}