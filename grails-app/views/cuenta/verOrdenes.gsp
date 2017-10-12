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

            });

        });
    </script>

    <script type="text/javascript">
        $(document).ready(function() {

            function valuesToArray(obj) {
                return Object.keys(obj).map(function (key) { return obj[key]; });
            }

            $('#example tbody').on( 'click', 'tr', function () {
                $(this).toggleClass('selected');
            } );


            $("#target").click(function(){

                var table = $('#example').DataTable();
//                var table = $('#example').DataTable({
//                    select: {
//                        style: 'multi'
//                    }
//                })

                var valores = table.rows({filter: 'applied'}).data().toArray();

                var contador=table.rows( { filter : 'applied'} ).nodes().length;

                //  alert( table.rows('.selected').data().length +' row(s) selected' );
//                $('#button').click( function () {
//                    alert( table.rows('.selected').data().length +' row(s) selected' );
//                } );

                var valoresOrdenDetalle=[]
                for(var key=0;key<contador;key++) {

                    var value = valores[key];

                    valoresOrdenDetalle.push(value[0])
                }

                console.log(valoresOrdenDetalle)


                jQuery.ajax(
                    {
                        type:'POST',

                        data:'OrdenDetalle='+valoresOrdenDetalle,

                        url:'/facturaDetalle/procesarOrden/',

                        success:function(data,textStatus)
                        {
                            window.location = "/facturaDetalle/facturar?factura="+data;

                        },

                        error:function(XMLHttpRequest,textStatus,errorThrown){}
                    });
            });


        });

    </script>
</head>
<body>


%{--<button id="button">Row</button>--}%
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
    <th>
        Acciones
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
            <td>
                <g:link action="eliminarOrdenDetalle" controller="cuenta"  params="[orden: listaOrden.id]"><button type="button" id="eliminar" class="btn  btn-info">Eliminar Orden</button></g:link>
            </td>

        </tr>
    </g:each>
    </tbody>

</table>
<g:submitButton id="target"  class="btn btn-primary btn-lg" name="Procesar Orden" />

</body>


</html>