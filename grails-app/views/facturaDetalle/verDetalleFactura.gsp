<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <link rel="stylesheet " type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    %{--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--}%
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />


</head>
<body>

<div class="box">
    <div class="box-header">
        <h3 class="box-title">Detalle de los ordenes de la factura</h3>

        <div class="box-tools">
            <div class="input-group input-group-sm" style="width: 150px;">

            </div>
        </div>
    </div>
    <!-- /.box-header -->
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover">
            <tr>
                <th class="bg-info">ID</th>
                <th class="bg-info">Plato</th>
                <th class="bg-info">Precio</th>
                <th class="bg-info">Cantidad</th>
            </tr>
            <g:each in="${factura.listaFacturaDetalle}" var="detalle">
                <tr>
                    <td>${detalle.id}</td>
                    <td>${detalle.ordenDetalle.nombrePlato}</td>
                    <td>${detalle.ordenDetalle.montoNeto}</td>
                    <td>${detalle.ordenDetalle.cantidad}</td>
                </tr>
            </g:each>

        </table>
    </div>
    <!-- /.box-body -->
</div>

</body>
</html>