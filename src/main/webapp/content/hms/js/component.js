const Component = {};

// 컴포넌트 데이터 init
Component.init = function() {
	if( $('div.content').length == 0) {
		var resp = Hms.getAjaxCall('/getListData?groupCode=' + $('#groupCode').val());
		if( resp && resp.isSuccess) {
			Component.data = resp.data;
			if( Component.data) {
				Component.setData();
			}
		}
	}
}

// 컴포넌트 생성
Component.setData = function() {
	var _HTML = '';
	_HTML += '<div class="comp-list">';
	$.each( Component.data, function( k, v) {
		
		var bgStyle = '';
		if ( v.bgType == 'I') {
			bgStyle = `background-image:url(/upload/${v.filePath}/${v.bgImg})`;
		} else if( v.bgType == 'C') {
			bgStyle = `background-color:${v.bgColor}`;
		}
		
		if( v.compType === 'I') {
			// 이미지 슬라이더 컴포넌트
			if ( v.bgType == 'I') {
				_HTML += Component.setSliderBg( v, `/upload/${v.filePath}/${v.bgImg}`);
			} else {
				_HTML += Component.setSlider( v, bgStyle);
			}
		} else if( v.compType === 'X') {
			// 기본 컴포넌트
			if ( v.bgType == 'I') {
				_HTML += Component.setCompBg( v, `/upload/${v.filePath}/${v.bgImg}`);
			} else {
				_HTML += Component.setComp( v, bgStyle);
			}
		} else if( v.compType === 'L' && v.linkType === 'I'){
			// 아이프레임 컴포넌트
			_HTML += Component.setIframe( v, bgStyle);
		}
	});
	_HTML += '</div>';
	
	$('.home-content').html(_HTML);
}

Component.setSliderContent = function( data) {
	var sliderClass = data.sliderType === 'A' ? 'arrow-slick' : 'single-slick';
	var containerClass = data.width && data.width > 0 ? '' : 'p0 m0 w100p';
	
	var styleWidth = '';
	if( data.width && data.width > 0) {
		var leftPx = ( window.innerWidth - Number(data.width)) / 2;
		if( leftPx < 0) leftPx = 0; 
		
		styleWidth = `width:${data.width}px; padding-left:${leftPx}px`;
	}
	
	var _CONTENT_HTML = '';
	_CONTENT_HTML += `<div class="slick-container ${containerClass}" style="${styleWidth}">`;
	_CONTENT_HTML += `<div class="${sliderClass}" >`;
	data.slider.forEach( function(v) {
		if( v.fileName.indexOf('mp4') > -1) {
			_CONTENT_HTML += Component.setVideo(v);
		} else {
			_CONTENT_HTML += Component.setImage(v);
		}
	});
	_CONTENT_HTML += '</div>';
	_CONTENT_HTML += '</div>';
	
	return _CONTENT_HTML; 
}

// 슬라이더 컴포넌트 tag 생성
Component.setSlider = function( data, bgStyle) {
	var _SLIDER_HTML = '';
	_SLIDER_HTML += `<div class="content" style="${bgStyle}" key="${data.seq}" id="K_${data.seq}">`;
	_SLIDER_HTML += Component.setSliderContent( data);
	_SLIDER_HTML += '</div>';
	
	return _SLIDER_HTML;
}

Component.setSliderBg = function( data, src) {
	var _SLIDER_HTML = '';

	_SLIDER_HTML += `<div class="content" style="position:relative;" key="${data.seq}" id="K_${data.seq}">`;
	_SLIDER_HTML += '<div class="div-align" style="position: absolute;">'; 
	_SLIDER_HTML += Component.setSliderContent( data);
	_SLIDER_HTML += '</div>'; 
	_SLIDER_HTML += `<img src="${src}">`;
	_SLIDER_HTML += '</div>';
	
	return _SLIDER_HTML;
}

// 이미지 슬라이더 이미지 tag 생성
Component.setImage = function( data) {
	return `<img alt="${data.fileOrgName}" src="/upload/${data.filePath}/${data.fileName}"/>`;
}

//이미지 슬라이더 동영상 tag 생성
Component.setVideo = function( data) {
	var _HTML = '';
	_HTML += '<video muted autoplay loop>';
	_HTML += `<source src="/upload/${data.filePath}/${data.fileName}" type="video/mp4">`;
	_HTML += '<strong>동영상 재생이 불가능합니다.</strong>';
	_HTML += '</video>';
	return _HTML;
}

