
function add_child() {
        var index = $("#content tr").children().length/2;
         $("#content").append( $ (' <tr>'+
                                     '<td><input class="form-control col-sm-11 my-2 float-right font-weight-bold" type="text" name="checklistItems['+index+'].nameItem" placeholder="Input the item of checklist" /></td>'+
                                     '<td><i class="btn btn-danger fas fa-trash-alt float-right mt-2 mr-3" onclick="deleteRow(this)"></i></td>'+
                                     '</tr>'));

                    }

function deleteRow(r) {
        var i = r.parentNode.parentNode.rowIndex;
        document.getElementById("content").deleteRow(i);
      }