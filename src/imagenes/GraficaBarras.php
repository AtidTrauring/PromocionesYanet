<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Gráfico de Valores Económicos 2024</title>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <style>
        #container {
            width: 90%;
            height: 500px;
            margin: 20px auto;
        }

        .x-label {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-size: 11px;
        }

        .x-label img {
            width: 40px;
            height: 40px;
            margin-bottom: 5px;
        }
    </style>
</head>

<body>
    <h1 style="text-align: center;">Gráfica de Valores Económicos (Año 2024)</h1>
    <div id="container"></div>

    <?php
    // Conexión a MySQL
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "empresa";
    $port = 3306;

    $conn = new mysqli($servername, $username, $password, $dbname, $port);
    if ($conn->connect_error) {
        die("Error de conexión: " . $conn->connect_error);
    }

    // Consulta SQL: obtiene empresas con su logo y valor económico en 2024
    $query = "
        SELECT e.nombre, e.direccionLogo, v.cantidad
        FROM ve v
        JOIN empresa e ON v.claveEmpresa = e.claveEmpresa
        JOIN anio a ON v.claveAnio = a.claveAnio
        WHERE a.anio = 2024;
    ";

    $result = $conn->query($query);
    $datos = [];

    while ($row = $result->fetch_assoc()) {
        $datos[] = [
            'nombre' => $row['nombre'],
            'logo' => $row['direccionLogo'],
            'cantidad' => (int) $row['cantidad']
        ];
    }

    $conn->close();

    // Convertir datos a JSON
    $json_datos = json_encode($datos);
    ?>

    <style>
        .x-label {
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            font-size: 11px;
            gap: 5px;
        }

        .x-label img {
            width: 10px;
            height: 10px;
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


    <script>
        const datos = <?php echo $json_datos; ?>;

        const categorias = datos.map(d => `
        <div class='x-label'>
            <span>${d.nombre}</span>
            <img src='http://localhost/Actividad9/${d.logo}' alt='${d.nombre}' />
        </div>
    `);

        const cantidades = datos.map(d => d.cantidad);

        Highcharts.chart('container', {
            chart: {
                type: 'column'
            },
            title: {
                text: 'Valor Económico por Empresa - 2024'
            },
            xAxis: {
                categories: categorias,
                labels: {
                    useHTML: true,
                    style: {
                        whiteSpace: 'nowrap',
                        textAlign: 'center'
                    },
                    step: 1 // Muestra todas las etiquetas
                }
            },
            plotOptions: {
                column: {
                    pointWidth: 23 // Ancho de las columnas para dejar más espacio
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Valor económico (Billones de dólares)'
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                headerFormat: '<b>{point.key}</b><br/>',
                pointFormat: 'Valor: {point.y}'
            },
            series: [{
                name: 'Valor Económico',
                data: datos.map((d, i) => ({
                    y: d.cantidad,
                    color: ['#7cb5aa', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#f9ac00'][i % 7]
                }))
            }]
        });
    </script>
    <a href="http://localhost/Actividad9/" class="btn-volver">
        <p>Volver</p>
    </a>
</body>

</html>