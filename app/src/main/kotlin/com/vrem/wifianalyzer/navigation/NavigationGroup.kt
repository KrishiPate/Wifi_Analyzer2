package com.vrem.wifianalyzer.navigation

import android.view.Menu

enum class NavigationGroup(val navigationMenus: List<NavigationMenu>) {
    // ADD NavigationMenu.TIME_GRAPh
    GROUP_FEATURE(listOf(NavigationMenu.ACCESS_POINTS, NavigationMenu.CHANNEL_RATING, NavigationMenu.CHANNEL_GRAPH)),
    GROUP_OTHER(listOf(NavigationMenu.EXPORT)),
    //add NavigationMenu.SETTINGS
    GROUP_SETTINGS(listOf(NavigationMenu.ABOUT));

    fun next(navigationMenu: NavigationMenu): NavigationMenu {
        var index = navigationMenus.indexOf(navigationMenu)
        if (index < 0) {
            return navigationMenu
        }
        index++
        if (index >= navigationMenus.size) {
            index = 0
        }
        return navigationMenus[index]
    }

    fun previous(navigationMenu: NavigationMenu): NavigationMenu {
        var index = navigationMenus.indexOf(navigationMenu)
        if (index < 0) {
            return navigationMenu
        }
        index--
        if (index < 0) {
            index = navigationMenus.size - 1
        }
        return navigationMenus[index]
    }

    fun populateMenuItems(menu: Menu): Unit =
            navigationMenus.forEach {
                val menuItem = menu.add(ordinal, it.ordinal, it.ordinal, it.title)
                menuItem.setIcon(it.icon)
            }

}