package com.example.passwordkeeper.presentation.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordkeeper.domain.AddResponse
import com.example.passwordkeeper.domain.DataRepository
import com.example.passwordkeeper.presentation.ui.UiStates
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddViewModel @Inject constructor(
    private val repository: DataRepository.AddPage
) : ViewModel() {

    private val _liveData = MutableLiveData<UiStates<Unit>>(UiStates.Init())
    val liveData: LiveData<UiStates<Unit>> = _liveData

    fun addPage(title: String, url: String, password: String) {
        viewModelScope.launch {
            _liveData.postValue(UiStates.Loading())
            when (val r = repository.add(title, url, password)) {
                is AddResponse.Error -> _liveData.postValue(UiStates.Error(r.message))
                AddResponse.Success -> {
                    _liveData.postValue(UiStates.Success(Unit))
                }
            }
        }

    }
}