-- Dev seed script: wipes and reseeds without duplicates
-- WARNING: destructive; only use in development


TRUNCATE TABLE
  detalle_muestra_sismica,
  muestra_sismica,
  serie_temporal,
  cambio_estado,
  estado,
  evento_sismico,
  sismografo,
  estacion_sismologica,
  sesion,
  usuario,
  empleado,
  alcance_sismo,
  origen_de_generacion,
  clasificacion_sismo,
  tipo_de_dato
RESTART IDENTITY CASCADE;

-- 1) Tipos de dato
INSERT INTO tipo_de_dato (denominacion, nombre_unidad_medida, valor_umbral) VALUES
  ('Velocidad de onda','Km/seg', 1.0),
  ('Frecuencia De Onda','Hz', 15.0),
  ('Longitud','Km/ciclo', 1.0);

-- 2) Detalles de muestra: se generarán más abajo, vinculados a cada muestra (3 por muestra)

-- 3) Muestras sismicas
-- Insertar 36 muestras (3 por cada una de las 12 series)
INSERT INTO muestra_sismica (fecha_hora_muestra)
SELECT ts FROM (
  VALUES
    ('2025-06-14 13:48:34'::timestamp),
    ('2025-06-14 13:28:54'::timestamp),
    ('2025-06-14 12:28:54'::timestamp)
) base(ts)
CROSS JOIN generate_series(1,12) g;

-- 3.1) Crear 3 detalles por cada muestra (velocidad, frecuencia, longitud) y vincularlos por FK
INSERT INTO detalle_muestra_sismica (valor, tipo_de_dato_id, muestra_sismica_id)
SELECT v.valor,
       (SELECT id FROM tipo_de_dato WHERE denominacion = v.denom),
       m.id
FROM (VALUES
  (0.7, 'Velocidad de onda'),
  (10.0, 'Frecuencia De Onda'),
  (0.7, 'Longitud')
     ) AS v(valor, denom)
CROSS JOIN (SELECT id FROM muestra_sismica) AS m;

-- 4) Estados (SerieTemporal + EventoSismico)
INSERT INTO estado (ambito, nombre_estado) VALUES
  ('SerieTemporal','Completada'),
  ('EventoSismico','AutoDetectado'),
  ('EventoSismico','PendienteDeRevision'),
  ('EventoSismico','BloqueadoEnRevision'),
  ('EventoSismico','Rechazado'),
  ('EventoSismico','Confirmado');

-- 5) Series temporales (6), todas con estado 'Completada'
INSERT INTO serie_temporal (
  condicion_alarma, fecha_hora_inicio_registro_muestras, fecha_hora_registro, frecuencia_muestreo, estado_id
) VALUES
  (false, '2025-06-14 12:21:54', '2025-06-14 12:21:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:23:54', '2025-06-14 12:23:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:25:54', '2025-06-14 12:25:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:27:54', '2025-06-14 12:27:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:29:54', '2025-06-14 12:29:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:31:54', '2025-06-14 12:31:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:33:54', '2025-06-14 12:33:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:35:54', '2025-06-14 12:35:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:37:54', '2025-06-14 12:37:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:39:54', '2025-06-14 12:39:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:41:54', '2025-06-14 12:41:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada')),
  (false, '2025-06-14 12:43:54', '2025-06-14 12:43:54', '50 hz', (SELECT id FROM estado WHERE ambito='SerieTemporal' AND nombre_estado='Completada'));

-- 5.1) Relación serie -> muestras: asignar 3 muestras por serie (12 series)
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 0)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 0);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 1)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 3);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 2)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 6);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 3)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 9);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 4)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 12);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 5)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 15);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 6)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 18);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 7)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 21);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 8)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 24);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 9)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 27);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 10)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 30);
UPDATE muestra_sismica SET serie_temporal_id = (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 11)
  WHERE id IN (SELECT id FROM muestra_sismica ORDER BY id LIMIT 3 OFFSET 33);

