abstract class BasePresenter<V : MvpView> : Presenter<V> {

    protected var mvpView: V? = null
    private var mData = mutableListOf<PresenterData<*>>()

    override fun onDestroyedByLoader() {
        mData.forEach {
            it.subscription?.unsubscribe()
        }
    }

    @CallSuper
    override fun onViewAttached(view: V) {
        this.mvpView = view
        mData.forEach {
            val data = it as PresenterData<Any>
            if (data.cachedValue != null) {
                data.displayToView(data.cachedValue!!)
            }
        }
    }

    fun getView(): V {
        return mvpView!!
    }

    fun <T> load(observable: Observable<T?>, displayToView: (T) -> Unit) {
        val data = PresenterData(observable, displayToView)
        data.subscription = data.observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.cachedValue = it
                    if (isViewAttached && it != null) {
                        data.displayToView(it)
                    }
                }, {
                    logError(it)
                })
        mData.add(data)
    }
}
