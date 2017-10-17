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

    <style>
    div.container {
        width: 80%;
    }
    </style>

    <script type="text/javascript">



        $(document).ready(function () {
            $('#example').dataTable({

            });

        });
    </script>

    <script type="text/javascript">

        $(document).ready(function(){

            $('#example tbody').on( 'click', 'tr', function () {
                $(this).toggleClass('selected');
            } );

            $('.eliminarPlato').click(function () {

                var id=$(this).attr('id');
                var parent = $(this).parent().parent();
                jQuery.ajax({
                    type:'POST',
                    data:'idPlato='+id,
                    url:'/platoCrear/eliminarPlato/',
                    cache:false,
                    success:function ()
                    {
                        parent.fadeOut(1000, function() {$(this).remove();});

                    },
                    error:function(XMLHttpRequest,textStatus,errorThrown){alert("error")}
                })
            })
        });

    </script>


</head>
<body>


%{--<button id="button">Row</button>--}%
<g:link action="crearNuevoPlato" controller="platoCrear" ><button type="button" id="crearPlato" class="btn btn-success btn-block">Crear Plato</button></g:link>
<br>
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
        Acciones
    </th>
    </thead>
    <tbody>
    <g:each in="${platos}" var="pla">
        <tr>
            <td>${pla.id}</td>
            <td>${pla.nombre}</td>
            <td>${pla.precio}</td>
            <td>
                    <g:link action="modificarPlato" controller="platoCrear"  params="[id: pla.id]"><button type="button" id="modificarUsuario" class="btn btn-link">Modificar</button></g:link>
                    <button style="text-decoration: none" type="button" class="eliminarPlato btn-link" id="${pla.id}">Eliminar</button>

        </td>

   </tr>
    </g:each>

    </tbody>

</table>
</body>
</html>