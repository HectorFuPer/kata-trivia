# Entrega - Trivia Refactoring Kata

## Contenido del repositorio

Este repositorio contiene dos partes:

- `kata-trivia-java/`: version Java refactorizada paso a paso usando Golden Master.
- `kata-trivia-python/`: port final en Python basado en el diseno refactorizado.

La rama principal `main` contiene ambas versiones.

## Evidencia de proceso

Se trabajo con ramas separadas:

- `practica/trivia-refactoring`: refactorizacion Java.
- `practica/trivia-python`: conversion a Python.

El historial de commits muestra cambios pequenos y progresivos:

- Extraccion de constantes.
- Extraccion de metodos de flujo de turno.
- Creacion de `Player`.
- Creacion de `QuestionDeck`.
- Correccion del typo y del bug.
- Validaciones extra de jugadores.
- Port a Python.
- Tests de Python.

## Ejecutar tests Java

Desde la carpeta `kata-trivia-java`:

```powershell
& 'C:\Program Files\Apache\apache-maven-3.9.15\bin\mvn.cmd' test
```

Resultado esperado:

```text
BUILD SUCCESS
Tests run: 4, Failures: 0, Errors: 0, Skipped: 1
```

## Ejecutar tests Python

Desde la carpeta `kata-trivia-python`:

```powershell
python -m unittest discover -s tests
```

Resultado esperado:

```text
Ran 5 tests
OK
```

## Jugar la version Python

Desde la carpeta `kata-trivia-python`:

```powershell
python -m trivia.play_game
```

## Archivos relevantes

- `kata-trivia-java/REFACTORING_REPORT.md`: reporte breve de refactorizacion Java.
- `RESPUESTAS_PREGUNTAS.md`: respuestas a las preguntas del taller.
- `kata-trivia-python/README.md`: instrucciones de la version Python.

## Resumen tecnico

La version final separa responsabilidades:

- `Game`: coordina el flujo de juego.
- `Player`: mantiene estado del jugador.
- `QuestionDeck`: administra categorias y preguntas.

Tambien se corrigieron:

- Typo: `corrent` -> `correct`.
- Bug: el jugador decia salir de la caja de penalti, pero su estado interno seguia penalizado.

