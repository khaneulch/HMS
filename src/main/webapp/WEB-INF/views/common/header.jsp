<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hms" 	uri="/WEB-INF/tlds/hms" %>

<meta charset="utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />

<title>HOME MGNT</title>

<!-- css -->
<link rel="stylesheet" type="text/css" href="/content/hms/css/font-awesome-4.7.0/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/content/hms/css/slick/slick.css">
<link rel="stylesheet" type="text/css" href="/content/hms/css/slick/slick-theme.css">
<link rel="stylesheet" type="text/css" href="/content/hms/css/common.css">

<!-- jquery js -->
<script type="text/javascript" src="/content/hms/js/jquery/jquery-3.5.1.min.js"></script>

<!-- ckeditor4 -->
<script type="text/javascript" src="/content/hms/js/ckeditor4/build/ckeditor.js"></script>

<!-- slick -->
<script type="text/javascript" src="/content/hms/js/slick/slick.min.js"></script>

<!-- custom -->
<script type="text/javascript" src="/content/hms/js/common.js"></script>
<script type="text/javascript" src="/content/hms/js/admin.js"></script>

<html>
	<header>
		<div class="header-content" id="header-content"></div>
		<input type="hidden" id="groupCode" name="groupCode" value="${param.groupCode}"/>
	</header>
	<script type="text/javascript" name="remove-zip" src="/content/hms/js/header-footer.js"></script>
	<script type="text/javascript">
		$(function() {
			$('.check-all').click( function() {
				var $self = $(this);
				var checked = $(this).is(':checked');
				if( checked) {
					$self.parents('div').find('input:checkbox').prop('checked', true);
				} else {
					$self.parents('div').find('input:checkbox').prop('checked', false);
				}
			});
		})
	</script>
</html>