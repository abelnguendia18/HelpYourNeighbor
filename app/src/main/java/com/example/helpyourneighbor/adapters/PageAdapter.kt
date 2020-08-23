package com.example.helpyourneighbor.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.helpyourneighbor.fragments.MyAnnouncementsFragment
import com.example.helpyourneighbor.fragments.MyProfileFragment

class PageAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                MyAnnouncementsFragment()
            }
            1 -> {
                MyProfileFragment()
            }
            else -> {
                MyAnnouncementsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "Meine Anzeigen"}
            1 -> {return "Mein Profil"}
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
       return 2
    }

}