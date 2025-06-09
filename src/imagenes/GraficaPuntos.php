<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Evolución Histórica de Empresas</title>

    <style type="text/css">
        #container {
            width: 2000px;
            margin: 0 auto;
        }

        .highcharts-figure {
            margin: 1em auto;
            width: 2000px;
        }

        .highcharts-data-table table {
            min-width: 360px;
            max-width: 800px;
            margin: 1em auto;
        }

        .highcharts-data-table table {
            font-family: Verdana, sans-serif;
            border-collapse: collapse;
            border: 1px solid #ebebeb;
            margin: 10px auto;
            text-align: center;
            width: 100%;
            max-width: 500px;
        }

        .highcharts-data-table caption {
            padding: 1em 0;
            font-size: 1.2em;
            color: #555;
        }

        .highcharts-data-table th {
            font-weight: 600;
            padding: 0.5em;
        }

        .highcharts-data-table td,
        .highcharts-data-table th,
        .highcharts-data-table caption {
            padding: 0.5em;
        }

        .highcharts-data-table thead tr,
        .highcharts-data-table tr:nth-child(even) {
            background: #f8f8f8;
        }

        .highcharts-data-table tr:hover {
            background: #f1f7ff;
        }

        .highcharts-description {
            margin: 0.3rem 10px;
        }

        .styled-button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .styled-button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        .styled-button:active {
            transform: scale(0.98);
        }

        .btn-volver {
            display: inline-block;
            background-color: #4CAF50;
            /* Verde */
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-family: Arial, sans-serif;
            font-size: 16px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-volver:hover {
            background-color: #45a049;
            /* Un tono más oscuro para el hover */
            transform: translateY(-2px);
        }
    </style>
</head>

<body>
    <script src="../Highcharts-12.2.0/code/highcharts.js"></script>
    <script src="../Highcharts-12.2.0/code/modules/series-label.js"></script>
    <script src="../Highcharts-12.2.0/code/modules/exporting.js"></script>
    <script src="../Highcharts-12.2.0/code/modules/export-data.js"></script>
    <script src="../Highcharts-12.2.0/code/modules/accessibility.js"></script>

    <figure class="highcharts-figure">
        <div id="container"></div>
        <p class="highcharts-description">
            Gráfica de líneas que muestra la evolución económica de distintas empresas entre los años 2014 y 2024.
            Cada línea representa una empresa específica y su comportamiento económico a lo largo del tiempo,
            expresado en billones de dólares. Esta gráfica utiliza el módulo <code>series-label</code>,
            que añade etiquetas automáticas a cada línea para facilitar su identificación.
            Puede hacer clic sobre alguno de los elementos de las leyendas para mostrar u ocultar las líneas
            correspondientes, permitiendo una visualización más personalizada.
        </p>
    </figure>

    <?php
    include("conexion.php");
    $link = Conectarse();

    $query = "
            SELECT 
                empresa.nombre AS nombre_empresa,
                anio.anio AS anio,
                empresa.direccionLogo AS logo,
                ve.cantidad AS cantidad
            FROM anio
            INNER JOIN ve ON anio.claveAnio = ve.claveAnio
            INNER JOIN empresa ON ve.claveEmpresa = empresa.claveEmpresa
            ORDER BY empresa.nombre ASC, anio.anio ASC
        ";

    $result = mysqli_query($link, $query);

    $seriesData = [];
    $logos_empresa = [];

    while ($row = mysqli_fetch_assoc($result)) {
        $empresa = $row["nombre_empresa"];
        $anio = (int) $row["anio"];
        $cantidad = (float) $row["cantidad"];
        $logo = $row["logo"];

        // Guardar logo por empresa
        if (!isset($logos_empresa[$empresa])) {
            $logos_empresa[$empresa] = $logo;
        }

        // Agregar datos por empresa
        if (!isset($seriesData[$empresa])) {
            $seriesData[$empresa] = [];
        }

        $seriesData[$empresa][] = [$anio, $cantidad];
    }

    // Construir series con logo
    $seriesJSON = [];

    foreach ($seriesData as $empresa => $valores) {
        $seriesJSON[] = [
            "name" => $empresa,
            "data" => $valores,
            "custom" => [
                "logo" => $logos_empresa[$empresa]
            ]
        ];
    }

    // Convertir a JSON
    $seriesJS = json_encode($seriesJSON);
    ?>

    <script type="text/javascript">
        Highcharts.chart('container', {

            chart: {
                height: 800
            },

            title: {
                text: 'Evolución económica de las empresas (Periodo 2014-2024)',
                align: 'center'
            },

            subtitle: {
                text: 'Evolución del costo de las empresas en un periodo determinado',
                align: 'center'
            },

            yAxis: {
                min: 0,
                max: 4000,
                tickInterval: 500,
                title: {
                    text: 'Valor económico (Billones de dólares)'
                }
            },

            xAxis: {
                accessibility: {
                    rangeDescription: 'Rango: 2014 a 2024'
                }
            },

            tooltip: {
                useHTML: true,
                formatter: function () {
                    const logo = this.series.userOptions.custom.logo;
                    return `
                <div style="text-align: center;">
                    <strong>${this.series.name}</strong><br/>
                    <img src= "http://localhost/Actividad9/${logo}" width="20" height="20" style="margin-top: 5px;" /><br/>
                    Año: ${this.x}<br/>
                    Valor: ${this.y} Billones de dólares
                </div>
            `;
                }
            },

            legend: {
                useHTML: true,
                labelFormatter: function () {
                    const logo = this.userOptions.custom.logo;
                    return `
                <div style="display: flex; align-items: center; gap: 5px;">
                    <img src="http://localhost/Actividad9/${logo}" width="20" height="20" />
                    ${this.name}
                </div>
            `;
                }
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010
                }
            },

            series: <?php echo $seriesJS; ?>,

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        legend: {
                            layout: 'horizontal',
                            align: 'center',
                            verticalAlign: 'bottom'
                        }
                    }
                }]
            }

        });

        let allVisible = true;

        function toggleAllSeries() {
            const chart = Highcharts.charts[0]; // Toma el primer gráfico
            chart.series.forEach(s => {
                s.setVisible(!allVisible, false); // Cambia la visibilidad
            });
            chart.redraw(); // Redibuja todo

            // Cambia el texto del botón
            const btn = document.getElementById("toggleAllBtn");
            btn.textContent = allVisible ? "Mostrar todas" : "Ocultar todas";
            allVisible = !allVisible;
        }

    </script>

    <div style="text-align: center; margin: 1em;">
        <button id="toggleAllBtn" onclick="toggleAllSeries()" class="styled-button">Ocultar todas</button>
    </div>
    <a href="http://localhost/Actividad9/" class="btn-volver">
        <p>Volver</p>
    </a>
</body>

</html>