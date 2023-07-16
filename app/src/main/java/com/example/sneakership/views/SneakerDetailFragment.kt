package com.example.sneakership.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sneakership.databinding.FragmentSneakerDetailBinding
import com.example.sneakership.models.Sneaker
import com.example.sneakership.viewModels.SneakerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SneakerDetailFragment : Fragment() {

    private lateinit var binding: FragmentSneakerDetailBinding
    var sneaker: Sneaker? = null

    @Inject
    lateinit var sneakerDetailViewModel: SneakerDetailViewModel

    companion object {
        const val SNEAKER_DATA = "SNEAKER_DATA"
        fun newInstance(sneaker: Sneaker): SneakerDetailFragment {
            return SneakerDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SNEAKER_DATA, sneaker)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(SNEAKER_DATA)?.let {
            sneaker = it as Sneaker
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSneakerDetailBinding.inflate(inflater)
        binding.viewModel = sneakerDetailViewModel
        sneakerDetailViewModel.sneaker = sneaker
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sneakerDetailViewModel.loadSneakerData()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}

