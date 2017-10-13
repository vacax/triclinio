<!DOCTYPE html>
<html lang="en">

<head>
    <title>Bootstrap Example</title>
    <meta name="layout" content="main"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <script type="application/javascript">0
        $(document).ready(function() {
        $('select').on('change', function() {

            if(this.value===("tarjeta") || this.value===("credito")){

                $("#cantidad").hide();
                $("#procesar").hide();
            }else if (this.value==="efectivo"){
                $("#cantidad").show();
                $("#procesar").show();
            }
            //alert(this.value);


            $("#procesar").click(function() {
                valor = $("#cantidad").val();
                alert(valor);
                total= $("#cantidadDinero #montoNeto").text();
                alert(total)
                cambio=valor-total
                total= $("#cantidadDinero #cambio").text(cambio);
            });


        })

        })

    </script>

</head>

<body>

<div class="row">
    <div class="col-md-4">
        <div class="panel panel-default" style="height:450px;width:500px;margin-top: 10%;margin-left:2%;  border-color: #ddd;">
            <div class="panel-heading"> Comida</div>
            <div class="panel-body">
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th class="bg-info">#</th>
                        <th class="bg-info">Comida</th>
                        <th class="bg-info">Precio/Unidad</th>
                    </tr>
                    </thead>

                    <tbody>
                    <g:each in="${factura.listaFacturaDetalle}" var="fac">
                        <tr>
                            <td>${fac.id}</td>
                            <td>${fac.ordenDetalle.nombrePlato}</td>
                            <td>${fac.ordenDetalle.precio}</td>
                            %{--<td>${fac.listaFacturaDetalle.ordenDetalle.nombrePlato}</td>--}%
                            %{--<td>${fac.listaFacturaDetalle.ordenDetalle.precio}</td>--}%
                        </tr>
                    </g:each>
                    </tbody>

                </table>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="panel panel-default" style="height:450px;width:500px;margin-top: 10%;margin-left:50%; border-color: #ddd;">
            <div class="panel-heading"> Forma Pago</div>
            <div class="panel-body">
                <select style="color: white;background-color: #5148ff; width: 200px;height: 40px">
                    <option>Seleccionar forma pago</option>
                    <option value="efectivo">Efectivo</option>
                    <option value="tarjeta">Tarjeta</option>
                    <option value="credito">Credito</option>
                </select>
                <input  type="text" id="cantidad" style="margin-top: 2%;height: 40px">
                <button style="height: 40px" class="btn btn-primary" id="procesar">Procesar</button>

                <div style="padding-top: 10%; padding-left:4%" class="row">
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
                                <th style="font-size: 30px">Cambio</th>
                                <strong ><td id="cambio" class="cambio" style="color: #ff5270;font-size: 30px"></td></strong>
                            </tr>
                        </table>
                        <button style=" align-self: center;" class="btn btn-primary btn-block">Imprimir</button>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>