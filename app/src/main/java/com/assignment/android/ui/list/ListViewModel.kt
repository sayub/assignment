package com.assignment.android.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.android.domain.ListRepository
import com.assignment.android.model.Item
import com.assignment.android.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(val listRepository: ListRepository) : ViewModel() {

    private val _listUiState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val listUiState: StateFlow<ScreenState>
        get() = _listUiState

    fun getListItems() {
        _listUiState.value = ScreenState.Loading

        try {
            viewModelScope.launch {
                val originalList = listRepository.getList()?.filter { it.name != null && it.name != "" }
                    ?.sortedWith(compareBy<Item> { it.listId }
                        .thenBy { extractNumberFromName(it.name ?: "0") })


                val list = originalList?.mapIndexed { index, item ->
                    // Check if current item listId is the same as previous one
                    if (index > 0 && item.listId == originalList[index - 1].listId) {
                        // Set the listId to null if the current listId is the same as the previous one
                        item.copy(listId = null)
                    } else {
                        item
                    }
                }
                _listUiState.value = ScreenState.Success(list)
            }
        } catch (e: HttpException) {
            _listUiState.value = ScreenState.Error(e.code(), e.message(), e.response()?.errorBody()?.string())
        } catch (e: Exception) {
            _listUiState.value = ScreenState.Error(message = e.message ?: "")
        }
    }

    fun extractNumberFromName(name: String): Int {
        // Extracts the first sequence of digits from the name (e.g., "item 929" -> 929)
        val matchResult = Regex("\\d+").find(name)
        return matchResult?.value?.toIntOrNull() ?: Int.MAX_VALUE // Default to Int.MAX_VALUE if no number found
    }
}