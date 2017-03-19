fun Query.observe(): Observable<DataSnapshot?> {
    return Observable.create<DataSnapshot?>({
        val listener = this.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                it.onNext(null)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                it.onNext(dataSnapshot)
            }
        })
        it.setCancellation {
            this.removeEventListener(listener)
        }
    }, Emitter.BackpressureMode.BUFFER)
            .observeOn(Schedulers.computation())
}
