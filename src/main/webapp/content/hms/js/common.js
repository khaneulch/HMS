const Hms = {};

/* 공통 alert */
Hms.alert = function(message,callback) {
	$div = '';
	$div +='<div id="myModal" class="modal-wrap is-visible">';
	$div +='	<div class="modal">';
	$div +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	$div +='		<div class="txt-wrap center">';
	$div +='			<p>' + message + '</p>';
	$div +='			<a href="javascript:void(0);" class="btn modal-ok right m0">확인</a>';
	$div +='		</div>';
	$div +='	</div><br><br>';
	$div +='</div>';
	$('body').append($div);
	
	$('body').css('overflow', 'hidden');
	
	$('.modal-wrap .modal-ok').focus();

	$('.modal-wrap .modal-ok').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(callback)) {
			callback();
		}
	});
	
	$('.modal-wrap .modal-close').on('click', function() {
		$('#myModal').remove();
	});
}

/* 공통 confirm */
Hms.confirm = function(message, confirmFunction, cancelFunction) {
	$div = '';
	$div +='<div id="myModal" class="modal-wrap is-visible">';
	$div +='	<div class="modal">';
	$div +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	$div +='		<div class="txt-wrap center">';
	$div +='			<p>' + message + '</p>';
	$div +='			<div class="btn-wrap">';
	$div +='				<a href="javascript:void(0);" class="modal-yes btn left">예</a>';
	$div +='				<a href="javascript:void(0);" class="modal-no btn right">아니오</a>';
	$div +='			</div>';
	$div +='		</div>';
	$div +='	</div><br><br>';
	$div +='</div>';
	$('body').append($div);

	$('body').css('overflow', 'hidden');
	
	$('.modal-wrap .modal-yes').focus();

	$('.modal-wrap .modal-yes').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(confirmFunction)) {
			confirmFunction();
		}
	});

	$('.modal-wrap .modal-no').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(cancelFunction)) {
			cancelFunction();
		}
	});
	
	$('.modal-wrap .modal-close').on('click', function() {
		$('#myModal').remove();
	});
}

/* 공통 ajax call */
Hms.getAjaxCall = function(url, params, successCallback, async) {
	var retData;
	if( typeof params === 'undefined') {
		params = {};
	}
	if( typeof async === 'undefined') {
		async = false;
	}
	
	var ajaxOptions = {
		type: 'post',
		async: async,
		url: url,
		data: params,
		success:function( data) {
			if ( $.isFunction(successCallback)) {
				successCallback();
			}
			else {
				retData = data;
			}
			return retData;
		},
		error:function(xhr,status,error) {
			if ( xhr.status == 999 ) {
				Mms.alert(xhr.responseText);
			}
		}
	};
	
	if( params instanceof FormData) {
		ajaxOptions.processData = false;
		ajaxOptions.contentType = false;
	}
	$.ajax(ajaxOptions);
		
	return retData;
}

/* form sumbit시 필수값 체크 */
Hms.formValidator = function( selector) {
	if( !selector) selector = 'form';
	var flag = true;
	$(selector).find('input, textarea, input:checkbox, input:radio').each( function() {
		var $self = $(this);
		if( $self.hasClass('folding') == false) {
			if( $self.attr('_required') && $self.attr('_required') == 'true') {
				if( $self.is('input, textarea')) {
					var v = $self.val();
					if( v == null || v == '') {
						var title = $self.attr('title');
						if( !title) title = '해당';
						Hms.alert(title + '값은 필수입니다.', function() {
							$self.focus();
						});
						flag = false;
						return false;
					}
				} else if( $self.is('checkbox') || $self.is('radio')) {
					var v = $self.val();
				}
			}
		}
	});
	return flag;
}

/* 페이지 이동 액션 */
Hms.goPage = function( page) {
	$('#listForm #currentPage').val(page);
	$('#listForm').submit();
}

/* ckeditor 관련 js */
Hms.editor = {
	options : {
		isInit : false
	}, 
	init : function( selector) {
		CKEDITOR.replace( 'editor-box', {
		    filebrowserUploadUrl: '/ckImageUpload?filePath=' + $('#filePath').val()
		    , width:'100%'
		 	, height: 500
		});
		
		CKEDITOR.on("instanceReady", function(event) {Hms.editor.options.isInit = true;});
	}
}

/* ckeditor 관련 js */
Hms.pop = function( url) {
	window.open(url);
}



