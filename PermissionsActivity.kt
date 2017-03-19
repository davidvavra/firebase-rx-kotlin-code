class PermissionsActivity : GroupActivity<PermissionsPresenter, PermissionsMvpView>(), PermissionsMvpView {

    private lateinit var mReadOnlyAccessAdapter: RecyclerAdapter<User>
    private lateinit var mEditAccessAdapter: RecyclerAdapter<User>

    override fun getLayoutRes(): Int {
        return R.layout.activity_permissions
    }

    override fun getPresenterFactory(): PresenterFactory<PermissionsPresenter> {
        return PermissionsPresenter.Factory(getGroupId())
    }

    override fun applyGroupColor(color: Int) {
        super.applyGroupColor(color)
        vSwitch.setColor(color)
        vFloatingActionMenu.applyGroupColor(color)
    }

    override fun setGroupOwner(groupOwner: User) {
        vAvatar.loadAvatar(groupOwner)
        vName.text = groupOwner.name
        vEmail.text = groupOwner.email
    }

    override fun setEditPermissions(users: List<User>, isOwner: Boolean) {
        mEditAccessAdapter.setData(users, PermissionsBinder(isOwner, R.menu.permission_edit_access, { user, permissionChange -> getPresenter().onPermissionChangeClicked(user, permissionChange) }))
    }

    override fun setReadOnlyPermissions(users: List<User>, isOwner: Boolean) {
        mReadOnlyAccessAdapter.setData(users, PermissionsBinder(isOwner, R.menu.permission_read_only_access, { user, permissionChange -> getPresenter().onPermissionChangeClicked(user, permissionChange) }))
    }

    override fun setInviteLinkEnabled(enabled: Boolean) {
        vSwitch.isChecked = enabled
    }
}
