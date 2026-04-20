package com.example.shoppinglist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.local.ShoppingDatabase
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.RetrofitInstance
import com.example.shoppinglist.data.repository.ShoppingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShoppingRepository

    val allItems: StateFlow<List<ShoppingItem>>
    val purchasedCount: StateFlow<Int>
    val totalCount: StateFlow<Int>

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLoadingSuggestions = MutableStateFlow(false)
    val isLoadingSuggestions: StateFlow<Boolean> = _isLoadingSuggestions.asStateFlow()

    private val _isUkrainian = MutableStateFlow(true)
    val isUkrainian: StateFlow<Boolean> = _isUkrainian.asStateFlow()

    init {
        val database = ShoppingDatabase.getDatabase(application)
        repository = ShoppingRepository(database.shoppingDao(), RetrofitInstance.api)

        allItems = repository.allItems
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        purchasedCount = repository.purchasedCount
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        totalCount = repository.totalCount
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        loadSuggestions()
    }

    fun addItem(name: String, quantity: Int, unit: String) {
        viewModelScope.launch {
            repository.addItem(ShoppingItem(name = name, quantity = quantity, unit = unit))
        }
    }

    fun togglePurchased(item: ShoppingItem) {
        viewModelScope.launch {
            repository.updateItem(item.copy(isPurchased = !item.isPurchased))
        }
    }

    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadSuggestions()
            delay(800)
            _isRefreshing.value = false
        }
    }

    fun toggleLanguage(context: android.content.Context) {
        viewModelScope.launch {
            _isUkrainian.value = !_isUkrainian.value
            val localeTag = if (_isUkrainian.value) "uk" else "en"
            val locales = androidx.core.os.LocaleListCompat.forLanguageTags(localeTag)
            androidx.appcompat.app.AppCompatDelegate.setApplicationLocales(locales)
        }
    }

    private fun loadSuggestions() {
        viewModelScope.launch {
            _isLoadingSuggestions.value = true
            repository.fetchSuggestions().onSuccess { list ->
                _suggestions.value = list
            }
            _isLoadingSuggestions.value = false
        }
    }
}
