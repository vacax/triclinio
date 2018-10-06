<%@ page import="com.triclinio.domains.restaurante.Mesa; com.triclinio.domains.restaurante.EstadoMesa; com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

</head>

<body>

<section class="content">
    <div class="row">
        <div class="row">
            %{--<div    class="col-xs-8">--}%

            <div class="box">
                <g:form action="crearNuevaCuenta" method="get" useToken="true">
                    <input id="mesa" name="mesaId" value="44" hidden/>
                    <input type="submit" class="btn btn-warning" value="Take Out" style="width: 100%;">
                </g:form>
            </div>
            <hr>

            <g:form action="crearNuevaCuenta" method="get" useToken="true">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>LISTADO DE MESAS DISPONIBLES</b></h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table class="table table-hover">
                            <tbody><tr>
                                <th>MARQUE</th>
                                <th>NOMBRE DE LA MESA</th>
                                <th>ACCION</th>
                            </tr>
                            <g:each in="${mesas}" var="mesa">
                                <g:if test="${mesa.numeroMesa != 0}">
                                    <tr>
                                        <td>
                                            <span>
                                                <g:if test="${mesa.numeroMesa != 0}">
                                                    <input class="myCheckBox" name="mesaId" value="${mesa.id}"
                                                           type="checkbox"/>
                                                </g:if>
                                            </span>
                                        </td>
                                        <g:if test="${mesa.numeroMesa != 0}">
                                            <td>${mesa.nombre}</td>
                                            <g:if test="${mesa.estadoMesa.codigo == EstadoMesa.DISPONIBLE}">
                                                <td><span class="label label-success">Disponible</span></td>
                                            </g:if>
                                        </g:if>
                                    </tr>
                                </g:if>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                </div>
                <g:link action="cuentasAbiertas" controller="cuenta"><button type="button"
                                                                             class="btn btn-danger btn-block">Cancelar</button></g:link>
                <button id="crearCuenta" name="crearCuenta" type="submit"
                        class="btn btn-success btn-block">Crear cuenta</button>

            </g:form>
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>

<script type="text/javascript">
    $(function () {
        //AL CARGAR LA VENTANA
        if ($('[name="mesaId"]').is(':checked')) {
            $('#crearCuenta').prop("disabled", false);
        }

        else {
            $('#crearCuenta').prop("disabled", true);
        }
        ;

        //SI CAMBIA EL ESTADO DE CHECKBOX
        $('[name="mesaId"]').change(function () {
            if ($('[name="mesaId"]').is(':checked')) {
                $('#crearCuenta').prop("disabled", false);
            }
            else {
                $('#crearCuenta').prop("disabled", true);
            }
            ;

        });
    });
</script>

</body>

</html>