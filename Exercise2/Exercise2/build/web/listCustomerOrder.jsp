<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <c:redirect url="/Admin"><c:param name="req" value="listCustomer"/></c:redirect>
            </c:if>
        </c:when>
    </c:choose>
    <div id="container">
        <div id="header">
            <a id="logo-admin" style="text-decoration: none;color: black;" href="#">
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
                <div class="path-admin">ORDERS LIST</b></div>
                <div class="content-main">
                    <div id="content-main-dashboard">
                        <div id="order-title">
                            <form action="<c:url value="/Admin"><c:param name="req" value="searchByCustomerName"/></c:url>" method="post">
                        <input type="text" name="txtSearchCustomerName" value="${charCustomerSearching}" placeholder="Input Customer's name here!" style="width: 35%"/>
                                <input type="submit" value="Search">
                            </form>
                        </div>
                        <div id="order-table">
                            <table id="orders">
                                <tr>
                                  <th>No</th>
                                  <th>CustomerID</th>
                                  <th>CompanyName</th>
                                  <th>ContactName</th>
                                  <th>ContactTitle</th>
                                  <th>Address</th>
                                  <th>Email</th>
                                </tr>
                            <c:set var="No" value="${0}"/>
                                <c:forEach items="${listCustomerInfor}" var="list">
                                    <c:set var="customer" value="${list.getCustomer()}"/>
                                    <c:set var="account" value="${list.getAccount()}"/>
                                    <tr>
                                        <c:set var="No" value="${No + 1}"/>
                                        <td>${No}</td>
                                        <td>${customer.customerID}</td>
                                        <td>${customer.companyName}</td>
                                        <td>${customer.contactName}</td>
                                        <td>${customer.contactTitle}</td>
                                        <td>${customer.address}</td>
                                        <td>${account.email}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div id="paging">
                            <div class="pagination">
                                <c:if test="${currentPage>1}">
                                    <c:url value="/Admin" var="paginationPrevious">
                                        <c:param name="currentPage" value="${currentPage-1}" />
                                        <c:param name="req" value="searchByCustomerName"/> 
                                        <c:param name="txtSearchCustomerName" value="${charCustomerSearching}"/>
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
                                                <c:param name="req" value="searchByCustomerName"/> 
                                                <c:param name="txtSearchCustomerName" value="${charCustomerSearching}"/>
                                            </c:url>
                                            <a href="${pagination}">${stepValue} </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${currentPage<numberOfPage}">
                                    <c:url value="/Admin" var="paginationNext">
                                        <c:param name="currentPage" value="${currentPage+1}" />
                                        <c:param name="req" value="searchByCustomerName"/> 
                                        <c:param name="txtSearchCustomerName" value="${charCustomerSearching}"/>
                                    </c:url>
                                    <a href="${paginationNext}">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer-admin">footer</div>
    </div>
</body>
</html>
