package com.example.smartlockerandroid.ui.components.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.data.model.module.output.ModuleDTO
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapBox(
    modules: List<ModuleDTO>,
    center: GeoPoint
) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }
    }

    DisposableEffect (Unit) {
        mapView.onResume()
        onDispose {
            mapView.onPause()
        }
    }

    AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize()) {
        it.controller.setZoom(17.0)
        it.controller.setCenter(center)

        it.overlays.clear()
        val user = Marker(it).apply {
            position = center
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            icon = ContextCompat.getDrawable(context, R.drawable.user_icon)
            title = "Your location"
        }
        it.overlays.add(user)

        modules.forEach{ module ->
            val marker = Marker(it).apply {
                position = GeoPoint(module.location.latitude, module.location.longitude)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                icon = ContextCompat.getDrawable(context, R.drawable.smartlocker_icon_mini)
                title = module.locName
                snippet = "Available: ${module.availableLockers}/${module.maxN}"
                setOnMarkerClickListener { pressed, _ ->
                    it.overlays.forEach { mark ->
                        if (mark is Marker && mark != pressed) mark.closeInfoWindow()
                    }
                    pressed.showInfoWindow()
                    true
                }
            }
            it.overlays.add(marker)
        }

    }
}