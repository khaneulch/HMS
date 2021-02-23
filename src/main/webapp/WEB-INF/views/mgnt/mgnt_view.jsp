<%@ page language="java"	contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<html>
	<body>
		<div class="main-content">
			<table class="gray-table w100p">
				<colgroup>
					<col width="120px"/> 
					<col/>
				</colgroup>
				<tbody>
					<tr>
						<th>명칭</th>
						<td class="title-td">${component.title}</td>
					</tr>
					<tr>
						<th>내용</th>
						<td class="h500">
							<c:choose>
								<c:when test="${component.compType eq 'I'}">
									<div class="slick-container">
										<div class="single-slick">
											<c:forEach var="img" items="${component.slider}">
												<c:choose>
													<c:when test="${fn:indexOf(img.fileOrgName, 'mp4') > 0}">
														<video muted autoplay loop>
															<source src="/upload/${component.filePath}/${img.fileName}" type="video/mp4">
														</video>
													</c:when>
													<c:otherwise>
														<img alt="${img.fileOrgName}" style="width:200px" src="/upload/${component.filePath}/${img.fileName}"/>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									${component.content}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
						<tr>
							<th>배경색상/이미지</th>
							<td>
								<c:choose>
									<c:when test="${component.bgType eq 'I'}">
										<img alt="배경이미지" src="/upload/${component.filePath}/${component.bgImg}" height="150px"/>
									</c:when>
									<c:when test="${component.bgType eq 'C'}">
										<div class="w100p" style="height:10px; background-color: ${component.bgColor};"></div>
									</c:when>
								</c:choose>
							</td>
						</tr>
				</tbody>
			</table>
			<div class="btn-wrap">
				<a href="/mgnt/list" class="btn right">목록</a>
				<a href="/mgnt/form?seq=${component.seq}" class="btn right">수정</a>
			</div>
		</div>
	</body>
	
	
	<script type="text/javascript">
		$(function() {
			$('.single-slick').on('init', function() {
				$('.slick-dots').addClass('b10');
			}).slick({
			    slidesToShow: 1,
			    slidesToScroll: 1,
			    arrows: true,
			    dots: true,
			    infinite: true,
			});
			
			var slickMaxWidth = $('.title-td').width();
			$('.slick-container').css('maxWidth', slickMaxWidth + 'px');
			$('.single-slick').slick('resize');
		});
		
		$(window).resize(function() {
			var slickMaxWidth = $('.title-td').width();
			$('.slick-container').css('maxWidth', slickMaxWidth + 'px')
			$('.single-slick').slick('resize');
		});
	</script>
</html>



<%@ include file="../common/footer.jsp" %>

