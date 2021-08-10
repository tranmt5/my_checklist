
function showConfirmModalDialogAssignment(id,name) {
    $('#assignmentName').text(name);
    $('#yesOptionAssignment').attr('href','/assignment/delete/' + id);
    $('#assignmentId').modal('show');
}