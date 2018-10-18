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

        <div class="box">
            <div class="box-header with-border" style="text-align: center">
                <h3 class="box-title"><b>CREAR NUEVA RESERVACION</b></h3>
            </div>

            <div class="box-body">
                <g:form action="save" method="post">
                    <h4>A nombe de:</h4>
                    <input class="form-control" name="aNombreDe" id="aNombreDe">
                    <h4>Cantidad de personas:</h4>
                    <input class="form-control" name="cantidadPersonas" id="cantidadPersonas" type="number" min="1">
                    <h4>Fecha:</h4>
                    <g:datePicker id="fecha" class="form-control" precision="" name="fecha" value="${new Date()}"
                                  noSelection="['': '-Choose-']" min="${new Date()}"/>
                    <h4>Observaciones:</h4>
                    <textarea maxlength="140" class="form-control" id="observaciones" name="observaciones">Default: N/A</textarea>
                    <br><br>
                    <input id="submit" style="width: 100%;" type="submit" value="Aceptar" class="btn btn-primary">
                </g:form>

            </div>
        </div>

    </div>
</section>

<script>
    $("#submit").on('click', function (e) {
        var fecha = $("#fecha_year").val() + "-" + $("#fecha_month").val() + "-" + $("#fecha_day").val() + " " + $("#fecha_hour").val() + ":" + $("#fecha_minute").val();
        console.log(fecha);
        console.log(new Date());
        if (new Date() >= new Date(fecha)) {
            e.preventDefault();
            alert('La Fecha de la Reservación tiene que ser mayor a la actual.');
        } else if (!$("#aNombreDe").val()) {
            e.preventDefault();
            alert('Debe ingresar un nombre a la reservación');
        } else if (!$("#cantidadPersonas").val()) {
            e.preventDefault();
            alert('La cantidad de personas debe ser un número mayor a 1.');
        }
    })
</script>

</body>
</html>



