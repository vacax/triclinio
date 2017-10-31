<g:applyLayout name="main">
    <content tag="encabezado">
        <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
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

            <g:if test="${tandaPlatos.valor=="0"}">

                <div class="info-box">
                    <span class="info-box-icon bg-yellow"><i class="fa fa-sun-o"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text"></span>
                        <span class="info-box-number">Habilitar Tanda Dia</span>
                        <g:link action="tandaPlatos" controller="app" ><button type="button" class="btn btn-block btn-warning btn-xs"><b>Presione aqui para activar</b></button></g:link>
                    </div>
                    <!-- /.info-box-content -->
                <!-- /.info-box -->
                </div>
            </g:if>
            </sec:ifAnyGranted>
            <g:if test="${tandaPlatos.valor=="1"}">
                <div class="info-box">
                    <span class="info-box-icon bg-blue"><i class="fa fa-star"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text"></span>
                        <span class="info-box-number">Habilitar Tanda Noche</span>
                        <g:link action="tandaPlatos" controller="app" ><button type="button" class="btn btn-block btn-blue btn-xs"><b>Presione aqui para activar</b></button></g:link>
                    </div>
                    <!-- /.info-box-content -->
                    <!-- /.info-box -->
                </div>
            </g:if>
        </div>
        <div class="row">
            %{--<div class="col-lg-5 col-xs-6">--}%
                <!-- small box -->
                <div class="small-box bg-green">
                    <div class="inner">
                        <h3>Cuenta<sup style="font-size: 20px"></sup></h3>

                        <p><small>Listado de cuentas abiertas</small></p>
                    </div>
                    <div class="icon">
                        <i class="fa fa-plus"></i>
                    </div>
                    <a href="/cuenta/nuevaCuenta" class="small-box-footer">Realizar nueva cuenta<i class="fa fa-arrow-circle-right"></i></a>
                </div>
            %{--</div>--}%
        </div>
        <div class="row">
            %{--<div class="col-lg-5 col-xs-6">--}%
                <!-- small box -->
                <div class="small-box bg-aqua">
                    <div class="inner">
                        <h3><triclinio:numeroCuentasAbiertas/></h3>

                        <p># Cuentas Abiertas</p>
                    </div>
                    <div class="icon">
                        <i class="fa fa-cutlery"></i>
                    </div>
                    <a href="/cuenta/cuentasAbiertas" class="small-box-footer">Ver cuenta abiertas <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            %{--</div>--}%

        </div>
        <div class="row">
            %{--<div class="col-lg-5 col-xs-6">--}%
            <!-- small box -->
            <div class="small-box bg-yellow">
                <div class="inner">
                    <h3>Desocupar mesa</h3>

                    <p><small>Listado de mesas ocupadas</small></p>
                </div>
                <div class="icon">
                    <i class="fa fa-table"></i>
                </div>
                <a href="/mesa/mesasOcupadasIndex" class="small-box-footer">Ver mesas ocupadas <i class="fa fa-arrow-circle-right"></i></a>
            </div>
            %{--</div>--}%

        </div>
    </content>

</g:applyLayout>