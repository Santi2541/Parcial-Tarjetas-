# Gestión de Tarjetas (JavaFX + MySQL) — Estado de la tarjeta

Aplicación de escritorio en JavaFX con persistencia en MySQL (JDBC, patrón DAO).
Jerarquía `Tarjeta` (abstracta) → `Debito` / `Credito`, asociada a un `Titular`.

## Historia de usuario HU-01

**Como** analista de crédito, **quiero** asignar un estado a una tarjeta **para** controlar si puede ser utilizada.

### Criterios de aceptación
- La tarjeta debe poder estar en uno de estos estados: **Activa**, **Bloqueada** o **Cancelada**.
- El estado se almacena de forma persistente en la base de datos.
- El estado se visualiza en el formulario principal de la aplicación.
- El estado es un campo obligatorio al crear una tarjeta.
- No es posible guardar una tarjeta sin un estado válido.

## Estimación
**5 puntos de historia** (≈ 8 horas de trabajo).

## Tareas técnicas

| # | Tarea | Estimación |
|---|-------|-----------|
| 1 | Crear el enum `EstadoTarjeta` (ACTIVA, BLOQUEADA, CANCELADA) | 0.5 h |
| 2 | Modificar `Tarjeta`, `Debito`, `Credito`: reemplazar `boolean estado` por `EstadoTarjeta`, validar nulo, agregar `cancelar()` y `puedeUtilizarse()` | 1 h |
| 3 | Actualizar `ScriptDB.sql`: columna `estado ENUM(...) NOT NULL` sin valor por defecto, datos de prueba y migración | 1 h |
| 4 | Actualizar `DAOTarjeta` (INSERT y SELECT con el nuevo estado) | 1 h |
| 5 | Formulario (`formCard.fxml` + `ControlFormCard`): `ComboBox` de estado en *Crear* y etiqueta de estado en *Consultar* | 1.5 h |
| 6 | Validación: no guardar sin estado válido (modelo, controlador y BD) | 0.5 h |
| 7 | Pruebas unitarias (modelo) e integración (DAO) | 1.5 h |
| 8 | JavaDoc, diagrama UML actualizado, readme y video | 1 h |

## Cómo ejecutar

1. Ejecutar `ScriptDB.sql` en MySQL (crea la BD `examen2_soto` y los datos de prueba).
   Si la BD ya existía con el estado anterior (`TINYINT`), ejecutar el bloque *MIGRACIÓN HU-01* al final del script.
2. Crear el archivo `examen2_Soto/.env`:
   ```
   DB_URL=jdbc:mysql://localhost:3306/examen2_soto?useSSL=false&serverTimezone=UTC
   DB_USER=root
   DB_PASSWORD=tu_clave
   ```
3. Ejecutar la aplicación: `mvn clean javafx:run` (clase principal `co.edu.poli.examen2_Soto.vista.App`).
4. Ejecutar las pruebas: `mvn test` (las de integración requieren la BD activa).

## Estructura
- `modelo/`: `Tarjeta`, `Debito`, `Credito`, `Titular`, `EstadoTarjeta` (nuevo).
- `servicios/`: `ConexionBD`, `CRUD`, `DAOTarjeta`, `DAOTitular`.
- `controlador/` y `vista/`: formulario JavaFX.
- `src/test`: pruebas unitarias (`TestTarjeta`) e integración (`TestDAOTarjeta`, `TestDAOTitular`).

