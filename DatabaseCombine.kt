fun permissionsUsers(groupId: String): Observable<PermissionsUsers?> {
        return DatabaseRead.permissions(groupId)
                .flatMap {
                    Observable.from(it).map {
                        combineLatest(DatabaseRead.user(it.getId()), Observable.just(it.level), ::UserLevel)
                    }.toList()
                }
                .flatMap {
                    Observable.combineLatest(it) {
                        var owner: User? = null
                        val editPermissions = mutableListOf<User>()
                        val readOnlyPermissions = mutableListOf<User>()
                        it.forEach {
                            it as UserLevel
                            when (it.level) {
                                Permission.LEVEL_OWNER -> owner = it.user
                                Permission.LEVEL_WRITE -> editPermissions.add(it.user)
                                Permission.LEVEL_READONLY -> readOnlyPermissions.add(it.user)
                            }
                        }
                        PermissionsUsers(owner!!, editPermissions, readOnlyPermissions) // Owner must exist in each group
                    }
                }
    }
    
    data class UserLevel(val user: User, val level: Int)
    
    data class PermissionsUsers(val owner: User, val editPermissions: List<User>, val readOnlyPermissions: List<User>)
