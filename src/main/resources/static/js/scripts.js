//String.prototype.format = function() {
//  var args = arguments;
//  return this.replace(/{(\d+)}/g, function(match, number) {
//    return typeof args[number] != 'undefined'
//        ? args[number]
//        : match
//        ;
//  });
//};
//
//$(document).ready(function(){
//	$('#btnToggle').click(function(){
//		if($(this).hasClass('on')){
//			$('#main .col-md-6').addClass('col-md-4').removeClass('col-md-6');
//			$(this).removeClass('on');
//		}else{
//			$('#main .col-md-4').addClass('col-md-6').removeClass('col-md-4');
//			$(this).removeClass('on');
//		}
//	});
//});
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e){
	e.preventDefault(); // 서버 전송을 막음.
	console.log('clck me', e);
	
	var queryString = $(".answer-write").serialize();
	console.log('clck queryString', queryString);
	var url = $(".answer-write").attr("action");
	console.log('clck queryString', url);
	$.ajax({
			type : 'post',
			url : url,
			data : queryString,
			dataType : 'json',
			error: onError,
			success : onSuccess
	});
}

function onError(){
	
}
function onSuccess(data, status){
	console.log('data', data)
}