
function showConfirmModalDialogRole(id,name) {
    $('#roleName').text(name);
    $('#yesOptionRole').attr('href','/setting/role/delete/' + id);
    $('#roleId').modal('show');
}