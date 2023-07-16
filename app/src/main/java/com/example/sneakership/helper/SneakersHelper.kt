package com.example.sneakership.helper

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sneakership.MyApplication

const val SPACE_FILLER = " "

fun getContext() = MyApplication.getContext()

fun getString(strRes: Int): String {
    return getContext().getString(strRes)
}

enum class SortOrder {
    DEFAULT, PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW
}

fun showShortToast(msg: String?, context: Context = getContext()) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

enum class FragmentType {
    HOME, CART
}

fun Double?.orZero(): Double = this ?: 0.0

enum class TABS(var tabId: Int, var tag: String) {
    MENU_LIST(0, "SNEAKER_LIST_FRAGMENT_TAG"), MNU_CART(
        1, "CART_DETAIL_FRAGMENT_TAG"
    ),
    MNU_DETAIL(2, "SNEAKER_DETAIL_FRAGMENT_TAG")
}

fun hideOtherFragments(tag: String?, supportFragmentManager: FragmentManager) {
    enumValues<TABS>().forEach {
        val fragmentToHide: Fragment? = supportFragmentManager.findFragmentByTag(it.tag)
        if (tag != it.tag && fragmentToHide != null) {
            hideMenuFragment(fragmentToHide, supportFragmentManager)
        }
    }
}

fun hideMenuFragment(fragmentToHide: Fragment, supportFragmentManager: FragmentManager) {
    supportFragmentManager.beginTransaction().hide(fragmentToHide)
        .commitNow()
}