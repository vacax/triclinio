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
        <section class="invoice">
            <!-- title row -->
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="page-header">
                        <i class="fa fa-globe"></i> Clientes/Ordenes tomadas.
                    </h2>
                </div>
                <!-- /.col -->
            </div>

            <div class="col-md-3">
                    <div class="box box-warning box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title"><b>Mesas seleccionadas</b></h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                            </div>
                            <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body" style="">
                            <ul>
                                <g:each in="${cuenta.listaMesa}" var="mesa">
                                    <li>${mesa.mesa.nombre}</li>
                                </g:each>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            <div class="col-md-9">
                    <div class="box box-warning">
                        <div class="box-header with-border">
                            <h3 class="box-title"><b>Cuenta Actual:</b></h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <form role="form">
                                <!-- text input -->
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calculator"></i></span>
                                        <input type="text" value="Cuenta #${cuenta.id}" class="form-control">
                                    </div>
                                </div>

                            </form>
                        </div>
                        <!-- /.box-body -->
                    </div>

                </div>

            %{--<div class="row invoice-info">--}%
                %{--<div class="col-sm-4 invoice-col">--}%
                    %{--# Cuenta:<strong>${cuenta.id}</strong>--}%
                %{--</div>--}%
            %{--</div>--}%
            %{--<div class="row invoice-info">--}%
                %{--<div class="col-sm-4 invoice-col">--}%
                    %{--Mesas seleccionadas:--}%
                    %{--<g:each in="${cuenta.listaMesa}" var="mesa">--}%
                        %{--<strong>${mesa.mesa.nombre},</strong>--}%
                    %{--</g:each>--}%
                %{--</div>--}%
            %{--</div>--}%



        <!-- info row -->
        <g:each in="${cuenta.listaClienteCuenta}" var="clientecuenta">

            <div class="row invoice-info">
                <div class="col-sm-4 invoice-col">
                    Cliente: <strong>${clientecuenta.nombre}</strong>
                </div>

            </div>
            <!-- /.row -->



            <!-- Table row -->
            <div class="row">
                <div class="col-xs-12 table-responsive">
                    <table border="1" class="table table-striped">
                        <thead>
                        <tr>
                            <th>Cantidad</th>
                            <th>Item</th>
                        </tr>
                        </thead>
                        <tbody>
            <g:each in="${clientecuenta.listaOrdenDetalle}" var="ordenesDetalle">
                        <tr>
                            <td>${ordenesDetalle.cantidad}</td>
                            <td>${ordenesDetalle.nombrePlato}</td>
                        </tr>
            </g:each>
                        </tbody>
                    </table>
                </div>
                <!-- /.col -->
            </div>

        </g:each>
            <!-- /.row -->
        </section>
    </div>

    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
            <g:form action="nuevoDetalleOrden">
                <input hidden="" id="idCuenta" name="idCuenta" value="${cuenta.id}">
                <button type="submit" class="btn btn-block btn-success btn-lg">AGREGAR NUEVO CLIENTE</button>
            </g:form>
            <g:form action="indexRedirect" >
                <button type="submit" class="btn btn-block btn-danger btn-lg">FINALIZAR</button>
            </g:form>
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
</section>

</body>

</html>