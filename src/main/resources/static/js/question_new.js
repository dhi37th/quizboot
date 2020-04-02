$(function(){
  $('.correctCheckbox').on('change', function () {
      if($('.correctCheckbox:checked').length > 0 ){
        $('.correctCheckbox').each(function(){
            if($(this).is(":checked") == false){
              $(this).attr("disabled", "disabled");
            }
        });
      }else{
        $('.correctCheckbox').each(function(){
           $(this).removeAttr("disabled");
        });
      }
  });
});