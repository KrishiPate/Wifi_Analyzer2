package com.vrem.wifianalyzer.wifi.band

import com.vrem.util.allCountries
import com.vrem.util.findByCountryCode
import java.util.*

class WiFiChannelCountry(private val country: Locale) {
    private val unknown = "-Unknown"
    private val wiFiChannelGHZ2 = WiFiChannelCountryGHZ2()
    private val wiFiChannelGHZ5 = WiFiChannelCountryGHZ5()
    private val wiFiChannelGHZ6 = WiFiChannelCountryGHZ6()

    fun countryCode(): String = country.country

    fun countryName(currentLocale: Locale): String {
        val countryName: String = country.getDisplayCountry(currentLocale)
        return if (country.country == countryName) countryName + unknown else countryName
    }

    fun channelsGHZ2(): SortedSet<Int> = wiFiChannelGHZ2.findChannels(country.country)

    fun channelsGHZ5(): SortedSet<Int> = wiFiChannelGHZ5.findChannels(country.country)

    fun channelsGHZ6(): SortedSet<Int> = wiFiChannelGHZ6.findChannels()

    fun channelAvailableGHZ2(channel: Int): Boolean = channelsGHZ2().contains(channel)

    fun channelAvailableGHZ5(channel: Int): Boolean = channelsGHZ5().contains(channel)

    fun channelAvailableGHZ6(channel: Int): Boolean = channelsGHZ6().contains(channel)

    companion object {
        fun find(countryCode: String): WiFiChannelCountry = WiFiChannelCountry(findByCountryCode(countryCode))

        fun findAll(): List<WiFiChannelCountry> = allCountries().map { WiFiChannelCountry(it) }
    }

}