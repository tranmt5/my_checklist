
function showConfirmModalDialogChecklist(id,name) {
    $('#checklistName').text(name);
    $('#yesOptionChecklist').attr('href','/checklist/delete/' + id);
    $('#checklistId').modal('show');
}