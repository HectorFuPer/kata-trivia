# Respuestas - Trivia Refactoring Kata

## Bloque 0 - Preparacion

### Resultado inicial del Golden Master

Antes de refactorizar se ejecuto el Golden Master de Java con Maven y paso correctamente.

Resultado esperado y obtenido:

```text
BUILD SUCCESS
```

Esto confirmo que `GameOld.java` y `Game.java` tenian el mismo comportamiento observable antes de empezar.

### Estructura del proyecto

- `Game.java`: clase objetivo de la refactorizacion.
- `GameOld.java`: oraculo del Golden Master.
- `PlayGame.java`: runner manual para entender el juego.
- `GameTest.java`: Golden Master que compara muchas partidas aleatorias.

## Bloque 1 - Analisis de Game.java

### 1. Olores de codigo detectados

1. Metodo demasiado largo:
   - `roll()` tenia demasiadas responsabilidades: imprimir turno, manejar penalti, mover jugador, calcular categoria y preguntar.

2. Nombres poco expresivos:
   - `places` no dejaba claro que eran posiciones de jugadores.
   - `purses` representaba monedas, pero el nombre era poco natural para el dominio.
   - `currentPlayer` era un indice, no el jugador actual.
   - `didPlayerWin()` devolvia lo contrario: `true` significaba que el juego continuaba.

3. Duplicacion:
   - Movimiento de jugadores repetido en turno normal y turno saliendo de penalti.
   - Avance al siguiente jugador repetido en respuestas correctas e incorrectas.
   - Impresion y suma de monedas duplicadas en `handleCorrectAnswer()`.

4. Arrays paralelos:
   - `players`, `places`, `purses` e `inPenaltyBox` dependian del mismo indice.
   - Esto indicaba una clase ausente: `Player`.

5. Numeros magicos:
   - `6`: maximo de jugadores y monedas para ganar.
   - `12`: tamano del tablero.
   - `50`: preguntas por categoria.

6. Mezcla de responsabilidades:
   - `Game` administraba jugadores, posiciones, monedas, penalti, mazos, categorias, turnos y salida por consola.

7. Colecciones sin genericos:
   - `ArrayList` y `LinkedList` se usaban sin tipo.

8. Logica de categorias dificil de extender:
   - `currentCategory()` tenia una cadena de `if` repetidos.

9. Typo:
   - Mensaje: `Answer was corrent!!!!`.

10. Bug:
   - Al salir de la caja de penalti, se imprimia que el jugador salia, pero el estado interno seguia marcado como penalizado.

### 2. Responsabilidades que debian separarse

- Estado del jugador:
  - nombre
  - posicion
  - monedas
  - estado de penalti

- Mazo de preguntas:
  - crear preguntas
  - guardar preguntas por categoria
  - entregar la siguiente pregunta
  - calcular categoria segun posicion

- Flujo de turno:
  - recibir tirada
  - manejar penalti
  - mover jugador
  - preguntar
  - procesar respuesta

- Control del juego:
  - agregar jugadores
  - validar reglas de alta
  - avanzar al siguiente jugador
  - determinar si alguien gano

### 3. Abstracciones ausentes

Se identificaron principalmente estas clases:

- `Player`
  - Encapsula nombre, posicion, monedas y penalti.
  - Expone comportamientos como `move`, `addCoin`, `sendToPenaltyBox` y `releaseFromPenaltyBox`.

- `QuestionDeck`
  - Encapsula preguntas y categorias.
  - Permite obtener la siguiente pregunta y calcular categoria por posicion.

- `Game`
  - Queda como orquestador del juego.
  - Coordina jugadores, turnos y respuestas.

### 4. Typo y bug encontrados

Typo:

```text
Answer was corrent!!!!
```

Correccion:

```text
Answer was correct!!!!
```

Bug:

Cuando un jugador estaba en la caja de penalti y tiraba un numero impar, el juego imprimia:

```text
<player> is getting out of the penalty box
```

Pero el estado interno `inPenaltyBox` no se actualizaba a `false`. Por eso el jugador seguia siendo tratado como penalizado en turnos futuros.

Correccion:

- En Java refactorizado se agrego `releaseFromPenaltyBox()` en `Player`.
- En `GameOld.java` se actualizo el arreglo `inPenaltyBox[currentPlayer] = false`.
- Se corrigio en ambos archivos porque el Golden Master compara `Game` contra `GameOld`.

