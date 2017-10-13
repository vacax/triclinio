<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>



    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({

            });

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
<table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
    <thead>
    <th>
        ID
    </th>
    <th>
        Username
    </th>
    <th>
        Nombre
    </th>
    <th>
        Acciones
    </th>
    </thead>
    <tbody>
    <g:each in="${listaUsuarios}" var="usuarios">
        <tr>
            <td>${usuarios.id}</td>
            <td>${usuarios.username}</td>
            <td>${usuarios.nombre}</td>
            <td>
            <g:link action="verPerfil" controller="UsuarioCrearController"  params="[id: usuarios.id]"><button style="height: 30px;width: 80px" type="button" id="verPerfil" class="btn  btn-primary">Ver Perfil</button></g:link>
            </td>

        </tr>
    </g:each>
    </tbody>

</table>
<g:link action="crearNuevoUsuario" controller="usuarioCrear" ><button type="button" id="crearUsuario" class="btn btn-success btn-block">Crear Perfil</button></g:link>

</body>


</html>