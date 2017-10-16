<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    %{--<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>--}%r
    <script  src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/select/1.2.3/js/dataTables.select.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>

    <script type="text/javascript" src="table_script.js"></script>
</head>

<body>

<section class="content">


    <div class="row">
        <div class="col-md-3">
            <div class="box box-warning box-solid">
                <div class="box-header with-border">
                    <h3 class="box-title"><b>Mesas seleccionadas</b></h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                    </div>
                </div>
                <div class="box-body" style="">
                    <ul>
                        <g:each in="${clienteCuenta.cuenta.listaMesa}" var="mesa">
                            <li>${mesa.mesa.nombre}</li>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-warning">
                <div class="box-header with-border">
                    <h3 class="box-title"><b>Cuenta Actual:</b></h3>
                </div>
                <div class="box-body">
                    <form role="form">
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-calculator"></i></span>
                                <input type="text" value="Cuenta #${clienteCuenta.cuenta.id}" class="form-control">
                            </div>
                        </div>

                    </form>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="box box-warning">
                <div class="box-header with-border">
                    <h3 class="box-title"><b>Cliente atendido:</b></h3>
                </div>
                <div class="box-body">
                    <form role="form">
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <input id="nombreCliente" disabled="disableda" value="${clienteCuenta.nombre}" name="nombreCliente" placeholder="Nombre del cliente" type="text" class="form-control">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    %{--ITEMS EXISTENTES EN LA CUENTA--}%
    <div class="box">
        <div class="box-header">
            <h3 class="box-title"><b>ITEMS PRESENTES EN LA CUENTA</b></h3>
        </div>
        <div class="box-body no-padding">
            <table align='center' class="table table-condensed" cellspacing=2 cellpadding=5 id="data_table1" >
                <tr>
                    <th>ID</th>
                    <th>Nombre Item/Platillo</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Acciones</th>

                </tr>
                <g:each in="${ordenesActivas}" var="orden">
                     <tr>
                        <td>${orden.id}</td>
                        <td>${orden.nombrePlato}</td>
                        <td>${orden.cantidad}</td>
                        <td>${orden.precio}</td>
                        <td><g:link action="sacarItemCuenta" controller="cuenta"  params="[clienteCuentaId:clienteCuenta.id, idPlato:orden.plato.id]"><button type="button" id="verPerfil" class="btn  btn-danger">Eliminar</button></g:link>
                        </td>

                    </tr>
                </g:each>


            %{--<tr hidden="hidden">--}%
                    %{--<td><input type="text" id="idPlatilloAgregado"></td>--}%
                    %{--<td><input type="text" id="nombrePlatilloAgregado"></td>--}%
                    %{--<td><input type="text" id="cantidadPlatilloAgregado"></td>--}%
                    %{--<td><input type="text" id="precioPlatilloAgregado"></td>--}%
                    %{--<td><input type="text"></td>--}%
                %{--</tr>--}%


            </table>
        </div>
    </div>


    <!--    TABLA LISTADO ITEMS-->
    <div class="box">
        <input hidden="hidden" id="cuentaAsignada" name="cuentaAsignada" value="${clienteCuenta.cuenta.id}">
        <div class="box-header with-border">
            <h3 class="box-title"><b>LISTADO ITEMS</b></h3>
        </div>
        <div class="box-body">
            <table id="example" class="table table-bordered" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th hidden="hidden">ID</th>
                    <th>Nombre Item / Platillo</th>
                    <th>Precio</th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <th hidden="hidden">ID</th>
                    <th>Nombre Item / Platillo</th>
                    <th>Precio</th>
                </tr>
                </tfoot>
                <tbody>
                <g:each in="${com.triclinio.domains.restaurante.Plato.list()}" status="counter" var="plato">
                    <tr>
                        <td  hidden="hidden">${plato.id}</td>
                        <td>${plato.nombre}</td>
                        <td>${plato.precio}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>

        </div>
        <div style="margin-bottom: 2%" class="box-footer clearfix">
            <input hidden="hidden" id="rowSelected" name="rowSelected">
            <input hidden="hidden"  id="nombrePlato" name="nombrePlato">
            <input hidden="hidden"  id="precioPlato" name="precioPlato">
            <button onclick="add_row()" disabled="disabled" id="agregarDetalle" type="button" class="btn btn-success btn-block">Agregar</button>
        </div>
    </div>

<!--    TABLA ITEMS AGREGADOS-->
    <div class="box">
        <div class="box-header">
            <h3 class="box-title"><b>ITEMS AGREGADOS</b></h3>
        </div>
        <div class="box-body no-padding">
            <table align='center' class="table table-condensed" cellspacing=2 cellpadding=5 id="data_table" >
                <tr>
                    <th>ID</th>
                    <th>Nombre Item/Platillo</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Acciones</th>

                </tr>

                <tr hidden="hidden">
                    <td><input type="text" id="idPlatilloAgregado"></td>
                    <td><input type="text" id="nombrePlatilloAgregado"></td>
                    <td><input type="text" id="cantidadPlatilloAgregado"></td>
                    <td><input type="text" id="precioPlatilloAgregado"></td>
                    <td><input type="text"></td>
                </tr>


            </table>
            <button id="guardarOrden" name="guardarOrden" style="margin-top: 2%" type="summit" class="btn btn-danger btn-block">Guardar</button>
        </div>
    </div>

</section>