-- 6) Estaciones sismologicas
INSERT INTO estacion_sismologica (codigo_estacion, documento_cerficacion_adq, fecha_solicitud_certificacion, latitud, longitud, nombre, nro_certificacion_adquisicion) VALUES
  ('AAA', NULL, NULL, -32.56, -94.56, 'Estacion Uno', NULL),
  ('AAB', NULL, NULL, -32.56, -94.56, 'Estacion Dos', NULL);

-- 7) Sismografos (2) y asignación de series (sin repetir series entre sismógrafos)
INSERT INTO sismografo (
  fecha_adquisicion, identificador_sismografo, nro_serie, estacion_sismologica_id, estado_actual_id
) VALUES
  (NULL, 1, NULL, (SELECT id FROM estacion_sismologica WHERE codigo_estacion='AAA'), NULL),
  (NULL, 2, NULL, (SELECT id FROM estacion_sismologica WHERE codigo_estacion='AAB'), NULL);

-- 7.1) Relación sismografo -> series (FK en serie_temporal)
UPDATE serie_temporal SET sismografo_id = (SELECT id FROM sismografo WHERE identificador_sismografo=1)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 1),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 3),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 5)
  );
UPDATE serie_temporal SET sismografo_id = (SELECT id FROM sismografo WHERE identificador_sismografo=2)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 0),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 2),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 4)
  );

-- 7.2) Completar asignación de sismógrafos para series 6..11
UPDATE serie_temporal SET sismografo_id = (SELECT id FROM sismografo WHERE identificador_sismografo=1)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 7),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 9),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 11)
  );
UPDATE serie_temporal SET sismografo_id = (SELECT id FROM sismografo WHERE identificador_sismografo=2)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 6),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 8),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 10)
  );

-- 8) Empleado, Usuario, Sesion
INSERT INTO empleado (apellido, mail, nombre, telefono) VALUES
  ('Gimenez','miltongimenez@gmail.com','Milton','3526062011');

