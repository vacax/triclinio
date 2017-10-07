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
    <div class="col-md-8">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Listado de items</h3>
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
                <button onclick="setPlatoOrdenDetalle()" id="agregarDetalle" type="button" class="btn btn-success btn-block">Agregar</button>
            </div>
        </div>
        <!-- /.box -->

        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Items agregados</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
                <table align='center' class="table table-condensed" cellspacing=2 cellpadding=5 id="data_table" >
                <tr>
                    <th hidden="hidden">ID</th>
                    <th>Nombre Item/Platillo</th>
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

                <tr>
                    <td><input type="text" id="nombrePlatilloAgregado"></td>
                    <td><input type="text" id="precioPlatilloAgregado"></td>
                    %{--<td><input type="text" id="new_age"></td>--}%
                    <td><input type="text"></td>
                </tr>


            </table>
            <g:form action="cuentaAgregarFinalizar">
                <button style="margin-top: 2%" type="summit" class="btn btn-danger btn-block">Guardar</button>
            </g:form>

            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </div>

</section>

<script>
    $(document).ready(function() {
        var table = $('#example').DataTable();

        $('#example tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
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


    function setPlatoOrdenDetalle() {
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
        var nombrePlatilloAgregado=document.getElementById("nombrePlato").value;
        var precioPlatilloAgregado=document.getElementById("precioPlato").value;

        var table=document.getElementById("data_table");
        var table_len=(table.rows.length)-1;
        var row = table.insertRow(table_len).outerHTML="" +
            "<tr id='row"+table_len+"'>" +
                "<td id='nombrePlatillo_row"+table_len+"'>"+nombrePlatilloAgregado+"</td>" +
                "<td id='precioPlatillo_row"+table_len+"'>"+precioPlatilloAgregado+"</td>" +
                "<td>" + "<input type='button' value='Eliminar' class='delete' onclick='delete_row("+table_len+")'></td>" +
            "</tr>";
    }
</script>
</body>


</html>



