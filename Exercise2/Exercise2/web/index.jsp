<%-- 
    Document   : index
    Created on : Sep 23, 2022, 1:45:24 PM
    Author     : LEGION
--%>

<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content-left">
    <c:if test="${empty stop}">
        <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect>     
    </c:if>
    <h3>CATEGORY</h3>
    <ul>
        <a href="<c:url value="/home">
               <c:param name="req" value="category"/>
               <c:param name="typeCate" value="All"/>
           </c:url>"><li >All</li></a>
            <c:forEach items="${category}" var="c">
                <c:if test="${c.categoryName == getCateName}">
                    <a href="<c:url value="/home">
                        <c:param name="req" value="category"/>
                        <c:param name="typeCate" value="${c.categoryName}"/>
                        </c:url>"><li style="background: sienna;color: white;">${c.categoryName}</li>
                    </a>
                </c:if>
                <c:if test="${c.categoryName != getCateName}">
                    <a href="<c:url value="/home">
                        <c:param name="req" value="category"/>
                        <c:param name="typeCate" value="${c.categoryName}"/>
                        </c:url>"><li>${c.categoryName}</li>
                </a>
                </c:if>   
            </c:forEach>
    </ul>
</div>

<!--CONTENT RIGHT-->

<div id="content-right">
    <div class="path">
        <form action="<c:url value="/home"/>" method="post">
            <input type="text" size="30" name="txtsearch" value="${charSearch}" placeholder="Input name of product here!"><input type="submit" value="search">
        </form>
    </div>
    <c:if test="${empty searchByProductName}">
        <c:if test="${empty search}"> 
        <c:if test="${currentPage == 1}">
            <div class="path">Hot</b></div>
            <div class="content-main" style="display: grid;
                 grid-template-columns: 23% 23% 23% 23%;row-gap: 1%; column-gap: 1%;margin-bottom: 5px">
                <c:forEach items="${hotProducts}" var="p">
                    <c:set var="product" value="${p.product}"></c:set>
                        <div class="product">
                            <a href="<c:url value="/home">
                               <c:param name="req" value="detail"/>
                               <c:param name="productID" value="${product.productID}"/>
                           </c:url>"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="<c:url value="/home">
                                                 <c:param name="req" value="detail"/>
                                                 <c:param name="productID" value="${product.productID}"/>
                                             </c:url>">${product.productName}</a>
                        </div>
                        <div class="price">${product.unitPrice}</div>
                        <div><a href="<c:url value="/Order">
                            <c:param name="order" value="byNow"/>
                            <c:param name="productID" value="${product.productID}"/>
                        </c:url>">Buy now</a></div>
                    </div>
                </c:forEach>    
            </div>
            <div class="path">Best Sale</b></div>
            <div class="content-main" style="display: grid;
                 grid-template-columns: 23% 23% 23% 23%;row-gap: 1%; column-gap: 1%;margin-bottom: 5px">
                <c:forEach items="${bestSaleProducts}" var="p">
                    <c:set var="product" value="${p.product}"></c:set>
                        <div class="product">
                            <a href="<c:url value="/home">
                               <c:param name="req" value="detail"/>
                               <c:param name="productID" value="${product.productID}"/>
                           </c:url>"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="<c:url value="/home">
                                                 <c:param name="req" value="detail"/>
                                                 <c:param name="productID" value="${product.productID}"/>
                                             </c:url>">${product.productName}</a></div>
                        <div class="price">${product.unitPrice}</div>
                        <div><a href="<c:url value="/Order">
                                    <c:param name="order" value="byNow"/>
                                    <c:param name="productID" value="${product.productID}"/>
                                </c:url>">Buy now</a></div>
                    </div>
                </c:forEach> 
            </div>
        </c:if>
        <div class="path">New Product</b></div>
        <div class="content-main" style="display: grid;
             grid-template-columns: 23% 23% 23% 23%;row-gap: 1%; column-gap: 1%;margin-bottom: 5px">
            <c:forEach items="${newProducts}" var="p">
                <c:set var="product" value="${p.product}"></c:set>
                    <div class="product">
                        <a href="<c:url value="/home">
                           <c:param name="req" value="detail"/>
                           <c:param name="productID" value="${product.productID}"/>
                       </c:url>"><img src="img/1.jpg" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/home">
                                             <c:param name="req" value="detail"/>
                                             <c:param name="productID" value="${product.productID}"/>
                                         </c:url>">${product.productName}</a></div>
                    <div class="price">${product.unitPrice}</div>
                    <div><a href="<c:url value="/Order">
                                    <c:param name="order" value="byNow"/>
                                    <c:param name="productID" value="${product.productID}"/>
                                </c:url>">Buy now</a></div>
                </div>
            </c:forEach>
        </div>
        <div class="pagination">
            <c:if test="${currentPage>1}">
                <c:url value="/home" var="paginationPrevious">
                    <c:param name="currentPage" value="${currentPage-1}" />
                    <c:param name="req" value="home"/> 
                </c:url>
                <a href="${paginationPrevious}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${numberOfPage}" step="1" var="stepValue">
                <c:choose>
                    <c:when test="${stepValue == currentPage}">
                        <div  style="display: inline-block;
                              float: left;
                              padding: 8px 16px;
                              background-color: sienna;
                              color: white;
                              border-radius: 5px;">${stepValue}</div>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/home" var="pagination">
                            <c:param name="currentPage" value="${stepValue}" />
                            <c:param name="req" value="home"/> 
                        </c:url>
                        <a href="${pagination}">${stepValue} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<numberOfPage}">
                <c:url value="/home" var="paginationNext">
                    <c:param name="currentPage" value="${currentPage+1}" />
                    <c:param name="req" value="home"/> 
                </c:url>
                <a href="${paginationNext}">Next</a>
            </c:if>
        </div>
    </c:if>

    <!--        search    -->

    <c:if test="${not empty search}">
        <c:set var="CateName" value="${getCateName}" scope="session"/>
        <div class="path">Categories<br></div>
        <div class="content-main" style="display: grid;
             grid-template-columns: 23% 23% 23% 23%;row-gap: 1%; column-gap: 1%">
            <c:if test="${empty productCategory}">
                <div style="padding-left: 10px"><c:out value="There is no Product with this category!"/></div>
            </c:if>
            <c:forEach items="${productCategory}" var="productCategory">
                <c:set var="product" value="${productCategory.product}"/>
                <c:set var="category" value="${productCategory.category}"/>
                <div class="product">
                    <a href="<c:url value="/home">
                           <c:param name="req" value="detail"/>
                           <c:param name="productID" value="${product.productID}"/>
                       </c:url>"><img src="img/1.jpg" width="100%"/></a>
                    <div class="name"><a href="<c:url value="/home">
                                             <c:param name="req" value="detail"/>
                                             <c:param name="productID" value="${product.productID}"/>
                                         </c:url>">${product.productName}</a></div>
                    <div class="price">${product.unitPrice}</div>
                    <div><a href="<c:url value="/Order">
                                    <c:param name="order" value="byNow"/>
                                    <c:param name="productID" value="${product.productID}"/>
                                </c:url>">Buy now</a></div>
                </div>
            </c:forEach>
        </div>
        <div class="pagination" >
            <c:if test="${currentPage>1}">
                <c:url value="/home" var="paginationPrevious">
                    <c:param name="currentPage" value="${currentPage-1}" />
                    <c:param name="req" value="category"/>
                    <c:param name="typeCate" value="${CateName}"/>
                </c:url>
                <a href="${paginationPrevious}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${numberOfPage}" step="1" var="stepValue">
                <c:choose>
                    <c:when test="${stepValue == currentPage}">
                        <div style="display: inline-block;
                              float: left;
                              padding: 8px 16px;
                              background-color: sienna;
                              color: white;
                              border-radius: 5px;">${stepValue}</div>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/home" var="pagination">
                            <c:param name="currentPage" value="${stepValue}" />
                            <c:param name="req" value="category"/>
                            <c:param name="typeCate" value="${CateName}"/>
                        </c:url>
                        <a href="${pagination}" >${stepValue} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<numberOfPage}">
                <c:url value="/home" var="paginationNext">
                    <c:param name="currentPage" value="${currentPage+1}" />
                    <c:param name="req" value="category"/>
                    <c:param name="typeCate" value="${CateName}"/>
                </c:url>
                <a href="${paginationNext}">Next</a>
            </c:if>
        </div>
    </c:if>
    </c:if>
     <!--Search By Product Name-->   
    <c:if test="${not empty searchByProductName}">
            <div class="path">Searching<br></div>
        <div class="content-main" style="display: grid;
             grid-template-columns: 23% 23% 23% 23%;row-gap: 1%; column-gap: 1%">
            <c:if test="${empty productCategory}">
                <div style="margin-left: 7px; color: red"><c:out value="There is no result!"/></div>
            </c:if>
            <c:forEach items="${productCategory}" var="productCategory">
                <c:set var="product" value="${productCategory.product}"/>
                <c:set var="category" value="${productCategory.category}"/>
                <div class="product">
                    <a href="<c:url value="/home">
                           <c:param name="req" value="detail"/>
                           <c:param name="productID" value="${product.productID}"/>
                       </c:url>"><img src="img/1.jpg" width="100%"/></a>
                    <div class="name">
                        <a href="<c:url value="/home">
                        <c:param name="req" value="detail"/>
                        <c:param name="productID" value="${product.productID}"/>
                        </c:url>">${product.productName}</a>
                    </div>
                    <div class="price">${product.unitPrice}</div>
                    <div><a href="<c:url value="/Order">
                                    <c:param name="order" value="byNow"/>
                                    <c:param name="productID" value="${product.productID}"/>
                                </c:url>">Buy now</a></div>
                </div>
            </c:forEach>
        </div>
        <div class="pagination" >
            <c:if test="${currentPage>1}">
                <c:url value="/home" var="paginationPrevious">
                    <c:param name="currentPage" value="${currentPage-1}" />
                    <c:param name="req" value="searchByProductName"/>
                    <c:param name="character" value="${charSearch}"/>
                </c:url>
                <a href="${paginationPrevious}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${numberOfPage}" step="1" var="stepValue">
                <c:choose>
                    <c:when test="${stepValue == currentPage}">
                        <div style="display: inline-block;
                              float: left;
                              padding: 8px 16px;
                              background-color: sienna;
                              color: white;
                              border-radius: 5px;">${stepValue}</div>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/home" var="pagination">
                            <c:param name="currentPage" value="${stepValue}" />
                            <c:param name="req" value="searchByProductName"/>
                            <c:param name="character" value="${charSearch}"/>
                        </c:url>
                        <a href="${pagination}" >${stepValue} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage<numberOfPage}">
                <c:url value="/home" var="paginationNext">
                    <c:param name="currentPage" value="${currentPage+1}" />
                    <c:param name="req" value="searchByProductName"/>
                    <c:param name="character" value="${charSearch}"/>
                </c:url>
                <a href="${paginationNext}">Next</a>
            </c:if>
        </div>
    </c:if>
</div>
<%@include file="template/footer.jsp" %>
