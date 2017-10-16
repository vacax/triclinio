<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>

    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({

            });

        });
    </script>
    %{--<title>Bienvenido...</title>--}%

    %{--<asset:link rel="icon" href="favicon.ico" type="image/x-ico" />--}%
    %{--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>--}%

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
                                <th>ACCION</th>
                            </tr>
                            <g:each in="${listadoMesas.mesa}" var="mesa">
                                <g:if test="${mesa.estadoMesa.codigo == com.triclinio.domains.restaurante.EstadoMesa.getOCUPADA()}">
                                    <tr>
                                        <td>${mesa.id}</td>
                                        <td>${mesa.nombre}</td>
                                        <td><span class="label label-warning">Ocupada</span></td>
                                        <td>
                                            <g:link action="procesarSacarMesaCuenta" controller="mesa"  params="[idMesa: mesa.id,idCuenta: cuenta.id]"><button type="button" class="btn btn-block btn-success">Desocupar</button></g:link>
                                        </td>
                                    </tr>
                                </g:if>
                            </g:each>

                            </tbody>
                        </table>

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
</body>

</html>





