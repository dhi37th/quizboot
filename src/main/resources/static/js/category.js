$(function(){
  $('#addCategory').on('click', function(){
      var newCat = parseInt($('#catSize').val());
      var text =
      "<div class='form-group form-row'>"+
        "<label for='categories["+newCat+"].name' class='col-sm-2 col-form-label'>Category</label>"+
        "<div class='col-sm-10'>"+
          "<input required type='text' class='form-control input-sm catInput' id='categories"+newCat+".name' name='categories["+newCat+"].name' value=''>"+
        "</div>"+
      "</div>";
      $('#categoryDiv').append(text);
      $('#catSize').val(newCat + 1);
  });

  $('.catInput').on('input' ,function(){
      let p = this.selectionStart;
      $(this).val($(this).val().toUpperCase());
      this.setSelectionRange(p, p);
  });
});