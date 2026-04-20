# 🛒 Shopping List — Список покупок

Android-додаток «Список покупок», розроблений на Jetpack Compose з використанням сучасної архітектури.

---

## Функціонал реалізовано

### Основне (за завданням)
- **Список покупок** — перегляд, додавання, редагування та видалення товарів
- **Room Database** — локальне збереження даних між сесіями
- **Retrofit** — завантаження пропозицій товарів із публічного API (`dummyjson.com/products`)
- **Jetpack Compose** — сучасний декларативний UI

### Функції
| Функція | Опис |
|---|---|
| ➕ Додати товар | Діалог із назвою, кількістю та одиницею виміру |
| ✏️ Редагувати товар | Зміна назви, кількості та одиниці |
| 🗑️ Видалити товар | Підтвердження видалення з анімацією |
| ✅ Відмітити куплено | Тап на чекбокс — закреслення тексту |
| 📊 Статистика | Лічильник куплених/всього + прогрес-бар |
| 💡 Пропозиції | Горизонтальний ряд тегів із API |
| 🔄 Pull-to-refresh | Оновлення пропозицій потягуванням вниз |
| 🌐 Мови | Перемикання між українською та англійською |

---

## Власне додаткове доповнення

### 🎨 Красива анімація та UX
Всі 4 запропоновані покращення реалізовано одночасно:

1. **Анімація чекбоксів** — spring-bounce ефект при натисканні, зміна кольору картки та закреслення тексту з плавним переходом (`animateColorAsState`, `animateFloatAsState`)
2. **Редагування та видалення** — кнопки на кожній картці; при видаленні з'являється анімований підтвердний блок (`AnimatedVisibility` зі `slideInHorizontally`)
3. **Лічильник куплених товарів** — красива картка із `LinearProgressIndicator` та святкове повідомлення при 100%
4. **Pull-to-refresh** — Material 3 `PullToRefreshBox`, оновлює пропозиції з API
5. **Локалізація** — кнопка EN/УК у шапці перемикає мову через `AppCompatDelegate.setApplicationLocales()`

### Додатково
- **Динамічна колірна схема** (Material You) на Android 12+
- **Fade + slide** анімація появи елементів списку
- **Підтвердження видалення** прямо на картці (без окремого діалогу)
- **Випадкові пропозиції** — кожне оновлення завантажує різну сторінку API

---

## Архітектура

```
com.example.shoppinglist/
├── data/
│   ├── local/          # Room: ShoppingItem, DAO, Database
│   ├── remote/         # Retrofit: API, DTO
│   └── repository/     # ShoppingRepository
├── viewmodel/          # ShoppingViewModel (AndroidViewModel)
├── ui/
│   ├── theme/          # Material 3 кольори, типографія
│   ├── screen/         # ShoppingListScreen (головний екран)
│   └── components/     # ShoppingItemCard, AddItemDialog, EditItemDialog
└── MainActivity.kt
```

**Патерн:** MVVM + Repository
**DB:** Room (SQLite)
**API:** Retrofit → `https://dummyjson.com/products`
**UI:** Jetpack Compose + Material 3

---

## Труднощі

1. **Pull-to-refresh у Material 3** — `PullToRefreshBox` з'явився лише у `material3:1.2.0`, потребує точної версії в BOM
2. **Анімація списку** — `AnimatedVisibility` в `LazyColumn` з ключами (`key = { _, item -> item.id }`) для правильної ідентифікації елементів при зміні стану
3. **Перемикання мови** — `AppCompatDelegate.setApplicationLocales()` потребує `AppCompat 1.6+` і коректного оголошення локалей у `AndroidManifest.xml`
4. **StateFlow у Compose** — використання `collectAsStateWithLifecycle()` замість `collectAsState()` для lifecycle-aware збору потоків

---

## Запуск

1. Відкрити папку `ShoppingList` в **Android Studio Hedgehog** або новіше
2. Дочекатися синхронізації Gradle
3. Запустити на емуляторі або пристрої (Android 7.0+)

> API `dummyjson.com` потребує підключення до інтернету для пропозицій. Основна функціональність списку працює офлайн.
