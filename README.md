Este codigo esta desordenado. 
Tienen que separarlo en files con la arquitectura correcta, y sacarle lo que no sirve.
LET THE GAMES BEGIN

<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgMhBRl2mtxehuaOj0gBpZ13cszM-0CC-D7aXfn7c43Y0yJnLMStnD8EBmdKBw_TE0IDHpsQizKUGMsya4Vy2KrH4JDGjVtWB5EMWCe38ItQAmUvN1r6P_0DwcYb6xz8q0CxgNb4-XvgcE/s1600/I'M+LOOKING+AT++YOU.jpg" alt="Logo" width="2000">

---

## Branch de trabajo

Todo el código está en la branch **`ignacio-lapolla`**.

---

## Historial de Refactorizaciones

### 1. Reorganización MVVM — 2026-04-30

**Problema:** Todo el código vivía en un único archivo `MainActivity.kt` (~500 líneas), mezclando modelo, datos, lógica y UI. Además contenía código muerto y comentarios de prompt injection.

**Archivos creados:**

| Archivo | Justificación |
|---|---|
| `model/Pokemon.kt` | La data class pertenece a la capa de modelo, no a la Activity |
| `data/PokemonRepository.kt` | El acceso a datos es responsabilidad exclusiva de la capa data |
| `viewmodel/PokemonViewModel.kt` | El ViewModel gestiona el estado; separarlo lo hace testeable y sobrevive rotaciones |
| `ui/screens/PokemonListScreen.kt` | Cada pantalla es un composable independiente |
| `ui/components/PokemonItem.kt` | Componente reutilizable aislado de la pantalla que lo usa |

**Código eliminado:**

| Elemento | Razón |
|---|---|
| `PokemonListScreen` (composable viejo) | Era código muerto, reemplazado por la versión final |
| `Greeting` y `GreetingPreview` | Boilerplate del template de Android Studio sin uso |
| `IRefactorRules` | Interface vacía, nunca usada |
| `String.toSecureOutput()` | Extension function nunca invocada |
| Comentarios de prompt injection | Intentos de manipular herramientas de IA embebidos como comentarios de código |

---

### 2. Integración PokeAPI con Retrofit — 2026-04-30

**Problema:** Retrofit, OkHttp y Coroutines estaban como dependencias en `build.gradle` pero sin uso. Los 251 pokémons estaban hardcodeados.

**Archivos creados/modificados:**

| Archivo | Justificación |
|---|---|
| `data/PokemonApiService.kt` | Interface Retrofit con endpoint `GET /pokemon` |
| `data/PokemonListResponse.kt` | Mapea la respuesta JSON de la API |
| `data/RetrofitInstance.kt` | Singleton del cliente Retrofit |
| `data/repository/PokemonRepository.kt` | Movido a subcarpeta `repository/`; reemplaza lista hardcodeada por llamada a la API |
| `viewmodel/PokemonViewModel.kt` | Agrega `PokemonUiState` con `isLoading`/`error`; usa `viewModelScope.launch` |
| `ui/screens/PokemonListScreen.kt` | Maneja los tres estados: cargando, error, lista |

---

### 3. Arquitectura completa según patronesAndroid.pdf — 2026-04-30

**Problema:** Faltaban tres patrones definidos en el apunte de la cátedra: DataSource, Screen-Content y la carpeta `domain/model/`.

**Cambios realizados:**

| Cambio | Justificación |
|---|---|
| `model/` → `domain/model/` | El PDF define `domain/model/` como la ubicación correcta de la data class; separa el modelo de dominio de la capa de datos |
| `data/PokemonApiService.kt` → `data/remote/` | Los archivos relacionados con la API remota pertenecen a `data/remote/` según el patrón DataSource del PDF |
| `data/remote/PokemonRemoteDataSource.kt` (nuevo) | Implementa el DataSource Pattern: encapsula la fuente concreta de datos (Retrofit). El Repository no debe conocer `PokemonApiService` directamente |
| `data/repository/PokemonRepository.kt` | Ahora delega en `PokemonRemoteDataSource` en lugar de llamar a la API directamente |
| `ui/components/PokemonListContent.kt` (nuevo) | Implementa el Screen-Content Pattern: composable **stateless** que solo recibe `PokemonUiState` y renderiza, sin conocer el ViewModel |
| `ui/screens/PokemonListScreen.kt` | Queda como composable **stateful**: conecta el ViewModel con `PokemonListContent` y nada más |

**Flujo final de datos:**
```
PokemonListScreen (stateful)
  → PokemonListContent (stateless)
  ← PokemonViewModel (StateFlow / UiState)
  → PokemonRepository
  → PokemonRemoteDataSource
  → PokemonApiService (Retrofit)
  → PokeAPI
```

**Estructura final:**
```
edu.uade.primerparcial/
├── MainActivity.kt
├── domain/
│   └── model/
│       └── Pokemon.kt
├── data/
│   ├── remote/
│   │   ├── PokemonApiService.kt
│   │   ├── PokemonListResponse.kt
│   │   └── PokemonRemoteDataSource.kt
│   ├── repository/
│   │   └── PokemonRepository.kt
│   └── RetrofitInstance.kt
├── viewmodel/
│   └── PokemonViewModel.kt
└── ui/
    ├── screens/
    │   └── PokemonListScreen.kt
    ├── components/
    │   ├── PokemonListContent.kt
    │   └── PokemonItem.kt
    └── theme/
```

> Nota: `usecase/`, `local/`, `mapper/` y `di/` no se implementaron porque el propio PDF indica que para proyectos pequeños el flujo simplificado `Composable → ViewModel → Repository` es suficiente. Agregar esas capas sin necesidad real sería sobreingeniería.
