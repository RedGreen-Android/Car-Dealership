package com.example.car_dealershipcatalog.presentation.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.car_dealershipcatalog.R
import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.presentation.viewmodel.VehicleViewModel
import io.reactivex.rxjava3.schedulers.Schedulers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavController,
    viewModel: VehicleViewModel = hiltViewModel(),
    requestPermission: (String) -> Unit,
    phoneCallPermissionGranted: Boolean
) {
    val context = LocalContext.current
    var vehicles by remember { mutableStateOf(emptyList<Vehicle>()) }
    //Since I am showing "Flowables" in RxJava, refrain from showing "Resource" class and StateFlow" here, instead shown in MainActivity

    LaunchedEffect(Unit) {
        // Collect from Flowable
        viewModel.getVehiclesFlowable()
            .observeOn(Schedulers.io())
            .subscribe { vehicleList ->
                vehicles = vehicleList
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = { /* Handle navigation icon press */ }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
            },
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)) {
            items(vehicles) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    onClick = {
                        navController.navigate("details/${vehicle.id}")
                    },
                    context = context,
                    requestPermission = requestPermission,
                    phoneCallPermissionGranted = phoneCallPermissionGranted
                )
            }
        }
    }
}

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onClick: () -> Unit,
    context: Context,
    requestPermission: (String) -> Unit,
    phoneCallPermissionGranted: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    model = vehicle.images.firstPhoto.large,
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    error = painterResource(R.drawable.ic_placeholder)
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$ ${vehicle.currentPrice} \t | \t ${vehicle.mileage} mi",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${vehicle.dealer.city}, ${vehicle.dealer.state}",
                    color = Color.Black
                )
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = stringResource(id = R.string.call_dealer),
                        color = Color.Blue,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}