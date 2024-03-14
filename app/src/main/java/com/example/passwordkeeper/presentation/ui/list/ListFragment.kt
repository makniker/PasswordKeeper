package com.example.passwordkeeper.presentation.ui.list

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.passwordkeeper.R
import com.example.passwordkeeper.core.showToast
import com.example.passwordkeeper.databinding.FragmentListBinding
import com.example.passwordkeeper.presentation.ui.UiStates
import dagger.android.support.AndroidSupportInjection
import java.util.concurrent.Executor
import javax.inject.Inject


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PagesListAdapter

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val listViewModel by createViewModelLazy(
        ListViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.fetch()
        with(binding) {
            adapter = PagesListAdapter {
                setPrompt(it)
            }
            addButton.setOnClickListener {
                findNavController().navigate(ListFragmentDirections.actionListFragmentToAddFragment())
            }
            webPagesList.adapter = adapter
            listViewModel.listLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is UiStates.Loading -> {
                        webPagesList.visibility = View.GONE
                        space.visibility = View.GONE
                        progress.visibility = View.VISIBLE
                    }

                    is UiStates.Success -> {
                        webPagesList.visibility = View.VISIBLE
                        space.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        adapter.submitList(it.data)
                    }

                    is UiStates.Error -> {
                        showToast(it.message)
                    }

                    is UiStates.Init -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setPrompt(item: PageListUI) {
        executor = ContextCompat.getMainExecutor(requireContext())
        val biometricManager = BiometricManager.from(requireContext())
        biometricPrompt = BiometricPrompt(this@ListFragment, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToPasswordFragment(
                            item.id
                        )
                    )
                }
            })
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val authFlag = DEVICE_CREDENTIAL or BIOMETRIC_STRONG
                    promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.password_auth))
                        .setSubtitle(getString(R.string.password_auth_subtitle))
                        .setDescription(getString(R.string.password_auth_description))
                        .setAllowedAuthenticators(authFlag)
                        .build()
                } else {
                    @Suppress("DEPRECATION")
                    promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getString(R.string.password_auth))
                        .setSubtitle(getString(R.string.password_auth_subtitle))
                        .setDescription(getString(R.string.password_auth_description))
                        .setDeviceCredentialAllowed(true)
                        .build()
                }
                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                if (Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    startActivity(enrollIntent)
                }
            }
        }

    }
}