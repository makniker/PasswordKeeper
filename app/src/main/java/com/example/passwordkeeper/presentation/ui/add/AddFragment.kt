package com.example.passwordkeeper.presentation.ui.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.passwordkeeper.R
import com.example.passwordkeeper.core.showToast
import com.example.passwordkeeper.databinding.FragmentAddBinding
import com.example.passwordkeeper.presentation.ui.UiStates
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val addViewModel by createViewModelLazy(
        AddViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            addButton.setOnClickListener {
                addViewModel.addPage(
                    title.text.toString(),
                    url.text.toString(),
                    password.text.toString()
                )
            }
            addViewModel.liveData.observe(viewLifecycleOwner) {
                when (it) {
                    is UiStates.Error -> {
                        showToast(it.message)
                        addButton.setStateData(getString(R.string.add_new_page))
                    }

                    is UiStates.Loading -> addButton.setStateLoading()
                    is UiStates.Success -> {
                        addButton.setStateData(getString(R.string.add_new_page))
                        findNavController().popBackStack()
                    }

                    is UiStates.Init -> addButton.setStateData(getString(R.string.add_new_page))
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}