<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>ReImpresion Factura</title>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css"/>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {
            $('#example').dataTable({
                "order": [[ 0, "desc" ]]
            });

        });
    </script>

    <script type="text/javascript">
        $(document).ready(function() {

            $('#example tbody').on( 'click', 'tr', function () {
                $(this).toggleClass('selected');
            } );



            $(".reimprimirFactura").click(function () {

                var id = $(this).attr('id');
                var parent = $(this).parent().parent();
                jQuery.ajax(
                    {
                        type:'POST',
                        data:'facturaid='+id,

                        url:'/facturaDetalle/reimpresionFactura/',
                        cache: false,
                        success: function()
                        {
                              //window.location = "/cuenta/cuentasAbiertas";
                              alert("Factura Reimpuesto!!!")
//                            parent.fadeOut(1000, function() {$(this).remove();});
                        },

                        error:function(XMLHttpRequest,textStatus,errorThrown){}
                    });

            });

        });

    </script>
</head>
<body>


%{--<button id="button">Row</button>--}%
<table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
    <thead>
    <th>
        ID
    </th>
    <th>
        Usuario
    </th>
    <th>
        Fecha
    </th>
    <th>
        Cliente
    </th>
    <th>
        Monto
    </th>
    <th>
        Acciones
    </th>
    </thead>
    <tbody>
    <g:each in="${facturas}" var="factura">
        <tr>
            <td>${factura.id}</td>
            <td>${factura.usuario.nombre}</td>
            <td>${factura.dateCreated}</td>
            <td>${factura.cliente.nombre}</td>
            <td>${factura.montoNeto}</td>
            <td>
                <button style="text-decoration: none" type="button" class="reimprimirFactura btn btn-primary" id="${factura.id}">Reimprimir Factura</button>
            </td>
        </tr>

    </g:each>
    </tbody>

</table>
<button class="btn btn-danger btn-lg"  onclick="window.history.back();" >Cancelar Proceso</button>
%{--<a style="background-color: #4c59ff;color: #FFFFFF; ;width: 150px; height: 150px;" href="#" onclick="window.history.back();">Cancelar</a>--}%
</body>
</html>