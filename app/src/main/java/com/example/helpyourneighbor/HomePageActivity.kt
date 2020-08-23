package com.example.helpyourneighbor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.helpyourneighbor.fragments.ChatFragmentTemporary
import com.example.helpyourneighbor.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //the first fragment is going to be default loaded
        loadFragment(HomeFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener@{ item->
        when(item.itemId)
        {
            R.id.home -> {
                //item.setIconTint
                loadFragment(HomeFragment())
                //Toast.makeText(this, getText(R.string.developer_name), Toast.LENGTH_LONG).show()
                return@onNavigationItemSelectedListener true
            }

            R.id.neue_anzeige -> {
                //Toast.makeText(this@MainActivity,getText(R.findViewB), Toast.LENGTH_LONG).show()
                //Toast.makeText(this, "Search Utils", Toast.LENGTH_LONG).show()
                loadFragment(CreateNewAnnouncement())
                return@onNavigationItemSelectedListener true
            }
            R.id.chat ->{

                //Toast.makeText(this, "Chats", Toast.LENGTH_LONG).show()
                //loadFragment(ChatFragment())
                loadFragment(ChatFragmentTemporary())
                return@onNavigationItemSelectedListener true
            }

            R.id.my_account ->{

                //Toast.makeText(this, "My Account", Toast.LENGTH_LONG).show()
                //loadFragment(AccountParameterFragment())
                val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)
                return@onNavigationItemSelectedListener true
            }
        }
        return@onNavigationItemSelectedListener false

    }
     fun loadFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }
    }
}
