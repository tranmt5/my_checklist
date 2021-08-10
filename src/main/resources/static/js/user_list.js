
function showConfirmModalDialogUser(id,name) {
    $('#username').text(name);
    $('#yesOptionUser').attr('href','/setting/user/delete/' + id);
    $('#userId').modal('show');
}