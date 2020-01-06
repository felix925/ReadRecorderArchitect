package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView

interface DataViewContract {
    interface View: BaseView<Presenter> {
        fun showProgress()
        fun deleteProgress()
    }

    interface Presenter: BasePresenter
}