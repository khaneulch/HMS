<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<html>
	<body>
		<div class="main-content">
		
			<h1><i class="fa fa-check-square mr5"></i>푸터 수정</h1>
			
			<form action="/mgnt/insert-footer" id="mgntForm" method="POST">
				<input type="hidden" id="groupCode" name="groupCode" value="${home.groupCode}"/>
				<table class="gray-table w100p">
					<colgroup>
						<col width="140px"/>
						<col width="360px"/>
						<col width="140px"/>
						<col/>
					</colgroup>
					<tbody>
						<tr>
							<th>내용</th>
							<td colspan="3">
								<div id="editor-box" style="height: 500px">${home.content}</div>
								<input type="hidden" class="eiditor-data" id="content" name="content" _required="true" title="내용"/>
							</td>
						</tr>
						<tr>
							<th>배경색상</th>
							<td colspan="3">
								<input type="text" class="_rgb w100p mt3" id="bgColor" name="bgColor" placeholder="#FF0000" value="${home.bgColor}"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-wrap">
					<a href="/mgnt/list" class="btn right">취소</a>
					<a href="javascript:save('#mgntForm');" class="btn right" id="submit-editor">저장</a>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript" src="/content/hms/js/form-event.js"></script>
</html>

<%@ include file="../common/footer.jsp" %>