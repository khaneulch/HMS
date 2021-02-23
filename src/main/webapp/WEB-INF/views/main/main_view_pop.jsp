<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="home-content" style="width: ${home.contentWidth}"></div>
</body>
<script type="text/javascript" src="/content/hms/js/component.js"></script>
<script type="text/javascript">
	$( function() {
		if( location.href.indexOf('/main-pop') === -1) {
			//
		} else {
			Component.init();
		}
		Component.slickEvent();
		Component.exportComp();
	});
</script>

<%@ include file="../common/footer.jsp" %>