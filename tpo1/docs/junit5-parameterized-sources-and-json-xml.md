# JUnit 5: источники аргументов и приём JSON / XML в параметризованных тестах

Документ про **junit-jupiter-params**: какие аннотации-«source» существуют в ядре JUnit 5, и **все разумные способы** подставлять тестовые данные в форматах JSON и XML (без обязательной привязки к одной библиотеке).

---

## 1. Встроенные источники (JUnit 5, `org.junit.jupiter.params.provider`)

| Аннотация | Кратко |
|-----------|--------|
| **`@ValueSource`** | Один «простой» тип: `String`, `int`, `long`, `double` и т.д. (массив литералов). |
| **`@NullSource`**, **`@EmptySource`**, **`@NullAndEmptySource`** | Специальные значения: `null`, пустая строка / пустой массив / пустая коллекция. |
| **`@EnumSource`** | Один enum и подмножество/все константы. |
| **`@MethodSource`** | `static` метод возвращает `Stream`, `Iterable`, массив или `Iterator` (часто `Stream<Arguments>`). Можно указать `ClassName#methodName`. |
| **`@CsvSource`** | Встроенные в аннотацию CSV-строки (`delimiter`, `textBlock` и т.д.). |
| **`@CsvFileSource`** | Те же отличия, что у CSV, но **из файла** в classpath (`resources`, `lineSeparator`, `numLinesToSkip`…). |
| **`@ArgumentsSource(MyProvider.class)`** | Собственный класс(ы), реализующие `ArgumentsProvider`. |
| **`@FieldSource`** (JUnit **5.11+**) | Ссылка на `static` поле с коллекцией, массивом или `Stream` аргументов. |
| **Композиция** | Несколько аннотаций на одном методе, например `@ValueSource` + `@NullSource` (где поддерживается). |

Типичные **импорты** ядра:

- `org.junit.jupiter.params.*`
- `org.junit.jupiter.params.provider.*`
- `org.junit.jupiter.params.converter.*` (опционально: `@ConvertWith`)

---

## 2. JSON в параметризованных тестах — «все» практичные варианты

JUnit **не** вставляет в ядро отдельной аннотации вроде `@JsonSource` (в отличие от `@CsvSource`). JSON почти всегда сводят к **чтению текста** + **доставке `Arguments`**.

### 2.1. `@MethodSource` + ручной разбор (или мини-утилита)

- Загрузка: `Class.getResource` / `getResourceAsStream`, `Files.readString`, `Path` из `module` или classpath.
- Парсинг: Jackson / Gson / `javax.json` (JSON-P) / `java.util` + regex, если договорились о простом подмножестве JSON.

**Плюс:** одна зависимость, полный контроль. **Минус:** парсер либо свой упрощённый, либо библиотека.

### 2.2. `@ArgumentsSource` + `ArgumentsProvider`

- Тот же разбор JSON, но **инкапсулирован** в отдельном классе(ах). Удобно, если наборы данных переиспользуются между тестовыми классами.

### 2.3. «Два чтения одного файла» (только доставка байтов, не формат)

Часто требуют **два способа** прочитать **один и тот же** `.json` с classpath:

1. `URL` + `Files.readString(Paths.get(url.toURI()))`
2. `getResourceAsStream` + `readAllBytes` + `new String(..., StandardCharsets.UTF_8)`

Семантически JSON один; отличается **API** доступа к ресурсу (NIO vs поток).

### 2.4. JSON как «таблица в строке» + `@MethodSource` без внешнего файла

- Java **text block** (`"""..."""`) с валидным JSON внутри — дальше тот же парсер или упрощённый разбор.
- **Плюс:** всё в одном классе. **Минус:** большие датасеты раздувают исходник.

### 2.5. «Псевдо-JSON» / JSON Lines в `@CsvFileSource` / плоский формат

- Если **по договорённости** строки файла = по одной записи, иногда **обходятся** `CsvFileSource` или `MethodSource` + `Files.lines` — но это уже **не стандартный JSON-объект** целиком, а **упрощённый формат** под тест (имя файла `.json` может вводить в заблуждение).

### 2.6. Сторонние расширения (не ядро JUnit)

