package com.my.dogapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.my.dogapp.databinding.FragmentBreedsBinding
import com.my.dogapp.utils.EventObserver
import com.my.dogapp.viewmodel.BreedsViewModel
import com.my.dogapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : Fragment() {

    private lateinit var binding: FragmentBreedsBinding
    private val viewModel: BreedsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getAllBreeds()
        mainViewModel.bottomNavActive.value = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            val action = BreedsFragmentDirections.actionBreedsFragmentToBreedPostFragment(it)
            findNavController().navigate(action)
        })
    }
}