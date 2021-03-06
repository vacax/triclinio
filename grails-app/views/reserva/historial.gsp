<!DOCTYPE html>
<meta name="layout" content="main"/>
<html>
<head>
    <meta name="layout" content="main"/>
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
            <g:link class="btn btn-warning" style="width: 100%;" action="index"><b>LISTA DE RESERVACIONES</b></g:link>
        </div>
        <hr>

        <div class="row">
            <div class="box">
                <div class="box-header with-border" style="text-align: center">
                    <h3 class="box-title"><b>HISTORIAL DE RESERVACIONES</b></h3>
                </div>

                <div class="box-body">
                    <table id="tabla_historial" class="table table-hover">
                        <thead>
                        <tr>
                            <th style="text-align: center">Nombre</th>
                            <th style="text-align: center">Cantidad Personas</th>
                            <th style="text-align: center">Fecha y Hora</th>
                            <th style="text-align: center">Camarero Asignado</th>
                            <th style="text-align: center">Estado</th>
                            <th style="text-align: center">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${reservas}" var="r">
                            <tr>
                                <td style="text-align: center">${r.aNombreDe}</td>
                                <td style="text-align: center">${r.cantidadPersonas}</td>
                                <td style="text-align: center">${r.fecha}</td>
                                <td style="text-align: center">
                                    <p>
                                        <g:if test="${usuarioReservas[r.id]}">
                                            ${usuarioReservas[r.id]}
                                        </g:if>
                                        <g:else>N/A</g:else>
                                    </p>
                                </td>
                                <td style="text-align: center">
                                    <g:if test="${r.estado == 1001}">
                                        <p class="btn-warning">ACTIVO</p>
                                    </g:if>
                                    <g:if test="${r.estado == 1002}">
                                        <p class="btn-danger">CANCELADA</p>
                                    </g:if>
                                    <g:if test="${r.estado == 1003}">
                                        <p class="btn-success">APROBADA</p>
                                    </g:if>
                                    <g:if test="${r.estado == 1004}">
                                        <p class="btn-warning">PENDIENTE</p>
                                    </g:if>
                                </td>
                                <td class="text-center">
                                    <g:if test="${r.estado == 1004}">
                                        <g:link class="btn btn-primary" action="retornar" controller="reserva"
                                                params="[reservaId: r.id]">Retornar</g:link>
                                    </g:if>
                                    <g:else>
                                        <p>-</p>
                                    </g:else>
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
    $("#tabla_historial").DataTable()
</script>
</body>
</html>



