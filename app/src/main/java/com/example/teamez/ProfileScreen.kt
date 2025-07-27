package com.example.teamez

import androidx.compose.foundation.layout.FlowRow
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Tap to detect") }
    var birthDate by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val interests = listOf("Cricket", "Turf Football", "Trekking", "Movies", "Travel")
    val selectedInterests = remember { mutableStateListOf<String>() }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ---------- Profile Picture Section ----------
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .align(Alignment.CenterHorizontally)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Tap to add image",
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Text(
            "Change Picture",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { imagePickerLauncher.launch("image/*") }
        )

        // ---------- Text Fields ----------
        Text("First Name", fontSize = 14.sp, color = Color.Black)
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Last Name", fontSize = 14.sp, color = Color.Black)
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // ---------- Location Field ----------
        Text("Location", fontSize = 14.sp, color = Color.Black)
        OutlinedTextField(
            value = location,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (!locationPermissionState.status.isGranted) {
                        locationPermissionState.launchPermissionRequest()
                    } else {
                        getUserCity(context) { city ->
                            location = city
                        }
                    }
                }
        )

        // ---------- Date Picker ----------
        Text("Date of Birth", fontSize = 14.sp, color = Color.Black)
        val datePickerState = rememberDatePickerState()
        var openDialog by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { openDialog = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (openDialog) {
            DatePickerDialog(
                onDismissRequest = { openDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            birthDate = formatDate(it)
                        }
                        openDialog = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState, showModeToggle = true)
            }
        }

        // ---------- Interests ----------
        Text("Interests", fontSize = 14.sp, color = Color.Black)

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            interests.forEach { interest ->
                FilterChip(
                    selected = selectedInterests.contains(interest),
                    onClick = {
                        if (selectedInterests.contains(interest))
                            selectedInterests.remove(interest)
                        else
                            selectedInterests.add(interest)
                    },
                    label = { Text(interest) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId == null) {
                    Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val userProfile = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "location" to location,
                    "birthDate" to birthDate,
                    "interests" to selectedInterests.toList(),
                    "profileImageUri" to profileImageUri?.toString()
                )

                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .set(userProfile)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed to save: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            }

        ) {
            Text("Save", color = Color.White)
        }
    }
}

@SuppressLint("MissingPermission")
fun getUserCity(context: Context, onCityDetected: (String) -> Unit) {
    val locationClient = LocationServices.getFusedLocationProviderClient(context)

    locationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val city = addresses?.firstOrNull()?.locality ?: "Unknown"
            onCityDetected(city)
        } else {
            onCityDetected("Location unavailable")
        }
    }.addOnFailureListener {
        onCityDetected("Failed to detect")
    }
}

fun formatDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}
