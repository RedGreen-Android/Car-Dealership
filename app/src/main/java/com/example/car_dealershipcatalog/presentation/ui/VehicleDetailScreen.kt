package com.example.car_dealershipcatalog.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.car_dealershipcatalog.R
import com.example.car_dealershipcatalog.domain.model.Vehicle

@Composable
fun VehicleDetailScreen(
    vehicle: Vehicle,
    requestPermission: (String) -> Unit,
    phoneCallPermissionGranted: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = vehicle.images.firstPhoto.medium,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder)
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(id = R.string.currency_symbol)} ${vehicle.currentPrice}  ${stringResource(id = R.string.price_mileage_separator)}  ${vehicle.mileage} mi",                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.vehicle_info),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row {
                Text(text = stringResource(id = R.string.location), modifier = Modifier.weight(1f))
                Text(
                    text = "${vehicle.dealer.city}, ${vehicle.dealer.state}",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.exterior_color), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.exteriorColor,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.interior_color), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.interiorColor,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.drive_type), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.drivetype,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.transmission), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.transmission,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.body_style), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.bodytype,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(text = stringResource(id = R.string.engine), modifier = Modifier.weight(1f))
                Text(
                    text = vehicle.engine,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (phoneCallPermissionGranted) {
                        requestPermission(vehicle.dealer.phone)
                    } else {
                        requestPermission(vehicle.dealer.phone)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                shape = RectangleShape
            ) {
                Text(
                    text = stringResource(id = R.string.call_dealer),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
