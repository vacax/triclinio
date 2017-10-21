<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido...</title>
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css"/>
    <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


    <script type="text/javascript">


        $(document).ready(function () {
            $('#example').dataTable({

                "aaSorting": [[1, "desc"]] // Sort by first column descending,
            });
        })

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
        Camarero
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
            %{--<td>${factura.listaFacturaDetalle.ordenDetalle.clienteCuenta.cuenta.listaMesa.numeroMesa.first()}</td>--}%
            <td><g:formatDate format="yyyy-MM-dd HH:mm:ss z" date="${factura.dateCreated}"/></td>
            <td>${factura.cliente.nombre}</td>
            <td>${factura.montoNeto}</td>
            <td>
                %{--<button style="text-decoration: none" type="button" class="verPerfil btn-link" id="${usuarios.id}">Ver usuario</button>--}%
                <g:link action="verDetalleFactura" controller="facturaDetalle"  params="[id: factura.id]"><button style="height: 30px;width: 80px" type="button" id="verPerfil" class="btn btn-link">Detalles de la factura</button></g:link>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<button class="btn btn-danger btn-lg"  onclick="window.history.back();" >Terminar</button>
</body>

</html>