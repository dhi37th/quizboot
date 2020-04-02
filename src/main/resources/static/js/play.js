function selectOption(question_id,option_id,total){
  var question = $("div").find("[data-question_id='"+question_id+"']");
  var question_option = question.find("[data-option_id]");
  question_option.each(function(){
    var option = $(this);
    if(option.data('option_id') == option_id){
      option.addClass("bg-success");
    }else{
       option.removeClass("bg-success");
    }
  });
  question.find("#answers"+(question_id-1)+"\\.selectedOption").val(option_id);
  $(".question_selectors_list").find("[data-answered_id='"+question_id+"']").addClass("bg-success");
  selectQuestion(question_id + 1, total);
};

function selectQuestion(question_id, total){
  question_id = question_id > total ? 1 : question_id;
  $(".question_selectors_list").find("[data-answered_id]").removeClass("current_selector");
  $(".question_selectors_list").find("[data-answered_id='"+question_id+"']").addClass("current_selector");
  $("div").find("[data-question_id]").addClass('d-none');
  $("div").find("[data-question_id='"+question_id+"']").removeClass('d-none');
}


/*
function submitQuiz(size,categoryId){
  var submit = true;
  if($(".question_selectors_list .bg-success").length < size){
    if(!confirm('You have not answered all question. Dou you want to continue?'))
    submit = false;
  }
  if(submit){
    var answers = [];
    for(var j=1;j<=size;j++){
      var ansObj = {"qval": j , "aval": $("div").find("[data-question_id='"+j+"']").data('selected_option')} ;
      answers.push(ansObj);
    }
    $.ajax({
    	type: 'POST',
      url: 'quizquestions/category/'+categoryId+'/answer',
      contentType: "application/json",
      data: JSON.stringify({ answers }),
      dataType: 'json',
    	success: function (data) {
    		console.log(data);
    	},
    	error : function(xhr, textStatus, errorThrown){
    		if(xhr.status == 404){

    		}else  if(xhr.status == 401 || xhr.status == 403){

    		}else{

    		}
    	}
    });
  }
}*/
