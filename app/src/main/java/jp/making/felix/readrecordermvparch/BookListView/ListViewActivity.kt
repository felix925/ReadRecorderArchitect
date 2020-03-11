package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import jp.making.felix.readrecordermvparch.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import jp.making.felix.readrecordermvparch.DI.AppComponent
import jp.making.felix.readrecordermvparch.DI.DaggerAppComponent


class ListViewActivity : AppCompatActivity() {
    lateinit var appComponent: AppComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar) //今は必要性を感じていないので外している
        Realm.init(this)
        appComponent = DaggerAppComponent.create()
        val fragment = ListViewFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_host_fragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
//    //TODO こやつらはここで定義してあげていいのか考える
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
