String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

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

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e){
	e.preventDefault(); // 서버 전송을 막음.
	console.log('clck deleteAnswer');
	me = $(this);
	var url = me.attr("href");
	console.log('url', url)
	$.ajax({
		type : 'get',
		url : url,
		dataType : 'json',
		error: function(xhr, status){
			console.log('error', error)
		},
		success : function(data, status){
			console.log('error', data);
			if (data.valid){
				me.closest("article").remove();
			}else{
				alert(data.errorMessage);
			}
		}
	});
}

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
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id , data.id);
//	$(".qna-comment-slipp-articles").append(template)
	$(".qna-comment-slipp-articles").prepend(template)
	$(".answer-write textarea").val("");
}



















