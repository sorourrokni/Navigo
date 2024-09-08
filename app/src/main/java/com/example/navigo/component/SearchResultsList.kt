//package com.example.navigo.component
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.navigo.data.model.common.Search
//import org.neshan.common.model.LatLng
//
//@Composable
//fun SearchResultsList(searchResults: List<Search>, onItemClick: (LatLng) -> Unit) {
//    LazyColumn(modifier = Modifier.padding(8.dp)) {
//        items(count = searchResults.size) { index ->
//            SearchItem(search = searchResults[index], onClick = onItemClick)
//        }
//    }
//}