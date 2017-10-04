<%@ page import="com.triclinio.domains.restaurante.Plato; com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({

//            bJQueryUI: true,
//            sPaginationType: "full_numbers"

                "bPaginate": true,
                "bLengthChange": false,
                "bFilter": true,
                "bSort": true,
                "bInfo": true,
                "bAutoWidth": true,
                "asStripClasses": null //To remove "odd"/"event" zebra classes

            });
        });

    </script>


</head>


<body>

<section class="content">
    <div class="box">
        <div class="box-header">
            <h3 class="box-title">Data Table With Full Features</h3>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                <thead>
                <th>
                    ID
                </th>
                <th>
                    Nombre
                </th>
                <th>
                    Precio
                </th>
                <th>
                    Precio
                </th>
                </thead>
                <tbody>
                <g:each in="${com.triclinio.domains.restaurante.Plato.findAll()}" var="plato">
                    <tr>
                        <td></td>
                        <td>${plato.nombre}</td>
                        <td>${plato.precio}</td>
                    </tr>
                </g:each>
                </tbody>

            </table>
        </div>
        <!-- /.box-body -->
    </div>
</section>


</body>

</html>