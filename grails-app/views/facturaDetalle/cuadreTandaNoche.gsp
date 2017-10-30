<%@ page import="com.triclinio.domains.venta.Factura" %>
<!DOCTYPE html>
%{--<meta name="layout" content="main"/>--}%
<html>
<head>


    %{--<meta name="layout" content="mainCuadre"/>--}%
    <title>Cuadre Factura</title>



    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.4.2/css/buttons.dataTables.min.css">

    %{--<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>--}%
    <script
            src="https://code.jquery.com/jquery-1.12.4.js"
            integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
            crossorigin="anonymous"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/buttons/1.4.2/js/buttons.flash.min.js"></script>

    <script type="text/javascript" charset="utf8" src="//cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script type="text/javascript" charset="utf8" src="//cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/pdfmake.min.js"></script>
    <script type="text/javascript" charset="utf8" src="//cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.32/vfs_fonts.js"></script>
    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/buttons/1.4.2/js/buttons.html5.min.js"></script>
    <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/buttons/1.4.2/js/buttons.print.min.js"></script>

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
        $(document).ready(function() {
            var table = $('#example').DataTable( {
                dom: 'Bfrtip',
                lengthChange: false,
                buttons: [ 'copy', 'csv', 'excel', 'pdf', 'print' ]
            } );

//                buttons: [
//                    'copy', 'csv', 'excel', 'pdf', 'print'
//                ]
//            } );
        } );
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
        <span class="logo-mini"><b>G</b>FT</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Guava</b><small> Fusión Tropical</small></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->

    </nav>
</header>

<div  style="margin-top: 1%;margin-left: 1%;margin-bottom: 1%;margin-right: 1%">
    <table style="width:1500px; margin:0 auto; margin-top: 10%" id="example" class="table striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">


        <thead>
        <th>
            ID
        </th>
        <th>
            Camarero
        </th>
        <th>
            Fecha
        </th>
        <th>
            Cliente
        </th>
        <th>
            Monto
        </th>

        </thead>
        <tbody>
        <g:each in="${facturas}" var="factura">
            <tr>
                <td>${factura.id}</td>
                <td>${factura.usuario.nombre}</td>
                %{--<td>${factura.listaFacturaDetalle.ordenDetalle.clienteCuenta.cuenta.listaMesa.numeroMesa.first()}</td>--}%
                <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${factura.dateCreated}"/></td>
                <td>${factura.cliente.nombre}</td>
                <td>${factura.montoNeto}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <button class="btn btn-danger btn-lg"  onclick="window.history.back();" >Terminar</button>
</div>
</body>

</html>