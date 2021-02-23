<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="paging">

	<a href="javascript:Hms.goPage('1');" class="prev"><i class="fa fa-angle-double-left" aria-hidden="true"></i></a>
	<c:if test="${pagination.previousPage > 0}">
		<a href="javascript:Hms.goPage('${pagination.previousPage}');" class="prev"><i class="fa fa-angle-left" aria-hidden="true"></i></a>
	</c:if>
	<c:if test="${pagination.previousPage == 0}">
		<a href="javascript:Hms.goPage('1');" class="prev"><i class="fa fa-angle-left" aria-hidden="true"></i></a>
	</c:if>
	
	<c:choose>
			<c:when test="${empty pagination.totalPages}">
				<a href="javascript:void(0);" class="selected">1</a>	
			</c:when>
			<c:when test="${pagination.totalPages == 0}">	
				<a href="javascript:void(0);" class="selected">1</a>
			</c:when>
		    <c:when test="${pagination.totalPages < 10}">	
			    <c:forEach begin="1" end="${pagination.totalPages}" var="i" >
			        <c:choose>
			            <c:when test="${i == pagination.currentPage}">
			        		<a href="javascript:void(0);" class="selected">${i}</a>
			            </c:when>
			            <c:otherwise>
			            	<a href="javascript:Hms.goPage('${i}');">${i}</a>
			            </c:otherwise>
			        </c:choose>
			    </c:forEach>	
			</c:when>
			
		    <c:when test="${pagination.currentPage < 9}">	
			    <c:forEach begin="1" end="10" var="i" >
			        <c:choose>
			            <c:when test="${i == pagination.currentPage}">
			        		<a href="javascript:void(0);" class="selected">${i}</a>
			            </c:when>
			            <c:otherwise>
			        		<a href="javascript:Hms.goPage('${i}');">${i}</a>
			            </c:otherwise>
			        </c:choose>
			    </c:forEach>	
			</c:when>
			
			<c:when test="${pagination.currentPage > pagination.totalPages - 8}">	
			    <c:forEach begin="${pagination.totalPages - 9}" end="${pagination.totalPages}" var="i" >
			        <c:choose>
			            <c:when test="${i == pagination.currentPage}">
			        		<a href="javascript:void(0);" class="selected">${i}</a>
			            </c:when>
			            <c:otherwise>
			        		<a href="javascript:Hms.goPage('${i}');">${i}</a>
			            </c:otherwise>
			        </c:choose>
			    </c:forEach>	
			</c:when>
			
			<c:otherwise>
				<c:forEach begin="${pagination.currentPage - 5}" end="${pagination.currentPage+5}" var="i" >
			        <c:choose>
			           <c:when test="${i == pagination.currentPage}">
			        		<a href="javascript:void(0);" class="selected">${i}</a>
			            </c:when>
			            <c:otherwise>
			        		<a href="javascript:Hms.goPage('${i}');">${i}</a>
			            </c:otherwise>
			        </c:choose>
			    </c:forEach>	
			</c:otherwise>
		</c:choose>
	
	<c:if test="${pagination.nextPage > 0}">
		<a href="javascript:Hms.goPage('${pagination.nextPage}');" class="next"><i class="fa fa-angle-right" aria-hidden="true"></i></a>
	</c:if>
	<c:if test="${pagination.nextPage == 0}">
		<a href="javascript:Hms.goPage('${pagination.totalPages}');" class="next"><i class="fa fa-angle-right" aria-hidden="true"></i></a>
	</c:if>
	<a href="javascript:Hms.goPage('${pagination.totalPages}');" class="next"><i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
</div>