// iframe 컴포넌트 tag 생성
Component.setIframe = function( data, bgStyle) {
	var _COMP_HTML = '';
	_COMP_HTML += `<div class="content" style="${bgStyle}" key="${data.seq}" id="K_${data.seq}">`;
	_COMP_HTML += `<iframe style="border : 0" width="100%" height="500px" src="${data.linkUrl}"/>`;
	_COMP_HTML += '</div>';
	return _COMP_HTML;
}

//일반 컴포넌트 tag 생성
Component.setComp = function( data, bgStyle) {
	var _COMP_HTML = '';
	
	_COMP_HTML += `<div class="content" style="${bgStyle}" key="${data.seq}" id="K_${data.seq}">`;
	_COMP_HTML += data.content;
	_COMP_HTML += '</div>'; 
	
	return _COMP_HTML;
} 

//배경이 이미지인 일반 컴포넌트 tag 생성
Component.setCompBg = function( data, src) {
	var _COMP_HTML = '';
	
	_COMP_HTML += `<div class="content" style="position:relative;" key="${data.seq}" id="K_${data.seq}">`;
	_COMP_HTML += '<div style="position: absolute;">'; 
	_COMP_HTML += data.content;
	_COMP_HTML += '</div>'; 
	_COMP_HTML += `<img src="${src}">`;
	_COMP_HTML += '</div>'; 
	
	return _COMP_HTML;
} 

// 슬라이더 이벤트
Component.slickEvent = function() {
	$('.single-slick').on('init', function() {
		$('.slick-dots').addClass('b10');
	}).slick({
	    slidesToShow: 1,
	    slidesToScroll: 1,
	    arrows: false,
	    dots: true,
	    infinite: true,
	    autoplay: true,
	    autoplaySpeed: 2000
	});
	
	$('.arrow-slick').on('init', function(event, slide, c) {
		$('.arrow-slick').find('.current-pg').remove();
		var _HTML = '';
		_HTML += '<div class="current-pg">';
		_HTML += `<p class="slick-custom-pg">1/${slide.$slides.length}</p>`;
		_HTML += '</div>';
		
		$('.arrow-slick').append(_HTML);
	})
	.on('afterChange', function(event, slide, currentPage) {
		$('.arrow-slick').find('.current-pg').remove();
		currentPage ++;
		var _HTML = '';
		_HTML += '<div class="current-pg">';
		_HTML += `<p class="slick-custom-pg">${currentPage}/${slide.$slides.length}</p>`;
		_HTML += '</div>';
		
		$('.arrow-slick').append(_HTML);
	})
	.slick({
		slidesToShow: 1,
		slidesToScroll: 1,
		arrows: true,
		dots: false,
		infinite: true,
		autoplay: false
	});

	var slickMaxWidth = $(window).width();
	$('.slick-container').css('maxWidth', slickMaxWidth + 'px');
	$('.single-slick').slick('resize');
	
	setTimeout( Component.alignToCenter, 500);
}

// 내보내기 액션
Component.exportComp = function() { 
	if( location.href.indexOf('/main-pop') != -1) {
		$('body').append('<a class="btn btn-out">내보내기</a>');
		
		$('.btn-out').click( function() {
			$(this).remove();
			$('.slick-slider').slick('unslick');
			
			var scriptArr = [];
			$('[name=remove-zip]').each( function() {
				var $script = $(this);
				scriptArr.push($script[0]);
				$script.remove();
			});
			
			var _HTML = $('body').html();
			var childHTML = '<html><body>' + _HTML + '</body></html>';
			
			var resp = Hms.getAjaxCall('/mgnt/make-home', {data : childHTML, groupCode : $('#groupCode').val()});
			
			if( resp.isSuccess) {
				location.href = '/mgnt/download-home?name=' + resp.data;
			}
			Component.slickEvent();
			
			scriptArr.forEach( function(v, i) {
				$('body').appned(v);
			});
		});
	} else {
		Component.slickEvent();
	}
}

// 데이터를 가운데 정렬한다.
Component.alignToCenter = function() {
	$('.div-align').each( function() {
		var $div = $(this);
		var $parent = $div.parent();
		
		var top = ($parent.height() - $div.height()) / 2 ;
		if( top > 0) {
			$div.find('.slick-container').css('padding-top', `${top}px`);
		}
	});
}