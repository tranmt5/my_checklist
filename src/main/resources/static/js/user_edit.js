$(document).ready(function() {
    $('#fileImage').change(function() {
        showImageThumbnail(this);
    });


    $('#role_input').tagEditor({ autocomplete: {
        source: function(request,response) {
                    $.ajax({
                    url: "/setting/user/edit/",
                    method:'GET',
                    dataType: "json",
                    data: {keyword:request.term},
                    success: function(data) {
//                        response(data);
                          console.log(data);
                          response($.map(data, function (item) {
                               return {
                                    label: item.name,
                                    value: item.id
                               };
                          }));
                    },
                    error: function() {
                          console.log("error");
                    }
                    })
        },
//             select: function (event, ui) {
//                var name = $("#nameRole").val();
//                console.log(name);
//                $(this).val(ui.item.label);
//                $("#nameRole").val(ui.item.label + name);
//                return false;
//              }
   },
        placeholder: 'Add role in here...',
   });
})


function showImageThumbnail(fileInput) {
    file = fileInput.files[0];
    reader = new FileReader();

    reader.onload = function(e) {
        $('#avatar').attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}









