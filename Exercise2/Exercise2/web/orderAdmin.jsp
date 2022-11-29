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
                    <c:redirect url="/Admin"><c:param name="req" value="orderList"/></c:redirect>
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
                        <div class="path-admin">ORDERS LIST</b></div>
                        <div class="content-main">
                            <div id="content-main-dashboard">
                                <div id="order-title">
                                    <b>Filter by Order date:</b>
                                    <form action ="<c:url value="/Admin"><c:param name="req" value="FilterOrderDate"/></c:url>" method="post">
                                        From: <input type="date"  name="txtStartOrderDate" value="${StartDate}"/>
                                        To: <input type="date" name="txtEndOrderDate" value="${EndDate}"/>
                                        <input type="submit" value="Filter">
                                    </form>
                                </div>
                                <div id="order-table">
                                    <div style="color: green"><c:out value="${msg}"/></div>
                                    <table id="orders">
                                        <tr>
                                            <th>OrderID</th>
                                            <th>OrderDate</th>
                                            <th>RequiredDate</th>
                                            <th>ShippedDate</th>
                                            <th>Employee</th>
                                            <th>Customer</th>
                                            <th>Freight($)</th>
                                            <th>Status</th>
                                        </tr>
                                    <c:forEach items="${ListOrderCustomer}" var="list">
                                        <c:set var="Order" value="${list.getOrder()}"></c:set>
                                        <c:set var="Customer" value="${list.getCustomer()}"></c:set>
                                        <c:set var="Employee" value="${list.getEmployee()}"></c:set>
                                            <tr>
                                            <td><a href="<c:url value="/Admin">
                                                    <c:param name="sendOrderID" value="${Order.orderID}"/>
                                                    <c:param name="req" value="OrderDetailAdmin"/></c:url>">#${Order.orderID}</a></td>
                                            <td>${Order.orderDate}</td>
                                            <td>${Order.requiredDate}</td>
                                            <td>${Order.shippedDate}</td>
                                            <td>${Employee.firstName}</td>
                                            <td>${Customer.contactName}</td>
                                            <td>${Order.freight}</td>
                                            <c:choose>
                                                <c:when test="${Order.shippedDate != null}">
                                                    <td>
                                                        <div class="profile-order-title-right">
                                                          <span style="color: green;">Completed Order</span>
                                                        </div>  
                                                    </td>
                                                </c:when>
                                                <c:when test="${Order.status == false}">
                                                    <td>
                                                        <div class="profile-order-title-right">
                                                            <span style="color: red;">Canceled Order</span>
                                                        </div>
                                                    </td>
                                                </c:when>
                                                <c:when test="${Order.shippedDate == null}">
                                                <td>
                                                    <span>
                                                        <div class="profile-order-title-right">
                                                            <a href="#" onclick="return checkComplete()">Complete</a>
                                                            <a style="color: white;
                                                                margin-left: 5px;
                                                                padding: 5px 15px;
                                                                font-weight: bold;
                                                                text-decoration: none;
                                                                border-radius: 5px;
                                                                background-color: sienna;" 
                                                                href="<c:url value="/Admin">
                                                                 <c:param name="getOrderID" value="${Order.orderID}"/>
                                                                 <c:param name="req" value="CancelOrder"/></c:url>" onclick="return check()"
                                                                >Cancel
                                                            </a>
                                                        </div>
                                                    </span>
                                                </td>  
                                                </c:when>
                                            </c:choose>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <c:choose>
                                <c:when test="${empty OrderFilter}">
                                    <div class="pagination" >
                                        <c:if test="${currentPage>1}">
                                            <c:url value="/Admin" var="paginationPrevious">
                                                <c:param name="currentPage" value="${currentPage-1}" />
                                                <c:param name="req" value="orderList"/>
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
                                                    <c:url value="/Admin" var="pagination">
                                                        <c:param name="currentPage" value="${stepValue}" />
                                                        <c:param name="req" value="orderList"/>
                                                    </c:url>
                                                    <a href="${pagination}" >${stepValue} </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${currentPage<numberOfPage}">
                                            <c:url value="/Admin" var="paginationNext">
                                                <c:param name="currentPage" value="${currentPage+1}" />
                                                <c:param name="req" value="orderList"/>
                                                
                                            </c:url>
                                            <a href="${paginationNext}">Next</a>
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:when test="${not empty OrderFilter}">
                                    <div class="pagination" >
                                        <c:if test="${currentPage>1}">
                                            <c:url value="/Admin" var="paginationPrevious">
                                                <c:param name="currentPage" value="${currentPage-1}" />
                                                <c:param name="req" value="FilterOrderDate"/>
                                                <c:param name="txtStartOrderDate" value="${StartDate}"/>
                                                <c:param name="txtEndOrderDate" value="${EndDate}"/>
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
                                                    <c:url value="/Admin" var="pagination">
                                                        <c:param name="currentPage" value="${stepValue}" />
                                                        <c:param name="req" value="FilterOrderDate"/>
                                                        <c:param name="txtStartOrderDate" value="${StartDate}"/>
                                                        <c:param name="txtEndOrderDate" value="${EndDate}"/>
                                                    </c:url>
                                                    <a href="${pagination}" >${stepValue} </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${currentPage<numberOfPage}">
                                            <c:url value="/Admin" var="paginationNext">
                                                <c:param name="currentPage" value="${currentPage+1}" />
                                                <c:param name="req" value="FilterOrderDate"/>
                                                <c:param name="txtStartOrderDate" value="${StartDate}"/>
                                                <c:param name="txtEndOrderDate" value="${EndDate}"/>
                                            </c:url>
                                            <a href="${paginationNext}">Next</a>
                                        </c:if>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>     
            </div>
            <div id="footer-admin">footer</div>
        </div>
    </body>
</html>
<script>
    function checkComplete() {
        if (confirm("Are you sure to complete this order") === false) {
            return false;
        } else {
            return true;
        }
    }
    function check() {
        if (confirm("Are you sure to cancel this order") === false) {
            return false;
        } else {
            return true;
        }
    }
</script>