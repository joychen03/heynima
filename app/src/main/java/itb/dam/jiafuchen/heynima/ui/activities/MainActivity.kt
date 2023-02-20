package itb.dam.jiafuchen.heynima.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import itb.dam.jiafuchen.heynima.R
import itb.dam.jiafuchen.heynima.databinding.ActivityMainBinding
import itb.dam.jiafuchen.heynima.ui.fragments.SearchFragment



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)

        setTheme(R.style.Theme_Heynima)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}