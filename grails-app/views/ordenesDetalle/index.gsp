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

    <script type="text/javascript">
        $(document).ready(function() {

            $("#target").click(function(){
                alert("button");
                var table = $('#example').DataTable();

                var data = table
                    .rows()
                    .data();

                var valores = table.rows({filter: 'applied'}).data();
                console.log(typeof (valores));
                alert('The table has ' + table.rows( { filter : 'applied'} ).nodes().length+ ' records');
                console.log('The table has ' + table.rows( { filter : 'applied'} ).data() + ' records');
                alert('The table has ' + data.length + ' records');

                var contador=table.rows( { filter : 'applied'} ).nodes().length;
                alert("Contador "+contador)

                for(var key=0;key<contador;key++) {
                    //if(key!==(contador-1)){
//                    if(valores.hasOwnProperty(key)) {
                        var value = valores[key];
                        //do something with value;
                        console.log("Heloooo: " + value);
                        console.log("Keyyyyy: " + key);
                   // }
                  //}
                }
            });
        });
//        $( "#target" ).click(function() {
//            alert("Holaaaaaaaaaaaaaaaaaa")
//            var table = $('#example').DataTable();
//
//            var data = table
//                .rows()
//                .data();
//
//            alert( 'The table has '+data.length+' records' );
//        });
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



