class PermissionsPresenter(groupId: String) : BasePresenter<PermissionsMvpView>(groupId) {

    override fun onCreatedByLoader() {
        loadOnce(DatabaseCombine.groupColor(groupId), {
            getView().applyGroupColor(it)
        })
        load(DatabaseRead.groupLinkEnabled(groupId)) {
            getView().setInviteLinkEnabled(it)
        }
        load(DatabaseCombine.permissionsUsers(groupId)) {
            getView().setGroupOwner(mOwner)
            getView().setEditPermissions(it.editPermissions, isOwner())
            getView().setReadOnlyPermissions(it.readOnlyPermissions, isOwner())
        }
    }
 }
 
 interface PermissionsMvpView : GroupMvpView {
    fun applyGroupColor(color: Int)
    fun setGroupOwner(groupOwner: User)
    fun setEditPermissions(users: List<User>, isOwner: Boolean)
    fun setReadOnlyPermissions(users: List<User>, isOwner: Boolean)
    fun setInviteLinkEnabled(enabled: Boolean)
}
