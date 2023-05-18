package com.notas.inventory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.notas.inventory.ui.AppViewModelProvider
import com.notas.inventory.ui.home.HomeDestination
import com.notas.inventory.ui.navigation.NavigationDestination


object pdfDestination : NavigationDestination {
    override val route = "pdf"
    override val titleRes = R.string.app_name
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Pdf(
    onNavigateUp: () -> Unit,
    navigateToPdfs: () -> Unit,
    onSendButtonClicked:(String, String) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: HoneViewModel = viewModel(factory = AppViewModelProvider.Factory),
    unionviewModel: UnionViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {


    val homeUiState by viewModel.homeUiState.collectAsState()
    val ole by remember { mutableStateOf("") }
  //  homeUiState.itemList.asSequence().map { it -> it.name = ole }
    var elo = homeUiState.itemList.asSequence().map { it -> it.name}.toList()

   // homeUiState.itemList.asSequence().map { it -> it.name = ole }
    var eli = homeUiState.itemList.asSequence().map { it -> it.price    }.toList()

    //homeUiState.itemList.asSequence().map { it -> it.name = ole }
    var ilo = homeUiState.itemList.asSequence().map { it -> it.quantity}.toList()

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )
    var nil by remember { mutableStateOf("") }

    @Composable
    fun golf(r: List<Double>): Double {
        var mil: Double= 0.0
        for (i in r.indices) {
            mil += r[i]

        }
        return mil
    }

    var cull = golf(eli).toString()

    // Load the weather at the initial composition.
    LaunchedEffect(true) {

    }


    val context = LocalContext


    val newOrder = stringResource(R.string.new_cupcake_order)
    val orderSumary = "$elo + $cull + $newOrder"

    Scaffold(
        topBar = {

            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },

    )
    {padding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            modifier = Modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ){

            item { Sample(multiplePermissionsState)}
            item {
                TextButton(onClick = { unionviewModel.crearPDF(elo, eli, ilo) }) {
                    Text(stringResource(R.string.add_pdf))
                }

            }
            item {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {onSendButtonClicked(newOrder, orderSumary)}
                ) {
                    Text(stringResource(R.string.send))
                }
            }
        }


    }


}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Sample(multiplePermissionsState: MultiplePermissionsState) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
        Text(" Read storage permissions Granted! \n File address:Download/file-pdf \n Thank you! ")
    } else {
        Column {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permissions")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them."
        }
    )
    return textToShow.toString()
}

