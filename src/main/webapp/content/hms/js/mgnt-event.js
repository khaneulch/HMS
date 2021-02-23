$( function() {
	Mgnt.init();
	Mgnt.sliderEvent();
});

const Mgnt = {};

Mgnt.init = function() {
	var resp = Hms.getAjaxCall('/getListData?groupCode=' + $('#groupCode').val());
	if( resp && resp.isSuccess) {
		Mgnt.data = resp.data;
		if( Mgnt.data) {
			Mgnt.setSlider();
		}
	}
}

Mgnt.setSlider = function() {
	var _HTML = '';
	
	var dataSlick = '{"slidesToShow": 3, "slidesToScroll": 2}';
	_HTML += `<div class="slick-list" data-slick='${dataSlick}'>`;
	$.each( Mgnt.data, function( k, v) {
		_HTML += Mgnt.setSliderData(v);
	});
	_HTML += '</div>'; 
	
	$('.pick-list').html(_HTML);
}

Mgnt.setSliderData = function( data) {
	var bgStyle = '';
	if ( data.bgType == 'I') {
		bgStyle = `background-image:url(/upload/${data.bgImg})`;
	} else if( data.bgType == 'C') {
		bgStyle = `background-color:${data.bgColor}`;
	}
	
	var _SLIDER_HTML = '';
	_SLIDER_HTML += `<div class="slick-data" style="${bgStyle}" key="${data.seq}" id="K_${data.seq}">`;
	_SLIDER_HTML += `<span class="no">${data.dpSeq}</span>`;
	_SLIDER_HTML += `<div class="title">${data.title}</div>`;
	_SLIDER_HTML += `<div class="slick-btn folding" data-seq="${data.seq}" data-dp-seq="${data.dpSeq}">`;
	_SLIDER_HTML += '<a href="javascript:void(0);" class="btn btn-order">순서변경</a>';
	_SLIDER_HTML += '<a href="javascript:void(0);" class="btn btn-delete">삭제</a>';
	_SLIDER_HTML += '</div>';
	_SLIDER_HTML += '</div>';
	
	return _SLIDER_HTML;
}

Mgnt.sliderEvent = function() {
	$('.slick-list').slick({arrows: true, dots:true, infinite: true});

	$('.slick-list .slick-data').click( function() {
		var $slick = $(this);
		var seq = $slick.find('.slick-btn').attr('data-seq');
		
		$('.slick-list .slick-btn').addClass('folding');
		$slick.find('.slick-btn').removeClass('folding');
		
		$slick.find('.btn-order').unbind('click').click( function( e) {
			e.stopPropagation();
			$(this).parents('.slick-btn').addClass('folding');
			$slick.trigger('dblclick');
		});
		
		$slick.find('.btn-delete').unbind('click').click( function(e) {
			e.stopPropagation();
			$(this).parents('.slick-btn').addClass('folding');
			Hms.confirm( '해당 컴포넌트를 메인에서 삭제하시겠습니까?', function(){
				Hms.getAjaxCall('/mgnt/delete-main', {seq : seq, groupCode : $('#groupCode').val()}, function(){
					location.reload();
				});
			});
		});
	});

	$('.slick-list .slick-data').dblclick( function() {
		var _HTML = $('#pop-comp-change').html();
		var bodyOver = $('body').css('overflow');
		var $slick = $(this)
		;
		$('body').append(_HTML);
		$('body').css('overflow', 'hidden');
		
		var $modal = $('.pop-comp-change');
		var seq = $slick.find('.slick-btn').attr('data-seq');
		var oldDpSeq = $slick.find('.slick-btn').attr('data-dp-seq');
		
		$modal.find('.oldDpSeq').append(oldDpSeq);
		
		$modal.find('.modal-yes').click( function() {
			var dpSeq = $modal.find('#dpSeq').val();
			Hms.getAjaxCall('/mgnt/update-main', {seq : seq, dpSeq : dpSeq}, function(){
				location.reload();
			});
		});
		
		$modal.find('.modal-close, .modal-no').click( function() {
			$modal.remove();
			$('body').css('overflow', bodyOver);
		});
	});
}