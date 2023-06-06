package com.alican.mvvm_starter.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alican.mvvm_starter.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<VM : ViewModel, DB : ViewDataBinding>(
    private val viewModelClass: Class<VM>
) : BottomSheetDialogFragment() {
    lateinit var viewModel: VM
    private var _binding: DB? = null
    val binding get() = _binding
    var callback: ((Boolean) -> Unit)? = null

    var permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            sendResult(isGranted)
        }

    fun show(manager: FragmentManager) {
        super.show(manager, this::class.java.simpleName)
    }

    override fun getTheme(): Int = com.google.android.material.R.style.Theme_Design_BottomSheetDialog

    private fun initas(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewModel = getViewM()
        _binding?.setVariable(BR.data, viewModel)
    }

    private fun getViewM(): VM = (ViewModelProvider(this).get(viewModelClass))

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initas(inflater, container)
        super.onCreateView(inflater, container, savedInstanceState)
        init()
        return _binding?.root
    }

    open fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }

    fun askCameraPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) -> {
                sendResult(true)
            }
            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun sendResult(isGranted: Boolean) {
        callback?.invoke(isGranted)
        callback = null
    }
}