## Bloque 2 - Itinerario de refactorizacion

El orden seguido fue de menor a mayor riesgo:

1. Verificar Golden Master en verde.
2. Crear rama de trabajo.
3. Extraer constantes para numeros magicos.
4. Extraer metodos pequenos del flujo de turno.
5. Tipar colecciones.
6. Crear `Player`.
7. Mover estado del jugador a `Player`.
8. Crear `QuestionDeck`.
9. Mover logica de categorias y preguntas a `QuestionDeck`.
10. Corregir typo y bug en `Game.java` y `GameOld.java`.
11. Simplificar `handleCorrectAnswer()`.
12. Agregar validaciones de jugadores.
13. Portar el diseno final a Python.

## Bloque 3 - Correccion del bug y typo

### Por que el Golden Master no detectaba el bug

El Golden Master compara la salida de `Game.java` con `GameOld.java`. Como ambos archivos tenian el mismo bug, el test no podia detectar que la regla del dominio era incorrecta.

Esto se conoce como un problema de oraculo corrupto: el test protege contra cambios accidentales de comportamiento, pero no prueba que el comportamiento original sea correcto.

### Orden correcto de correccion

Se corrigieron `Game.java` y `GameOld.java` para que ambos siguieran coincidiendo. Despues de la correccion el Golden Master siguio en verde.

## Bloque 4 - Retrospectiva

### Sobre Golden Master

El Golden Master dio seguridad para hacer refactorizaciones internas porque verificaba que la salida por consola se mantuviera igual en muchas partidas aleatorias.

El momento de mayor confianza fue despues de extraer `Player` y `QuestionDeck` manteniendo los tests en verde, porque esas eran las modificaciones estructurales mas importantes.

### Cambio que el Golden Master no detectaba

No detectaba el bug de la caja de penalti porque tanto la version nueva como el oraculo antiguo tenian el mismo error.

### Por que no escribir tests unitarios al inicio

Durante la refactorizacion, escribir tests unitarios muy pronto puede fijar detalles internos del diseno antiguo. El Golden Master permite cambiar la estructura libremente mientras se conserva el comportamiento observable. Los tests unitarios son mas utiles despues, cuando el diseno ya esta limpio.

### Olor mas dificil de eliminar

El olor mas dificil fue el de arrays paralelos, porque afectaba muchas partes del codigo: agregar jugadores, moverlos, sumar monedas, penalti y condicion de victoria.

### Refactorizacion manual mas arriesgada

La mas arriesgada fue mover estado a `Player`, porque cualquier error de indices o estado podia cambiar la salida de muchas partidas.

### Como mejoro el diseno

El diseno mejoro porque ahora cada clase tiene una responsabilidad clara:

- `Player` gestiona estado individual.
- `QuestionDeck` gestiona preguntas y categorias.
- `Game` coordina reglas y turnos.

Esto reduce acoplamiento y facilita nuevos requisitos.

## Cambio de requisito implementado

Se implementaron dos reglas adicionales:

1. Maximo de 6 jugadores.
2. No se permiten nombres duplicados.

Estas reglas se agregaron principalmente en la validacion de alta de jugadores. Tambien se agregaron tests para cubrirlas.

## Version Python

Despues de estabilizar Java, se creo una version Python con el mismo diseno:

- `trivia/game.py`
- `trivia/player.py`
- `trivia/question_deck.py`
- `trivia/play_game.py`

Se agregaron tests con `unittest`:

- Golden Master Python.
- Validacion de maximo de jugadores.
- Validacion de nombres duplicados.
- Prueba del bug corregido de penalti.

Comando:

```powershell
python -m unittest discover -s tests
```

Resultado esperado:

```text
Ran 5 tests
OK
```

## Conclusion

El resultado final cumple los objetivos principales del taller:

- Se uso Golden Master como red de seguridad.
- Se hicieron commits pequenos y trazables.
- Se eliminaron olores de codigo importantes.
- Se aplico SRP separando `Player`, `QuestionDeck` y `Game`.
- Se corrigieron typo y bug en ambos archivos Java.
- Se implementaron requisitos nuevos.
- Se entrego una version final en Python con pruebas.

