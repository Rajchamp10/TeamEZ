package com.example.teamez

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

data class Interest(val name: String, val imageRes: Int)

val interests = listOf(
    Interest("Technology", R.drawable.cricket),
    Interest("Books", R.drawable.football),
    Interest("Music", R.drawable.trekking),
    Interest("Travel", R.drawable.travel),
    Interest("Food", R.drawable.karaoke)
)

@Composable
fun HomeScreen(
    userEmail: String = "user@example.com"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome back, $userEmail 👋",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Your Interests:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(interests) { interest ->
                InterestChip(interest)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Joined Groups", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Suggested Groups", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun InterestChip(interest: Interest) {
    Surface(
        color = Color(0xFFEEF3F8),
        shape = RoundedCornerShape(50),
        shadowElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = interest.imageRes),
                contentDescription = interest.name,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 6.dp)
            )
            Text(
                text = "#${interest.name}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
