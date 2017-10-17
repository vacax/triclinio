<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>



<!-- Horizontal Form -->
<div class="box box-info">
    <div class="box-header with-border">
        <h3 class="box-title">Crear Usuario</h3>
    </div><!-- /.box-header -->
<!-- form start -->
    <g:form action="nuevoUsuario">
        <div class="box-body">
            <div style="margin-bottom:5%" class="form-group">
                <label for="inputEmail3" class="col-sm-2 control-label">Username</label>
                <div class="col-sm-10">
                    <input value="${usuario.username}" type="text" name="username" class="form-control" id="inputEmail3" placeholder="Email">
                </div>
            </div>

            <div style="margin-top:5%" class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Nombre</label>
                <div class="col-sm-10">
                    <input value="${usuario.nombre}" type="text" name="nombreUser" class="form-control" id="inputName" placeholder="nombre">
                </div>
            </div>

            <div style="padding-top:5%" class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Rol</label>
                <div class="col-sm-10">

                    <select value="${usuario.rol}" name="SeleccionarRol" id="SeleccionarRol"  class="form-control selcls">
                        <option value="selecionarRol" >Seleccionar Rol</option>
                        <option value="ROLE_ADMIN">admin</option>
                        <option value="ROLE_CAMARERO">camarero</option>
                        <option value="ROLE_FACTURADOR">facturarador</option>
                        <option value="ROLE_SUPERVISOR_FACTURADOR">Supervisor facturador</option>
                        <option value="ROLE_SUPERVISOR_CAMARERO">Supervisor Camarero</option>
                    </select>
                </div>
            </div>


        </div><!-- /.box-body -->
        <div class="box-footer">
            <button type="submit" class="btn btn-default">Cancel</button>
            <button type="submit" class="btn btn-info pull-right">Crear</button>
        </div><!-- /.box-footer -->
    </g:form>

</div><!-- /.box -->

</body>
</html>