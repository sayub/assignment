package com.assignment.android.ui.list

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.android.model.Item
import com.assignment.android.ui.ScreenState
import com.assignment.android.ui.composables.DropDownMenu
import com.assignment.android.ui.composables.ShowCircularProgressIndicator

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel()
) {
    val listUiState = viewModel.listUiState.collectAsState()

    when (val state = listUiState.value) {
        is ScreenState.Empty -> ShowOptionsPane(modifier, viewModel)
        is ScreenState.Loading -> ShowCircularProgressIndicator()
        is ScreenState.Success -> {
            val items = state.data as? List<Item> ?: emptyList()
            Column {
                ShowOptionsPane(modifier, viewModel)
                ItemList(items, modifier)
            }
        }
        is ScreenState.Error -> Text("Error: ${state.message}", modifier = modifier)
    }
}

@Composable
fun ShowOptionsPane(modifier: Modifier = Modifier, viewModel: ListViewModel) {
    val optionSelection = remember { mutableIntStateOf(-1) }
    val isChecked = remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(30.dp)) {
            Text("Select option for grouping results")
            DropDownMenu(listOf("listId", "listId and name"), onSelection = {
                optionSelection.intValue = it
            })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )

            Text(text = "Filter out empty names")
        }


        ElevatedButton(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.getListItems() }) {
            Text("Proceed to Results")
        }
    }
}

@Composable
fun ItemList(itemsList: List<Item>, modifier: Modifier = Modifier) {
    Column (modifier = modifier.padding(8.dp)) {
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
        LazyColumn {
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
}

