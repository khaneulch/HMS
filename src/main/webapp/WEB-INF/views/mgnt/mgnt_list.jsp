<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="hms" 	uri="/WEB-INF/tlds/hms" %>
<%@ include file="../common/header.jsp" %>

<html>
	<body>
		<div class="main-content">
			<div>
				<div class="btn-wrap">
					<c:if test="${not empty param.groupCode}">
						<a href="javascript:downloadHome();" class="btn right">홈페이지 내보내기</a>
						<a href="javascript:deleteHome();" class="btn right">그룹삭제</a>
					</c:if>
				</div>
				<ul class="group-ul">
					<c:if test="${not empty group}">
						<li class="group-li line${empty param.groupCode ? ' active' : ''}"><a href="/mgnt/list">NEW</a></li>
						<c:forEach items="${group}" var="item">
							<li class="group-li line${param.groupCode eq item.groupCode ? ' active' : ''}">
								<a href="/mgnt/list?groupCode=${item.groupCode}">${item.groupName}</a>
							</li>
							<c:if test="${param.groupCode eq item.groupCode}">
								<c:set var="groupName" value="${item.groupName}"/>
							</c:if>
						</c:forEach>
					</c:if>
				</ul>
				
				<input type="hidden" id="groupName" name="groupName" value="${groupName}"/>
				<c:if test="${not empty param.groupCode}">
					<h1 class="title"><i class="fa fa-check-square-o mr5"></i>기본 설정</h1>
					<table class="gray-table w100p">
						<colgroup>
							<col width="120px"/>
							<col width="380px"/>
							<col width="120px"/>
							<col/>
						</colgroup>
						<tbody>
							<tr>
								<th class="center">로고 이미지</th>
								<td>
									<input type="file" id="logoImg" class="input-70"/>
									<a href="javascript:imageChange();" class="btn right">변경</a>
								</td>
								<th class="center">헤더 색상</th>
								<td>
									<input type="text" class="_rgb mt3 input-70" id="headerRgb" placeholder="#FF0000" value="${home.headerRgb}"/>
									<a href="javascript:void(0);" class="btn right btn-setting" data-id="headerRgb">변경</a>
								</td>
							</tr>
							<tr>
								<th class="center">페이지 너비</th>
								<td>
									<input type="text" class="_px mt3 input-70" id="contentWidth" value="${home.contentWidth}"/>
									<a href="javascript:void(0);" class="btn right btn-setting" data-id="contentWidth">변경</a>
								</td>
								<th class="center">푸터 수정</th>
								<td><a href="/mgnt/form-footer?groupCode=${param.groupCode}" class="btn">푸터 수정</a></td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</div>
			
			<div>
				<h1 class="title"><i class="fa fa-check-square-o mr5"></i>컴포넌트 목록</h1>
				<form action="/mgnt/list" id="listForm">
					<page:pagination-input-hidden/>
					<input type="hidden" id="groupCode" name="groupCode" value="${param.groupCode}"/>
				</form>
				<table class="gray-table center w100p">
					<colgroup>
						<col width="50px"/>
						<col width="80px"/>
						<col/>
						<col width="80px"/>
						<col width="60px"/>
						<col width="120px"/>
						<col width="70px"/>
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" class="check-all"/></th>
							<th>순번</th>
							<th>메뉴명</th>
							<th>컴포넌트<br/>타입</th>
							<th>메뉴<br/>여부</th>
							<th>등록일자</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${list}">
							<tr>
								<td><input type="checkbox" name="main-check" value="${item.seq}" ${not empty item.isMain ? ' disabled' : ''}/></td>
								<td>${item.seq}</td>
								<td><a href="/mgnt/view?seq=${item.seq}">${item.title}</a></td>
								<td>${item.compType eq 'L' ? '링크' : item.compType eq 'I' ? '이미지' : '기본'}</td>
								<td>${item.menuYn}</td>
								<td>${hms:getFormattedDate(item.regDt)}</td>
								<td class="p0-im">
									<a href="javascript:void(0);" data-seq="${item.seq}" data-title="${item.title}" class="btn btn-comp-delete">삭제</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<page:pagination-manager/>
				<div class="btn-wrap">
					<a href="/mgnt/form" class="btn right">컴포넌트 생성</a>
					<a href="javascript:addComponent();" class="btn left">추가</a>
				</div>
			</div>
			
			<c:if test="${not empty param.groupCode}">
				<div>
					<h1 class="title"><i class="fa fa-check-square-o mr5"></i>메인 순서 변경</h1>
					<div class="pick-list">
					</div>
					<div class="btn-wrap">
						<a href="javascript:downloadHome();" class="btn right">홈페이지 내보내기</a>
					</div>
				</div>
			</c:if>
		</div>
	</body>
	
	<c:if test="${not empty param.groupCode}">
		<script type="text/javascript" src="/content/hms/js/mgnt-event.js"></script>
	</c:if>
	<script type="text/javascript">
		$( function() {
			/* 메인 컴포넌트 슬라이더 */
			//$('.slick-list').slick();
			
			settingEvent();
			deleteCompEvent();
		});
		
		/* 로고이미지 변경 클릭 이벤트 */
		function imageChange() {
			if( $('#logoImg')[0].files[0]) {
				Hms.confirm('로고 이미지를 변경하시겠습니까?', function() {
			   		var formData = new FormData();
			        formData.append('logoImage', $('#logoImg')[0].files[0]);
			        formData.append('groupCode', $('#groupCode').val());
			        
			        var resp = Hms.getAjaxCall('/mgnt/logo-upload', formData);
			        if( resp && resp.isSuccess) {
			        	location.reload();
			        }
				});
			} else {
				Hms.alert('로고 이미지를 등록해주세요.');
			}
		}
		
		/* 컴포넌트 추가 클릭 이벤트 */
		function addComponent() {
			if( $('[name=main-check]:checked').length > 0) {
				Hms.confirm('컴포넌트를 메인 페이지에 추가하시겠습니까?', function() {
					var groupCode = $('#groupCode').val();
					if( groupCode) {
						sendComponent(groupCode);
					} else {
						setGroupName();						
					}
				});
			} else {
				Hms.alert('컴포넌트를 선택해주세요.')
			}
		}
		
		/* 컴포넌트 추가 전송 */
		function sendComponent( groupCode) {
			var data = {};
			data.seqs = $('[name=main-check]:checked').map(function(){return $(this).val()}).get().join(',');
			data.groupCode = groupCode; 
			data.groupName = $('#groupName').val();
			var resp = Hms.getAjaxCall('/mgnt/add-main', data);
	        if( resp && resp.isSuccess) {
	        	location.reload();
	        }
		}
		
		/* 내보내기 클릭 이벤트 */
		function downloadHome( _HTML) {
			Hms.alert( '팝업창에서 내보내기를 클릭시 zip파일이 다운로드 됩니다.', function() {
				window.open('/main-pop?groupCode=' + $('#groupCode').val(), '_blank');
			})
		}
		
		/* 기본설정 변경 클릭 이벤트 */
		function settingEvent() {
			$('.btn-setting').click( function() {
				var $self = $(this);
				Hms.confirm('기본설정을 변경하시겠습니까?', function() {
					var dataId = $self.attr('data-id');
					var param = {groupCode : $('#groupCode').val()};
					param[dataId] = $('#' + dataId).val();
					var resp = Hms.getAjaxCall('/mgnt/update-setting', param);
				});
			});
		}
		
		/* 컴포넌트 삭제 이벤트 */
		function deleteCompEvent() {
			$('.btn-comp-delete').click( function() {
				var $self = $(this);
				var seq = $self.attr('data-seq');
				var title = $self.attr('data-title');
				Hms.confirm('컴포넌트[' + title + ']을 삭제하시겠습니까?', function() {
					var resp = Hms.getAjaxCall('/mgnt/delete', {seq : seq});
					
					if( resp && resp.isSuccess) location.reload();
				});
			});
		}
		
		/* 그룹명 지정 팝업 */
		function setGroupName() {
			var _HTML = $('#pop-group-name').html();
			var bodyOver = $('body').css('overflow');
			
			$('body').append(_HTML);
			$('body').css('overflow', 'hidden');
			
			var $modal = $('.pop-group-name');
			
			$modal.find('.modal-yes').click( function() {
				var groupName = $modal.find('#groupNameTemp').val();
				$modal.remove();
				$('body').css('overflow', bodyOver);
				$('#groupName').val(groupName);
				sendComponent(-1);
			});
			
			$modal.find('.modal-close, .modal-no').click( function() {
				$modal.remove();
				$('body').css('overflow', bodyOver);
			});
		}
		
		/* 그룹 삭제 이벤트 */
		function deleteHome() {
			var groupName = $('#groupName').val();
			var groupCode = $('#groupCode').val();
			Hms.confirm('그룹[' + groupName + ']을 삭제하시겠습니까?', function() {
				var resp = Hms.getAjaxCall('/mgnt/delete-main', {groupCode : groupCode, deleteGroup : 'Y'});
				if( resp && resp.isSuccess) location.href = '/mgnt/list';
			});
		}
		
	</script>
	
	<script type="text/html" id="pop-comp-change">
		<jsp:include page="../common/pop_comp_change.jsp"/>
	</script>
	
	<script type="text/html" id="pop-group-name">
		<jsp:include page="../common/pop_group_name.jsp"/>
	</script>
</html>

<%@ include file="../common/footer.jsp" %>