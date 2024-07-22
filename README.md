# Тестовое задание для VK

Приложения конвертирует валюты, с помошью API [Abstract](https://www.abstractapi.com/api/exchange-rate-api)

### Сборка приложения
> [!IMPORTANT]
> Нужно добавить API ключ в файл **./local.properties**
> Получить ключ можно на сайте [Abstract API](https://www.abstractapi.com/api/exchange-rate-api)

```kotlin
API_KEY=api_key_here
```

## Screenshots - Light Theme

<img src="https://imgur.com/nXtGlp4.png" width="200"> <img src="https://imgur.com/liAIl79.png" width="200"> <img src="https://imgur.com/SisFMDP.png" width="200">

## Screenshots - Dark Theme

<img src="https://imgur.com/plAVePy.png" width="200"> <img src="https://imgur.com/fAFTiVu.png" width="200"> <img src="https://imgur.com/PRHmZf5.png" width="200"> 

### Технологический стек:
- Android SDK
- [Android Jetpack](https://developer.android.com/jetpack)
- [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation)
- [Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/)
- [MD3 Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)

### Основные модули
- api - модуль для работы с сетевыми запросами
- data - модуль для работы с данными
- features:* - все фичи приложения
- app - сборка приложения