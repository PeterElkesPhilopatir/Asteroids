package com.peter.asteroids.framework.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.peter.asteroids.R
import com.peter.asteroids.databinding.FragmentMainBinding
import com.peter.asteroids.framework.ui.MainViewModel
import com.peter.asteroids.framework.ui.util.bindImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.imageOfDay.observe(viewLifecycleOwner) {
            bindImage(binding.ivImgOfDay, it.url)
        }
        viewModel.data.observe(viewLifecycleOwner) {
            val adapter =
                AsteroidsAdapter(OnAsteroidClickListener {
                    findNavController().navigate(R.id.detailsFragment)
                    viewModel.setSelectedAsteroid(it)
                })
            binding.rvData.adapter = adapter
            adapter.submitList(it)
        }
    }
}