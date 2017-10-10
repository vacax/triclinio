<%@ page import="com.triclinio.domains.restaurante.Cuenta" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
            %{--<div    class="col-xs-8">--}%
                <g:form action="nuevoDetalleOrden">

                    <button type="submit" class="btn btn-block btn-success btn-lg">AGREGAR NUEVO CLIENTE</button>
                </g:form>
                <g:form action="indexRedirect" >
                    <button type="submit" class="btn btn-block btn-danger btn-lg">FINALIZAR</button>
                </g:form>
            <!-- /.box -->
            %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>

</body>

</html>