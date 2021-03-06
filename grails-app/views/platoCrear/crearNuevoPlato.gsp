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
    <g:form action="nuevoPlato" useToken="true">
        <div class="box-body">

            <div style="margin-top:5%" class="form-group">
                <label  class="col-sm-2 control-label">Nombre</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;"  type="text" name="nombrePlato" required class="form-control" id="inputName" placeholder="nombre">
                </div>
            </div>

            <div style="margin-top:5%" class="form-group">
                <label  class="col-sm-2 control-label">Alias</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;"  type="text" name="alias" required class="form-control" id="alias" placeholder="alias">
                </div>
            </div>

            <div style="margin-top:10%" class="form-group">
                <label  class="col-sm-2 control-label">Precio</label>
                <div class="col-sm-10">
                    <input style="border: 1px solid cornflowerblue;"  type="number" name="precioPlato" required class="form-control" id="inputPrecio" placeholder="precio">
                </div>
            </div>

            <div style="margin-top:10%" class="form-group">
                <label  class="col-sm-2 control-label">Categoria</label>
                <div class="col-sm-10">
                    <g:select class="form-control" name="categoriaId" from="${listaCategoriaPlato}" optionKey="id" optionValue="nombre"/>
                </div>
            </div>

            <div style="margin-top:10%" class="form-group">
                <label  class="col-sm-2 control-label">Tanda:</label>
                <div class="col-sm-10">
                    <g:select class="form-control" name="tandaId" from="${com.triclinio.domains.restaurante.PlatoTanda.list()}" optionKey="id" optionValue="nombre"/>
                </div>
            </div>

            <div class="row" style="margin-left: 0.1%">
                %{--<div class="col-lg-5 col-xs-6">--}%
                <!-- small box -->
                <label  class="col-sm-2 control-label">Comanda</label>
                <div class="col-sm-10">
                    <div class="checkbox">
                        <input type="checkbox" name="comanda"  id="comanda" placeholder="comanda">
                    </div>
                </div>
            </div>

        <div class="row" style="margin-left: 0.1%">
            %{--<div class="col-lg-5 col-xs-6">--}%
            <!-- small box -->
            <label  class="col-sm-2 control-label">Prefix</label>
            <div class="col-sm-10">
                <div class="checkbox">
                    <input type="checkbox" name="prefix"  id="prefix" placeholder="prefix">
                </div>
            </div>
                </div>


            
        </div><!-- /.box-body -->
        <div class="box-footer">
            <a href="/platoCrear/index"  style="margin-bottom: 20px;" class="btn btn-danger">Cancel</a>
            <button type="submit" class="btn btn-info pull-right">Crear</button>
        </div><!-- /.box-footer -->
    </g:form>
</div>

</body>
</html>