$(function(){
  $('#addCategory').on('click', function(){
      var newCat = parseInt($('#catSize').val());
      var text =
      "<div class='form-group form-row'>"+
        "<label for='categories["+newCat+"].name' class='col-2 col-form-label'>Category</label>"+
        "<div class='col-10'>"+
          "<input required type='text' class='form-control input-sm' id='categories"+newCat+".name' name='categories["+newCat+"].name' value='' oninput='changeInput(this);'>"+
        "</div>"+
      "</div>";
      $('#categoryDiv').append(text);
      $('#catSize').val(newCat + 1);
  });
});

function changeInput(obj){
   let p = obj.selectionStart;
   $(obj).val($(obj).val().toUpperCase());
   obj.setSelectionRange(p, p);
}