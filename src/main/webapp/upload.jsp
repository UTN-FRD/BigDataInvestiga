<%@page import="java.util.List"%>
<%@page import="utn.frd.bigdatainvestiga.entity.UserFile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cargar Archivo</title>
        <link href="Styles/BootstrapCss/bootstrap.css" rel="stylesheet" />
        <link href="Styles/BootstrapCss/bootstrap-select.css" rel="stylesheet" />
        <link href="Styles/Site.css" rel="stylesheet" />
        <link href="Styles/BootstrapCss/datepicker.css" rel="stylesheet" />
        <link href="Styles/jquery-ui.css" rel="stylesheet" />
        <link href="Styles/jquery-ui.theme.css" rel="stylesheet" />
        <link href="Styles/jquery-ui.structure.css" rel="stylesheet" />
        <link href="Styles/shCore.css" rel="stylesheet" />
        <link href="Styles/demo.css" rel="stylesheet" />
        <link href="Styles/ColorPicker/bootstrap-colorpicker.css" rel="stylesheet" />
        <link href="Styles/DataTable/jquery.dataTables.min.css" rel="stylesheet" />
        <link href="Styles/BootstrapCss/prettify.css" rel="stylesheet" />

        <script src="Scripts/config.js"></script>
        <script src="Scripts/jquery-2.1.1.js"></script>
        <script src="Scripts/Bootstrap/bootstrap-datepicker.js"></script>
        <script src="Scripts/Bootstrap/bootstrap.min.js"></script>
        <script src="Scripts/Bootstrap/bootstrap-select.js"></script>
        <script src="Scripts/shCore.js"></script>
        <script src="Scripts/demo.js"></script>
        <script src="Scripts/DataTable/jquery.dataTables.js"></script>
        <script src="Scripts/AjaxFileUploader/ajaxfileupload.js"></script>
        <script src="Scripts/jquery.validate.js"></script>
        <script src="Scripts/JqueryLoading/loadingoverlay.js"></script>
        <script src="Scripts/Numeric/jquery.numeric.js"></script>
        <script src="Scripts/ColorPicker/bootstrap-colorpicker.js"></script>
        <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <script src="Scripts/jquery-ui.js"></script>
        <script src="Scripts/Bootstrap/jquery.bootstrap.wizard.js"></script>
        <script src="Scripts/Bootstrap/prettify.js"></script>
        <script src="Scripts/Bootstrap/bootstrap-datepicker.js"></script>
        
        <script>
        
        $( document ).ready(function() {
            $.ajax({
                url: BASEURL+"archivos"+ENDURL
            }).done(function(data){
                for(var elem in data){
                    var fileInfo = data[elem];
                    $("#rest-files>tbody").append('<tr>'+
                                '<td>'+fileInfo.fecha+'</td>'+
                                '<td>'+fileInfo.nombre+'</td>'+
                                '<td>'+fileInfo.id_investigacion+'</td>'+
                                '<td style="text-align:right;">'+
                                    '<a href="filter#'+fileInfo.id_investigacion+'" class="btn btn-default glyphicon glyphicon-filter"></a>'+
                                '</td>'+
                            '</tr>');
                }
            });
        });
        </script>
        

    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a id="logoInvestiga" href="javascript:void(0)">
                        <img src="Content/Images/AttachFileHandler.png" width="100%" height="100%" />
                    </a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#" data-toggle="modal" data-target="#myModal">Subir Archivo</a>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"> Usuario: <label for="testutnfrd"><%= session.getAttribute("userName") %></label><span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="logout">Log-Out</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">Versi&oacute;n <span class="sr-only">TEST</span></a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h4>Archivos Subidos</h4>
                    <div>
                        <h2>desde Firebase</h2>
                        <table class="table" id="rest-files">
                            <thead>
                                <tr>
                                    <th>Fecha Subido</th>
                                    <th>Nombre Archivo</th>
                                    <th>ID Investigaci&oacute;n</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        
                    </div>
                    <div id="result" class="hide">
                        <table class="table" id="docs">
                            <thead>
                                <tr>
                                    <th>Fecha Subido</th>
                                    <th>Nombre Archivo</th>
                                    <th>ID Investigaci&oacute;n</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                            <% for(UserFile uf : (List<UserFile>) request.getAttribute("files")){%>
                            <tr>
                                <td><%= uf.getFechaSubido() %></td>
                                <td><%= uf.getNombreArchivo() %></td>
                                <td><%= uf.getIdInvestigacion() %></td>
                                <td style="text-align:right;">
                                    <a href="remove?idInvestigacion=<%= uf.getIdInvestigacion() %>" class="btn btn-danger glyphicon glyphicon-remove"></a>
                                    <a href="filter#<%= uf.getIdInvestigacion() %>" class="btn btn-default glyphicon glyphicon-filter"></a>
                                </td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-12">
                    <nav aria-label="Page navigation">
                        <ul class="pagination" id="pager"></ul>
                    </nav>
                </div>
            </div>

        </div>

        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">SUBIR ARCHIVO</h4>
              </div>
              <div class="modal-body">
                <form action="upload" method="post" enctype="multipart/form-data" id="upload_form">
                    <div class="form-group">
                        <label>ID_Investigacion:</label>
                        <input type="text" name="idInvestigacion" value="1" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Archivo:</label>
                        <input type="file" name="file" multiple="true" size="50" class="form-control" />
                    </div>
                </form>
                <div id="tooTimeAlert" class="alert alert-info" style="display:none" role="alert">El proceso puede demorar varios minutos</div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                <input id="uploadBtn" type="submit" class="btn btn-primary" value="Subir Archivos" />
                <script>
                    $("#uploadBtn").click(function(){
                        $("#tooTimeAlert").show();
                        $("#uploadBtn").addClass("loader");
                        $("#uploadBtn").prop( "disabled", true );
                        $("#uploadBtn").css("background-repeat",  "no-repeat");
                        $("#uploadBtn").css("padding-left", "32px");
                        $("#upload_form").submit();
                    });
                    $('#myModal').on('hidden.bs.modal', function (e) {
                        $("#tooTimeAlert").hide();
                        $("#uploadBtn").removeClass("loader");
                        $("#uploadBtn").prop( "disabled", false );
                        $("#uploadBtn").css("padding-left", "12px");
                    });
                </script>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>