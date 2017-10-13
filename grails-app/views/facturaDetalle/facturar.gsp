<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Factura</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="../../bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../../bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="../../bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../../dist/css/AdminLTE.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

    <!-- Google Font -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body>

<div class="wrapper">
    <!-- Main content -->
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i> Triclinio

                </h2>
            </div>
        </div>
        <div style="margin-bottom: 2%" class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                Usuario
                <address>
                    <strong>Triclinio</strong><br>
                </address>
            </div>

            <div class="col-sm-4 invoice-col">
                Cliente
                <address>
                    <strong>${factura.cliente.nombre}</strong><br>
                </address>
            </div>

    <div class="col-sm-4 invoice-col">
        <b>Factura #${factura.id}</b><br>
        <b>Fecha creacion:</b> ${factura.dateCreated}<br>

    </div>
        </div>

        <div class="row">
            <div class="col-xs-12 table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th class="bg-info">#</th>
                        <th class="bg-info">Comida</th>
                        <th class="bg-info">Cantidad</th>
                        <th class="bg-info">Precio/Unidad</th>
                        <th class="bg-info">Importe</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                    <g:each in="${factura.listaFacturaDetalle}" var="fac">
                        <tr>
                            <td>${fac.id}</td>
                            <td>${fac.ordenDetalle.nombrePlato}</td>
                            <td>${fac.ordenDetalle.cantidad}</td>
                            <td>${fac.ordenDetalle.precio}</td>
                            <td>${fac.ordenDetalle.importe}</td>
                        </tr>
                    </g:each>
                    </tr>

                    <tr>
                        <th class="bg-info">#</th>
                        <th class="bg-info">Comida</th>
                        <th class="bg-info">Cantidad</th>
                        <th class="bg-info">Precio/Unidad</th>
                        <th class="bg-info">Importe</th>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>

    <div class="row">
    <h2 style="margin-left: 2%; margin-top: 2%">Montos Totales</h2>
        
    <div class="col-xs-6">

    <div class="table-responsive">

        <table class="table">
            <tr>
                <th style="width:50%">Monto Bruto:</th>
                <td>${factura.montoBruto}</td>
            </tr>
            <tr>
                <th>Impuesto(${factura.porcientoImpuesto})</th>
                <td>${factura.montoImpuesto}</td>
            </tr>
        </table>
    </div>

            </div>
            <!-- /.col -->
            <div class="col-xs-6">

                <div class="table-responsive">
                    <table class="table">
                        <tr>
                            <th>Descuento(${factura.porcientoDescuento})</th>
                            <td>${factura.montoDescuento}</td>
                        </tr>
                        <tr>
                            <th>Monto Total</th>
                            <td>${factura.montoNeto}</td>
                        </tr>
                    </table>
                </div>
            </div>

        </div>

    </section>

</div>

<section class="content">
    <div class="row">
        <div class="row">
            <div    class="col-xs-8">
                <g:form action="imprimir" id="${factura.id}" value="id=${factura.id}">
                    <button type="submit"  class="btn btn-primary btn-lg">Imprimir Factura</button>
                </g:form>
            <!-- /.box -->
            </div>
        </div>
    </div>
    <!-- /.row -->
</section>

</body>
</html>
