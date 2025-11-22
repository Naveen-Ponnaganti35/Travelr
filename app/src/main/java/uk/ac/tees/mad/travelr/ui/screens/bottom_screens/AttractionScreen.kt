package uk.ac.tees.mad.travelr.ui.screens.bottom_screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AttractionScreen(
//    attractionName: String,
//    latitude: Double,
//    longitude: Double,
//    description: String,
//    onAddToItinerary: () -> Unit,
//    onBack: () -> Unit
//) {
//    val context = LocalContext.current
//    var showSnackbar by remember { mutableStateOf(false) }
//    var snackbarMessage by remember { mutableStateOf("") }
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    // Orientation sensor state
//    val (azimuth, pitch, roll) = rememberOrientationSensor()
//
//    // Image URL from free API (Unsplash Source - no API key needed!)
//    val imageUrl = remember(attractionName) {
//        "https://source.unsplash.com/1600x900/?${attractionName.replace(" ", "+")},landmark,travel"
//    }
//
//    var isAddedToItinerary by remember { mutableStateOf(false) }
//
//    LaunchedEffect(showSnackbar) {
//        if (showSnackbar) {
//            snackbarHostState.showSnackbar(snackbarMessage)
//            showSnackbar = false
//        }
//    }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) },
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = attractionName,
//                        maxLines = 1
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, "Back")
//                    }
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            isAddedToItinerary = !isAddedToItinerary
//                            if (isAddedToItinerary) {
//                                onAddToItinerary()
//                                snackbarMessage = "Added to itinerary!"
//                            } else {
//                                snackbarMessage = "Removed from itinerary"
//                            }
//                            showSnackbar = true
//                        }
//                    ) {
//                        Icon(
//                            imageVector = if (isAddedToItinerary) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                            contentDescription = if (isAddedToItinerary) "Remove from itinerary" else "Add to itinerary",
//                            tint = if (isAddedToItinerary) Color.Red else MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            // Panoramic Image View with Orientation Sensor
//            PanoramicImageView(
//                imageUrl = imageUrl,
//                azimuth = azimuth,
//                pitch = pitch,
//                attractionName = attractionName,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(400.dp)
//            )
//
//            // Scrollable Content
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .padding(16.dp)
//            ) {
//                // Sensor Info Card
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer
//                    )
//                ) {
//                    Row(
//                        modifier = Modifier.padding(12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            Icons.Default.PhoneAndroid,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.primary
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "🎯 Tilt your device to explore the view",
//                            style = MaterialTheme.typography.bodyMedium,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                // About Section
//                Text(
//                    text = "About",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = description.ifEmpty {
//                        "Discover this amazing attraction. Experience an immersive panoramic view by tilting your device in different directions. The image will move as you tilt, creating an interactive viewing experience."
//                    },
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
//                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                // Location Card
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.surfaceVariant
//                    )
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                Icons.Default.LocationOn,
//                                contentDescription = null,
//                                tint = MaterialTheme.colorScheme.primary,
//                                modifier = Modifier.size(24.dp)
//                            )
//                            Spacer(modifier = Modifier.width(12.dp))
//                            Column {
//                                Text(
//                                    text = "Location Coordinates",
//                                    style = MaterialTheme.typography.labelMedium,
//                                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                                )
//                                Text(
//                                    text = "${
//                                        String.format(
//                                            "%.4f",
//                                            latitude
//                                        )
//                                    }, ${String.format("%.4f", longitude)}",
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    fontWeight = FontWeight.Medium
//                                )
//                            }
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Features Info
//                Card(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text(
//                            text = "Features",
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        FeatureItem(
//                            icon = Icons.Default.ScreenRotation,
//                            title = "Orientation Sensor",
//                            description = "Move your device to explore"
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        FeatureItem(
//                            icon = Icons.Default.Image,
//                            title = "High Quality Images",
//                            description = "Powered by Unsplash"
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        FeatureItem(
//                            icon = Icons.Default.TouchApp,
//                            title = "Interactive View",
//                            description = "Drag or tilt to navigate"
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PanoramicImageView(
//    imageUrl: String,
//    azimuth: Float,
//    pitch: Float,
//    attractionName: String,
//    modifier: Modifier = Modifier
//) {
//    var manualOffsetX by remember { mutableFloatStateOf(0f) }
//    var manualOffsetY by remember { mutableFloatStateOf(0f) }
//    var useManualControl by remember { mutableStateOf(false) }
//
//    // Calculate offset based on sensor or manual control
//    val offsetX = if (useManualControl) manualOffsetX else azimuth * 3f
//    val offsetY = if (useManualControl) manualOffsetY else pitch * 5f
//
//    // Smooth animation
//    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "offsetX")
//    val animatedOffsetY by animateFloatAsState(targetValue = offsetY, label = "offsetY")
//
//    Box(
//        modifier = modifier
//            .pointerInput(Unit) {
//                detectDragGestures(
//                    onDragStart = { useManualControl = true },
//                    onDragEnd = {
//                        kotlinx.coroutines.GlobalScope.launch {
//                            delay(3000)
//                            useManualControl = false
//                        }
//                    }
//                ) { change, dragAmount ->
//                    change.consume()
//                    manualOffsetX += dragAmount.x * 0.5f
//                    manualOffsetY += dragAmount.y * 0.5f
//
//                    // Limit the offset
//                    manualOffsetX = manualOffsetX.coerceIn(-300f, 300f)
//                    manualOffsetY = manualOffsetY.coerceIn(-200f, 200f)
//                }
//            }
//    ) {
//        // Background image with parallax effect
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(imageUrl)
//                .crossfade(true)
//                .build(),
//            contentDescription = attractionName,
//            modifier = Modifier
//                .fillMaxSize()
//                .scale(1.3f) // Scale up to allow movement
//                .graphicsLayer {
//                    translationX = animatedOffsetX.coerceIn(-200f, 200f)
//                    translationY = animatedOffsetY.coerceIn(-150f, 150f)
//                },
//            contentScale = ContentScale.Crop
//        )
//
//        // Gradient overlay
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(
//                            Color.Transparent,
//                            Color.Black.copy(alpha = 0.3f),
//                            Color.Black.copy(alpha = 0.7f)
//                        ),
//                        startY = 100f
//                    )
//                )
//        )
//
//        // Title overlay at bottom
//        Column(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(20.dp)
//        ) {
//            Text(
//                text = attractionName,
//                style = MaterialTheme.typography.headlineMedium,
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        // Sensor indicator badge
//        Surface(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(16.dp),
//            shape = RoundedCornerShape(20.dp),
//            color = Color.Black.copy(alpha = 0.6f)
//        ) {
//            Row(
//                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(8.dp)
//                        .clip(CircleShape)
//                        .background(Color.Green)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = if (useManualControl) "Manual" else "Sensor Active",
//                    color = Color.White,
//                    style = MaterialTheme.typography.labelSmall
//                )
//            }
//        }
//
//        // Orientation values overlay (for debugging/demo)
//        Surface(
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp),
//            shape = RoundedCornerShape(12.dp),
//            color = Color.Black.copy(alpha = 0.5f)
//        ) {
//            Column(
//                modifier = Modifier.padding(12.dp)
//            ) {
//                Text(
//                    text = "Azimuth: ${azimuth.toInt()}°",
//                    color = Color.White,
//                    style = MaterialTheme.typography.labelSmall
//                )
//                Text(
//                    text = "Pitch: ${pitch.toInt()}°",
//                    color = Color.White,
//                    style = MaterialTheme.typography.labelSmall
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun FeatureItem(
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    title: String,
//    description: String
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            modifier = Modifier.size(20.dp),
//            tint = MaterialTheme.colorScheme.primary
//        )
//        Spacer(modifier = Modifier.width(12.dp))
//        Column {
//            Text(
//                text = title,
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.Medium
//            )
//            Text(
//                text = description,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    }
//}
//
//// Orientation Sensor Hook
//@Composable
//fun rememberOrientationSensor(): Triple<Float, Float, Float> {
//    val context = LocalContext.current
//    var azimuth by remember { mutableFloatStateOf(0f) }
//    var pitch by remember { mutableFloatStateOf(0f) }
//    var roll by remember { mutableFloatStateOf(0f) }
//
//    DisposableEffect(context) {
//        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
//
//        val accelerometerReading = FloatArray(3)
//        val magnetometerReading = FloatArray(3)
//        val rotationMatrix = FloatArray(9)
//        val orientationAngles = FloatArray(3)
//
//        val listener = object : SensorEventListener {
//            override fun onSensorChanged(event: SensorEvent) {
//                when (event.sensor.type) {
//                    Sensor.TYPE_ACCELEROMETER -> {
//                        System.arraycopy(
//                            event.values,
//                            0,
//                            accelerometerReading,
//                            0,
//                            accelerometerReading.size
//                        )
//                    }
//
//                    Sensor.TYPE_MAGNETIC_FIELD -> {
//                        System.arraycopy(
//                            event.values,
//                            0,
//                            magnetometerReading,
//                            0,
//                            magnetometerReading.size
//                        )
//                    }
//                }
//
//                // Calculate orientation
//                SensorManager.getRotationMatrix(
//                    rotationMatrix, null,
//                    accelerometerReading, magnetometerReading
//                )
//                SensorManager.getOrientation(rotationMatrix, orientationAngles)
//
//                azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
//                pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
//                roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()
//            }
//
//            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
//        }
//
//        accelerometer?.let {
//            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_GAME)
//        }
//        magnetometer?.let {
//            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_GAME)
//        }
//
//        onDispose {
//            sensorManager.unregisterListener(listener)
//        }
//    }
//
//    return Triple(azimuth, pitch, roll)
//}


//@Composable
//fun AttractionScreen(latitude: Double, longitude: Double,modifier: Modifier = Modifier) {
////    Column(
////        modifier=Modifier.fillMaxSize(),
////        verticalArrangement = Arrangement.Center,
////        horizontalAlignment = Alignment.CenterHorizontally
////    ){
////        val context = LocalContext.current
////
////        Button(onClick = {
////            val uri = Uri.parse("https://www.google.com/maps/@?api=1&map_action=pano&viewpoint=$latitude,$longitude")
////            val intent = Intent(Intent.ACTION_VIEW, uri)
////            context.startActivity(intent)
////        }) {
////            Text("Open Street View")
////        }
////
////            StreetViewWeb(latitude, longitude, modifier = Modifier.weight(1f))
////    }
//
////    Column(modifier = modifier.fillMaxSize()) {
//////        val context = LocalContext.current
//////        Button(
//////            onClick = {
//////                // Fallback to external browser (no key needed)
//////                val uri = Uri.parse("https://www.mapillary.com/app/?lat=$latitude&lng=$longitude&z=18")
//////                val intent = Intent(Intent.ACTION_VIEW, uri)
//////                context.startActivity(intent)
//////            },
//////            modifier = Modifier.fillMaxWidth()
//////        ) {
//////            Text("Open in Mapillary Explorer")
//////        }
//////
//////        MapillaryWebViewer(
//////            latitude = latitude,
//////            longitude = longitude,
//////            modifier = Modifier
//////                .weight(1f)
//////                .fillMaxWidth()
//////        )
////
//////        SimpleStreetView(latitude,longitude)
////
////        StreetViewWeb(latitude,longitude)
////    }
//    val context = LocalContext.current
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "View Location in Street View",
//            style = MaterialTheme.typography.titleMedium
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                // ✅ Open Google Maps Street View (no API key needed)
//                val uri = Uri.parse("https://www.google.com/maps/@?api=1&map_action=pano&viewpoint=$latitude,$longitude")
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                context.startActivity(intent)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Text("Open Street View in Google Maps")
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionScreen(
    latitude: Double,
    longitude: Double,
    attractionName: String = "Attraction",
    onBackClick: () -> Unit = {},
    onAddToItinerary: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    // Gyroscope state
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) }
    var gyroscopeEnabled by remember { mutableStateOf(true) }

    // Sensor listener for gyroscope control
    DisposableEffect(gyroscopeEnabled) {
        val listener = object : SensorEventListener {
            private val rotationMatrix = FloatArray(9)
            private val orientation = FloatArray(3)

            override fun onSensorChanged(event: SensorEvent) {
                if (gyroscopeEnabled && event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientation)

                    val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                    val pitch = Math.toDegrees(orientation[1].toDouble()).toFloat()

                    webView?.evaluateJavascript(
                        "if(typeof viewer !== 'undefined') { viewer.setYaw($azimuth); viewer.setPitch($pitch); }",
                        null
                    )
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (gyroscopeEnabled && gyroscope != null) {
            sensorManager.registerListener(
                listener,
                gyroscope,
                SensorManager.SENSOR_DELAY_GAME
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(attractionName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { gyroscopeEnabled = !gyroscopeEnabled }) {
                        Text(if (gyroscopeEnabled) "Gyro: ON" else "Gyro: OFF")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onAddToItinerary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Add to Itinerary")
            }
        },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // WebView for panoramic display
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            allowFileAccess = true
                            allowContentAccess = true
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        }

                        webChromeClient = WebChromeClient()
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }
                        }

                        loadDataWithBaseURL(
                            "https://example.com",
                            generatePanoramaHTML(latitude, longitude, gyroscopeEnabled),
                            "text/html",
                            "UTF-8",
                            null
                        )

                        webView = this
                    }
                },
                update = { view ->
                    webView = view
                },
                modifier = Modifier.fillMaxSize()
            )

            // Loading indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

private fun generatePanoramaHTML(lat: Double, lng: Double, gyroEnabled: Boolean): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
            <title>Panorama</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pannellum@2.5.6/build/pannellum.css"/>
            <script src="https://cdn.jsdelivr.net/npm/pannellum@2.5.6/build/pannellum.js"></script>
            <style>
                * { margin: 0; padding: 0; }
                body { overflow: hidden; }
                #panorama { width: 100vw; height: 100vh; }
                .loading { 
                    position: absolute; 
                    top: 50%; 
                    left: 50%; 
                    transform: translate(-50%, -50%);
                    color: white;
                    font-size: 20px;
                    z-index: 1000;
                }
            </style>
        </head>
        <body>
            <div class="loading" id="loadingText">Loading panorama...</div>
            <div id="panorama"></div>
            
            <script>
                let viewer;
                const lat = $lat;
                const lng = $lng;
                
                // Fetch image from Mapillary API (FREE - no key required for basic use)
                async function loadPanorama() {
                    try {
                        // Using Mapillary's public imagery
                        const radius = 100; // Search radius in meters
                        const apiUrl = 'https://graph.mapillary.com/images?fields=id,thumb_2048_url&bbox=' + 
                            (lng - 0.001) + ',' + (lat - 0.001) + ',' + 
                            (lng + 0.001) + ',' + (lat + 0.001) + 
                            '&access_token=MLY|4142433049200173|72206abe5035850d6743b23a49c41333';
                        
                        const response = await fetch(apiUrl);
                        const data = await response.json();
                        
                        let imageUrl;
                        
                        if (data.data && data.data.length > 0) {
                            // Found Mapillary image
                            imageUrl = data.data[0].thumb_2048_url;
                        } else {
                            // Fallback: Use equirectangular test image
                            imageUrl = 'https://pannellum.org/images/alma.jpg';
                        }
                        
                        document.getElementById('loadingText').style.display = 'none';
                        
                        // Initialize Pannellum viewer
                        viewer = pannellum.viewer('panorama', {
                            "type": "equirectangular",
                            "panorama": imageUrl,
                            "autoLoad": true,
                            "autoRotate": ${if (!gyroEnabled) "-2" else "0"},
                            "compass": true,
                            "showControls": true,
                            "showFullscreenCtrl": false,
                            "showZoomCtrl": true,
                            "mouseZoom": true,
                            "draggable": ${!gyroEnabled},
                            "friction": 0.15,
                            "hfov": 100,
                            "minHfov": 50,
                            "maxHfov": 120,
                            "pitch": 0,
                            "yaw": 0
                        });
                        
                    } catch (error) {
                        console.error('Error loading panorama:', error);
                        document.getElementById('loadingText').textContent = 
                            'Loading default panorama...';
                        
                        // Fallback image
                        viewer = pannellum.viewer('panorama', {
                            "type": "equirectangular",
                            "panorama": "https://pannellum.org/images/alma.jpg",
                            "autoLoad": true,
                            "autoRotate": ${if (!gyroEnabled) "-2" else "0"},
                            "compass": true,
                            "showControls": true,
                            "draggable": ${!gyroEnabled},
                            "mouseZoom": true
                        });
                    }
                }
                
                // Load on page ready
                window.addEventListener('load', loadPanorama);
            </script>
        </body>
        </html>
    """.trimIndent()
}



@Composable
fun StreetViewWeb(lat: Double, lng: Double) {
    val context = LocalContext.current

    // ✅ Remember WebView to prevent recreation on recomposition
    val webView = remember {
        WebView(context).apply {
            // Enable smooth GPU rendering
            setLayerType(View.LAYER_TYPE_HARDWARE, null)

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                userAgentString =
                    "Mozilla/5.0 (Linux; Android 10; Pixel 3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"
            }

            webViewClient = WebViewClient()

            // ✅ Use the simple Street View URL (no API key)
            val streetViewUrl =
                "https://www.google.com/maps/@?api=1&map_action=pano&viewpoint=$lat,$lng"
            loadUrl(streetViewUrl)
        }
    }

    // ✅ Use AndroidView with remembered WebView
    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxSize(),
        update = { }
    )
}





@Composable
fun SimpleStreetView(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Static map preview (always loads)
        AsyncImage(
            model = "https://maps.stamen.com/terrain/#12/$latitude/$longitude",
            contentDescription = "Location",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentScale = ContentScale.Crop
        )

        // Multiple service buttons
        Row {
            StreetViewButton(
                label = "Google",
                url = "google.streetview://panorama?ll=$latitude,$longitude"
            )
            StreetViewButton(
                label = "OSM",
                url = "https://www.openstreetmap.org/?mlat=$latitude&mlon=$longitude#map=18/$latitude/$longitude"
            )
        }
    }
}

@Composable
fun StreetViewButton(label: String, url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            try {
                val uri = Uri.parse(url)
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: Exception) {
                // Fallback to browser
                val browserUri = Uri.parse(url.replace("://", "www.").replaceFirstChar { it.uppercase() })
                context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        },
    ) {
        Text(label)
    }
}





