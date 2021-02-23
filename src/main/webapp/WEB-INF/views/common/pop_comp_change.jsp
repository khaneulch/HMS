<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hms" 	uri="/WEB-INF/tlds/hms" %>

<div class="modal-wrap pop-comp-change is-visible">
	<div class="modal">
		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>
		<h2>순서변경</h2>
		<div class="txt-wrap center">
			<p class="oldDpSeq">현재 : </p>
			<p>변경 : <input type="text" class="w80 _number" id="dpSeq"/></p>
			<div class="btn-wrap">
				<a href="javascript:void(0);" class="modal-yes btn left">예</a>
				<a href="javascript:void(0);" class="modal-no btn right">아니오</a>
			</div>
		</div>
	</div><br><br>
</div>
