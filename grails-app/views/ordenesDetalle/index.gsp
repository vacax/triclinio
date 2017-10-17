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

                                url:'/ordenesDetalle/eliminarOrdenDetalle/',
                                cache: false,
                                success: function()
                                {
                                    parent.fadeOut(1000, function() {$(this).remove();});
                                },

                                error:function(XMLHttpRequest,textStatus,errorThrown){}
                            });

                    });

        })



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
            </td>
        </tr>
    </g:each>
    </tbody>

</table>
<g:submitButton id="target"  class="btn btn-primary btn-lg" name="Terminar Proceso" />

</body>


</html>



