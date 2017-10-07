<%@ page import="com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
            <div    class="col-xs-8">
            <g:form action="crearNuevaCuenta">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">LISTADO DE MESAS</h3>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table class="table table-hover">
                            <tbody><tr>
                                <th>MARQUE</th>
                                <th>NOMBRE DE LA MESA</th>
                                <th>ESTADO</th>
                            </tr>
                            <g:each in="${com.triclinio.domains.restaurante.Mesa.findAll()}" var="mesa">
                                <tr>
                                    <td>
                                        <span>
                                            <g:if test="${mesa.estadoMesa.codigo == 1000}">
                                                <input id="mesaId" name="mesaId" type="checkbox" value="${mesa.id}" disabled="disabled"/>
                                            </g:if>
                                            <g:if test="${mesa.estadoMesa.codigo == 1001}">
                                                <input id="mesaId" name="mesaId" value="${mesa.id}" type="checkbox"/>
                                            </g:if>

                                        </span>
                                    </td>
                                    <td>${mesa.nombre}</td>
                                    <g:if test="${mesa.estadoMesa.codigo == 1000}">
                                        <td><span class="label label-warning">Ocupada</span></td>
                                    </g:if>
                                    <g:if test="${mesa.estadoMesa.codigo == 1001}">
                                        <td><span class="label label-success">Disponible</span></td>
                                    </g:if>
                                </tr>
                            </g:each>

                            </tbody></table>

                    </div>
                    <!-- /.box-body -->
                </div>
                <button type="summit" class="btn btn-success btn-block">Crear cuenta</button>


            </g:form>
                <!-- /.box -->
            </div>
        </div>
    </div>
    <!-- /.row -->

</section>

</body>

</html>