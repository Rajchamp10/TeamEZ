package com.example.teamez

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Profile", modifier = Modifier
            .fillMaxWidth()
            .clickable { onProfileClick() }
            .padding(16.dp))

        Text("Logout", modifier = Modifier
            .fillMaxWidth()
            .clickable { onLogoutClick() }
            .padding(16.dp))
    }
}
