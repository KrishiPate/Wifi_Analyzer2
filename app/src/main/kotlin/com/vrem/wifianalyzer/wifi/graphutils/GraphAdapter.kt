package com.vrem.wifianalyzer.wifi.graphutils

import com.jjoe64.graphview.GraphView
import com.vrem.wifianalyzer.wifi.model.WiFiData
import com.vrem.wifianalyzer.wifi.scanner.UpdateNotifier

open class GraphAdapter(private val graphViewNotifiers: List<GraphViewNotifier>) : UpdateNotifier {
    fun graphViews(): List<GraphView> = graphViewNotifiers.map { it.graphView() }

    override fun update(wiFiData: WiFiData) = graphViewNotifiers.forEach { it.update(wiFiData) }

    fun graphViewNotifiers(): List<GraphViewNotifier> = graphViewNotifiers

}