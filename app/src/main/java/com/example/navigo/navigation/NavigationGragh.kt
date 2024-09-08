package com.example.navigo.navigation


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.navigo.ui.MainScreen
import com.example.navigo.ui.SearchScreen
import com.example.navigo.viewModel.LocationViewModel
import org.neshan.common.model.LatLng


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavigationGraph(
    locationViewModel: LocationViewModel,
    navController: NavHostController,
    modifier: Modifier,
) {
    val navControllerWithHistory = remember { NavControllerWithHistory(navController) }
    NavHost(
        navController = navControllerWithHistory.navController,
        startDestination = NavigationItem.Main.route,
        modifier = modifier
    ) {

        composable(NavigationItem.Main.route) {
            MainScreen(
                locationViewModel = locationViewModel,
                navController = navControllerWithHistory.navController
            )
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(
                locationViewModel = locationViewModel,
                navController = navControllerWithHistory.navController
            )
        }
        composable(
            NavigationItem.SearchToMain.route,
            arguments = listOf(
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType })
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 0.0
            val longitude =
                backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: 0.0
            MainScreen(
                locationViewModel = locationViewModel,
                navController = navControllerWithHistory.navController,
                initialLatLng = LatLng(latitude, longitude)
            )
        }
    }
}