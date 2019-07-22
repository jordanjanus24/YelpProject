package com.app.yelpproject.lib.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(id: Int, fragment: Fragment) =
    supportFragmentManager.beginTransaction().replace(id, fragment).commit()