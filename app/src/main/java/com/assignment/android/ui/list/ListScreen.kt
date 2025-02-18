package com.assignment.android.ui.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.android.model.Item
import com.assignment.android.ui.ScreenState
import com.assignment.android.ui.ShowCircularProgressIndicator

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel()
) {
    val listUiState = viewModel.listUiState.collectAsState()

    when (val state = listUiState.value) {
        is ScreenState.Empty -> viewModel.getListItems()
        is ScreenState.Loading -> ShowCircularProgressIndicator()
        is ScreenState.Success -> {
            val items = state.data as? List<Item> ?: emptyList()
            ItemList(items, modifier)
        }
        is ScreenState.Error -> Text("Error: ${state.message}", modifier = modifier)
    }
}

@Composable
fun ItemList(itemsList: List<Item>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray) // Background to distinguish header
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ID",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "List ID",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Name",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        items(itemsList.size) { index ->
           Row {
               Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                   Text(itemsList[index].id.toString())
               }
               Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                   Text(itemsList[index].listId.toString())
               }
               Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                   Text(itemsList[index].name.toString())
               }
           }
        }
    }
}

