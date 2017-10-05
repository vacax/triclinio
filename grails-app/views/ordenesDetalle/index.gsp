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

                "bPaginate": true,
                "bLengthChange": false,
                "bFilter": true,
                "bSort": true,
                "bInfo": true,
                "bAutoWidth": true,
                "asStripClasses": null

            });
        });

    </script>

    <script type="text/javascript">


        $(document).ready(function() {

            function valuesToArray(obj) {
                return Object.keys(obj).map(function (key) { return obj[key]; });
            }


            $("#target").click(function(){

                var table = $('#example').DataTable();

                var valores = table.rows({filter: 'applied'}).data().toArray();
                console.log(typeof (valores));

                var contador=table.rows( { filter : 'applied'} ).nodes().length;
                console.log(JSON.stringify(valores))



                for(var key=0;key<contador;key++) {

                    var value = valores[key];

                    $.ajax({
                        type: "POST",
                        url: "${createLink(controller: 'facturaDetalle', action: 'procesarOrden')}",
                        dataType: 'JSON',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            id: valuesToArray(value[0])
                        })
                        ,
                        success: function (msg) {
                            alert("Data Saved: " + msg);
                        },
                        error: (function (msg) {
                            alert("eRROR: " + msg);
                        })
                    });
                }
            });
        });

    </script>

</head>

<body>

<table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
    <thead>
    <th>
        ID
    </th>
    <th>
        Nombre
    </th>
    <th>
        Cantidad
    </th>
    <th>
        Precio
    </th>
    <th>
        NombrePlato
    </th>
    </thead>
    <tbody>
    <g:each in="${listaOrdenDetalle}" var="listaOrden">
        <tr>
            <td>${listaOrden.id}</td>
            <td>${listaOrden.clienteCuenta.nombre}</td>
            <td>${listaOrden.cantidad}</td>
            <td>${listaOrden.precio}</td>
            <td>${listaOrden.nombrePlato}</td>
        </tr>
    </g:each>
    </tbody>

</table>
%{--<a id="target" href="#" class="button">--}%
    %{--<span>Procesar Orden</span>--}%
%{--</a>--}%
<button id="target">Procesar Orden</button>
</body>


</html>



