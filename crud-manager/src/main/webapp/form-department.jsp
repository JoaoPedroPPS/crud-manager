<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="base-head.jsp" %>
</head>
<body>
    <%@ include file="nav-menu.jsp" %>
    
    <div id="container" class="container-fluid">
        <h3 class="page-header">${not empty department ? 'Editar' : 'Adicionar'} Departamento</h3>
        
        <form action="${pageContext.request.contextPath}/department/${action}" method="POST">
            <input type="hidden" value="${department.getId()}" name="departmentId">
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="name">Nome</label>
                    <input type="text" class="form-control" id="name" name="name" 
                           autofocus="autofocus" placeholder="Nome do Departamento" 
                           required 
                           oninvalid="this.setCustomValidity('Por favor, informe o nome do departamento.')"
                           oninput="setCustomValidity('')" />
                </div>
                
                <div class="form-group col-md-6">
                    <label for="manager">Gerente</label>
                    <input type="text" class="form-control" id="manager" name="manager" 
                           autofocus="autofocus" placeholder="Nome do Gerente" 
                           required 
                           oninvalid="this.setCustomValidity('Por favor, informe o nome do gerente.')"
                           oninput="setCustomValidity('')" />
                </div>      
                
                <div class="form-group col-md-6">
                    <label for="budget">Orçamento</label>
                    <input type="text" class="form-control" id="budget" name="budget" 
                           autofocus="autofocus" placeholder="Orçamento do setor" 
                           required 
                           oninvalid="this.setCustomValidity('Por favor, informe o orçamento para este setor.')"
                           oninput="setCustomValidity('')" />
                </div>  
                
                <div class="form-group col-md-4">
						<label for="company">Empresa</label>
						<select id="company" class="form-control selectpicker" name="company" 
							    required oninvalid="this.setCustomValidity('Por favor, informe a empresa.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione uma empresa</option>
						  <c:forEach var="company" items="${companies}">
						  	<option value="${company.getId()}"  ${ceo.getCompany().getId() == company.getId() ? "selected" : ""}>
						  		${company.getName()}
						  	</option>	
						  </c:forEach>
						</select>
				</div>
                  
                
                
                <!--
                <div class="form-group col-md-6">
                    <label for="company">Empresa</label>
                    <input type="text" class="form-control" id="company" name="company" 
                           autofocus="autofocus" placeholder="Id da empresa" 
                           required 
                           oninvalid="this.setCustomValidity('Por favor, informe o id da empresa.')"
                           oninput="setCustomValidity('')" />
                </div> 
                
                 <div class="form-group col-md-4">
                    <label for="company">Empresa prestadora</label>
                     <select id="company"
                        class="form-control selectpicker" name="company" required
                        oninvalid="this.setCustomValidity('Por favor, informe a empresa prestadora.')"
                        oninput="setCustomValidity('')">
                        <option value="" ${not empty departments ? "" : 'selected'}>Selecione uma empresa
                        </option>
                        <c:forEach var="company" items="${company}">
                            <option value="${company.getId()}" ${departments.getCompany().getId() == company.getId() ? 'selected' : ''}>
                                ${company.getName()}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                
                
                 -->
                
               
               
                
            </div>
            
            
            
             
            
            
            
            
            <hr />
            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/departments" class="btn btn-default">Cancelar</a>
                    <button type="submit" class="btn btn-primary">${not empty department ? "Alterar Departamento" : "Cadastrar Departamento"}</button>
                </div>
            </div>
        </form>
        
    </div>

</body>
</html>
