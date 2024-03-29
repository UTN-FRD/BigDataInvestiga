<%-- 
    Document   : index
    Created on : 26-jul-2018, 1:53:23
    Author     : Sergio

    Framework: https://github.com/evolvingweb/ajax-solr
               https://www.highcharts.com/demo/arearange
--%>
<%@page import="utn.frd.bigdatainvestiga.util.PropertyUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- https://github.com/evolvingweb/ajax-solr/wiki -->
<html>
    <head>
        <meta charset="utf-8">
        <title>Prefiltro de Datos</title>

        <link rel="stylesheet" href="css/ibd.css">
        <link href="../Styles/Site.css" rel="stylesheet" />
        <link href="../Styles/jquery-ui.css" rel="stylesheet" />
        <link href="../Styles/jquery-ui.theme.css" rel="stylesheet" />
        <link href="../Styles/BootstrapCss/bootstrap.css" rel="stylesheet" />

        <script src="../Scripts/config.js"></script>
        <script src="../Scripts/jquery-2.1.1.js"></script>
        <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <!--script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script-->
        <!--script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.24/jquery-ui.min.js"></script-->
        <script src="core/Core.js"></script>
        <script src="core/AbstractManager.js"></script>
        <script src="managers/Manager.jquery.js"></script>
        <script src="core/Parameter.js"></script>
        <script src="core/ParameterStore.js"></script>
        <script src="core/AbstractWidget.js"></script>
        <script src="widgets/ResultWidget.js"></script>
        <script src="widgets/jquery/PagerWidget.js"></script>
        <script src="core/AbstractFacetWidget.js"></script>
        <script src="widgets/TagcloudWidget.js"></script>
        <script src="widgets/TipoComunicacionWidget.js"></script>
        <script src="widgets/CurrentSearchWidget.js"></script>
        <script src="core/AbstractTextWidget.js"></script>
        <script src="widgets/AutocompleteWidget.js"></script>
        <script src="widgets/CountryCodeWidget.js"></script>
        <script src="widgets/CalendarWidget.js"></script>
        <script src="widgets/HighchartsWidget.js"></script>
        <script src="highcharts/highstock.js"></script>
        <script src="highcharts/modules/exporting.js"></script>
        <script src="../Scripts/Bootstrap/bootstrap.min.js"></script>

        <script src="js/ibd.js"></script>

        <script type="text/javascript">
            var stockChart;
            var SOLR_URL = '<%= PropertyUtils.get("solr.url") %>/';

            $(function () {
                $('#idInvestigacion').html(window.location.hash.substr(1));
                $('#idInvestigacionInput').val(window.location.hash.substr(1));
            });
        </script>

    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a id="logoInvestiga" href="javascript:void(0)">
                        <img src="../Content/Images/AttachFileHandler.png" width="100%" height="100%" />
                    </a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="../upload">Ver Archivos</a>
                        </li>
                        <li>
                            <a href="#" data-toggle="modal" data-target="#uploadFileModal">Subir Archivo</a>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"> Usuario: <label for="testutnfrd"><%= session.getAttribute("userName")%></label><span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="../logout">Log-Out</a></li>
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
            <h4 class="tituloPasoAsistente">PREFILTRO DE DATOS - Investigaci&oacute;n: <span id="idInvestigacion"></span></h4>
            
        <% if( request.getSession().getAttribute("uploadResult")!=null ){ %>
            
<!--            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
-->            
            <div class="modal show" id="uploadResultModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#uploadResultModal').removeClass('show');"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Resultados</h4>
                      </div>
                      <div class="modal-body">
                        <%= (String)request.getSession().getAttribute("uploadResult") %>
                      </div>
                  </div>
                </div>
            </div>
            <script>
                
            </script>
            
            <% request.getSession().removeAttribute("uploadResult"); 
        }%>
            
            <div class="row">
                <div class="col-md-10">
                    <div id="highcharts-container" style="height: 300px; min-width: 310px"></div>
                </div>
                <div class="col-md-2">
                    <button id="removeDateFilter" class="btn" style="margin-left:0;display:none">Eliminar Filtro de Fechas</button>
                    <button id="applyFilter" class="btn" style="margin-left:0">Aplicar Filtro de Fechas</button>
                    <h4>Tipo</h4>
                    <ul id="tipo-comunicacion" class="nav nav-pills" role="tablist">
                        <li role="presentation" class="active"><a href="#">Todos</a></li>
                        <li role="presentation"><a href="#">Llamadas <span id="cantidad-Llamada" class="badge"></span></a></li>
                        <li role="presentation"><a href="#">Mensajes <span id="cantidad-Mensaje" class="badge"></span></a></li>
                    </ul>
                    <div class="hide">
                        <h4>Filtros Aplicados</h4>
                        <ul id="selection"></ul>
                        <div class="hide">
                            <h2>Buscar</h2>
                            <span id="search_help">(presionar ESC para cerrar las sugerencias)</span>
                            <input class="form-control" type="text" id="query" name="query" autocomplete="off">
                            <ul id="search">
                            </ul>
                        </div>
                    </div>			
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h4>Nro Origen</h4>
                    <div class="tagcloud col-md-12" id="Nro_Origen"></div>
                </div>
                <div class="col-md-6">
                    <h4>Nro Destino</h4>
                    <div class="tagcloud col-md-12" id="Nro_Destino"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h4>Celda Origen</h4>
                    <div class="tagcloud col-md-12" id="Celda_Origen"></div>
                </div>
                <div class="col-md-6">
                    <h4>Duraci&oacute;n</h4>
                    <div class="tagcloud col-md-12" id="Duracion"></div>
                </div>
                <div class="col-md-3 hide">
                    <h4>Fecha</h4>
                    <div id="calendar"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h4>Resultado - <div id="pager-header"></div></h4>
                    <div id="result">
                        <table class="table" id="docs"></table>
                    </div>
                </div>
                <div class="col-md-12">
                    <nav aria-label="Page navigation">
                        <ul class="pagination" id="pager"></ul>
                    </nav>
                </div>
                <div class="col-md-12">
                    <a id="download-results" class="btn btn-success pull-right" target="_blank" href="#">Descargar</a>
                </div>
            </div>
        </div>
        <br><br><br><br>

        <div class="modal fade" id="uploadFileModal" tabindex="-1" role="dialog" aria-labelledby="uploadFileModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="uploadFileModalLabel">SUBIR ARCHIVO</h4>
                    </div>
                    <div class="modal-body">
                        <form action="../upload" method="post" enctype="multipart/form-data" id="upload_form">
                            <div class="form-group">
                                <label>ID_Investigacion:</label>
                                <input type="text" name="idInvestigacion" id="idInvestigacionInput" class="form-control" />
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
                            $("#uploadBtn").click(function () {
                                $("#tooTimeAlert").show();
                                $("#uploadBtn").addClass("loader");
                                $("#uploadBtn").prop("disabled", true);
                                $("#uploadBtn").css("background-repeat", "no-repeat");
                                $("#uploadBtn").css("padding-left", "32px");
                                $("#upload_form").submit();
                            });
                            $('#uploadFileModal').on('hidden.bs.modal', function (e) {
                                $("#tooTimeAlert").hide();
                                $("#uploadBtn").removeClass("loader");
                                $("#uploadBtn").prop("disabled", false);
                                $("#uploadBtn").css("padding-left", "12px");
                            });
                        </script>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
