<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css"/>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.js"></script>


    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({

            });

        });
    </script>

    <script type="text/javascript">
        $(document).ready(function() {

            $('#example tbody').on( 'click', 'tr', function () {
                $(this).toggleClass('selected');
            } );



            $(".eliminarOrdenDetalle").click(function () {

                var id = $(this).attr('id');
                var parent = $(this).parent().parent();
                jQuery.ajax(
                    {
                        type:'POST',

                        data:'idOrden='+id,

                        url:'/cuenta/eliminarOrdenDetalle/',
                        cache: false,
                        success: function()
                        {
                            parent.fadeOut(1000, function() {$(this).remove();});
                        },

                        error:function(XMLHttpRequest,textStatus,errorThrown){}
                    });

            });


            $("#target").click(function(){

                var table = $('#example').DataTable();
//                var table = $('#example').DataTable({
//                    select: {
//                        style: 'multi'
//                    }
//                })

                var valores = table.rows({filter: 'applied'}).data().toArray();

                var contador=table.rows( { filter : 'applied'} ).nodes().length;



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
                <button style="text-decoration: none" type="button" class="eliminarOrdenDetalle btn btn-link" id="${listaOrden.id}">Eliminar Orden</button>
                %{--<g:link action="verOrdenEspecifico" controller="cuenta"  params="[idOrden: listaOrden.id]"><button type="button" class="btn">Ver Detalle Orden</button></g:link>--}%

            </td>
        </tr>

    </g:each>
    </tbody>

</table>
<g:submitButton id="target"  class="btn btn-primary btn-lg" name="Procesar Orden" />
<button class="btn btn-danger btn-lg"  onclick="window.history.back();" >Cancelar orden</button>
%{--<a style="background-color: #4c59ff;color: #FFFFFF; ;width: 150px; height: 150px;" href="#" onclick="window.history.back();">Cancelar</a>--}%


</body>


</html>



