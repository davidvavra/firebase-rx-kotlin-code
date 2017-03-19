    @Nullable
    public static Observable<DataSnapshot> observe(final Query query) {
        return Observable.create(new Action1<Emitter<DataSnapshot>>() {
            @Override
            public void call(final Emitter<DataSnapshot> dataSnapshotEmitter) {
                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshotEmitter.onNext(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        dataSnapshotEmitter.onNext(null);
                    }
                });
                dataSnapshotEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        query.removeEventListener(valueEventListener);
                    }
                });
            }
        }, Emitter.BackpressureMode.BUFFER)
                .observeOn(Schedulers.computation());
    }
