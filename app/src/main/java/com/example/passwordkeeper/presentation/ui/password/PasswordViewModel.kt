package com.example.passwordkeeper.presentation.ui.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordkeeper.domain.DataRepository
import com.example.passwordkeeper.domain.PasswordResponse
import com.example.passwordkeeper.presentation.ui.UiStates
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordViewModel @Inject constructor(
    private val repository: DataRepository.WatchPassword
) : ViewModel() {
    private val _nameLiveData = MutableLiveData<UiStates<Pair<String, String>>>(UiStates.Init())
    val nameLiveData: LiveData<UiStates<Pair<String, String>>> = _nameLiveData

    private val _passwordLiveData = MutableLiveData<UiStates<String>>(UiStates.Init())
    val passwordLiveData: LiveData<UiStates<String>> = _passwordLiveData

    fun init(pageId: Long) {
        viewModelScope.launch {
            _nameLiveData.postValue(UiStates.Loading())
            _nameLiveData.postValue(UiStates.Success(repository.getPageById(pageId)))
        }
    }

    fun encryptPassword(pageId: Long) {
        viewModelScope.launch {
            _passwordLiveData.postValue(UiStates.Loading())
            when (val r = repository.getPasswordById(pageId)) {
                is PasswordResponse.Error -> _passwordLiveData.postValue(UiStates.Error(r.message))
                is PasswordResponse.Success -> _passwordLiveData.postValue(UiStates.Success(r.password))
            }
        }
    }

    fun copyPassword(pageId: Long): String? {
        var string: String? = null
        viewModelScope.launch {
            when (val r = repository.getPasswordById(pageId)) {
                is PasswordResponse.Error -> _passwordLiveData.postValue(UiStates.Error(r.message))
                is PasswordResponse.Success -> {
                    string = r.password
                }
            }
        }
        return string
    }
}