package ru.je_dog.tinkoff_school

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.je_dog.products.presentation.FilmsFragment
import ru.je_dog.tinkoff_school.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container,FilmsFragment())
            .commit()

    }
}