<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

<div class="container">

    <div class="row">
        <div class="col-md-8">

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">${usuario.nombre}</h3>
                </div>
                <div class="panel-body">
                    <div class="row">

                        <div class=" col-md-9 col-lg-9 ">
                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <td><b>Username:</b></td>
                                    <td>${usuario.username}</td>
                                </tr>
                                <tr>
                                    <td><b>Fecha Creado:</b></td>
                                    <td>${usuario.dateCreated}</td>
                                    <input value="${usuario.id}" type="hidden"  name="idUsuario" class="form-control" id="idUsuario">
                                </tr>
                                <tr>
                                    <td><b>Roles: </b></td>
                                <g:each in="${listaPerfil}" var="perfil">
                                        <td>${perfil.authority}</td>
                                </g:each>
                                </tr>
                                </tbody>
                            </table>

                            <a href="/usuarioCrear/index/" class="btn btn-primary">Ver lista Usuarios</a>
                            <a href="/usuarioCrear/modificarUser/${usuario.id}"  disabled="true" class="btn btn-primary">Editar</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>

