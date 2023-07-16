package com.example.sneakership.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sneakership.R
import com.example.sneakership.adapters.SneakersListAdapter
import com.example.sneakership.databinding.FragmentSneakersListBinding
import com.example.sneakership.helper.SortOrder
import com.example.sneakership.helper.hideOtherFragments
import com.example.sneakership.models.Sneaker
import com.example.sneakership.viewModels.SneakersListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SneakersListFragment : Fragment() {

    private lateinit var binding: FragmentSneakersListBinding
    private lateinit var sneakersListAdapter: SneakersListAdapter

    @Inject
    lateinit var sneakersListViewModel: SneakersListViewModel

    private val TAG = "SNEAKER_DETAIL_FRAGMENT_TAG"

    companion object {
        fun newInstance() = SneakersListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSneakersListBinding.inflate(inflater)
        binding.viewModel = sneakersListViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialise()
        setUpObservers()
        fetchData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.sortBy.setOnClickListener {
            showSortOptionsPopup()
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                sneakersListViewModel.filterSneakers(newText)
                return true
            }
        })
        binding.search.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.toolbarTitle.visibility = View.INVISIBLE
            } else {
                binding.toolbarTitle.visibility = View.VISIBLE
            }
        }
    }

    private fun initialise() {
        sneakersListAdapter = SneakersListAdapter(sneakersListViewModel) { sneaker ->
            openSneakerDetailFragment(sneaker)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = sneakersListAdapter
        }
    }

    private fun setUpObservers() {
        sneakersListViewModel.sneakerList.observe(this) { sneakers ->
            sneakersListAdapter.submitList(sneakers)
        }

        sneakersListViewModel.filteredSneakers.observe(this) { filteredList ->
            sneakersListAdapter.submitList(filteredList)
        }
    }

    private fun fetchData() {
        sneakersListViewModel.fetchSneakers()
    }

    private fun setSortedOption(itemId: Int, popupMenu: PopupMenu?) {
        popupMenu?.menu?.findItem(itemId)?.isChecked = true
    }

    private fun showSortOptionsPopup() {
        val popupMenu = activity?.let { PopupMenu(it, binding.sortBy) }
        popupMenu?.inflate(R.menu.sort_menu)

        when (sneakersListViewModel.currentSortOrder.value) {
            SortOrder.DEFAULT -> setSortedOption(R.id.action_sort_default, popupMenu)
            SortOrder.PRICE_LOW_TO_HIGH -> setSortedOption(
                R.id.action_sort_price_low_to_high, popupMenu
            )
            SortOrder.PRICE_HIGH_TO_LOW -> setSortedOption(
                R.id.action_sort_price_high_to_low, popupMenu
            )
            else -> {}
        }

        popupMenu?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_sort_default -> {
                    sneakersListViewModel.sortSneakers(SortOrder.DEFAULT)
                    true
                }
                R.id.action_sort_price_low_to_high -> {
                    sneakersListViewModel.sortSneakers(SortOrder.PRICE_LOW_TO_HIGH)
                    true
                }
                R.id.action_sort_price_high_to_low -> {
                    sneakersListViewModel.sortSneakers(SortOrder.PRICE_HIGH_TO_LOW)
                    true
                }
                else -> false
            }
        }
        popupMenu?.show()
    }

    private fun openSneakerDetailFragment(sneaker: Sneaker) {
        if (activity?.isFinishing == true) return
        val fragment = SneakerDetailFragment.newInstance(sneaker)
        activity?.supportFragmentManager?.let {
            hideOtherFragments(TAG, it)
            it.beginTransaction().add(R.id.child_fragment_container, fragment, TAG)
                
                .commitNow()
        }
    }
}