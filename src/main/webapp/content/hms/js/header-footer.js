$( function() {
	setHeaderFooter();
});

function setHeaderFooter() {
	var resp = Hms.getAjaxCall('/getHeaderData?groupCode=' + $('#groupCode').val());
	if( resp && resp.isSuccess) {
		var data = resp.data;
		
		// footer를 변경함
		var _FOOTER_HTML = '';
		_FOOTER_HTML += '<div ';
		if( data.home.bgColor && data.home.bgColor !== '') {
			_FOOTER_HTML += `style="background-color:${data.home.bgColor}"`;
		}
		_FOOTER_HTML += '>';
		_FOOTER_HTML += data.home.content;
		_FOOTER_HTML += '</div>';
		$('.footer-content').html(_FOOTER_HTML);
		
		// header를 변경함
		var logoSrc = data.logo ? data.logo : '/content/hms/images/logo.png';
		var _HEADER_HTML = '<div>';
		_HEADER_HTML += '<div class="logo">';
		_HEADER_HTML += `<img alt="LOGO" src="${logoSrc}">`;
		_HEADER_HTML += '</div>';
		if( location.pathname == '/' || location.pathname == '/main-pop') {
			_HEADER_HTML += '<ul class="menu-ul">';
			$.each( data.list, function( k, v) {
				if( v.menuYn == 'Y') {
					if( v.compType === 'L' && v.linkType === 'P') {
						_HEADER_HTML += `<li key="${v.seq}" class="menu-li line"><p class="pop" data-url="${v.linkUrl}">${v.title}</p></li>`;
					} else {
						_HEADER_HTML += `<li key="${v.seq}" class="menu-li line"><a id="K_${v.seq}">${v.title}</a></li>`;
					}
				}
			});
			_HEADER_HTML += '</ul>';
		}
		_HEADER_HTML += '</div>';
		$('.header-content').html(_HEADER_HTML);
		
		if( data.home.headerRgb && data.home.headerRgb !== '') {
			$('.header-content').css('background-color', data.home.headerRgb);
		}
		
		$('.menu-li').has('p.pop').click( function() {
			var url = $(this).find('p.pop').attr('data-url');
			Hms.pop(url);
		});
	}
}