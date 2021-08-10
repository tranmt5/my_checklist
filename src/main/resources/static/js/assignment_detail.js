

function display_cancelComment(){
      document.getElementById("cancelComment").style.setProperty("display", "block", "important");
      }

function display_closeComment(){
      document.getElementById("closeComment").style.setProperty("display", "block", "important");
      }

$(document).ready(function(){

    $('table tbody').sortable({

        update: function(event,ui) {
                $(this).children().each(function (index){
                   $(this).addClass('update');
                });
                saveNewPosition();
        }
    });


   var value =  $('#test').val();
    $("#progressbar" ).progressbar({
                  value: parseInt(value),
                  });

});


  function saveNewPosition() {
        var assignmentDetailsId = [];
        var id;
        $('.update').each(function(){
            assignmentDetailsId.push($(this).attr('assignmentDetailsId'))
            id = $(this).attr('assignmentId');
            $(this).removeClass('update');
        });

        $.ajax({
            url:'/assignmentDetails/sequence',
            method:'GET',
            dataType:'json',
            contentType: 'application/json',
            data: {
                id : id,
                assignmentDetailsId: assignmentDetailsId
            }, success: function(response) {
            console.log(response);
            }
        })
  }

