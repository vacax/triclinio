<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({

            });

        });
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
            <g:form action="crearNuevaCuenta">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>LISTADO DE MESAS</b></h3>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                            <tbody><tr>
                                <th>NOMBRE DE LA MESA</th>
                                <th>ESTADO</th>
                                <th>ACCIONES</th>
                            </tr>
                            <g:each in="${mesas}" var="mesa">
                                <tr>
                                    <td>${mesa.nombre}</td>
                                    <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.OCUPADA}">
                                        <td><span class="label label-danger">Ocupada</span></td>
                                    </g:if>
                                    <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.DISPONIBLE}">
                                        <td><span class="label label-success">Disponible</span></td>
                                    </g:if>
                                    <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.DESACTIVADA}">
                                        <td><span class="label label-warning">Desactivada</span></td>
                                    </g:if>
                                    <td>
                                        <g:link action="mesaHistorial" controller="mesa"  params="[idMesa: mesa.id]"><button type="button" class="btn btn-block btn-success">Ver Historial</button></g:link>
                                    </td>
                                </tr>
                            </g:each>

                            </tbody></table>

                    </div>
                    <!-- /.box-body -->
                </div>
            </g:form>
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>

<script type="text/javascript">
    $(function()
    {
        //AL CARGAR LA VENTANA
        if ($('[name="mesaId"]').is(':checked')) {
            $('#crearCuenta').prop("disabled", false);
        }

        else {
            $('#crearCuenta').prop("disabled", true);
        };

        //SI CAMBIA EL ESTADO DE CHECKBOX
        $('[name="mesaId"]').change(function()
        {
            if ($('[name="mesaId"]').is(':checked')) {
                $('#crearCuenta').prop("disabled", false);
            }
            else {
                $('#crearCuenta').prop("disabled", true);
            };

        });
    });
</script>

</body>

</html>