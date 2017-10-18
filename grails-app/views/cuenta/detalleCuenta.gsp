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
                            <g:each in="${listadoMesas}" var="mesa">
                                <li>${mesa.nombre}</li>
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


            <div class="row invoice-info">
                <div class="col-sm-4 invoice-col">
                    <h2><b>Listado clientes en la mesa:</b></h2>
                </div>

            </div>
            <!-- /.row -->

            <!-- Table row -->
            <div class="row">
                <div class="col-xs-12 table-responsive">
                    <table border="1" class="table table-striped">
                        <thead>
                        <tr>
                            <th>Nombre Cliente</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${cuenta}" var="clientecuenta">
                            <tr>
                                <td>${clientecuenta.nombre}</td>
                                %{--<td>${ordenesDetalle.nombrePlato}</td>--}%
                                <td>

                                %{--<g:form action="verOrdenes">--}%
                                    %{--<input hidden="hidden" id="clienteCuenta" name="clienteCuenta" value="${clientecuenta.id}">--}%
                                    %{--<button type="submit" class="btn btn-block btn-danger btn-sm">Facturar</button>--}%
                                %{--</g:form>--}%
                                <g:link action="verOrdenes" controller="cuenta"  params="[clienteCuenta: clientecuenta.id]"><button type="button" class="btn btn-info">Facturar</button></g:link>


                                %{--<g:form action="nuevoDetalleOrden">--}%
                                        <input hidden="hidden" id="clienteCuenta" name="clienteCuenta" value="${clientecuenta.id}">
                                        <g:link action="nuevoDetalleOrden2" controller="cuenta"  params="[clienteCuenta: clientecuenta.id]"><button type="button" class="btn btn-adn">Agregar/eliminar orden a la cuenta</button></g:link>
                                        <g:link action="separarCuenta" controller="cuenta"  params="[clienteCuenta: clientecuenta.id]"><button type="button" class="btn btn-warning">Separar cuenta</button></g:link>


                                    %{--<button type="submit" class="btn btn-block btn-danger btn-sm">Agregar Detalle Orden</button>--}%
                                    %{--</g:form>--}%
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <!-- /.col -->
            </div>
            <g:link action="cuentasAbiertas" controller="cuenta" ><button type="button" class="btn btn-block btn-danger">Volver Atras</button></g:link>


            <!-- /.row -->
        </section>
    </div>
</section>

</body>

</html>