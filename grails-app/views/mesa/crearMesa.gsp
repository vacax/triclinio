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
    <g:form action="procesarNuevaMesa">
        <div class="box-body">

            <div style="margin-top:5%" class="form-group">
                <label  class="col-sm-2 control-label">Nombre:</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;"  type="text" name="nombreMesa" required class="form-control" id="nombreMesa" placeholder="Nombre de la mesa">
                </div>
            </div>

            <div style="margin-top:10%" class="form-group">
                <label  class="col-sm-2 control-label">Numero de la mesa:</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;"  type="number" name="numeroMesa" required class="form-control" id="numeroMesa" placeholder="Numero de la mesa">
                </div>
            </div>
        </div><!-- /.box-body -->
        <div class="box-footer">
            <a href="/mesa/crearMesaIndex"  style="margin-bottom: 20px;" class="btn btn-danger">Cancelar</a>
            <button type="submit" class="btn btn-info pull-right">Crear</button>
        </div><!-- /.box-footer -->
    </g:form>


</div>

</body>
</html>