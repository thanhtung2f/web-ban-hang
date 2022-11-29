<%@page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8"%>
<%@include  file="template/header.jsp" %>
<c:if test="${empty AccSession}">
    <c:redirect url="/SignIn"/>
</c:if>
<c:set var="customer" value="${CustomerInfor.customer}"/>
<c:set var="account" value="${CustomerInfor.account}"/>
<div id="content-left"> 
    <h3 style="font-weight: normal;">Welcome, ${customer.contactName}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href="<c:url value="/home"><c:param name="req" value="profile"/></c:url>">
            <li>Personal information</li>
        </a>
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
    <div class="path">Personal information</b></div>
    <div class="content-main">
        <!--FORM-->
        <form id="profile-content" action="<c:url value="/Edit"/>" method="post">
            <div class="profile-content-col">
                <div>Company name: <br/>
                    <c:if test="${not empty edit}"><input type="text" value="${customer.companyName}" name="txtCompanyName"></c:if>
                    <c:if test="${empty edit}">${customer.companyName}</c:if>
                </div>
                <div>Contact name: <br/>
                    <c:if test="${not empty edit}"><input type="text" value="${customer.contactName}" name="txtContactName"></c:if>
                    <c:if test="${empty edit}">${customer.contactName}</c:if>
                </div>
            <div>
                <c:if test="${empty edit}">
                    <a href="<c:url value="Edit"/>">Edit Infor</a>
                </c:if>
                <c:if test="${not empty edit}">
                    <input type="submit" value="Edit Infor" />
                </c:if>
                <input type="submit" value="Back" name="txtBack"/>
                <div style="color: green; margin: 10px 0px"><c:out value="${UpdateSucess}"/></div>
            </div>
            </div>
            <div class="profile-content-col">
                <div>Company title: <br/>
                    <c:if test="${not empty edit}"><input type="text" value="${customer.contactTitle}" name="txtContactTitle"></c:if>
                    <c:if test="${empty edit}">${customer.contactTitle}</c:if>        
                </div>
                <div>Address: <br/>
                    <c:if test="${not empty edit}"><input type="text" value="${customer.address}" name="txtAddress" ></c:if>
                    <c:if test="${empty edit}">${customer.address}</c:if>       
                </div>
            </div>
            <div class="profile-content-col">
                <div>Email: <br/>
                <c:if test="${not empty edit}"><input type="text" value="${account.email}" name="txtEmail" readonly></c:if>
                    <c:if test="${empty edit}">${account.email}</c:if>
                </div>
            </div>
        </form>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