INSERT INTO usuario (contrasenia, nombre_usuario, empleado_id) VALUES
  ('123','miltong', (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com'));

-- Sesion abierta (sin cierre)
INSERT INTO sesion (fecha_hora_inicio, fecha_hora_cierre, usuario_id) VALUES
  ('2025-06-20 10:00:00', NULL, (SELECT id FROM usuario WHERE nombre_usuario='miltong'));

-- 9) Catálogos: Alcance, Origen, Clasificacion
-- Alcance: locales, regionales, telesismos
INSERT INTO alcance_sismo (descripcion, nombre) VALUES
  ('Hasta 100 km', 'Local'),
  ('Hasta 1000 km', 'Regional'),
  ('Mas de 1000 km', 'Telesismo');

-- Origen de generación: interplaca, volcánico, explosiones
INSERT INTO origen_de_generacion (nombre, descripcion) VALUES
  ('Interplaca', 'Sismo por interacción de placas tectonicas'),
  ('Volcanico', 'Sismo asociado a actividad volcanica'),
  ('Explosion en minas', 'Sismo provocado por explosiones de minas');

-- Clasificación por profundidad: superficiales, intermedios, profundos
INSERT INTO clasificacion_sismo (km_profundidad_desde, km_profundidad_hasta, nombre) VALUES
  (0.0, 60.0, 'Superficial'),
  (61.0, 300.0, 'Intermedio'),
  (301.0, 650.0, 'Profundo');

-- 10) Cambios de estado (6)
INSERT INTO cambio_estado (fecha_hora_fin, fecha_hora_inicio, estado_id, empleado_responsable_id) VALUES
  (NULL, '2025-06-20 10:00:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='AutoDetectado'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com')),
  (NULL, '2025-06-20 10:01:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='PendienteDeRevision'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com')),
  (NULL, '2025-06-20 10:02:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='BloqueadoEnRevision'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com')),
  (NULL, '2025-06-20 10:03:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Rechazado'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com')),
  (NULL, '2025-06-20 10:04:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Confirmado'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com')),
  (NULL, '2025-06-20 10:05:00', (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Rechazado'), (SELECT id FROM empleado WHERE mail='miltongimenez@gmail.com'));

-- 11) Eventos sismicos (6)
INSERT INTO evento_sismico (
  fecha_hora_fin, fecha_hora_ocurrencia, latitud_epicentro, latitud_hipocentro, longitud_epicentro, longitud_hipocentro, valor_magnitud,
  estado_actual_id, clasificacion_id, origen_generacion_id, alcance_sismo_id, analista_superior_id
) VALUES
  (NULL, '2025-06-16 15:58:21', -21.53, -34.47, -21.72, -34.63, 6.9,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='BloqueadoEnRevision'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Intermedio'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Interplaca'),
   (SELECT id FROM alcance_sismo WHERE nombre='Regional'), NULL),
  (NULL, '2025-06-17 15:59:20', -22.10, -35.05, -22.30, -35.20, 5.8,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Rechazado'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Superficial'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Explosion en minas'),
   (SELECT id FROM alcance_sismo WHERE nombre='Local'), NULL),
  (NULL, '2025-06-16 16:58:21', -24.80, -33.90, -24.60, -34.10, 7.3,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='AutoDetectado'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Profundo'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Volcanico'),
   (SELECT id FROM alcance_sismo WHERE nombre='Telesismo'), NULL),
  (NULL, '2025-06-15 15:54:20', -23.60, -37.80, -21.90, -30.60, 8.2,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='PendienteDeRevision'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Superficial'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Interplaca'),
   (SELECT id FROM alcance_sismo WHERE nombre='Local'), NULL),
  (NULL, '2025-06-18 16:51:20', -20.45, -32.75, -20.55, -32.85, 6.1,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Confirmado'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Intermedio'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Explosion en minas'),
   (SELECT id FROM alcance_sismo WHERE nombre='Regional'), NULL),
  (NULL, '2025-06-19 15:58:20', -22.95, -36.25, -22.70, -36.10, 7.8,
   (SELECT id FROM estado WHERE ambito='EventoSismico' AND nombre_estado='Rechazado'),
   (SELECT id FROM clasificacion_sismo WHERE nombre='Profundo'),
   (SELECT id FROM origen_de_generacion WHERE nombre='Volcanico'),
   (SELECT id FROM alcance_sismo WHERE nombre='Telesismo'), NULL);

-- 11.1) Relación evento -> series (FK en serie_temporal) asigna 2 por evento
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 0)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 0),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 6)
  );
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 1)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 1),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 7)
  );
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 2)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 2),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 8)
  );
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 3)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 3),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 9)
  );
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 4)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 4),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 10)
  );
UPDATE serie_temporal SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 5)
  WHERE id IN (
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 5),
    (SELECT id FROM serie_temporal ORDER BY id LIMIT 1 OFFSET 11)
  );

-- 11.2) Relación evento -> cambio_estado (FK en cambio_estado)
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 0)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='BloqueadoEnRevision' LIMIT 1);
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 1)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='Rechazado' ORDER BY ce.fecha_hora_inicio ASC LIMIT 1);
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 2)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='AutoDetectado' LIMIT 1);
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 3)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='PendienteDeRevision' LIMIT 1);
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 4)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='Confirmado' LIMIT 1);
UPDATE cambio_estado SET evento_sismico_id = (SELECT id FROM evento_sismico ORDER BY id LIMIT 1 OFFSET 5)
WHERE id = (SELECT ce.id FROM cambio_estado ce JOIN estado es ON ce.estado_id=es.id WHERE es.ambito='EventoSismico' AND es.nombre_estado='Rechazado' ORDER BY ce.fecha_hora_inicio DESC LIMIT 1);
