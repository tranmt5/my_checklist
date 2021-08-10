
function showConfirmModalDialogChecklistItem(id,name) {
    $('#checklistItemName').text(name);
    $('#yesOptionChecklistItem').attr('href','/checklist/item/delete/' + id);
    $('#checklistItemId').modal('show');
}