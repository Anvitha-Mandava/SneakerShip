package com.example.sneakership.views

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sneakership.R
import com.example.sneakership.databinding.ActivityHomeBinding
import com.example.sneakership.helper.hideOtherFragments
import com.example.sneakership.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding
    private var sneakersListFragment: SneakersListFragment = SneakersListFragment.newInstance()
    private var cartDetailFragment: CartDetailFragment = CartDetailFragment.newInstance()
    private val sneakersListFragmentTag = "SNEAKER_LIST_FRAGMENT_TAG"
    private val cartDetailFragmentTag = "CART_DETAIL_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.viewModel = homeViewModel
        setListeners()
        addInitialFragment()
        setContentView(binding.root)
        handleBackPress()
    }

    private fun handleBackPress() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.child_fragment_container)
                if (currentFragment != sneakersListFragment) {
                    showFragment(sneakersListFragment, sneakersListFragmentTag)
                    binding.bottomNavigationView.selectedItemId = R.id.home
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    showFragment(sneakersListFragment, sneakersListFragmentTag)
                    true
                }
                R.id.cart -> {
                    showFragment(cartDetailFragment, cartDetailFragmentTag)
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().show(fragment).commitNow()
        hideOtherFragments(tag, supportFragmentManager)
    }

    private fun addInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.child_fragment_container, sneakersListFragment, sneakersListFragmentTag)
            .add(R.id.child_fragment_container, cartDetailFragment, cartDetailFragmentTag)
            .commitNow()
        supportFragmentManager.beginTransaction().hide(cartDetailFragment)
            .show(sneakersListFragment).commitNow()
    }
}