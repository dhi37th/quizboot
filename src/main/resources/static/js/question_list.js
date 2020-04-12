$(function(){
  if ('URLSearchParams' in window) {
    var searchParams = new URLSearchParams(window.location.search);
    let param = searchParams.has('category') ? searchParams.get('category') == 0 ? '':searchParams.get('category') : 0;
    $('#selectCategory').val(param);
  }
});

function onCategorySelection(){
  var category = $('#selectCategory').val();
  var url = window.location.protocol+"//"+window.location.host+"/quiz/questions?page=1";
  if(parseInt(category) > 0){
    url += "&category="+category;
  }
  window.location.href = url;
}
