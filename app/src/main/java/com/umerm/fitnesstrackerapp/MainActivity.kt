package com.umerm.fitnesstrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*

data class Workout(
    val name: String,
    val reps: String,
    val sets: String,
    val weight: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF6C63FF),
                    secondary = Color(0xFF00C9A7)
                )
            ) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var workouts by remember { mutableStateOf(listOf<Workout>()) }

    NavHost(navController = navController, startDestination = "main") {

        composable("main") {
            MainScreen(workouts) {
                navController.navigate("add")
            }
        }

        composable("add") {
            AddWorkoutScreen { workout ->
                workouts = workouts + workout
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun MainScreen(workouts: List<Workout>, onAddClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // 💎 DIAMOND LOGO
            DiamondLogo()

            Spacer(modifier = Modifier.height(50.dp)) // ⬅ pushes title lower


            Text(
                text = "Fitness Tracker",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Stay strong 💪",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutCard(workout)
                }
            }
        }


        Button(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text("Add Workout")
        }
    }
}

@Composable
fun DiamondLogo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(160.dp)
            .rotate(45f)
            .background(
                color = Color(0xFF6C63FF),
                shape = MaterialTheme.shapes.large
            )
    ) {
        Text(
            text = "💎",
            modifier = Modifier.rotate(-45f),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun WorkoutCard(workout: Workout) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F2FF)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = workout.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${workout.reps} reps • ${workout.sets} sets • ${workout.weight} kg",
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun AddWorkoutScreen(onSave: (Workout) -> Unit) {

    var name by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add Workout",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Exercise Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = reps,
            onValueChange = { reps = it },
            label = { Text("Reps") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = sets,
            onValueChange = { sets = it },
            label = { Text("Sets") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val workout = Workout(name, reps, sets, weight)
                onSave(workout)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Workout")
        }
    }
}