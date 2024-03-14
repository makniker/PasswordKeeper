package com.example.passwordkeeper.presentation.ui.password

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.passwordkeeper.R
import com.example.passwordkeeper.core.showToast
import com.example.passwordkeeper.databinding.FragmentPasswordBinding
import com.example.passwordkeeper.presentation.ui.UiStates

import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val passwordViewModel by createViewModelLazy(
        PasswordViewModel::class,
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
        _binding = FragmentPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.getLong("pageId")?.let { it1 ->
                passwordViewModel.init(it1)
                passwordViewModel.nameLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is UiStates.Loading -> {
                            title.text = getString(R.string.loading)
                            url.text = getString(R.string.loading)
                        }

                        is UiStates.Success -> {
                            title.text = it.data.first
                            url.text = it.data.second
                        }

                        else -> {}
                    }
                }
                passwordViewModel.passwordLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is UiStates.Error -> showToast(it.message)
                        is UiStates.Init -> {
                            password.setText(R.string.secret_string)
                            watchButton.setStateData(getString(R.string.see_password))
                        }

                        is UiStates.Loading -> {
                            watchButton.setStateLoading()
                        }

                        is UiStates.Success -> {
                            password.setText(it.data)
                            watchButton.setStateData(getString(R.string.see_password))
                        }
                    }
                }
                watchButton.setOnClickListener {
                    passwordViewModel.encryptPassword(it1)
                }
                copyButton.setOnClickListener {
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData =
                        ClipData.newPlainText("simple text", passwordViewModel.copyPassword(it1))
                    clipboard.setPrimaryClip(clip)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}