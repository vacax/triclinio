<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    %{--<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>--}%r
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/select/1.2.3/js/dataTables.select.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet"/>
</head>

<body>

<section class="content">
    %{--<div class="col-md-8">--}%

    <div class="row">

        <div class="col-md-3">
            <div class="box box-warning box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title"><b>Mesas seleccionadas</b></h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                class="fa fa-minus"></i>
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
                                <input type="text" value="Cuenta #${cuenta.id}" class="form-control" disabled>
                            </div>
                        </div>

                    </form>
                </div>
                <!-- /.box-body -->
            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="box box-warning">
                <div class="box-header with-border">
                    <h3 class="box-title"><b>Cliente atendido:</b></h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <form role="form">
                        <!-- text input -->
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <input id="nombreCliente" name="nombreCliente" placeholder="Nombre del cliente"
                                       type="text" class="form-control">
                            </div>

                            <div class="form-group">
                                <label>Comentario:</label>
                                <textarea id="comentario" name="comentario" class="form-control" rows="3"
                                          placeholder="¿Algun comentario sobre la cuenta? "></textarea>
                            </div>
                        </div>

                    </form>
                </div>
                <!-- /.box-body -->
            </div>

        </div>
    </div>

    <div class="box">
        <input hidden="hidden" id="cuentaAsignada" name="cuentaAsignada" value="${cuenta.id}">

        <div class="box-header with-border">
            <h3 class="box-title"><b>LISTADO ITEMS</b></h3>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <label for="categorias_select">Categoría:</label>
            <select id="categorias_select">
                <option selected="selected">Todo</option>
                <g:each in="${platosPorCategoria}" var="categoria">
                    <option id="option_${categoria.key.id}" value="${categoria.key.id}">${categoria.key}</option>
                </g:each>
            </select>
            <hr>

            <div id="div_Todo" class="">
                <table id="table_Todo" class="table table-bordered" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Seleccionar</th>
                        <th>Nombre Item / Platillo</th>
                        <th hidden>Precio</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Seleccionar</th>
                        <th>Nombre Item / Platillo</th>
                        <th hidden>Precio</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <g:each in="${listaPlatos}" var="plato">
                        <tr>
                            <td hidden="hidden">${plato.id}</td>
                            <td width="5%"><button
                                    onclick="add_row(${plato.id}, '${plato.nombre}', ${plato.precio});"
                                    class="btn btn-primary">Agregar</button></td>
                            <td>${plato.nombre}</td>
                            <td hidden>${plato.precio}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

            <g:each in="${platosPorCategoria}" var="categoria">
                <div id="div_${categoria.key.id}" class="hidden">
                    <table id="table_${categoria.key.id}" class="table table-bordered" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th hidden="hidden">ID</th>
                            <th>Seleccionar</th>
                            <th>Nombre Item / Platillo</th>
                            <th hidden>Precio</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th hidden="hidden">ID</th>
                            <th>Seleccionar</th>
                            <th>Nombre Item / Platillo</th>
                            <th hidden>Precio</th>
                        </tr>
                        </tfoot>
                        <tbody>
                        <g:each in="${categoria.value}" var="plato">
                            <tr>
                                <td hidden="hidden">${plato.id}</td>
                                <td width="5%"><button
                                        onclick="add_row(${plato.id}, '${plato.nombre}', ${plato.precio});"
                                        class="btn btn-primary">Agregar</button></td>
                                <td>${plato.nombre}</td>
                                <td hidden>${plato.precio}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </g:each>

            <input id="div_activo" value="div_Todo" hidden>
        </div>
        <!-- /.box-body -->
    </div>
    <!-- /.box -->

    <div class="box">
        <div class="box-header">
            <h3 class="box-title"><b>ITEMS AGREGADOS</b></h3>
        </div>
        <!-- /.box-header -->
        <div class="box-body no-padding">
            <table align='center' class="table table-condensed" id="data_table">
                <tr>
                    <th hidden>ID</th>
                    <th>Nombre Item/Platillo</th>
                    <th>Cantidad</th>
                    <th hidden>Precio</th>
                    <th>Acciones</th>

                </tr>
                <tr hidden="hidden">
                    <td><input type="text" id="idPlatilloAgregado" hidden></td>
                    <td><input type="text" id="nombrePlatilloAgregado"></td>
                    <td><input type="text" id="cantidadPlatilloAgregado"></td>
                    <td><input type="text" id="precioPlatilloAgregado"></td>
                    <td><input type="text"></td>
                </tr>

            </table>


            <button id="guardarOrden" name="guardarOrden" style="margin-top: 2%" type="button"
                    class="btn btn-success btn-block">Guardar</button>
            <g:link action="cancelarCuentaEnProgreso" controller="cuenta" params="[idCuenta: cuenta.id]"><button
                    type="button" id="cancelarOrden" class="btn btn-danger btn-block">Cancelar</button></g:link>

        </div>
        <!-- /.box-body -->
    </div>
    <!-- /.box -->
    %{--</div>--}%

