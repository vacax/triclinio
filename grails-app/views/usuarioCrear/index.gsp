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

<g:link action="crearNuevoUsuario" controller="usuarioCrear" ><button type="button" id="crearUsuario" class="btn btn-success btn-block">Crear Perfil</button></g:link>
<br>
%{--<button id="button">Row</button>--}%
<table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
    <thead>
    <th>
        ID
    </th>
    <th>
        Username
    </th>
    <th>
        Nombre
    </th>
    <th>
        Acciones
    </th>
    </thead>
    <tbody>
    <g:each in="${listaUsuarios}" var="usuarios">
        <tr>
            <td>${usuarios.id}</td>
            <td>${usuarios.username}</td>
            <td>${usuarios.nombre}</td>
            <td>
                %{--<button style="text-decoration: none" type="button" class="verPerfil btn-link" id="${usuarios.id}">Ver usuario</button>--}%
                <g:link action="verUsuario" controller="usuarioCrear"  params="[id: usuarios.id]"><button style="height: 30px;width: 80px" type="button" id="verPerfil" class="btn btn-link">Ver Usuario</button></g:link>
            </td>
        </tr>
    </g:each>
    </tbody>

</table>

</body>


</html>