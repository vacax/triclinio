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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

</head>
<body>

<section class="content">
    <div class="row">
        <div class="row">
        %{--<div    class="col-xs-8">--}%
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title"><b>LISTADO DE NOTIFICACIONES</b></h3>

                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive no-padding">
                        <table id="example" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                            <tbody><tr>
                                <th>DESCRIPCION</th>
                                <th>USUARIO</th>
                                <th>FECHA</th>
                            </tr>
                            <g:each in="${notificaciones}" var="notificacion">
                                <tr>
                                    <td>${notificacion.usuario.nombre}</td>
                                    <td>${notificacion.descripcion}</td>
                                    <td>${notificacion.fecha}</td>
                                </tr>
                            </g:each>

                            </tbody></table>

                    </div>
                    <!-- /.box-body -->
                </div>
        <!-- /.box -->
        %{--</div>--}%
        </div>
    </div>
    <!-- /.row -->

</section>

</body>

</html>