package com.example.f1app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.f1app.data.model.DriverResponse
import com.example.f1app.data.model.PositionResponse

@Composable
fun DriverPositionRow(
    driver: PositionResponse,
    driverInfo: DriverResponse?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 5.dp, end = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "P${driver.position}",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )

                VerticalDivider(
                    color = Color.Red,
                    thickness = 5.dp,
                    modifier = Modifier.height(80.dp).padding(start = 15.dp, end = 5.dp)
                )

                if (driverInfo?.headshotUrl != null) {
                    AsyncImage(
                        model = driverInfo.headshotUrl,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "#${driver.driverNumber} | ${driverInfo?.fullName ?: "Unknown"}",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = driverInfo?.teamName ?: "",
                        color = Color.DarkGray
                    )
                }
            }

        }
    }

}