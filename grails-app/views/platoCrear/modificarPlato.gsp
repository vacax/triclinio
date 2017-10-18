<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Crear Plato</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

<!-- Horizontal Form -->
<div class="box box-info">
    <div class="box-header with-border">
        <h3 class="box-title">Crear Plato</h3>
    </div>
<!-- form start -->
    <g:form action="modificarPlatoPost">
        <div class="box-body">

            <div style="margin-top:5%" class="form-group">
                <label  class="col-sm-2 control-label">Nombre</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;" required value="${plato.nombre}"  type="text" name="nombrePlato" class="form-control" id="inputName" placeholder="nombre">
                </div>
            </div>

            <div style="margin-top:10%" class="form-group">
                <label  class="col-sm-2 control-label">Precio</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;" required value="${plato.precio}" type="number" name="precioPlato" class="form-control" id="inputPrecio" placeholder="precio">
                    <input value="${plato.id}" type="hidden"  name="idPlato" class="form-control" id="idPlato">
                </div>
            </div>
        </div><!-- /.box-body -->
        <div class="box-footer">
            <button type="submit" class="btn btn-default">Cancel</button>
            <button type="submit" class="btn btn-info pull-right">Crear</button>
        </div><!-- /.box-footer -->
    </g:form>

</div>

</body>
</html>