fun DatabaseQuery.observe(): Observable<DataSnapshot?> {
    return Observable.create<DataSnapshot?>({
        val query = this.build()
        val listener = query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                it.onNext(null)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                it.onNext(dataSnapshot)
            }
        })
        it.setCancellation {
            query.removeEventListener(listener)
        }
    }, Emitter.BackpressureMode.BUFFER)
            .observeOn(Schedulers.computation())
}
