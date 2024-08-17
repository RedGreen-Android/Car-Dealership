package com.example.car_dealershipcatalog.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.car_dealershipcatalog.R
import com.example.car_dealershipcatalog.domain.model.Vehicle
import com.example.car_dealershipcatalog.presentation.ui.VehicleDetailScreen
import com.example.car_dealershipcatalog.presentation.ui.VehicleListScreen

/**
 * Sets up the navigation host for the vehicle catalog app.
 *
 * Composable function defines the navigation structure for the app, including the
 * `VehicleListScreen` and `VehicleDetailScreen`. It handles checking for phone call
 * permissions and passing necessary parameters to the screens.
 *
 * @param navController The [NavHostController] used for navigating between screens.
 * @param modifier A [Modifier] to apply to the NavHost (default is [Modifier]).
 * @param startDestination The starting route for the navigation host, typically a string
 *                         resource defining the initial screen (default is the list route).
 * @param vehicles A list of [Vehicle] objects used to populate the detail screen.
 * @param onVehicleSelected A lambda function invoked when a vehicle is selected, receiving
 *                           the vehicle ID as a parameter.
 * @param onCallDealer A lambda function invoked when user wants to call the dealer, receiving
 *                     the dealer's phone number as a parameter.
 */
@Composable
fun VehicleNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = stringResource(id = R.string.route_list),
    vehicles: List<Vehicle>,
    onVehicleSelected: (String) -> Unit,
    onCallDealer: (String) -> Unit
) {

    val context = LocalContext.current
    val phoneCallPermissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CALL_PHONE
    ) == PackageManager.PERMISSION_GRANTED

    // string resources to be used inside composable functions
    val listRoute = stringResource(id = R.string.route_list)
    val detailsRoute = stringResource(id = R.string.route_details)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(listRoute) {
            VehicleListScreen(
                navController = navController,
                requestPermission = onCallDealer,
                phoneCallPermissionGranted = ContextCompat.checkSelfPermission(LocalContext.current, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
            )
        }
        composable(detailsRoute) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString(stringResource(id = R.string.vehicle_id_arg))
            val vehicle = vehicles.find { it.id == vehicleId }
            vehicle?.let {
                VehicleDetailScreen(
                    vehicle = it,
                    requestPermission = onCallDealer,
                    phoneCallPermissionGranted = phoneCallPermissionGranted
                )
            }
        }
    }
}