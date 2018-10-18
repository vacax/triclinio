<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({});

        });
    </script>

    %{--<script type="text/javascript">--}%
    %{--$(document).ready(function() {--}%

    %{--function valuesToArray(obj) {--}%
    %{--return Object.keys(obj).map(function (key) { return obj[key]; });--}%
    %{--}--}%

    %{--$('#example tbody').on( 'click', 'tr', function () {--}%
    %{--$(this).toggleClass('selected');--}%
    %{--} );--}%


    %{--$("#target").click(function(){--}%

    %{--var table = $('#example').DataTable();--}%
    %{--//                var table = $('#example').DataTable({--}%
    %{--//                    select: {--}%
    %{--//                        style: 'multi'--}%
    %{--//                    }--}%
    %{--//                })--}%

    %{--var valores = table.rows({filter: 'applied'}).data().toArray();--}%

    %{--var contador=table.rows( { filter : 'applied'} ).nodes().length;--}%

    %{--//  alert( table.rows('.selected').data().length +' row(s) selected' );--}%
    %{--//                $('#button').click( function () {--}%
    %{--//                    alert( table.rows('.selected').data().length +' row(s) selected' );--}%
    %{--//                } );--}%

    %{--var valoresOrdenDetalle=[]--}%
    %{--for(var key=0;key<contador;key++) {--}%

    %{--var value = valores[key];--}%

    %{--valoresOrdenDetalle.push(value[0])--}%
    %{--}--}%

    %{--console.log(valoresOrdenDetalle)--}%


    %{--jQuery.ajax(--}%
    %{--{--}%
    %{--type:'POST',--}%

    %{--data:'OrdenDetalle='+valoresOrdenDetalle,--}%

    %{--url:'/facturaDetalle/procesarOrden/',--}%

    %{--success:function(data,textStatus)--}%
    %{--{--}%
    %{--window.location = "/facturaDetalle/facturar?factura="+data;--}%

    %{--},--}%

    %{--error:function(XMLHttpRequest,textStatus,errorThrown){}--}%
    %{--});--}%
    %{--});--}%


    %{--});--}%

    %{--</script>--}%
</head>

<body>

%{--<button id="button">Row</button>--}%


<section class="content">
    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
            <g:form action="crearNuevaCuenta">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>CUENTAS ABIERTAS</b></h3>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table id="example" class="table table-striped table-bordered dt-responsive nowrap"
                               cellspacing="0" width="100%">
                            <thead>
                            <th>
                                ID
                            </th>
                            <th>
                                Atendido por:
                            </th>
                            <th>
                                Clientes:
                            </th>
                            <th>
                                Estado de la cuenta
                            </th>
                            <th>
                                Mesas
                            </th>
                            <th>
                                Acciones
                            </th>
                            </thead>
                            <tbody>
                            <g:each in="${cuentasAbiertas}" var="cuenta">
                                <tr>
                                    <td>${cuenta.id}</td>
                                    <td>${cuenta.usuario.nombre}</td>
                                    <td><g:each in="${cuenta.listaClienteCuenta}" var="c">
                                        (${c.nombre})
                                    </g:each></td>
                                    <g:if test="${cuenta.estadoCuenta.codigo == com.triclinio.domains.restaurante.EstadoCuenta.ABIERTO}">
                                        <td><span class="label label-success">Abierta</span></td>
                                    </g:if>
                                    <td>
                                        <g:each in="${cuenta.listaMesa}" var="cuentaMesa">
                                            <g:if test="${cuentaMesa.habilitado}">
                                                (${cuentaMesa.mesa.nombre})
                                            </g:if>
                                        </g:each>
                                    </td>
                                    <td>
                                        <sec:ifAnyGranted
                                                roles="ROLE_ADMIN,ROLE_SUPERVISOR_CAMARERO,ROLE_CAMARERO,ROLE_SUPERVISOR_FACTURADOR,ROLE_FACTURADOR">
                                            <g:link action="detalleCuenta" controller="cuenta"
                                                    params="[idCuenta: cuenta.id]"><button type="button" id="verPerfil"
                                                                                           class="btn  btn-success">Ver detalle Cuenta</button></g:link>
                                        </sec:ifAnyGranted>
                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR_CAMARERO,ROLE_CAMARERO">

                                            <g:link action="sacarMesaCuenta" controller="mesa"
                                                    params="[idCuenta: cuenta.id]"><button type="button" id="verPerfil"
                                                                                           class="btn  btn-warning">Sacar mesa(s)</button></g:link>
                                        </sec:ifAnyGranted>

                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR_CAMARERO,ROLE_CAMARERO">

                                            <g:link action="detalleOrdenIndex" controller="cuenta"
                                                    params="[idCuenta: cuenta.id]"><button type="button" id="verPerfil"
                                                                                           class="btn  btn-warning">Agregar cliente</button></g:link>

                                        </sec:ifAnyGranted>
                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR_CAMARERO,ROLE_CAMARERO">

                                            <g:link action="reImprimirComanda" controller="cuenta"
                                                    params="[idCuenta: cuenta.id]"><button type="button" id="verPerfil"
                                                                                           class="btn  btn-warning">Reimprimir</button></g:link>

                                        </sec:ifAnyGranted>
                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR_CAMARERO">

                                            <g:link onclick="return window.confirm('Desea Cancelar la cuenta');"
                                                    action="cerraCuenta" controller="cuenta"
                                                    params="[idCuenta: cuenta.id]"><button type="button" id="verPerfil"
                                                                                           class="btn  btn-warning">Cancelar Cuenta</button></g:link>

                                        </sec:ifAnyGranted>
                                    </td>

                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                </div>

            </g:form>
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>

</body>

</html>