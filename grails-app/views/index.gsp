<g:applyLayout name="main">
    <content tag="encabezado">
        <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    </content>

    <content tag="encabezado">
        %{--<h1>Sistema de Gestión de Restaurante</h1>--}%
        %{--<asset:image src="GUAVA.jpg" width="auto" height="auto" alt="Computer Hope" ></asset:image>--}%
    </content>

    <content tag="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
    </content>

    <content tag="contenido">
        <div class="row">
    <sec:ifAnyGranted roles="ROLE_ADMIN">

        <g:if test="${tandaPlatos.valor == "0"}">

            <div class="info-box">
                <span class="info-box-icon bg-yellow"><i class="fa fa-sun-o"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text"></span>
                    <span class="info-box-number">Habilitar Tanda Dia</span>
                    <g:link action="tandaPlatos" controller="app"><button type="button"
                                                                          class="btn btn-block btn-warning btn-xs"><b>Presione aqui para activar</b>
                    </button></g:link>
                </div>
                <!-- /.info-box-content -->
                <!-- /.info-box -->
            </div>
        </g:if>
    </sec:ifAnyGranted>
    <g:if test="${tandaPlatos.valor == "1"}">
        <div class="info-box">
            <span class="info-box-icon bg-blue"><i class="fa fa-star"></i></span>

            <div class="info-box-content">
                <span class="info-box-text"></span>
                <span class="info-box-number">Habilitar Tanda Noche</span>
                <g:link action="tandaPlatos" controller="app"><button type="button"
                                                                      class="btn btn-block btn-blue btn-xs"><b>Presione aqui para activar</b>
                </button></g:link>
            </div>
            <!-- /.info-box-content -->
            <!-- /.info-box -->
        </div>
    </g:if>
    </div>
    <div class="row" style="margin: 1%">
        <a href="/cuenta/nuevaCuenta">
            <!-- small box -->
            <div class="small-box bg-green">
                <div class="inner">
                    <h3>Abrir</h3>

                    <p><small>Abrir Cuenta Nueva</small></p>
                </div>

                <div class="icon">
                    <i class="fa fa-plus"></i>
                </div>
            </div>
        </a>
    </div>
%{--</div>--}%
    <div class="row" style="margin: 1%">
        <a href="/cuenta/cuentasAbiertas">
            %{--<div class="col-lg-5 col-xs-6">--}%
            <!-- small box -->
            <div class="small-box bg-aqua">
                <div class="inner">
                    <h3>Cuentas Abiertas</h3>

                    <p>Cuentas Abiertas: <triclinio:numeroCuentasAbiertas/></p>
                </div>

                <div class="icon">
                    <i class="fa fa-cutlery"></i>
                </div>
            </div>
        </a>
        %{--</div>--}%

    </div>

    <div class="row" style="margin: 1%">
        <a href="/mesa/mesasOcupadasIndex">
            %{--<div class="col-lg-5 col-xs-6">--}%
            <!-- small box -->
            <div class="small-box bg-yellow">
                <div class="inner">
                    <h3>Desocupar</h3>

                    <p><small>Desocupar Mesa</small></p>
                </div>

                <div class="icon">
                    <i class="fa fa-check"></i>
                </div>
            </div>
            %{--</div>--}%
    </div>
    </a>

    <g:if test="${authorities.contains('ROLE_RESERVADOR') || authorities.contains('ROLE_HOST') || authorities.contains('ROLE_ADMIN')}">
        <div class="row" style="margin: 1%">
            <a href="/reserva/index">
                <div class="small-box bg-red">
                    <div class="inner">
                        <h3>Reservaciones</h3>

                        <p><small>Gestionar Reservaciones</small></p>
                    </div>

                    <div class="icon">
                        <i class="fa fa-table"></i>
                    </div>
                </div>
            </a>
            %{--</div>--}%
        </div>
    </g:if>
    <g:else>
        <div class="row" style="margin: 1%">
            <a href="/usuarioReserva/index">
                <div class="small-box bg-maroon">
                    <div class="inner">
                        <h3>Reservaciones Pendientes de Camareros</h3>

                        <p><small>Reservaciones Pendientes de Camareros</small></p>
                    </div>

                    <div class="icon">
                        <i class="fa fa-table"></i>
                    </div>
                </div>
            </a>
            %{--</div>--}%
        </div>
    </g:else>


    </content>

</g:applyLayout>