package com.example.ecom.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.ecom.R
import com.example.ecom.activities.shopping
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hide(){
    val bn=(activity as shopping).findViewById<BottomNavigationView>(R.id.navbar)
    bn.visibility= View.GONE
}
fun Fragment.show(){
    val bn=(activity as shopping).findViewById<BottomNavigationView>(R.id.navbar)
    bn.visibility= View.VISIBLE
}