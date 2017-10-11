<%@ page import="com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
            %{--<g:form action="crearNuevaCuenta">--}%
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>LISTADO DE CUENTAS ABIERTAS</b></h3>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table class="table table-hover">
                            <tbody><tr>
                                <th>ATENDIDO POR:</th>
                                <th>LISTADO DE MESAS</th>
                                <th>ESTADO DE CUENTA</th>
                                <th>ACCIONES</th>

                            </tr>
                            <g:each in="${Cuenta.list()}" var="cuenta">
                                <g:if test="${cuenta.estadoCuenta.codigo==1000}">
                                    <tr>
                                        <td>
                                            ${cuenta.usuario.nombre}
                                        </td>

                                        <td>
                                            <g:each in="${cuenta.listaMesa}" var="mesa">
                                                    (${mesa.mesa.nombre})
                                            </g:each>
                                        </td>
                                        <td>
                                            <g:if test="${cuenta.estadoCuenta.codigo==1000}">
                                                <span class="label label-success">Abierta</span>
                                            </g:if>
                                        </td>
                                        <td>
                                            <g:form action="editarOrdenCuenta">
                                                <input hidden="hidden" id="idCuenta" name="idCuenta" value="${cuenta.id}">
                                                <button type="submit" class="btn btn-sm btn-success"><b>Ver Clientes de la cuenta</b></button>
                                            </g:form>
                                        </td>
                                    </tr>


                                </g:if>
                            </g:each>

                            </tbody></table>

                    </div>
                    <!-- /.box-body -->
                </div>



            %{--</g:form>--}%
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>


</body>

</html>