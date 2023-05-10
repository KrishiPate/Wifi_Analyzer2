package com.vrem.wifianalyzer.wifi.predicate

import com.vrem.wifianalyzer.settings.Settings
import com.vrem.wifianalyzer.wifi.band.WiFiBand
import com.vrem.wifianalyzer.wifi.model.SSID
import com.vrem.wifianalyzer.wifi.model.Security
import com.vrem.wifianalyzer.wifi.model.Strength
import com.vrem.wifianalyzer.wifi.model.WiFiDetail

internal typealias Predicate = (wiFiDetail: WiFiDetail) -> Boolean
internal typealias ToPredicate<T> = (T) -> Predicate

internal val truePredicate: Predicate = { true }
internal val falsePredicate: Predicate = { false }

internal fun List<Predicate>.anyPredicate(): Predicate =
        { wiFiDetail -> this.any { predicate -> predicate(wiFiDetail) } }

internal fun List<Predicate>.allPredicate(): Predicate =
        { wiFiDetail -> this.all { predicate -> predicate(wiFiDetail) } }

fun WiFiBand.predicate(): Predicate =
        { wiFiDetail -> wiFiDetail.wiFiSignal.wiFiBand == this }

internal fun Strength.predicate(): Predicate =
        { wiFiDetail -> wiFiDetail.wiFiSignal.strength == this }

internal fun SSID.predicate(): Predicate =
        { wiFiDetail -> wiFiDetail.wiFiIdentifier.ssid.contains(this) }

internal fun Security.predicate(): Predicate =
        { wiFiDetail -> wiFiDetail.securities.contains(this) }

private fun Set<SSID>.ssidPredicate(): Predicate =
        if (this.isEmpty())
            truePredicate
        else
            this.map { it.predicate() }.anyPredicate()

internal fun <T : Enum<T>> makePredicate(values: Array<T>, filter: Set<T>, toPredicate: ToPredicate<T>): Predicate =
        if (filter.size >= values.size)
            truePredicate
        else
            filter.map { toPredicate(it) }.anyPredicate()

private fun predicates(settings: Settings, wiFiBands: Set<WiFiBand>): List<Predicate> =
        listOf(settings.findSSIDs().ssidPredicate(),
                makePredicate(WiFiBand.values(), wiFiBands) { wiFiBand -> wiFiBand.predicate() },
                makePredicate(Strength.values(), settings.findStrengths()) { strength -> strength.predicate() },
                makePredicate(Security.values(), settings.findSecurities()) { security -> security.predicate() })

fun makeAccessPointsPredicate(settings: Settings): Predicate =
        predicates(settings, settings.findWiFiBands()).allPredicate()

fun makeOtherPredicate(settings: Settings): Predicate =
        predicates(settings, setOf(settings.wiFiBand())).allPredicate()
