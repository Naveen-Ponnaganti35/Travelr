//package uk.ac.tees.mad.travelr.ui.screens.bottom_screens
//
//import android.content.Context
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import org.maplibre.android.maps.CameraUpdateFactory
//import org.maplibre.android.maps.MapView
//import org.maplibre.android.maps.Style
//import org.maplibre.android.style.layers.PropertyFactory
//import org.maplibre.android.style.layers.SymbolLayer
//import org.maplibre.android.style.sources.GeoJsonSource
//import org.maplibre.geojson.Feature
//import org.maplibre.geojson.FeatureCollection
//import org.maplibre.geojson.Point
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TestScreen(
//    attractionName: String,
//    latitude: Double,
//    longitude: Double,
//    description: String,
//    onAddToItinerary: () -> Unit,
//    onBack: () -> Unit
//) {
//    var heading by remember { mutableStateOf(0f) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(attractionName) },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = onAddToItinerary) {
//                Icon(Icons.Filled.Add, contentDescription = "Add to Itinerary")
//            }
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            // Map Section
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(350.dp)
//                    .padding(16.dp),
//                elevation = CardDefaults.cardElevation(4.dp)
//            ) {
//                OrientationAwareMap(
//                    latitude = latitude,
//                    longitude = longitude,
//                    heading = heading,
//                    onHeadingChange = { heading = it },
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            // Details
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(text = "About", style = MaterialTheme.typography.headlineSmall)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = description)
//                Spacer(modifier = Modifier.height(16.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(Icons.Filled.LocationOn, contentDescription = null)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "$latitude, $longitude")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun OrientationAwareMap(
//    latitude: Double,
//    longitude: Double,
//    heading: Float,
//    onHeadingChange: (Float) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    var mapView: MapView? by remember { mutableStateOf(null) }
//
//    // Device rotation sensor
//    val sensorManager =
//        remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
//    val rotationSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) }
//
//    DisposableEffect(Unit) {
//        val listener = object : SensorEventListener {
//            override fun onSensorChanged(event: SensorEvent?) {
//                event?.let {
//                    val rotationMatrix = FloatArray(9)
//                    val orientation = FloatArray(3)
//                    SensorManager.getRotationMatrixFromVector(rotationMatrix, it.values)
//                    SensorManager.getOrientation(rotationMatrix, orientation)
//                    var azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
//                    if (azimuth < 0) azimuth += 360f
//                    onHeadingChange(azimuth)
//
//                    // Update map camera
//                    mapView?.getMapAsync { map ->
//                        map.animateCamera(
//                            CameraUpdateFactory.newCameraPosition(
//                                org.maplibre.android.camera.CameraPosition.Builder()
//                                    .target(com.mapbox.mapboxsdk.geometry.LatLng(latitude, longitude))
//                                    .zoom(15.0)
//                                    .tilt(45.0)
//                                    .bearing(azimuth.toDouble())
//                                    .build()
//                            )
//                        )
//                    }
//                }
//            }
//
//            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//        }
//
//        sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_GAME)
//        onDispose { sensorManager.unregisterListener(listener) }
//    }
//
//    AndroidView(
//        modifier = modifier,
//        factory = { ctx ->
//            MapView(ctx).apply {
//                mapView = this
//
//                getMapAsync { map ->
//                    // Load free MapLibre style
//                    map.setStyle(Style.Builder().fromUri("https://demotiles.maplibre.org/style.json")) { style ->
//                        // Add a marker
//                        val feature = Feature.fromGeometry(Point.fromLngLat(longitude, latitude))
//                        val collection = FeatureCollection.fromFeature(feature)
//                        val source = GeoJsonSource("marker-source")
//                        source.setGeoJson(collection)
//                        style.addSource(source)
//
//                        val layer = SymbolLayer("marker-layer", "marker-source")
//                            .withProperties(PropertyFactory.iconImage("marker-15"))
//                        style.addLayer(layer)
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun TestScreenPreview() {
//    TestScreen(
//        attractionName = "Prado Museum",
//        latitude = 40.414000,
//        longitude = -3.691000,
//        description = "The Prado Museum is the main Spanish national art museum.",
//        onAddToItinerary = {},
//        onBack = {}
//    )
//}
