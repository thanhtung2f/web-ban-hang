<%@include  file="template/header.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty adminAccount}">
    <c:redirect url="/Admin"><c:param name="req" value="listProduct"/></c:redirect>
</c:if>
<c:if test="${empty cart}">
    <c:redirect url="/Order"><c:param name="order" value="cart"/></c:redirect>   
</c:if>
<div id="cart">
    <form action="<c:url value="/Order"><c:param name="order" value="order"/></c:url>" method="post">
            <div id="cart-title">
                <h3>SHOPPING CART</h3>
            </div>
        <c:set var="i" value="${-1}"/>
        <c:if test="${not empty listProductOrder}">
            <c:forEach items="${listProductOrder}" var="productOrder">
                <c:set var="i" value="${i+1}"/>
                <div id="cart-content">
                    <div class="cart-item">
                        <div class="cart-item-infor">
                            <div class="cart-item-img">
                                <img src="img/1.jpg"/>
                            </div>
                            <div class="cart-item-name">
                                <a href="<c:url value="/home">
                                       <c:param name="req" value="detail"/>
                                       <c:param name="productID" value="${productOrder.productID}"/>
                                   </c:url>">${productOrder.productName}
                                </a>
                            </div>
                            <div class="cart-item-price">${productOrder.unitPrice}</div>
                            <div class="cart-item-button">
                                <a href="<c:url value="/Order">
                                       <c:param name="order" value="remove"/>
                                       <c:param name="index" value="${i}"/>
                                   </c:url>">Remove</a>
                            </div>
                        </div>
                        <div class="cart-item-function">
                            <a href="<c:url value="/Order">
                                   <c:param name="order" value="-"/>
                                   <c:param name="productID" value="${productOrder.productID}"/>
                                   <c:param name="index" value="${i}"/>
                               </c:url>">-</a>  
                            <a href="<c:url value="/Order">
                                   <c:param name="order" value="+"/>
                                   <c:param name="productID" value="${productOrder.productID}"/>
                               </c:url>">+</a>
                            <input type="text" value="${productOrder.quantity}" disabled/>
                        </div>
                    </div>  
                </div>
            </c:forEach>   
        </c:if>
        <c:if test="${empty listProductOrder}">
            <div style="padding-left: 13px"><c:out value="There is no Order here"/></div>
        </c:if><br>
        <div id="cart-summary">
            <div id="cart-summary-content">Total amount: <span style="color:red">${totalPrice}</span></div>
        </div>
        <c:if test="${empty AccSession}">
            <div id="customer-info">
                <div id="customer-info-content">
                    <h3>CUSTOMER INFORMATION:</h3>
                    <div id="customer-info-detail">
                        <div id="customer-info-left">
                            <input type="text" placeholder="Company name *" name="txtCompanyName"/><br/>
                            <c:if test="${not empty msgCompanyName}">
                                <div style="color: red;padding-bottom:10px;"><c:out value="${msgCompanyName}"/></div>
                            </c:if>
                            <input type="text" placeholder="Contact name *" name="txtContactName"/><br/>
                            <c:if test="${not empty msgContactName}">
                                <div style="color: red;padding-bottom:10px;"><c:out value="${msgContactName}"/></div>
                            </c:if>
                        </div>
                        <div id="customer-info-right">
                            <input type="text" placeholder="Contact title *" name="txtContactTitle"/><br/>
                            <c:if test="${not empty msgContactTitle}">
                                <div style="color: red;padding-bottom:10px;"><c:out value="${msgContactTitle}"/></div>
                            </c:if>
                            <input type="text" placeholder="Address *" name="txtAddress"/><br/>
                            <c:if test="${not empty msgAddress}">
                                <div style="color: red;padding-bottom:10px;"><c:out value="${msgAddress}"/></div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${empty Fail}">
            <div style="color: green;padding-left: 13px;"><c:out value="${msg}"/></div>
        </c:if>
        <c:if test="${not empty Fail}">
            <div style="color: red;padding-left: 13px;"><c:out value="${msg}"/></div>
        </c:if>
        <div id="customer-info">
            <div id="customer-info-content">
                <h3>PAYMENT METHODS:</h3>
                <div id="customer-info-payment">
                    <div>
                        <input type="radio" name="rbPaymentMethod" checked/>
                        Payment C.O.D - Payment on delivery
                    </div>
                    <div>
                        <input type="radio" name="rbPaymentMethod" disabled/>
                        Payment via online payment gateway
                    </div>
                </div>
            </div>
        </div>
        <div id="cart-order">
            <input type="submit" value="ORDER" style="width:22%;
                   background-color: brown;
                   color: #fff;
                   margin-bottom: 15px;
                   line-height: 40px;
                   border-radius: 5px;
                   border: none;
                   cursor: pointer;" onclick="return check()"/>
        </div>
    </form>
</div>
<%@include  file="template/footer.jsp" %>
<script>
    function check() {
        let text;
        if (confirm("Are you sure to ordering") === false) {
            return false;
        } else {
            return true;
        }
    }
</script>
