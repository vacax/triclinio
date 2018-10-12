<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta charset="utf-8"/>
    <title>Bienvenido...</title>
    %{--<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css'>--}%
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/select/1.2.3/js/dataTables.select.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet"/>
</head>

<body>

<section class="content">
    <div class="row">
        <div class="row">
            <div class="box">
                <div class="box-header with-border" style="text-align: center">
                    <h3 class="box-title"><b>LISTA DE RESERVACIONES PENDIENTES</b></h3>
                </div>

                <div class="box-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th style="text-align: center">Nombre</th>
                            <th style="text-align: center">Cantidad Personas</th>
                            <th style="text-align: center">Fecha y Hora</th>
                            <th style="text-align: center">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${pendientes}" var="r">
                            <tr>
                                <td style="text-align: center">${r.aNombreDe}</td>
                                <td style="text-align: center">${r.cantidadPersonas}</td>
                                <td style="text-align: center">${r.fecha}</td>
                                <td style="text-align: center">
                                    <a href="abrirCuenta/${r.id}" class="btn btn-success">Abrir cuenta</a>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
</script>
</body>
</html>



