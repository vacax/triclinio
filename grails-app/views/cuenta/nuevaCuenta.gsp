<%@ page import="com.triclinio.domains.restaurante.Mesa; com.triclinio.domains.restaurante.EstadoMesa; com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/iCheck/1.0.2/icheck.js"></script>
</head>

<body>

<section class="content">
    <div class="row">
        <div class="row">
            <div class="box">
                <g:form action="crearNuevaCuenta" method="get" useToken="true">
                    <input id="mesaId" name="mesaId" value="0" hidden/>
                    <input type="submit" class="btn btn-warning" value="Take Out" style="width: 100%;">
                </g:form>
            </div>
            <hr>

            <g:form action="crearNuevaCuenta" method="get" useToken="true">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>LISTADO DE MESAS DISPONIBLES</b></h3>
                    </div>

                    <div class="box-body table-responsive no-padding">
                        <table class="table table-hover">
                            <tbody><tr>
                                <th>MARQUE</th>
                                <th>NOMBRE DE LA MESA</th>
                            </tr>
                            <g:each in="${mesas}" var="mesa">
                                <g:if test="${mesa.numeroMesa != 0}">
                                    <tr>
                                        <td>
                                            <span>
                                                <g:if test="${mesa.numeroMesa != 0}">
                                                    <input class="myCheckBox" name="mesaId" value="${mesa.numeroMesa}"
                                                           type="checkbox"/>
                                                </g:if>
                                            </span>
                                        </td>
                                        <g:if test="${mesa.numeroMesa != 0}">
                                            <td>${mesa.nombre}</td>
                                        </g:if>
                                    </tr>
                                </g:if>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <g:link action="cuentasAbiertas" controller="cuenta">
                    <button type="button" class="btn btn-danger btn-block">Cancelar</button>
                </g:link>
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

    $(document).ready(function () {
        $('.myCheckBox').iCheck();
    });

    $(function () {
        //AL CARGAR LA VENTANA
        if ($('[name="mesaId"]').is(':checked')) {
            $('#crearCuenta').prop("disabled", false);
        }
        else {
            $('#crearCuenta').prop("disabled", true);
        }

        //SI CAMBIA EL ESTADO DE CHECKBOX
        $('[name="mesaId"]').change(function () {
            if ($('[name="mesaId"]').is(':checked')) {
                $('#crearCuenta').prop("disabled", false);
            }
            else {
                $('#crearCuenta').prop("disabled", true);
            }
        });
    });
</script>

</body>

</html>