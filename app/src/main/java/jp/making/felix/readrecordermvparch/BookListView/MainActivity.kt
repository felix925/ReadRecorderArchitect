package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.realm.Realm
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.DI.AppComponent
import jp.making.felix.readrecordermvparch.DI.DaggerAppComponent
import jp.making.felix.readrecordermvparch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var appComponent: AppComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        appComponent = DaggerAppComponent.create()
    }
}
