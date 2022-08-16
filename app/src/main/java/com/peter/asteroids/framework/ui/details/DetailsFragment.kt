package com.peter.asteroids.framework.ui.details

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.peter.asteroids.R
import com.peter.asteroids.databinding.FragmentDetailsBinding
import com.peter.asteroids.framework.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivHelp.setOnClickListener {displayDialog()  }
    }
    private fun displayDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.au_exp))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

}