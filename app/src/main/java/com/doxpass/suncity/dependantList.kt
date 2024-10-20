package com.doxpass.suncity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.dox.profile.GuestDetails.Guest
import com.dox.suncity.AddDependant.Dependant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dependantList(navController: NavController) {
    var dependants by remember {
        mutableStateOf<List<Dependant>>(emptyList())
    }

    var searchText by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<List<Dependant>>(emptyList()) }
    var dependantFound by remember { mutableStateOf(false) }
    var foundDependant: Dependant? by remember { mutableStateOf(null) }


    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    LaunchedEffect(usersRef) {
        // Add a listener for all users
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allDependants = mutableListOf<Dependant>()

                for (userSnapshot in snapshot.children) {
                    val guestsSnapshot = userSnapshot.child("dependants")
                    for (guestSnapshot in guestsSnapshot.children) {
                        val dependant = guestSnapshot.getValue(Dependant::class.java)
                        dependant?.let { allDependants.add(it) }
                    }
                }

                dependants = allDependants
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dependants") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Search") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        searchResult = dependants.filter { dependant ->
                            dependant.firstName.contains(searchText, ignoreCase = true) ||
                                    dependant.lastName.contains(searchText, ignoreCase = true)
                        }
                    }
                ) {
                    Text("Search")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(if (searchText.isNotBlank()) searchResult else dependants) { dependant ->
                    DependantItem(dependant = dependant)
                }
            }
        }
    }
}
@Composable
fun DependantItem(dependant: Dependant) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dependant.firstName,
               modifier = Modifier.padding(end = 8.dp),
                fontSize = 18.sp
            )
            Text(
                text = dependant.lastName,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp
            )
            Text(
                text = dependant.relationship,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontSize = 14.sp
            )
        }
        // Add a divider
        Divider(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            thickness = 1.dp
        )
    }
}