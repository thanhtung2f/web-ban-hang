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
                    <div class="path-admin">ORDER DETAIL</b></div>
                    <div class="content-main">
                        <div id="content-main-dashboard">
                            <div>
                            <c:forEach begin="0" items="${listOrderbyOrderID}" var="o" end="0">
                                <c:set var="order" value="${o.getOrder()}"/>
                                    <div class="profile-order-title">
                                        <div class="profile-order-title-left">
                                            <div>OrderID: #${order.orderID}</div>
                                            <div>Order creation date: ${order.orderDate}</div>
                                        </div>
                                        <c:choose>
                                            <c:when test="${order.shippedDate != null}">
                                                <div class="profile-order-title-right">
                                                    <span style="color: green;">Completed</span>
                                                </div>
                                            </c:when>
                                            <c:when test="${order.status == false}">
                                                <div class="profile-order-title-right">
                                                    <span style="color: red;">Canceled</span>
                                                </div>
                                            </c:when>
                                            <c:when test="${order.shippedDate == null}">
                                                <div class="profile-order-title-right">
                                                    <span style="color: grey;">Pending</span>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                            </c:forEach>
                            <c:forEach items="${listOrderbyOrderID}" var="o">
                                <c:set var="product" value="${o.getProductOrder()}"/>
                                <div class="profile-order-content" style="background-color: white;">
                                    <div class="profile-order-content-col1">
                                        <a href="<c:url value="/home">
                                                   <c:param name="req" value="detail"/>
                                                   <c:param name="productID" value="${product.productID}"/>
                                               </c:url>"><img src="img/2.jpg" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${product.productName}</div>
                                    <div class="profile-order-content-col3">Quantity: ${product.quantity}</div>
                                    <div class="profile-order-content-col4">${product.unitPrice} $</div>
                                </div>
                            </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>     
            </div>
            <div id="footer-admin">footer</div>
        </div>
    </body>
</html>