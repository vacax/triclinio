<!DOCTYPE html>
<html lang="en">

<head>
    <title>Bootstrap Example</title>
    <meta name="layout" content="main"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>



    %{--<style>--}%
        %{--.modal {--}%
            %{--display: none; /* Hidden by default */--}%
            %{--position: fixed; /* Stay in place */--}%

            %{--padding-top: 200px; /* Location of the box */--}%

            %{--width: 100%; /* Full width */--}%
            %{--height: 100%; /* Full height */--}%
        %{--}--}%

        %{--/* Modal Content */--}%
        %{--.modal-content {--}%
            %{--background-color: #fefefe;--}%
            %{--margin: auto;--}%
            %{--padding: 20px;--}%
            %{--border: 1px solid #888;--}%
            %{--width: 80%;--}%
        %{--}--}%
    %{--</style>--}%

    <style>
    /* The Modal (background) */
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        left: 0;
        top: 0;
        margin-left: 230px;

        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0); /* Black w/ opacity */
        -webkit-animation-name: fadeIn; /* Fade in the background */
        -webkit-animation-duration: 0.4s;
        animation-name: fadeIn;
        animation-duration: 0.4s
    }

    /* Modal Content */
    .modal-content {
        position: fixed;
        bottom: 0;
        background-color: #fefefe;
        width: 80%;
        -webkit-animation-name: slideIn;
        -webkit-animation-duration: 0.4s;
        animation-name: slideIn;
        animation-duration: 0.4s
    }

    /* The Close Button */
    .close {
        color: white;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: #000;
        text-decoration: none;
        cursor: pointer;
    }

    .modal-header {
        padding: 2px 16px;
        background-color: #007FFF;
        color: white;
    }

    .modal-body {padding: 2px 16px;}

    /* Add Animation */
    @-webkit-keyframes slideIn {
        from {bottom: -300px; opacity: 0}
        to {bottom: 0; opacity: 1}
    }

    @keyframes slideIn {
        from {bottom: -300px; opacity: 0}
        to {bottom: 0; opacity: 1}
    }

    @-webkit-keyframes fadeIn {
        from {opacity: 0}
        to {opacity: 1}
    }

    @keyframes fadeIn {
        from {opacity: 0}
        to {opacity: 1}
    }
    </style>

    <script type="application/javascript">
        $(document).ready(function() {
        $('select').on('change', function() {

            if(this.value===("tarjeta") || this.value===("credito")){

                $("#cantidad").hide();
                $("#procesar").hide();
                $("#cambio").hide();
                $("#labelCambio").hide();

            }else if (this.value==="efectivo"){
                $("#cantidad").show();
                $("#procesar").show();
                $("#cambio").show();
                $("#labelCambio").show();
            }

            var modal = document.getElementById('myModal');
            var span = document.getElementsByClassName("close")[0];


            $("#procesar").click(function() {

                var valor = Number($("#cantidad").val());
                var total = Number($("#cantidadDinero #montoNeto").text());

                if(valor < total){

                    modal.style.display = "block";

// When the user clicks on <span> (x), close the modal
                    span.onclick = function() {
                        modal.style.display = "none";
                    }

// When the user clicks anywhere outside of the modal, close it
                    window.onclick = function(event) {
                        if (event.target === modal) {
                            modal.style.display = "none";
                        }
                    }
                }else if(valor>total){
                    cambio=valor-total
                    $("#cantidadDinero").find("#cambio").text(cambio);
                }


            });


        })

        })

    </script>

</head>

<body>



<section class="content">
    <div class="row">
        <div class="col-md-6">
        <div class="panel panel-default" style="height:503px;">
            <div class="panel-heading"> Comida</div>
            <div class="panel-body">
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th class="bg-info">#</th>
                        <th class="bg-info">Comida</th>
                        <th class="bg-info">Cantidad</th>
                        <th class="bg-info">Precio/Unidad</th>
                    </tr>
                    </thead>

                    <tbody>
                    <g:each in="${factura.listaFacturaDetalle}" var="fac">
                        <tr>
                            <td>${fac.id}</td>
                            <td>${fac.ordenDetalle.nombrePlato}</td>
                            <td>${fac.ordenDetalle.cantidad}</td>
                            <td>${fac.ordenDetalle.precio}</td>


                        </tr>
                    </g:each>
                    </tbody>



                </table>
            </div>
        </div>
    </div>


        <div class="col-md-6">
            <div class="panel panel-default" style="height:503px;">
                <div class="panel-heading"> Forma Pago</div>
                <div class="panel-body">
                    <select class="form-control selcls">
                        <option>Seleccionar forma pago</option>
                        <option value="efectivo">Efectivo</option>
                        <option value="tarjeta">Tarjeta</option>
                        <option value="credito">Credito</option>
                    </select>
                    <div style="margin-top: 2%" class="input-group">
                        <input id="cantidad" type="text" class="form-control" placeholder="Entrar cantidad...">
                        <span class="input-group-btn">
                            <button id="procesar" class="btn btn-primary" type="button">Procesar</button>
                        </span>
                    </div>

                    <div style="padding-top: 5%; padding-left:4%" class="row">
                        <div class="table-responsive">

                            <table class="table" id="cantidadDinero" name="cantidadDinero">
                                <tr>
                                    <th style="font-size: 30px">Monto Bruto:</th>
                                    <td style="font-size: 30px">${factura.montoBruto}</td>
                                </tr>
                                <tr>
                                    <th style="font-size: 30px">Impuesto(${factura.porcientoImpuesto})</th>
                                    <td style="font-size: 30px">${factura.montoImpuesto}</td>
                                </tr>
                                <tr>
                                    <th style="font-size: 30px">Total</th>
                                    <td style="font-size: 30px" id="montoNeto" class="montoNeto">${factura.montoNeto}</td>
                                </tr>
                                <tr>
                                    <th id="labelCambio" style="font-size: 30px">Cambio</th>
                                    <td id="cambio" class="cambio" style="color: #ff5270;font-size: 30px"></td>
                                </tr>

                                <tr>

                                    <!-- The Modal -->
                                    <div id="myModal" class="modal">

                                        <!-- Modal content -->
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <span class="close">&times;</span>
                                                <h2>Importante</h2>
                                            </div>
                                            <div class="modal-body">
                                                <p>Ese valor debe ser mayor que el monto Total</p>

                                            </div>
                                        </div>

                                    </div>
                                </tr>
                            </table>

                        <!-- The Modal -->


                            <g:form style="padding-right: 50px;padding-left: 50px" action="imprimirFactura" id="${factura.id}" value="id=${factura.id}">
                                <button type="submit"  class="btn btn-primary btn-block btn-lng">Imprimir</button>
                            </g:form>



                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>




    </div>
</div>
</body>


</html>