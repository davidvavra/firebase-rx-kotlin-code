object DatabaseRead {
    fun group(groupId: String): Observable<Group?> {
        return DatabaseQuery().apply { path = "groups/$groupId" }
                .observe()
                .toObjectObservable(Group::class.java)
    }
    
    fun user(userId: String?): Observable<User?> {
        return DatabaseQuery().apply { path = "users/$userId" }
                .observe()
                .toObjectObservable(User::class.java)
    }    
    
    fun permissions(groupId: String): Observable<List<Permission>?> {
        return DatabaseQuery().apply { path = "permissions/$groupId" }
                .observe()
                .toListObservable(Permission::class.java)
    }
    
    fun groupLinkEnabled(groupId: String): Observable<Boolean?> {
        return DatabaseQuery().apply { path = "groups/$groupId/inviteLinkActive" }
                .observe()
                .toPrimitiveObservable(Boolean::class.java)
    }
    
    fun groupColor(groupId: String): Observable<String?> {
        return DatabaseQuery().apply { path = "userGroups/${Auth.getUserId()}/$groupId/color" }
                .observe()
                .toPrimitiveObservable(String::class.java)
    }
}
