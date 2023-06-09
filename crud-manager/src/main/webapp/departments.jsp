<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="base-head.jsp" %>
</head>
<body>
    <%@ include file="modal.html" %>
    <%@ include file="nav-menu.jsp" %>
    
    <div id="container" class="container-fluid">
        <div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${message}
        </div>
        
        <div id="top" class="row">
            <div class="col-md-3">
                <h3>Departamentos</h3>
            </div>
         
            <div class="col-md-6">
                <div class="input-group h2">
                    <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar departamentos">
                    <span class="input-group-btn">
                        <button class="btn btn-danger" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
         
            <div class="col-md-3">
                <a href="/crud-manager/department/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbspAdicionar Departamento</a>
            </div>
        </div>
        
        <hr />
        
        <div id="list" class="row">
            <div class="table-responsive col-md-12">
                <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Gerente</th>
                            <th>ID da Empresa</th>
                            <th>Orçamento</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                         </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="department" items="${departments}">
                            <tr>
                                <td>${department.getNome()}</td>
                                <td>${department.getManager()}</td>
                                <td>${department.getCompanyId()}</td>
                                <td>${department.getBudget()}</td>
                                
                                
                                <td class="actions">
                                    <a class="btn btn-info btn-xs" 
                                       href="${pageContext.request.contextPath}/department/update?departmentId=${department.getId()}" >
                                       <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                
                                <td class="actions">
                                    <a class="btn btn-danger btn-xs modal-remove"
                                       department-id="${department.getId()}" 
                                       department-nome="${department.getNome()}" data-toggle="modal" 
                                       data-target="#delete-modal"  href="#">
                                       <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                 </table>
         
             </div>
        </div>
        <div id="bottom" class="row">
            <div class="col-md-12">
                <ul class="pagination">
                    <li class="disabled"><a>&lt; Anterior</a></li>
                    <li class="disabled"><a>1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
                </ul><!-- /.pagination -->
            </div>
        </div>
    </div>
    
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            // fecha o alert após 3 segundos
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);
            
            // ao clicar no delete de algum departamento, pega o nome do departamento, 
            // o id do departamento e a ação (delete) e envia para o modal 
            $(".modal-remove").click(function () {
                var departmentName = $(this).attr('department-nome');
                var departmentId = $(this).attr('department-id');
                $(".modal-body #hiddenValue").text("o departamento '" + departmentNome + "'");
                $("#id").attr("value", departmentId);
                $("#form").attr("action", "department/delete");
            })
        });
    </script>
</body>
</html>
