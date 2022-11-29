<%@include  file="template/header.jsp" %>
<c:if test="${not empty AccSession}">
    <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect> 
</c:if>
<c:if test="${empty AccSession && empty check}">
    <c:redirect url="/SignUp"></c:redirect> 
</c:if>
<div id="form">
    <div id="form-title">
        <span><a href="<%=request.getContextPath()%>/SignUp" style="color: red;">SIGN UP</a></span>
        <span><a href="<%=request.getContextPath()%>/SignIn">SIGN IN</a></span>
    </div>
    <div id="form-content">
        <form action="SignUp" method="post">
            <c:if test="${not empty msg}">
                <div style="text-align: center; color: red;"><strong><c:out value="${msg}"></c:out> </strong></div>
            </c:if>
            <label>Company name<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtCompanyName"/><br/>
            <c:if test="${not empty msgCompanyName}">
                <span class="msg-error">Company name is required</span><br/>
            </c:if>
            <label>Contact name<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtContactName"/><br/>
            <c:if test="${not empty msgContactName}">
                <span class="msg-error">Contact name is required</span><br/>
            </c:if>
            
            <label>Contact title<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtContactTitle"/><br/>
            <c:if test="${not empty msgContactTitle}">
                <span class="msg-error">Contact title is required</span><br/>
            </c:if>
            
            <label>Address<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtAddress"/><br/>
            <c:if test="${not empty msgAddress}">
                <span class="msg-error">Address is required</span><br/>
            </c:if>
            
            <label>Email<span style="color: red;">*</span></label><br/>
            <input type="text" name="txtEmail"/><br/>
            <c:if test="${not empty msgEmail}">
                <span class="msg-error">Email is required</span><br/>
            </c:if>
            
            <label>Password<span style="color: red;">*</span></label><br/>
            <input type="password" name="txtPass"/><br/>
            <c:if test="${not empty msgPassword}">
                <span class="msg-error">Pass is required</span><br/>
            </c:if>
            
            <label>Re-Password<span style="color: red;">*</span></label><br/>
            <input type="password" name="txtRePass"/><br/>
            <c:if test="${not empty msgRePassword}">
                <span class="msg-error">Re-Password is required</span><br/>
            </c:if>
            <div></div>
            <input type="submit" value="SIGN UP" style="margin-bottom: 30px;"/>
        </form>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
