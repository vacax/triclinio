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



</head>
<body>

<g:link action="crearMesa" controller="mesa" ><button type="button" id="crearMesa" class="btn btn-success btn-block">Crear Mesa</button></g:link>
<br>
%{--<button id="button">Row</button>--}%
<table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
    <thead>
    <th>
        ID
    </th>
    <th>
        Nombre mesa
    </th>
    <th>
        Estado
    </th>
    <th>
        Acciones
    </th>
    </thead>
    <tbody>
    <g:each in="${listadoMesas}" var="mesa">
        <tr>
            <td>${mesa.id}</td>
            <td>${mesa.nombre}</td>
            <td>${mesa.estadoMesa.nombre}</td>
            <td>
                <g:link action="eliminarMesa" controller="mesa"  params="[idMesa: mesa.id]"><button style="height: 30px;width: 80px" type="button" id="verPerfil" class="btn btn-link">Eliminar</button></g:link>
            </td>
        </tr>
    </g:each>
    </tbody>

</table>

</body>


</html>