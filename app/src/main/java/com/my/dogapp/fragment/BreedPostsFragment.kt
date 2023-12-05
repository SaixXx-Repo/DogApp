package com.my.dogapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.my.dogapp.databinding.FragmentBreedPostsBinding
import com.my.dogapp.viewmodel.BreedPostsViewModel
import com.my.dogapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedPostsFragment : Fragment() {

    private lateinit var binding: FragmentBreedPostsBinding
    private val viewModel: BreedPostsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: BreedPostsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedPostsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvBreeds.itemAnimator = null
        viewModel.selectedBreed.value = args.breedDto
        viewModel.fetchBreedImages()
        mainViewModel.bottomNavActive.value = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}