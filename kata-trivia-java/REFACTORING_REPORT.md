# Trivia Refactoring Kata - Reporte

## Golden Master

- Rama de trabajo: `practica/trivia-refactoring`
- Baseline inicial verificado con `mvn test`.
- Cada cambio de produccion se hizo en pasos pequenos y se confirmo con `mvn test` antes del commit.
- Resultado final: `BUILD SUCCESS`, 4 tests, 0 failures, 0 errors, 1 skipped.

## Olores detectados

- Arrays paralelos para nombre, posicion, monedas y penalti.
- `Game` mezclaba responsabilidades de jugador, tablero, mazos, turno y salida por consola.
- Numeros magicos: `6`, `12`, `50`.
- Duplicacion al mover jugadores, avanzar turnos y premiar respuestas correctas.
- Metodo `roll()` demasiado largo y con ramas anidadas.
- Metodo `currentCategory()` con muchos `if` repetidos.
- Colecciones sin tipos genericos.
- Typo en el mensaje `Answer was corrent!!!!`.

## Refactorizaciones principales

- Se extrajeron constantes de dominio: maximo de jugadores, tamano del tablero, monedas para ganar y preguntas por categoria.
- Se introdujo `Player` para encapsular nombre, posicion, monedas y estado de penalti.
- Se introdujo `QuestionDeck` para construir mazos, entregar preguntas y calcular la categoria por posicion.
- Se simplifico `roll()` con metodos de flujo de turno y penalti.
- Se simplifico `handleCorrectAnswer()` eliminando duplicacion.
- Se encapsularon campos internos de `Game`.

## Bug y typo

- Typo corregido en `Game.java` y `GameOld.java`: `corrent` -> `correct`.
- Bug corregido en ambos archivos: cuando un jugador salia de la caja de penalti, el mensaje decia que salia, pero el estado interno seguia marcado como penalizado. Ahora se libera del penalti al obtener un tiro impar para salir.
- El Golden Master por si solo no detectaba el bug porque `GameOld` tenia el mismo error que `Game`: era un oraculo corrupto.

## Cambio de requisito

- Se agregaron validaciones al alta de jugadores:
  - Maximo de 6 jugadores.
  - No se permiten nombres duplicados.
- Se agrego `GameRulesTest` para cubrir estas reglas nuevas.

## Comando de verificacion

```powershell
& 'C:\Program Files\Apache\apache-maven-3.9.15\bin\mvn.cmd' test
```
