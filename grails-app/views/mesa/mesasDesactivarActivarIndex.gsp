<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
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
                            <g:each in="${listadoMesas}" var="mesa">
                                <tr>
                                    <td>${mesa.nombre}</td>
                                    <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.DISPONIBLE}">
                                        <td><span class="label label-success">Disponible</span></td>
                                        <td>
                                            <g:link action="desactivarMesa" controller="mesa"  params="[idMesa: mesa.id]"><button type="button" class="btn btn-block btn-warning">Desactivar</button></g:link>
                                        </td>
                                    </g:if>
                                    <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.DESACTIVADA}">
                                        <td><span class="label label-warning">Desactivado</span></td>
                                        <td>
                                            <g:link action="habilitarMesa2" controller="mesa"  params="[idMesa: mesa.id, ventanaNombre:'activarDesactivar']"><button type="button" class="btn btn-block btn-success">Activar</button></g:link>
                                        </td>
                                    </g:if>

                                </tr>
                            </g:each>

                            </tbody></table>

                    </div>
                    <!-- /.box-body -->
                </div>



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