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



    %{--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--}%



    %{--<script type="text/javascript">--}%

        %{--$(document).ready(function () {--}%
            %{--$('#example').dataTable({--}%

%{--//            bJQueryUI: true,--}%
%{--//            sPaginationType: "full_numbers"--}%

                %{--"bPaginate": true,--}%
                %{--"bLengthChange": false,--}%
                %{--"bFilter": true,--}%
                %{--"bSort": true,--}%
                %{--"bInfo": true,--}%
                %{--"bAutoWidth": true,--}%
                %{--"asStripClasses": null //To remove "odd"/"event" zebra classes--}%

            %{--});--}%
        %{--});--}%

    %{--</script>--}%

    %{--<script type="text/javascript">--}%
        %{--$(document).ready(function() {--}%

            %{--$("#target").click(function(){--}%
                %{--alert("button");--}%
                %{--var table = $('#example').DataTable();--}%

                %{--var data = table--}%
                    %{--.rows()--}%
                    %{--.data();--}%

                %{--var valores = table.rows({filter: 'applied'}).data();--}%
                %{--console.log(typeof (valores));--}%
                %{--alert('The table has ' + table.rows( { filter : 'applied'} ).nodes().length+ ' records');--}%
                %{--console.log('The table has ' + table.rows( { filter : 'applied'} ).data() + ' records');--}%
                %{--alert('The table has ' + data.length + ' records');--}%

                %{--var contador=table.rows( { filter : 'applied'} ).nodes().length;--}%
                %{--alert("Contador "+contador)--}%

                %{--for(var key=0;key<contador;key++) {--}%
                    %{--//if(key!==(contador-1)){--}%
%{--//                    if(valores.hasOwnProperty(key)) {--}%
                    %{--var value = valores[key];--}%
                    %{--//do something with value;--}%
                    %{--console.log("Heloooo: " + value);--}%
                    %{--console.log("Keyyyyy: " + key);--}%
                    %{--// }--}%
                    %{--//}--}%
                %{--}--}%
            %{--});--}%
        %{--});--}%
        %{--//        $( "#target" ).click(function() {--}%
        %{--//            alert("Holaaaaaaaaaaaaaaaaaa")--}%
        %{--//            var table = $('#example').DataTable();--}%
        %{--//--}%
        %{--//            var data = table--}%
        %{--//                .rows()--}%
        %{--//                .data();--}%
        %{--//--}%
        %{--//            alert( 'The table has '+data.length+' records' );--}%
        %{--//        });--}%
    %{--</script>--}%

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
                                    <input id="nombreCliente" name="nombreCliente" placeholder="Nombre del cliente" type="text" class="form-control">
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
                <g:each in="${com.triclinio.domains.restaurante.Plato.list()}" var="plato">
                    <tr>
                        <td hidden="hidden">${plato.id}</td>
                        <td>${plato.nombre}</td>
                        <td>${plato.precio}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>

        </div>
            <!-- /.box-body -->
            <div style="margin-bottom: 2%" class="box-footer clearfix">
                <input hidden="hidden"  id="rowSelected" name="rowSelected">
                <input hidden="hidden"  id="nombrePlato" name="nombrePlato">
                <input hidden="hidden"  id="precioPlato" name="precioPlato">
                <button onclick="add_row()" disabled="disabled" id="agregarDetalle" type="button" class="btn btn-success btn-block">Agregar</button>
            </div>
        </div>
        <!-- /.box -->

        <div class="box">
            <div class="box-header">
                <h3 class="box-title"><b>ITEMS AGREGADOS</b></h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
                <table align='center' class="table table-condensed" cellspacing=2 cellpadding=5 id="data_table" >
                <tr>
                    <th>ID</th>
                    <th>Nombre Item/Platillo</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Acciones</th>

                </tr>

                %{--<tr id="row1">--}%
                    %{--<td id="nombrePlatillo_row1">Ankit</td>--}%
                    %{--<td id="precioPlatillo_row1">India</td>--}%
                    %{--<td id="age_row1">20</td>--}%
                    %{--<td>--}%
                        %{--<input type="button" id="edit_button1" value="Edit" class="edit" onclick="edit_row('1')">--}%
                        %{--<input type="button" id="save_button1" value="Save" class="save" onclick="save_row('1')">--}%
                        %{--<input type="button" value="Delete" class="delete" onclick="delete_row('1')">--}%
                    %{--</td>--}%
                %{--</tr>--}%

                <tr hidden="hidden">
                    <td><input type="text" id="idPlatilloAgregado"></td>
                    <td><input type="text" id="nombrePlatilloAgregado"></td>
                    <td><input type="text" id="cantidadPlatilloAgregado"></td>
                    <td><input type="text" id="precioPlatilloAgregado"></td>
                    %{--<td><input type="text" id="new_age"></td>--}%
                    <td><input type="text"></td>
                </tr>


            </table>
            %{--<g:form action="cuentaAgregarFinalizar">--}%
                <button id="guardarOrden" name="guardarOrden" style="margin-top: 2%" type="summit" class="btn btn-danger btn-block">Guardar</button>
            %{--</g:form>--}%

            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    %{--</div>--}%

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


    function agregarNuevoPlato() {
        add_row();

        var idPlato = document.getElementById("rowSelected").value
        $.ajax({
            type: "POST",
            url:"/cuenta/nuevaOrdenDetalle?idPlato="+idPlato,
            dataType: "JSON",
            contentType:"application/json; charset=utf-8",
            success:(
                function (data) {
                    console.log("SE ENTREGO!");
                    add_row();
                }),
            error :(function(data){
                alert("ERROR")
            })
        });
    }

</script>

<script>

    function delete_row(no)
    {
        document.getElementById("row"+no+"").outerHTML="";
    }

    function add_row()
    {
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
//                alert($(this).find("td").eq(0).html())
                if($(this).find("td").eq(0).html()==idPlatilloAgregado){
                    var cantidadAnterior = parseInt( $(this).find("td").eq(2).html());
                    $(this).find("td").eq(2).html(cantidadAnterior+1);
//                    console.log($(this).find("td").eq(2).html())
                    existe=1;
                }

            }


        });
        
        //SINO EXISTE AGREGO NUEVO FILA

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

%{--<script>--}%
    %{--$(function () {--}%
        %{--alert(        document.getElementById('data_table').length--}%
        %{--)--}%
%{--//        $("#guardarOrden").click(function () {--}%
%{--//            var nFilas = $("data_table tr").length;--}%
%{--//            var nColumnas = $("#data_table tr:last td").length;--}%
%{--//            var msg = "Filas: "+nFilas+" - Columnas: "+nColumnas;--}%
%{--//            alert(msg);--}%
%{--//        });--}%
    %{--});--}%
%{--</script>--}%

<script>

    $("#guardarOrden").click(function () {
        var T = document.getElementById('data_table');
        var rows =$(T).find('> tbody > tr').length;

        var nombreCliente1;
        if(rows==2){
            alert("NO TIENE PLATOS SELECCIONADOS!!")
        }
        else{
            var nombreCliente=document.getElementById("nombreCliente").value;
            if(document.getElementById("nombreCliente").value.length!=0){
                $.ajax({
                    type: "POST",
                    url:"/cuenta/clienteCuenta?cliente="+nombreCliente,
                    dataType: "JSON",
                    contentType:"application/json; charset=utf-8",
                    success:(
                        function (data) {
                            console.log("SE ENTREGO!");
                            nombreCliente1=data.nombre;
                        }),
                    error :(function(data){
                        alert("ERROR CLIENTE")
                    })
                });

            }
            else{
                $.ajax({
                    type: "POST",
                    url:"/cuenta/clienteCuenta?cliente=Cliente Generico",
                    dataType: "JSON",
                    contentType:"application/json; charset=utf-8",
                    success:(
                        function (data) {
                            console.log("SE ENTREGO!");
                            nombreCliente1=data.nombre;
                        }),
                    error :(function(data){
                        alert("ERROR CLIENTE")
                    })
                });

            }




            var cuentaAsignada = document.getElementById("cuentaAsignada").value
            $(T).find('> tbody > tr').each(function (index,value) {
                if(index!=0 && index!=rows-1){
                    var idPlato=$(this).find("td").eq(0).html();
                    var cantidad=$(this).find("td").eq(2).html();

                    $.ajax({
                        type: "POST",
                        url:"/cuenta/nuevaOrdenDetalle?idPlato="+idPlato+"&cantidad="+cantidad,
                        dataType: "JSON",
                        contentType:"application/json; charset=utf-8",
                        success:(
                            function (data) {
                                console.log("SE ENTREGO! ORDEN");
                            }),
                        error :(function(data){
                            alert("ERROR PLATOS!")
                        })
                    });

                }

//                    alert($(this).find("td").eq(1).html());

            });
            parent.location="/cuenta/cuentaAgregarFinalizar?idCuenta="+cuentaAsignada;


        }
    });
</script>



</body>


</html>