</section>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>

<script>
    $(document).ready(function () {
        var table = $('#table_Todo').DataTable();
        var table1 = $('#table_1').DataTable();
        var table2 = $('#table_3').DataTable();
        var table3 = $('#table_4').DataTable();
        var table4 = $('#table_5').DataTable();
        var table5 = $('#table_6').DataTable();
    });

</script>
<script>
    function delete_row(no) {
        document.getElementById("row" + no + "").outerHTML = "";
    }

    function add_row(id, nombre, precio) {

        var idPlatilloAgregado = id;
        var nombrePlatilloAgregado = nombre;
        var precioPlatilloAgregado = precio;

//        CHEQUEO SI EL PLATO YA EXISTE
        var T = document.getElementById('data_table');
        var rows = $(T).find('> tbody > tr').length;
        var existe = 0;
        $(T).find('> tbody > tr').each(function (index, value) {

            if (index !== 0 && index !== rows - 1) {
                console.log($(this).find("td").eq(0).html() === $(this).find("td").eq(0).html());
                if ($(this).find("td").eq(0).html() === idPlatilloAgregado) {
                    var cantidadAnterior = parseInt($(this).find("td").eq(2).html());
                    $(this).find("td").eq(2).html(cantidadAnterior + 1);
                    existe = 1;
                }

            }


        });

        //SINO EXISTE AGREGO NUEVO FILA

        if (existe !== 1) {
            var table = document.getElementById("data_table");
            var table_len = (table.rows.length) - 1;
            var row = table.insertRow(table_len).outerHTML = "" +
                "<tr id='row" + table_len + "'>" +
                "<td id='idPlatillo_row" + table_len + "' hidden>" + idPlatilloAgregado + "</td>" +
                "<td id='nombrePlatillo_row" + table_len + "'>" + nombrePlatilloAgregado + "</td>" +
                "<td id='cantidadPlatillo_row" + table_len + "'>" + 1 + "</td>" +
                "<td id='precioPlatillo_row" + table_len + "' hidden>" + precioPlatilloAgregado + "</td>" +
                "<td>" + "<input type='button' value='Eliminar' class='delete' onclick='delete_row(" + table_len + ")'></td>" +
                "</tr>";
        }
    }
</script>

<script>
    var boton_presionado = false;

    $("#guardarOrden").click(function () {
        var T = document.getElementById('data_table');
        var rows = $(T).find('> tbody > tr').length;
        var cuentaAsignada = document.getElementById("cuentaAsignada").value
        if (rows === 2) {
            alert("NO TIENE PLATOS SELECCIONADOS!!")
        } else {
            if (boton_presionado === false) {
                procesarCliente_OrdenDetalle(cuentaAsignada)
            }
            boton_presionado = true;
        }
    });

    function procesarCliente_OrdenDetalle(cuentaId) {
        var objeto = {};
        objeto.cuentaId = cuentaId;
        objeto.nombreCliente = document.getElementById("nombreCliente").value;
        objeto.comentario = document.getElementById("comentario").value;

        var listaPlato = [];

        var T = document.getElementById('data_table');
        var rows = $(T).find('> tbody > tr').length;

        var contadorFila = 0; //TODO: mejorar...
        $(T).find('> tbody > tr').each(function (index, value) {
            if (index !== 0 && index !== rows - 1) {   //TODO:// mejorar...
                listaPlato[contadorFila] = {};
                listaPlato[contadorFila].idPlato = parseInt($(this).find("td").eq(0).text());
                listaPlato[contadorFila].cantidad = parseInt($(this).find("td").eq(2).text());
                contadorFila++;
            }
            console.log("" + JSON.stringify(listaPlato));

        });
        objeto.listaPlato = listaPlato;
        $.ajax({
            type: "post",
            url: "/cuenta/nuevaOrdenDetalle",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(objeto),
            success: function (data) {
                window.location = "/cuenta/cuentaAgregarFinalizar/" + data.id
            }
        });

    }

    $(function () {
        //AL CARGAR LA VENTANA CHEQUEA SI EXISTE UNA CLIENTECUENTA YA ASOCIADO
        if (${cuenta.listaClienteCuenta.size()>=1}) {
            $('#cancelarOrden').prop("disabled", true);
        }

    });
</script>

<script type="text/javascript">
    $('#categorias_select').change(function () {
        $("#" + $("#div_activo").val()).addClass("hidden");
        $("#div_" + $(this).val()).removeClass("hidden");
        $("#div_activo").val("div_" + $(this).val());
    });
</script>
</body>

</html>



