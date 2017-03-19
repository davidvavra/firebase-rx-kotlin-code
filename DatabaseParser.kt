fun <T> Observable<DataSnapshot?>.toPrimitiveObservable(type: Class<T>): Observable<T?> {
    return this.map {
        if (it == null) {
            return@map null
        }
        it.getValue(type)
    }
}

fun <T : DatabaseModel> Observable<DataSnapshot?>.toObjectObservable(type: Class<T>): Observable<T?> {
    return this.map {
        if (it == null) {
            return@map null
        }
        val data = it.getValue(type)
        data?.setId(it.key)
        data
    }
}

fun <T : DatabaseModel> Observable<DataSnapshot?>.toListObservable(type: Class<T>): Observable<List<T>?> {
    return this.map {
        if (it == null) {
            return@map null
        }
        it.children.map {
            val data = it.getValue(type)
            data!!.setId(it.key) // We expect to call only existing db paths.
            data
        }
    }
}
