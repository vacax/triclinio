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
            $('#example').dataTable({});

        });
    </script>

</head>

<body>

<section class="content">
    <div class="row">
        <div class="row">

            <div class="box">
                <div class="box-header">
                    <h3 class="box-title"><b>Orden Detalle ${orden.id}</b></h3>

                </div>
                <!-- /.box-header -->
                <div class="box-body table-responsive no-padding">
                    %{--<button id="button">Row</button>--}%
                    <table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0"
                           width="100%">
                        <thead>
                        <th>
                            ID
                        </th>
                        <th>
                            Nombre Cliente
                        </th>
                        <th>
                            Nombre Plato
                        </th>
                        <th>
                            Precio
                        </th>
                        <th>
                            Fecha Pedida
                        </th>
                        <th>
                            Cuenta
                        </th>
                        </thead>
                        <tbody>
                        <g:each in="${orden}" var="ord">
                            <tr>
                                <td>${ord.id}</td>
                                <td>${ord.clienteCuenta.nombre}</td>
                                <td>${ord.nombrePlato}</td>
                                <td>${ord.precio}</td>
                                <td>${ord.dateCreated}</td>
                                <td>${ord.clienteCuenta.cuenta.id}</td>

                            </tr>
                        </g:each>

                        </tbody>

                    </table>
                    <g:link action="eliminarOrdenDetalle" controller="cuenta" params="[idOrden: orden.id]"><button type="button" id="eliminarOrden" class="btn btn-success ">Eliminar Orden</button></g:link>

</body>

</html>