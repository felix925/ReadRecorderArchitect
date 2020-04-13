package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import io.realm.Realm
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.DI.AppComponent
import jp.making.felix.readrecordermvparch.DI.DaggerAppComponent
import jp.making.felix.readrecordermvparch.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var appComponent: AppComponent
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setContentView(binding.root)
        Realm.init(this)
        appComponent = DaggerAppComponent.create()
    }
}
