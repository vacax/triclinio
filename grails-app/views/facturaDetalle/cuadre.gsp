<%@ page import="com.triclinio.domains.venta.Factura" %>
<!DOCTYPE html>
%{--<meta name="layout" content="main"/>--}%
<html>
<head>

    <title>Cuadre Factura Dia Completo</title>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/buttons/1.4.2/css/buttons.dataTables.min.css">

    %{--<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>--}%
    <script
            src="https://code.jquery.com/jquery-1.12.4.js"
            integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
            crossorigin="anonymous"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="//cdn.datatables.net/buttons/1.4.2/js/buttons.flash.min.js"></script>

    <script type="text/javascript" charset="utf8"
            src="//cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="//cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="//cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
    <script type="text/javascript" charset="utf8"
            src="//cdn.datatables.net/buttons/1.4.2/js/buttons.html5.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="//cdn.datatables.net/buttons/1.4.2/js/buttons.print.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/plug-ins/1.10.16/api/sum().js"></script>

    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/webjars/AdminLTE/2.4.0/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/webjars/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="/webjars/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/webjars/AdminLTE/2.4.0/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect. -->
    <link rel="stylesheet" href="/webjars/AdminLTE/2.4.0/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.4.2/css/buttons.bootstrap.min.css">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            var table = $('#example').DataTable({

                responsive: true,
                "footerCallback": function (row, data, start, end, display) {
                    var api = this.api(), data;

                    // converting to interger to find total
                    var intVal = function (i) {
                        return typeof i === 'string' ?
                            i.replace(/[\$,]/g, '') * 1 :
                            typeof i === 'number' ?
                                i : 0;
                    };


                    var thuTotal = api
                        .column(4)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);


                    // Update footer by showing the total with the reference of the column index
                    $(api.column(0).footer()).html('Total De todas las facturas');
                    $(api.column(1).footer()).html(thuTotal + " (Monto Neto Total)");
                },

                dom: 'Bfrtip',
                lengthChange: false,
                buttons: [
                    {
                        extend: 'print',
                        footer: true
                    },
                    {
                        extend: 'pdf',
                        footer: true
                    }
                ]


            });

        });
    </script>

    %{--<g:layoutHead/>--}%

    %{-- Para incluir otras recursos.--}%
    %{--<g:pageProperty name="page.header"/>--}%
    %{----}%
</head>

<body class="hold-transition skin-blue sidebar-mini">

<!-- Main Header -->
<header class="main-header">

    <!-- Logo -->
    <a href="/" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>M</b>RT</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Marco</b><small>Restaurante</small></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->

    </nav>
</header>

<div style="margin-top: 1%;margin-left: 1%;margin-bottom: 1%;margin-right: 1%">
    <div>
        <p>Desde:</p>
        <g:datePicker name="desde" id="fecha_desde" value="${new Date()}" precision="day"
                      years="${Calendar.getInstance().get(Calendar.YEAR)..2017}"/>
        <p>Hasta:</p>
        <g:datePicker name="hasta" id="fecha_hasta" value="${new Date()}" precision="day"
                      years="${Calendar.getInstance().get(Calendar.YEAR)..2017}"/>
        <br><br>
        <button id="refrescar_button" class="btn btn-instagram">Refrescar Data</button>
    </div>

    <hr>

    <table style="width:1500px; margin:0 auto; margin-top: 10%" id="example"
           class="table striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
        <thead>
        <th>
            Número Factura
        </th>
        <th>
            Camarero
        </th>
        <th>
            Fecha
        </th>
        <th>
            Metodo de Pago
        </th>
        <th>
            Monto
        </th>

        </thead>
        <tfoot align="right">
        <tr><th></th><th></th><th></th></tr>
        </tfoot>
        <g:each in="${facturas}" var="factura">
            <tr>
                <td>${factura.id}</td>
                <td>${factura.usuario}</td>
                %{--<td>${factura.listaFacturaDetalle.ordenDetalle.clienteCuenta.cuenta.listaMesa.numeroMesa.first()}</td>--}%
                <td>${factura.fecha}</td>
                <td>${factura.metodoPago}</td>
                <td>${factura.monto}</td>
            </tr>
        </g:each>
    </table>
    <button class="btn btn-danger btn-lg" onclick="window.history.back();">Terminar</button>
    <button class="btn btn-success btn-lg" id="descargar">Descargar Reporte</button>
</div>

<script>
    $("#refrescar_button").on('click', function () {
        var desde = $("#fecha_desde_year").val() + '-' + $("#fecha_desde_month").val() + '-' + $("#fecha_desde_day").val();
        var hasta = $("#fecha_hasta_year").val() + '-' + $("#fecha_hasta_month").val() + '-' + $("#fecha_hasta_day").val();
        var data = desde + '_' + hasta;
        $.ajax({
            url: "refrescar/",
            data: {data: data},
            success: function (data) {
                console.log(data);

                $('#example').DataTable({
                    "data": data,
                    "columns": [
                        {"data": "id"},
                        {"data": "usuario"},
                        {"data": "fecha"},
                        {"data": "metodoPago"},
                        {"data": "monto"}
                    ],
                    "destroy": true,
                    "footerCallback": function (row, data, start, end, display) {
                        var api = this.api(), data;

                        // converting to interger to find total
                        var intVal = function (i) {
                            return typeof i === 'string' ?
                                i.replace(/[\$,]/g, '') * 1 :
                                typeof i === 'number' ?
                                    i : 0;
                        };

                        var thuTotal = api
                            .column(4)
                            .data()
                            .reduce(function (a, b) {
                                return intVal(a) + intVal(b);
                            }, 0);


                        // Update footer by showing the total with the reference of the column index
                        $(api.column(0).footer()).html('Total De todas las facturas');
                        $(api.column(1).footer()).html(thuTotal + " (Monto Neto Total)");
                    },

                    dom: 'Bfrtip',
                    lengthChange: false,
                    buttons: [
                        {
                            extend: 'print',
                            footer: true
                        },
                        {
                            extend: 'pdf',
                            footer: true
                        }
                    ]

                });

                //window.location = "/cuenta/cuentaAgregarFinalizar/" + data.id
            }
        });
    })
</script>

<script>
    $("#descargar").on('click', function (e) {
        var desde = $("#fecha_desde_year").val() + '-' + $("#fecha_desde_month").val() + '-' + $("#fecha_desde_day").val();
        var hasta = $("#fecha_hasta_year").val() + '-' + $("#fecha_hasta_month").val() + '-' + $("#fecha_hasta_day").val();
        var data = desde + '_' + hasta;
        console.log(data);
        window.location = "/facturaDetalle/descargarCuadre/?tanda=3&data="+data;
    })
</script>

</body>

</html>