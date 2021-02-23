/* 메뉴 클릭시 스크롤 */
$(document).on('click', 'li.menu-li > a', function() {
	var targetId = $(this).attr('id');
	$('body').animate({
		scrollTop: $('div#' + targetId).offset().top - 99
	}, 800);
});

/* _number 클래스인 경우 숫자만 입력되도록 */
$(document).on('keyup ', '._number', function(e) {
    var obj = $(this);

    if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
        e.preventDefault();
    }

    var pattern = /^[0-9]+/g;
    var matchValue = obj.val().match(pattern);

    if (!pattern.test(obj.val())) {
        obj.val('');
    }

    if (obj.val() != matchValue) {
        obj.val(matchValue);
    }
});

/* 숫자 + px 패턴만 입력되도록 */
$(document).on('change ', '._px', function(e) {
    var $el = $(this);
    var pattern = /^[0-9]+px$/g;

	if( !$el.val().match(pattern)) {
		Hms.alert('너비값을 정확히 입력해주세요.(ex:1200px)');
		$el.val('');
	}
});

/* rgb패턴 체크 */
$(document).on('change ', '._rgb', function(e) {
	var colorPattern = /^\#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$/;
	var $el = $(this);
	
	if( !$el.val().match(colorPattern)) {
		Hms.alert('RGB 컬러코드를 정확히 입력해주세요.');
		$el.val('');
	}
});

/* 윈도우 사이즈 변경시 슬라이더 이미지 reset */
/*if( $('.single-slick').length > 0) {
	$(window).resize(function() {
		var slickMaxWidth = $(window).width();
		$('.slick-container').css('maxWidth', slickMaxWidth + 'px')
		$('.single-slick').slick('resize');
	});
}*/


const scrollHeight = [];
$(window).scroll( function () {
	if( !$('body').hasClass('mob') === true) {
		if( scrollHeight.length === 0) {
			$('.content').each( function() {
				var idx = scrollHeight.length;
				var heigth = $(this).height();
				if( idx !== 0) {
					heigth += scrollHeight[idx-1];
				} else {
					heigth = heigth - 220;
				}
				scrollHeight.push( heigth);
			});
		}
		var scrollValue = $(document).scrollTop();
		var startIdx = 0;
		if( scrollHeight.length !== 0 && scrollValue > scrollHeight[0] && scrollValue < scrollHeight[scrollHeight.length - 1]) {
			scrollHeight.forEach( function(v, i) {
				if( scrollValue > v) startIdx = i;
			});
		}

		$('.menu-ul').find('li a').removeClass('selected');
		$('.menu-ul').find('li').eq(startIdx).find('a').addClass('selected');

		if( startIdx < scrollHeight.length) {
			var middlePX = (scrollHeight[startIdx + 1] + scrollHeight[startIdx]) / 2;

			if( scrollValue > middlePX && scrollValue < middlePX + 10) {

				$('body').animate({
					scrollTop: scrollHeight[startIdx + 1]
				}, 500);
			}
		}
	}
});