<script>

    $(document).ready(function() {
        var table = $('#example').DataTable();

        $('#example tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                //HABILITA O DESABILITA EL BOTON SI NO HAY NADA SELECCIONADO
                $('#agregarDetalle').prop("disabled", true);

                $(this).removeClass('selected');
            }
            else {
                //HABILITA O DESABILITA EL BOTON SI NO HAY NADA SELECCIONADO
                $('#agregarDetalle').prop("disabled", false);

                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                var pos = table.row(this).index();
                var row = table.row(pos).data();
                document.getElementById("rowSelected").value=row[0]
                document.getElementById("nombrePlato").value=row[1]
                document.getElementById("precioPlato").value=row[2]
            }
        } );

    } );

    %{--$("#guardarOrden").click(function () {--}%
        %{--var T = document.getElementById('data_table');--}%
        %{--var rows =$(T).find('> tbody > tr').length;--}%

        %{--var cuentaAsignada = document.getElementById("cuentaAsignada").value--}%

        %{--var idCliente=${clienteCuenta.id}--}%

        %{--alert(rows)--}%
        %{--if(rows==2){--}%
            %{--alert("NO TIENE PLATOS SELECCIONADOS!!")--}%
        %{--}--}%
        %{--else{--}%
            %{--crearPlato(idCliente)--}%






            %{--parent.location="/cuenta/cuentaAgregarFinalizar?idCuenta="+cuentaAsignada;--}%


        %{--}--}%
    %{--});--}%

//    function crearPlato(idCliente) {
//        var T = document.getElementById('data_table');
//        var rows =$(T).find('> tbody > tr').length;
//
//        $(T).find('> tbody > tr').each(function (index,value) {
//            if(index!=0 && index!=rows-1){
//                var idPlato=$(this).find("td").eq(0).html();
//                var cantidad=$(this).find("td").eq(2).html();
//                $.ajax({
//                    type: "POST",
//                    url:"/cuenta/nuevaOrdenDetalle?idPlato="+idPlato+"&cantidad="+cantidad+"&idCliente="+idCliente,
//                    dataType: "JSON",
//                    contentType:"application/json; charset=utf-8",
//                    success:(
//                        function (data) {
//                            console.log("SE ENTREGO! ORDEN");
//                        }),
//                    error :(function(data){
//                        alert("ERROR PLATOS!")
//                    })
//                });
//
//            }
//        });
//
//    }

    function delete_row(no) {
        document.getElementById("row"+no+"").outerHTML="";
    }

    function add_row() {
        var idPlatilloAgregado=document.getElementById("rowSelected").value;
        var nombrePlatilloAgregado=document.getElementById("nombrePlato").value;
        var precioPlatilloAgregado=document.getElementById("precioPlato").value;

//        CHEQUEO SI EL PLATO YA EXISTE
        var T = document.getElementById('data_table');
        var rows =$(T).find('> tbody > tr').length;
        var existe=0;
        $(T).find('> tbody > tr').each(function (index,value) {
            if(index!=0 && index!=rows-1)
            {
                if($(this).find("td").eq(0).html()==idPlatilloAgregado){
                    var cantidadAnterior = parseInt( $(this).find("td").eq(2).html());
                    $(this).find("td").eq(2).html(cantidadAnterior+1);
                    existe=1;
                }

            }


        });

//        SINO EXISTE AGREGO NUEVO FILA

        if(existe!=1){
            var table=document.getElementById("data_table");
            var table_len=(table.rows.length)-1;
            var row = table.insertRow(table_len).outerHTML="" +
                "<tr id='row"+table_len+"'>" +
                "<td id='idPlatillo_row"+table_len+"'>"+idPlatilloAgregado+"</td>" +
                "<td id='nombrePlatillo_row"+table_len+"'>"+nombrePlatilloAgregado+"</td>" +
                "<td id='cantidadPlatillo_row"+table_len+"'>"+1+"</td>" +
                "<td id='precioPlatillo_row"+table_len+"'>"+precioPlatilloAgregado+"</td>" +
                "<td>" + "<input type='button' value='Eliminar' class='delete' onclick='delete_row("+table_len+")'></td>" +
                "</tr>";

        }
    }
</script>

<script>
    $("#guardarOrden").click(function () {
        var T = document.getElementById('data_table');
        var rows =$(T).find('> tbody > tr').length;
        var cuentaAsignada = document.getElementById("cuentaAsignada").value
        if(rows==2){
            alert("NO TIENE PLATOS SELECCIONADOS!!")
        } else{
            procesarCliente_OrdenDetalle(cuentaAsignada)
        }
    });


    function procesarCliente_OrdenDetalle(cuentaId) {
        var objeto = {};
        %{--alert(${clienteCuenta.cuenta.id + clienteCuenta.id})--}%
        objeto.cuentaId = ${clienteCuenta.cuenta.id}
        objeto.clienteId = ${clienteCuenta.id}

        var listaPlato = [];

        var T = document.getElementById('data_table');
        var rows =$(T).find('> tbody > tr').length;

        var contadorFila=0; //TODO: mejorar...
        $(T).find('> tbody > tr').each(function (index,value) {
            if(index!=0 && index!=rows-1){   //TODO:// mejorar...
                listaPlato[contadorFila]= {};
                listaPlato[contadorFila].idPlato=parseInt($(this).find("td").eq(0).text());
                listaPlato[contadorFila].cantidad=parseInt($(this).find("td").eq(2).text());
                contadorFila++;
            }

        });

        objeto.listaPlato = listaPlato;
        console.log(""+JSON.stringify(objeto));

        $.ajax({
            type: "post",
            url:"/cuenta/nuevaOrdenDetalleCuentaExistentes",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(objeto),
            success: function (data) {
                window.location = "/cuenta/detalleCuenta?idCuenta="+data.id
            }
        });

    }

</script>
</body>


</html>