- **JUnit Pioneer** и подобные проекты иногда дают удобные обёртки; состав аннотаций смотрите в документации **точной версии** артефакта.
- **Testcontainers**, **WireMock** и т.д. — JSON как ответы HTTP, аргументы строятся в `@MethodSource` / `@BeforeAll` (это уже интеграционный уровень, но **способ** «подать JSON в параметры» — тот же: прочитать и развернуть в `Stream<Arguments>`).

### 2.7. `ArgumentConverter` (редко для целого набора)

- `@ConvertWith` + `ArgumentConverter` удобен для **одного** сложного аргумента; для **множества** строк теста обычно остаются `MethodSource` / `ArgumentsSource`.

### 2.8. Сводка по JSON

| Подход | Ключевая JUnit-часть |
|--------|----------------------|
| Файл + ручной/библиотечный JSON | `MethodSource` / `ArgumentsSource` |
| Два API чтения ресурса | тот же, два метода/два `ArgumentsProvider` |
| JSON в исходнике | `MethodSource` (text block) |
| JUnit 5.11+ | при желании: `FieldSource` со `static` полем `List<Arguments>`, **заполненным** из JSON в `static {}` / `@BeforeAll` (редко) |

---

## 3. XML в параметризованных тестах

В ядре JUnit 5 **нет** `@XmlFileSource`. Варианты те же по идее: **текст → `Arguments`**.

### 3.1. `@MethodSource` + строка (text block) или файл

- Встроенный XML в `static final String` + **DOM** / **StAX** / **XPath** (JDK) или regex для **очень** простой разметки (хрупко).
- Либо файл `*.xml` в `src/test/resources` + `Files.readString` / поток, как для JSON.

### 3.2. `@ArgumentsSource` + `ArgumentsProvider`

- Вся логика чтения/разбора XML в провайдере (удобно переиспользовать).

### 3.3. `MethodSource` + JAXB / `javax.xml` / jOOX и т.д.

- **JDK:** `DocumentBuilder`, XPath — полноценный разбор; для учебы иногда **ограничивают** библиотеками/форматом.

### 3.4. «Два способа чтения» для XML-файла

Аналогично JSON:

- `URL` + NIO
- `InputStream` + `readAllBytes`

Разница только в **после**текстовом: парсер XML (или ad-hoc сопоставление тегов/атрибутов).

### 3.5. Сводка по XML

| Подход | Ключевая JUnit-часть |
|--------|----------------------|
| Файл на classpath + парсер | `MethodSource` / `ArgumentsSource` |
| XML целиком в коде | `MethodSource` |
| JUnit 5.11+ | `FieldSource` (если заранее собрали `Arguments`) |

---

## 4. Минимальная схема, общая для JSON и XML

1. **Получить `String` или `InputStream`** с classpath / файловой системы.
2. **Превратить** в `List<Arguments>` / `Stream<Arguments>` (`x`, `expected`, и т.д.).
3. Подцепить к тесту через **`@MethodSource`**, **`@ArgumentsSource`**, либо при JUnit 5.11+ **`@FieldSource`**.

**Пример** сигнатуры (идея, не копипаста под ваш проект):

```java
@ParameterizedTest
@MethodSource("casesFromJson")   // или @ArgumentsSource(JsonCases.class)
void test(double x, double y) { ... }
```

---

## 5. Ссылки (актуальные на момент написания)

- [JUnit 5 User Guide — Parameterized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
- Список провайдеров в исходниках: пакет `org.junit.jupiter.params.provider` (версия JUnit, указанная в `pom.xml` / `build.gradle`).

---

## 6. Таблица «что в ядре / что нет»

| Фича | В JUnit 5 out-of-the-box? |
|------|---------------------------|
| CSV-строки/файл | Да: `@CsvSource`, `@CsvFileSource` |
| JSON | **Нет** отдельной аннотации; только через `MethodSource` / `ArgumentsSource` / обходной формат |
| XML | **Нет**; только через `MethodSource` / `ArgumentsSource` / сторонние плагины |
| Свой провайдер | Да: `ArgumentsProvider` + `@ArgumentsSource` |

Если в задании требуют **«два способа»** для JSON или XML, обычно имеют в виду либо **два API чтения ресурса** (URL+Files vs InputStream), либо **два механизма JUnit** (`MethodSource` vs `ArgumentsSource`), либо **файл vs строка в коде** — уточните у преподавателя, какой смысл закреплён в курсе.
