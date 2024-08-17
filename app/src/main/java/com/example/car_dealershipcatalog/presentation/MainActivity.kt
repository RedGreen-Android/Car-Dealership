package com.example.car_dealershipcatalog.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.car_dealershipcatalog.presentation.viewmodel.VehicleViewModel
import com.example.car_dealershipcatalog.ui.theme.Car_DealershipCatalogTheme
import com.example.car_dealershipcatalog.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vehicleViewModel: VehicleViewModel by viewModels()
    private var pendingPhoneNumber: String? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registering the permission launcher
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // If permission is granted, make the call if a phone number is pending
                pendingPhoneNumber?.let { phoneNumber ->
                    makePhoneCall(phoneNumber)
                    pendingPhoneNumber = null
                }
            } else {
                // Handle the case where permission is denied
            }
        }

        setContent {
            Car_DealershipCatalogTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val resource by vehicleViewModel.vehicleState.collectAsState()

                    val vehicles = when (resource.status) {
                        Status.SUCCESS -> resource.data ?: emptyList()
                        Status.LOADING -> emptyList() // If I had more time I would Show loading indicator/spinner if I extended project
                        Status.ERROR -> emptyList() // Handle error, possibly show a message here
                    }

                    VehicleNavHost(
                        navController = navController,
                        vehicles = vehicles,
                        onVehicleSelected = { vehicleId ->
                            navController.navigate("details/$vehicleId")
                        },
                        onCallDealer = { phoneNumber ->
                            // Save the phone number and request permission
                            pendingPhoneNumber = phoneNumber
                            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                        }
                    )
                }
            }
        }
    }
    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            // If permission is not granted, request it
            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }
}