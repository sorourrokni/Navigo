package com.example.navigo.navigation

sealed class NavigationItem(var route: String) {
    data object Main :
        NavigationItem(
            route = "main"
        )

    data object Search :
        NavigationItem(
            route = "search"
        )

    data object SearchToMain :
        NavigationItem(
            route = "main/{latitude}/{longitude}"
        )
}