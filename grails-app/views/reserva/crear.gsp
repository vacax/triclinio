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
        <g:form>
            <label>A Nombre De:
                <input class="form-control" name="aNombreDe">
            </label>
            <label>Cantidad de Personas:
                <input class="form-control" name="cantidadPersonas" type="number">
            </label>
            <label>Fecha
                <input class="form-control" name="fecha" type="date">
            </label>
            <label>Hora
                <input class="form-control" name="hora" type="time">
            </label>
        </g:form>
    </div>
</section>
</body>
</html>



