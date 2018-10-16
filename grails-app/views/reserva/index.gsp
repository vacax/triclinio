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
            <g:link class="btn btn-warning" style="width: 100%;" action="crear"><b>CREAR RESERVACIÓN</b></g:link>
            <g:link class="btn btn-success" style="width: 100%;"
                    action="historial"><b>HISTORIAL DE RESERVACIONES</b></g:link>
        </div>
        <hr>

        <div class="row">
            <div class="box">
                <div class="box-header with-border" style="text-align: center">
                    <h3 class="box-title"><b>LISTA DE RESERVACIONES</b></h3>
                </div>

                <div class="box-body">
                    <table id="tabla_reservaciones" class="table table-hover">
                        <thead>
                        <tr>
                            <th style="text-align: center">Nombre</th>
                            <th style="text-align: center">Cantidad Personas</th>
                            <th style="text-align: center">Fecha y Hora</th>
                            <th style="text-align: center">Observaciones</th>
                            <th style="text-align: center">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${reservas}" var="r">
                            <tr>
                                <td style="text-align: center">${r.aNombreDe}</td>
                                <td style="text-align: center">${r.cantidadPersonas}</td>
                                <td style="text-align: center">${r.fecha}</td>
                                <td style="text-align: center"><button type="button" class="btn btn-info"
                                                                       data-toggle="modal"
                                                                       data-target="#modalObserv${r.id}">Ver</button>
                                </td>
                                <td style="text-align: center">
                                    <g:if test="${r.estado != 1002}">
                                        <button type="button" class="reserv_button btn btn-info"
                                                data-toggle="modal"
                                                data-target="#myModal${r.id}">Asignar Mesero</button>

                                        <a class="btn btn-danger" href="cancelar/${r.id}">Cancelar</a>
                                    </g:if>
                                    <g:else>
                                        <a class="btn btn-warning" href="activar/${r.id}">Activar</a>
                                    </g:else>
                                </td>
                            </tr>

                            <div id="modalObserv${r.id}" class="modal fade" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content" style="word-wrap: break-word;">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Observaciones de ${r.aNombreDe}</h4>
                                        </div>

                                        <div class="modal-body">
                                            <p>${r.observaciones}</p>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-danger"
                                                    data-dismiss="modal">OK</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="myModal${r.id}" class="modal fade" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Reservación de ${r.aNombreDe}</h4>
                                        </div>

                                        <div class="modal-body">
                                            <p>A qué camarero irá la reservación?</p>
                                            <select class="select_camareros">
                                                <option>Seleccione un camarero</option>
                                                <g:each in="${camareros}" var="c">
                                                    <option value="${c.id}">${c.nombre}</option>
                                                </g:each>
                                            </select>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-danger"
                                                    data-dismiss="modal">Cancelar</button>
                                            <button class="btn btn-success" id="aprobar_reservacion_button"
                                                    onclick="aprobar(${r.id})">Aceptar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <input value="0" id="selected_reservacion" hidden/>
    <input value="0" id="selected_camarero" hidden/>
</section>

<script>

    $("#tabla_reservaciones").DataTable();

    $('.select_camareros').change(function (e) {
        $('#selected_camarero').val(this.value);
    });

    function aprobar(reservacion_id) {
        $(location).attr('href', '/reserva/aprobar/?reserv=' + reservacion_id + '&camarero=' + $('#selected_camarero').val())
    }
</script>
</body>
</html>



