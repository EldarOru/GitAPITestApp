package com.example.gitapitestapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gitapitestapp.R
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.databinding.ActivityMainBinding
import com.example.gitapitestapp.presentation.fragments.MainFragment
import com.example.gitapitestapp.presentation.fragments.OnFragmentInteractionsListener

class MainActivity : AppCompatActivity(), OnFragmentInteractionsListener {
    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(mainActivityBinding.mainContainer.id, MainFragment())
            .commit()
    }


    override fun onChangeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(mainActivityBinding.mainContainer.id, fragment)
            .commit()
    }

    override fun onAddBackStack(name: String, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(name)
            .replace(mainActivityBinding.mainContainer.id, fragment)
            .commit()
    }

    override fun onPopBackStack() {
        for(i in 0..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

}