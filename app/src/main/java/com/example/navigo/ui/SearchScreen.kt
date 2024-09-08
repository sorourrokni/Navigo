package com.example.navigo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.navigo.R
import com.example.navigo.component.SearchItem
import com.example.navigo.component.SpeakDialog
import com.example.navigo.data.repository.DistanceRepository
import com.example.navigo.data.repository.SearchRepository
import com.example.navigo.viewModel.DistanceViewModel
import com.example.navigo.viewModel.DistanceViewModelFactory
import com.example.navigo.viewModel.LocationViewModel
import com.example.navigo.viewModel.SearchViewModel
import com.example.navigo.viewModel.SearchViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng

@Composable
fun SearchScreen(
    locationViewModel: LocationViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            SearchRepository()
        )
    )
    val distanceViewModel: DistanceViewModel = viewModel(
        factory = DistanceViewModelFactory(
            DistanceRepository()
        )
    )

    val scope = rememberCoroutineScope()
    var showSpeakDialog by remember { mutableStateOf(false) }
    var speakText by remember { mutableStateOf("") }

    val latitude by locationViewModel.latitude.collectAsState()
    val longitude by locationViewModel.longitude.collectAsState()
    val userLocation = LatLng(latitude, longitude)

    val query by searchViewModel.query.collectAsState()
    val searchResults by searchViewModel.searchResults.collectAsState(emptyList())

    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(latitude, longitude) {
        latitude.let { lat ->
            longitude.let { lon ->
                searchViewModel.updateLocation(lat, lon)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.1f))
    ) {
        if (showSpeakDialog) {
            SpeakDialog(
                text = speakText,
                onDismissRequest = { showSpeakDialog = false },
            )
        }

        TextField(
            value = query,
            trailingIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "back icon",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            },
            leadingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { searchViewModel.onQueryChange("") }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear text",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                } else {
                    IconButton(onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            showSpeakDialog = true
                            startSpeechToText(
                                speechRecognizer = speechRecognizer,
                                onResult = { resultText ->
                                    speakText = resultText
                                    searchViewModel.onQueryChange(speakText)
                                    scope.launch {
                                        delay(1000)
                                        speakText = ""
                                        showSpeakDialog = false
                                    }
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    showSpeakDialog = false
                                })
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_microphone),
                            contentDescription = "Mic icon",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = "جستجو",
                    style = MaterialTheme.typography.titleLarge.copy(
                        textDirection = TextDirection.Rtl,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            onValueChange = { newQuery -> searchViewModel.onQueryChange(newQuery) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colorScheme.background),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textDirection = TextDirection.Rtl,
                textAlign = TextAlign.Start
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.surfaceTint.copy(0.7f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.surfaceTint.copy(0.7f),
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(count = searchResults.size) { index ->
                SearchItem(
                    search = searchResults[index].search,
                    distance = searchResults[index].distance,
                    onClick = { latLng ->
                        selectedLocation = latLng
                        selectedLocation?.let {
                            navController.navigate("main/${it.latitude}/${it.longitude}")
                        }
                    },
                )
            }
        }
    }
}

fun startSpeechToText(
    speechRecognizer: SpeechRecognizer,
    onResult: (String) -> Unit,
    onError: (String) -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_PROMPT, "لطفاً صحبت کنید...")
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR")
    }
    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
            onError("صدای شما دریافت نشد")
        }

        override fun onResults(results: Bundle?) {
            val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!data.isNullOrEmpty()) {
                onResult(data[0])
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    })
    speechRecognizer.startListening(intent)
}
