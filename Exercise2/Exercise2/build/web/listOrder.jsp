<%@include  file="template/header.jsp" %>
<%@page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8"%>
<c:set var="customer" value="${CustomerInfor.customer}"/>
<c:set var="account" value="${CustomerInfor.account}"/>
<c:choose>
    <c:when test="${empty AccSession}">
        <c:redirect url="/SignIn"/>
    </c:when>
    <c:when test="${empty check}">
        <c:redirect url="/ShowOrders">
            <c:param name="customerID" value="${customer.customerID}"/>
            <c:param name="req" value="showAllOrder"/>
        </c:redirect>
    </c:when>
</c:choose>
<div id="content-left">
    <h3 style="font-weight: normal;">Welcome, ${customer.contactName}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href="<c:url value="/home"><c:param name="req" value="profile"/></c:url>"><li>Personal information</li></a>
        </ul>
        <h3>My order</h3>
        <ul>
            <a href="<c:url value="/ShowOrders">
               <c:param name="customerID" value="${customer.customerID}"/>
               <c:param name="req" value="showAllOrder"/>
           </c:url>"><li>All orders</li></a>
        <a href="<c:url value="/ShowOrders">
               <c:param name="customerID" value="${customer.customerID}"/>
               <c:param name="req" value="showCancelOrders"/>
           </c:url>"><li>Canceled order</li></a>
    </ul>
</div>
<div id="content-right">
<c:choose>
    <c:when test="${empty showCancelOrder}">
        <div class="path">LIST ORDERS</b></div>
    </c:when>
    <c:when test="${not empty showCancelOrder}">
        <div class="path">LIST CANCEL ORDERS</b></div>
    </c:when>
</c:choose>
    
    <div class="content-main">
        <div id="profile-content-order">
                <c:if test="${empty listOrderID}">
                    <div class="profile-order-content">Your order have no items!</div>
                </c:if>
                <c:if test="${not empty listOrderID}">
                    <c:forEach items="${listOrderID}" var="o">
                            <div class="profile-order-title">
                                <div class="profile-order-title-left">
                                    <div>Order creation date: ${o.orderDate}</div>
                                    <div>Order: #${o.orderID}</div>
                                </div>
                                    <c:choose>
                                        <c:when test="${o.shippedDate != null}">
                                            <div class="profile-order-title-right">
                                                <span style="color: green;">Completed</span>
                                            </div>
                                        </c:when>
                                        <c:when test="${o.status == false}">
                                            <div class="profile-order-title-right">
                                                <span style="color: red;">Canceled</span>
                                            </div>
                                        </c:when>
                                        <c:when test="${o.shippedDate == null}">
                                            <div class="profile-order-title-right">
                                                <span style="color: grey;">Pending</span>
                                                <a style="color: white;
                                                    margin-left: 5px;
                                                    padding: 5px 15px;
                                                    border-radius: 5px;
                                                    background-color: sienna;" 
                                                    href="<c:url value="/ShowOrders">
                                                        <c:param name="customerID" value="${customer.customerID}"/>
                                                        <c:param name="getOrderID" value="${o.orderID}"/>
                                                        <c:param name="req" value="CancelOrder"/></c:url>" onclick="return check()">
                                                        <span>Cancel</span>
                                                </a>
                                            </div>
                                        </c:when>
                                    </c:choose>
                            </div>
                            <c:forEach items="${listOrder}" var="list">
                                <c:set var="order" value="${list.getOrder()}"/>
                                <c:set var="productOrder" value="${list.getProductOrder()}"/>

                                <c:if test="${o.orderID == order.orderID}">
                                    <div class="profile-order-content">
                                        <div class="profile-order-content-col1">
                                            <a href="<c:url value="/home">
                                                   <c:param name="req" value="detail"/>
                                                   <c:param name="productID" value="${productOrder.productID}"/>
                                               </c:url>"><img src="img/2.jpg" width="100%"/></a>
                                        </div>
                                        <div class="profile-order-content-col2">${productOrder.productName}</div>
                                        <div class="profile-order-content-col3">Quantity: ${productOrder.quantity}</div>
                                        <div class="profile-order-content-col4">${productOrder.unitPrice} $</div>
                                    </div>
                                </c:if>
                            </c:forEach>
                    </c:forEach>
                </c:if>
        </div>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
<script>
    function check() {
        let text;
        if (confirm("Are you sure to delete this order") === false) {
            return false;
        } else {
            return true;
        }
    }
</script>
