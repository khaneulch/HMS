<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<html>
	<body>
		<div class="main-content">
			
			<h1><i class="fa fa-check-square mr5"></i>컴포넌트 ${empty component.seq ? '생성' : '수정'}</h1>
			
			<c:if test="${empty component.seq}">
				<ul class="group-ul mb10">
					<li class="group-li line active"><a href="javascript:selectComp('comp-basic')" data-selector="comp-basic">기본</a></li>
					<li class="group-li line"><a href="javascript:selectComp('comp-slider')" data-selector="comp-slider">이미지 슬라이드</a></li>
					<li class="group-li line"><a href="javascript:selectComp('comp-link')" data-selector="comp-link">링크</a></li>
				</ul>
			</c:if>
			
			<div class="comp-type comp-basic ${empty component || component.compType eq 'X' ? '' : ' folding '}">
				<form action="/mgnt/insert" id="mgntForm" enctype="multipart/form-data" method="POST">
					<input type="hidden" id="seq" name="seq" value="${empty component.seq ? -1 : component.seq}"/>
					<input type="hidden" id="filePath" name="filePath" value="${empty component.filePath ? filePath : component.filePath}"/>
					<input type="hidden" id="compType" name="compType" value="X"/>
					<table class="gray-table w100p">
						<colgroup>
							<col width="140px"/>
							<col width="360px"/>
							<col width="140px"/>
							<col/>
						</colgroup>
						<tbody>
							<tr>
								<th>메뉴명</th>
								<td>
									<input type="text" class="w100p" id="title" name="title" placeholder="메뉴명" value="${component.title}" _required="true" title="메뉴명"/>
								</td>
								<th>메뉴노출여부</th>
								<td>
									<input type="checkbox" id="menuYn" name="menuYn" value="Y" ${component.menuYn eq 'Y' ? ' checked' : ''}/>
								</td>
							</tr>
							<tr>
								<th>내용</th>
								<td colspan="3">
									<div id="editor-box" style="height: 500px">${component.content}</div>
									<input type="hidden" class="eiditor-data" id="content" name="content" _required="true" title="내용"/>
								</td>
							</tr>
							<tr>
								<th>배경색상/이미지</th>
								<td colspan="3">
									<div class="left p8">
										<input type="radio" id="bgTypeXX" name="bgType" value="X" ${empty component.bgType || component.bgType eq 'X' ? ' checked' : ''}/>
										<label for="bgTypeXX">없음</label>
										<input type="radio" id="bgTypeXA" name="bgType" data-id="bgImg" value="I" ${component.bgType eq 'I' ? ' checked' : ''}/>
										<label for="bgTypeXA">이미지</label>
										<input type="radio" id="bgTypeXB" name="bgType" data-id="bgColor" value="C" ${component.bgType eq 'C' ? ' checked' : ''}/>
										<label for="bgTypeXB">색상(RGB)</label>
									</div>
									<div class="left">
										<c:choose>
											<c:when test="${component.bgType eq 'I' && not empty component.bgImg}">
												<span class="file-name">${component.bgImg}<i class="fa fa-times bg-delete" aria-hidden="true"></i></span>
											</c:when>
											<c:otherwise>
												<input type="file" class="w100p bgType ${component.bgType eq 'I' ? '' : ' folding'}" id="bgImg" name="bgImg" _required="true" title="배경이미지"/>
											</c:otherwise>
										</c:choose>
										<input type="text" class="_rgb w100p bgType mt3 ${component.bgType eq 'C' ? '' : ' folding'}" id="bgColor" name="bgColor" placeholder="#FF0000" value="${component.bgColor}" _required="true" title="배경색상"/>
									</div>
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
			
			<div class="comp-type comp-slider ${component.compType eq 'I' ? '' : ' folding '}">
				<form action="/mgnt/insert-slider" id="sliderForm" enctype="multipart/form-data" method="POST">
					<input type="hidden" id="seq" name="seq" value="${empty component.seq ? -1 : component.seq}"/>
					<input type="hidden" id="filePath" name="filePath" value="${empty component.filePath ? filePath : component.filePath}"/>
					<input type="hidden" id="compType" name="compType" value="I"/>
					<input type="hidden" id="deleteFiles" name="deleteFiles" value=""/>
					<table class="gray-table w100p">
						<colgroup>
							<col width="140px"/>
							<col width="360px"/>
							<col width="140px"/>
							<col/>
						</colgroup>
						<tbody>
							<tr>
								<th>메뉴명</th>
								<td>
									<input type="text" class="w100p" id="title" name="title" placeholder="메뉴명" value="${component.title}" _required="true" title="메뉴명"/>
								</td>
								<th>메뉴노출여부</th>
								<td>
									<input type="checkbox" id="menuYn" name="menuYn" value="Y" ${component.menuYn eq 'Y' ? ' checked' : ''}/>
								</td>
							</tr>
							<tr>
								<th>이미지/동영상(mp4)</th>
								<td colspan="3">
									<span>이미지를 추가한 순서대로 노출됩니다.</span>
									<ul class="file-ul">
										<li><input type="file" id="sliderImg"/><i class="fa fa-times file-delete" aria-hidden="true"></i></li>
										<c:forEach var="img" items="${component.slider}">
											<li data-file-name="${img.fileName}">${img.fileOrgName}<i class="fa fa-times li-delete" aria-hidden="true"></i></li>
										</c:forEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th>슬라이더 유형</th>
								<td>
									<input type="radio" id="sliderTypeX" name="sliderType" value="X" ${empty component or component.sliderType eq 'X' ? 'checked' : ''}/>
									<label for="sliderTypeX">화살표없음/자동롤링</label>
									<input type="radio" id="sliderTypeA" name="sliderType" value="A" ${component.sliderType eq 'A' ? 'checked' : ''}/>
									<label for="sliderTypeA">화살표있음</label>
								</td>
								<th>슬라이드 너비</th>
								<td>
									<input type="number" class="w100p" min="100" max="1900" step="100" id="width" name="width" value="${component.width}" placeholder="100~1900 미입력시 최대 넓이로 자동 지정됩니다."/>
								</td>
							</tr>
							<tr>
								<th>배경색상/이미지</th>
								<td colspan="3">
									<div class="left p8">
										<input type="radio" id="bgTypeIX" name="bgType" value="X" ${empty component.bgType || component.bgType eq 'X' ? ' checked' : ''}/>
										<label for="bgTypeIX">없음</label>
										<input type="radio" id="bgTypeIA" name="bgType" data-id="bgImg" value="I" ${component.bgType eq 'I' ? ' checked' : ''}/>
										<label for="bgTypeIA">이미지</label>
										<input type="radio" id="bgTypeIB" name="bgType" data-id="bgColor" value="C" ${component.bgType eq 'C' ? ' checked' : ''}/>
										<label for="bgTypeIB">색상(RGB)</label>
									</div>
									<div class="left">
										<c:choose>
											<c:when test="${component.bgType eq 'I' && not empty component.bgImg}">
												<span class="file-name">${component.bgImg}<i class="fa fa-times bg-delete" aria-hidden="true"></i></span>
											</c:when>
											<c:otherwise>
												<input type="file" class="w100p bgType ${component.bgType eq 'I' ? '' : ' folding'}" id="bgImg" name="bgImg" _required="true" title="배경이미지"/>
											</c:otherwise>
										</c:choose>
										<input type="hidden" id="oldBgImg" name="oldBgImg" value="${component.bgImg}"/>
										<input type="text" class="_rgb w100p bgType mt3 ${component.bgType eq 'C' ? '' : ' folding'}" id="bgColor" name="bgColor" placeholder="#FF0000" value="${component.bgColor}"/>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="btn-wrap">
						<a href="/mgnt/list" class="btn right">취소</a>
						<a href="javascript:save('#sliderForm');" class="btn right" id="submit-editor">저장</a>
					</div>
				</form>
			</div>
			
			<div class="comp-type comp-link ${component.compType eq 'L' ? '' : ' folding '}">
				<form action="/mgnt/insert" id="linkForm" enctype="multipart/form-data" method="POST">
					<input type="hidden" id="seq" name="seq" value="${empty component.seq ? -1 : component.seq}"/>
					<input type="hidden" id="filePath" name="filePath" value="${empty component.filePath ? filePath : component.filePath}"/>
					<input type="hidden" id="compType" name="compType" value="L"/>
					<input type="hidden" id="deleteFiles" name="deleteFiles" value=""/>
					<table class="gray-table w100p">
						<colgroup>
							<col width="140px"/>
							<col width="360px"/>
							<col width="140px"/>
							<col/>
						</colgroup>
						<tbody>
							<tr>
								<th>메뉴명</th>
								<td>
									<input type="text" class="w100p" id="title" name="title" placeholder="메뉴명" value="${component.title}" _required="true" title="메뉴명"/>
								</td>
								<th>메뉴노출여부</th>
								<td>
									<input type="checkbox" id="menuYn" name="menuYn" value="Y" ${component.menuYn eq 'Y' ? ' checked' : ''}/>
								</td>
							</tr>
							<tr>
								<th>링크 옵션</th>
								<td>
									<input type="radio" id="linkTypeP" name="linkType" value="P" ${empty component.linkType || component.linkType eq 'P' ? ' checked' : ''}/>
									<label for="linkTypeP">팝업</label>
									<input type="radio" id="linkTypeI" name="linkType" value="I" ${component.linkType eq 'I' ? ' checked' : ''}/>
									<label for="linkTypeI">Iframe</label>
								</td>
								<th>링크 주소</th>
								<td><input type="text" class="w100p" id="linkUrl" name="linkUrl" placeholder="링크 주소(http://stam.kr)" value="${component.linkUrl}" _required="true" title="링크 주소"/></td>
							</tr>
							<tr>
								<th>배경색상/이미지</th>
								<td colspan="3">
									<div class="left p8">
										<input type="radio" id="bgTypeLX" name="bgType" value="X" ${empty component.bgType || component.bgType eq 'X' ? ' checked' : ''}/>
										<label for="bgTypeLX">없음</label>
										<input type="radio" id="bgTypeLA" name="bgType" data-id="bgImg" value="I" ${component.bgType eq 'I' ? ' checked' : ''}/>
										<label for="bgTypeLA">이미지</label>
										<input type="radio" id="bgTypeLB" name="bgType" data-id="bgColor" value="C" ${component.bgType eq 'C' ? ' checked' : ''}/>
										<label for="bgTypeLB">색상(RGB)</label>
									</div>
									<div class="left">
										<c:choose>
											<c:when test="${component.bgType eq 'I' && not empty component.bgImg}">
												<span class="file-name">${component.bgImg}<i class="fa fa-times bg-delete" aria-hidden="true"></i></span>
											</c:when>
											<c:otherwise>
												<input type="file" class="w100p bgType ${component.bgType eq 'I' ? '' : ' folding'}" id="bgImg" name="bgImg" _required="true" title="배경이미지"/>
											</c:otherwise>
										</c:choose>
										<input type="hidden" id="oldBgImg" name="oldBgImg" value="${component.bgImg}"/>
										<input type="text" class="_rgb w100p bgType mt3 ${component.bgType eq 'C' ? '' : ' folding'}" id="bgColor" name="bgColor" placeholder="#FF0000" value="${component.bgColor}"/>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="btn-wrap">
						<a href="/mgnt/list" class="btn right">취소</a>
						<a href="javascript:save('#linkForm');" class="btn right" id="submit-editor">저장</a>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="/content/hms/js/form-event.js"></script>
</html>

<%@ include file="../common/footer.jsp" %>