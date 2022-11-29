<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include  file="template/header.jsp" %>
<div id="form">
    <h3 style="padding: 20px;">Change password</h3>
    <div style="padding: 0px 20px 10px;">
        Please change your password here!
    </div>
    <div id="form-content">
        <c:if test="${empty sendEmailSuccessfully}">
            <c:redirect url="/ForgotPass"/>
        </c:if>
        <form action="<c:url value="/ForgotPass">
                  <c:param name="EmailRequest" value="${EmailRequest}"/>
                  <c:param name="checkCode" value="${ForgorPassWordRequest}"/>
                  <c:param name="req" value="changePassword"/></c:url>" method="post">
            <label>Enter your current password<span style="color: red;">*</span></label><br/>
            <input type="password" name="txtCode"/><br/>
            <div style="text-align: left; margin: 5px 0px; color: #ff3333">
                <c:if test="${not empty ErrorCode}"><c:out value="${ErrorCode}"/></c:if>
            </div>
            <label>Enter your new password<span style="color: red;">*</span></label><br/>
            <input type="password" name="txtNewPass"/><br/>
            <div style="text-align: left; margin: 5px 0px; color: #ff3333">
                <c:if test="${not empty ErrorNewPassword}"><c:out value="${ErrorNewPassword}"/></c:if>
            </div>
            <label>Enter your new password again<span style="color: red;">*</span></label><br/>
            <input type="password" name="txt2ndNewPass"/><br/>
            <div style="text-align: left; margin: 5px 0px; color: #ff3333">
                <c:if test="${not empty Error2ndNewPassword}"><c:out value="${Error2ndNewPassword}"/></c:if>
            </div>
            <input type="submit" value="CHANGE PASSWORD" style="margin-bottom: 30px;" name="txtChangePassword"/><br/>
            <input type="submit" value="BACK" style="margin-bottom: 30px;" name="txtBack"/><br/>
        </form>
    </div>
</div>
<%@include  file="template/footer.jsp" %>
