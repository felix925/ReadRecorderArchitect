package jp.making.felix.readrecordermvparch.DI

import dagger.Component
import jp.making.felix.readrecordermvparch.BookDataRegist.DataRegistFragment
import jp.making.felix.readrecordermvparch.BookDataUpdate.DataUpdateFragment
import jp.making.felix.readrecordermvparch.BookDataView.DataViewFragment
import jp.making.felix.readrecordermvparch.BookListView.ListViewFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create():AppComponent
    }
    fun inject(fragment:ListViewFragment):ListViewFragment
}