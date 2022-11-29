<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href="css/style.css" rel="stylesheet"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    </head>
    <body>
        <c:choose>
            <c:when test="${empty adminAccount}">
                <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect>
            </c:when>
            <c:when test="${not empty adminAccount}">
                <c:if test="${empty admin}">
                    <c:redirect url="/Admin"><c:param name="req" value="listProduct"/></c:redirect>
                </c:if>
            </c:when>
        </c:choose>
        <div id="container">
            <div id="header">
                <a id="logo-admin" style="text-decoration: none;color: black;" href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>">
                    Ecommerce Admin
                </a>
                <div id="banner-admin">
                    <ul>
                        <li><a href="<c:url value="/home"><c:param name="req" value="home"/></c:url>">Home</a></li>
                        <li><a href="<c:url value="/home"><c:param name="req" value="remove"/></c:url>">SignOut</a></li>
                        </ul>
                    </div>
                </div>
                <div id="content">
                    <div id="content-left">
                        <ul>
                        <a href="<c:url value="/DashBoard"><c:param name="req" value="dashBoard"/></c:url>"><li>Dashboard</li></a>
                        <a href="<c:url value="/Admin"><c:param name="req" value="orderList"/></c:url>"><li>Orders</li></a>
                        <a href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>"><li>Products</li></a>
                        <a href="<c:url value="/Admin"><c:param name="req" value="listCustomer"/></c:url>"><li>Customers</li></a>
                        </ul>
                    </div>
                    <div id="content-right">
                        <div class="path-admin">PRODUCTS LIST</b></div>
                        <div class="content-main">
                            <div id="content-main-dashboard">
                                <div id="product-title-header">
                                    <div id="product-title-1" style="width: 25%;">
                                        <b>Filter by Catetory:</b>
                                        <form action="<c:url value="/Admin"><c:param name="req" value="category"/></c:url>" method="post">
                                            <select name="ddlCategory">
                                                <option value="All">--- Select ---</option>
                                            <c:forEach items="${category}" var="c">
                                                <c:if test="${c.categoryName == getCateName}">
                                                    <option value="${c.categoryName}" selected>${c.categoryName}</option>
                                                </c:if>
                                                <c:if test="${c.categoryName != getCateName}">
                                                    <option value="${c.categoryName}">${c.categoryName}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <input type="submit" value="Filter">
                                    </form>
                                </div>
                                <div id="product-title-2" style="width: 55%;">
                                    <form action="<c:url value="/Admin"><c:param name="req" value="searchByProductName"/></c:url>" method="post">
                                        <input type="text" name="txtSearch" placeholder="Enter product name to search" value="${adminSearch}"/>
                                        <input type="submit" value="Search">
                                    </form>
                                </div>
                                <div id="product-title-3" style="width: 20%;">
                                    <a href="<c:url value="/adminCRUD">
                                           <c:param name="req" value="Create"/></c:url>">Create a new Product</a>
                                        <form action="">
                                            <label for="upload-file">Import .xls or .xlsx file</label>
                                            <input type="file" name="file" id="upload-file" />
                                        </form>
                                    </div> 
                                </div>            
                            <c:if test="${empty searchByProductName}">
                                <c:if test="${empty search}">
                                    <div id="order-table-admin">
                                        <div style="padding: 10px;color: green;"><c:out value="${msgCRUD}"/></div>
                                        <c:remove var="msgCRUD"/>
                                        <table id="orders">
                                            <tr>
                                                <th>ProductID</th>
                                                <th>ProductName</th>
                                                <th>UnitPrice</th>
                                                <th>Unit</th>
                                                <th>UnitsInStock</th>
                                                <th>Category</th>
                                                <th>Discontinued</th>
                                                <th></th>
                                            </tr>
                                            <c:forEach items="${productCategory}" var="pc">
                                                <c:set var="product" value="${pc.product}"/>
                                                <c:set var="category" value="${pc.category}"/>
                                                <tr>
                                                    <td><a href="<c:url value="/home">
                                                               <c:param name="req" value="detail"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>">#${product.productID}</a></td>
                                                    <td>${product.productName}</td>
                                                    <td>${product.unitPrice}</td>
                                                    <td>pieces</td>
                                                    <td>${product.unitsInStock}</td>
                                                    <td>${category.categoryName}</td>
                                                    <td>${product.discontinued == false?0:1}</td>
                                                    <td>
                                                        <a href="<c:url value="/adminCRUD">
                                                               <c:param name="req" value="Edit"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>">Edit</a> &nbsp; | &nbsp; 
                                                        <a href="<c:url value="/adminCRUD">
                                                               <c:param name="req" value="Delete"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>" onclick="return check()">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                    <div id="paging">
                                        <div class="pagination">
                                            <c:if test="${currentPage>1}">
                                                <c:url value="/Admin" var="paginationPrevious">
                                                    <c:param name="currentPage" value="${currentPage-1}" />
                                                    <c:param name="req" value="listProduct"/> 
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
                                                        <c:url value="/Admin" var="pagination">
                                                            <c:param name="currentPage" value="${stepValue}" />
                                                            <c:param name="req" value="listProduct"/> 
                                                        </c:url>
                                                        <a href="${pagination}">${stepValue} </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage<numberOfPage}">
                                                <c:url value="/Admin" var="paginationNext">
                                                    <c:param name="currentPage" value="${currentPage+1}" />
                                                    <c:param name="req" value="listProduct"/> 
                                                </c:url>
                                                <a href="${paginationNext}">Next</a>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                                <!--SEARCH BY CATEGORY NAME-->
                                <c:if test="${not empty search}">
                                    <c:set var="CateName" value="${getCateName}" scope="session"/>
                                    <div id="order-table-admin">
                                        <table id="orders">
                                            <c:if test="${empty productCategory}">
                                                <c:out value="There is no Product with this category"/>
                                            </c:if>
                                            <tr>
                                                <th>ProductID</th>
                                                <th>ProductName</th>
                                                <th>UnitPrice</th>
                                                <th>Unit</th>
                                                <th>UnitsInStock</th>
                                                <th>Category</th>
                                                <th>Discontinued</th>
                                                <th></th>
                                            </tr>
                                            <c:forEach items="${productCategory}" var="pc">
                                                <c:set var="product" value="${pc.product}"/>
                                                <c:set var="category" value="${pc.category}"/>
                                                <tr>
                                                    <td><a href="<c:url value="/home">
                                                               <c:param name="req" value="detail"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>">#${product.productID}</a></td>
                                                    <td>${product.productName}</td>
                                                    <td>${product.unitPrice}</td>
                                                    <td>pieces</td>
                                                    <td>${product.unitsInStock}</td>
                                                    <td>${category.categoryName}</td>
                                                    <td>${product.discontinued == false?0:1}</td>
                                                    <td>
                                                        <a href="<c:url value="/adminCRUD">
                                                               <c:param name="req" value="Edit"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>">Edit</a> &nbsp; | &nbsp; 
                                                        <a href="<c:url value="/adminCRUD">
                                                               <c:param name="req" value="Delete"/>
                                                               <c:param name="productID" value="${product.productID}"/>
                                                           </c:url>" onclick="return check()">Delete</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                    <div id="paging">
                                        <div class="pagination">
                                            <c:if test="${currentPage>1}">
                                                <c:url value="/Admin" var="paginationPrevious">
                                                    <c:param name="currentPage" value="${currentPage-1}" />
                                                    <c:param name="req" value="category"/>
                                                    <c:param name="typeCate" value="${CateName}"/>
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
                                                        <c:url value="/Admin" var="pagination">
                                                            <c:param name="currentPage" value="${stepValue}" />
                                                            <c:param name="req" value="category"/> 
                                                            <c:param name="typeCate" value="${CateName}"/>
                                                        </c:url>
                                                        <a href="${pagination}">${stepValue} </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage<numberOfPage}">
                                                <c:url value="/Admin" var="paginationNext">
                                                    <c:param name="currentPage" value="${currentPage+1}" />
                                                    <c:param name="req" value="category"/>
                                                    <c:param name="typeCate" value="${CateName}"/>
                                                </c:url>
                                                <a href="${paginationNext}">Next</a>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>
                            <!--SEARCH BY PRODUCT NAME-->
                            <c:if test="${not empty searchByProductName}">
                                <div id="order-table-admin">
                                    <table id="orders">
                                        <div style="color: red;padding: 13px"><c:out value="${msgErrorSearching}"></c:out></div>
                                        <tr>
                                            <th>ProductID</th>
                                            <th>ProductName</th>
                                            <th>UnitPrice</th>
                                            <th>Unit</th>
                                            <th>UnitsInStock</th>
                                            <th>Category</th>
                                            <th>Discontinued</th>
                                            <th></th>
                                        </tr>
                                        <c:forEach items="${productCategory}" var="pc">
                                            <c:set var="product" value="${pc.product}"/>
                                            <c:set var="category" value="${pc.category}"/>
                                            <tr>
                                                <td><a href="<c:url value="/home">
                                                           <c:param name="req" value="detail"/>
                                                           <c:param name="productID" value="${product.productID}"/>
                                                       </c:url>">#${product.productID}</a></td>
                                                <td>${product.productName}</td>
                                                <td>${product.unitPrice}</td>
                                                <td>pieces</td>
                                                <td>${product.unitsInStock}</td>
                                                <td>${category.categoryName}</td>
                                                <td>${product.discontinued == false?0:1}</td>
                                                <td>
                                                    <a href="<c:url value="/adminCRUD">
                                                           <c:param name="req" value="Edit"/>
                                                           <c:param name="productID" value="${product.productID}"/>
                                                       </c:url>">Edit</a> &nbsp; | &nbsp; 
                                                    <a href="<c:url value="/adminCRUD">
                                                           <c:param name="req" value="Delete"/>
                                                           <c:param name="productID" value="${product.productID}"/>
                                                       </c:url>" onclick="return check()">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <div id="paging">
                                    <div class="pagination">
                                        <c:if test="${currentPage>1}">
                                            <c:url value="/Admin" var="paginationPrevious">
                                                <c:param name="currentPage" value="${currentPage-1}" />
                                                <c:param name="req" value="searchByProductName"/>
                                                <c:param name="character" value="${adminSearch}"/>
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
                                                    <c:url value="/Admin" var="pagination">
                                                        <c:param name="currentPage" value="${stepValue}" />
                                                        <c:param name="req" value="searchByProductName"/> 
                                                        <c:param name="character" value="${adminSearch}"/>
                                                    </c:url>
                                                    <a href="${pagination}">${stepValue} </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${currentPage<numberOfPage}">
                                            <c:url value="/Admin" var="paginationNext">
                                                <c:param name="currentPage" value="${currentPage+1}" />
                                                <c:param name="req" value="searchByProductName"/> 
                                                <c:param name="character" value="${adminSearch}"/>
                                            </c:url>
                                            <a href="${paginationNext}">Next</a>
                                        </c:if>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer-admin">footer</div>
        </div>
    </body>
</html>
<script>
    function check() {
        let text;
        if (confirm("Are you sure to delete this product") === false) {
            return false;
        } else {
            return true;
        }
    }
</script>
