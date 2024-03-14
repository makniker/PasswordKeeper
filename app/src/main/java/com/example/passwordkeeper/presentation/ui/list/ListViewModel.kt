package com.example.passwordkeeper.presentation.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordkeeper.domain.DataRepository
import com.example.passwordkeeper.presentation.ui.UiStates
import kotlinx.coroutines.launch
import javax.inject.Inject


class ListViewModel @Inject constructor(
    private val repository: DataRepository.ProvideList,
) : ViewModel() {

    private val _listLiveData = MutableLiveData<UiStates<MutableList<PageListUI>>>()
    val listLiveData: LiveData<UiStates<MutableList<PageListUI>>> = _listLiveData

    fun fetch() {
        viewModelScope.launch {
            _listLiveData.postValue(UiStates.Loading())
            _listLiveData.postValue(
                UiStates.Success(
                    ArrayList(
                        repository.pagesOnline().map {
                            PageListUI(it.id, it.icon, it.name, it.iconProvided)
                        }
                    )
                )
            )
        }
    }
}