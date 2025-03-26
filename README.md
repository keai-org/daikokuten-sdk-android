# daikokuten-android-sdk
El SDK de Android proporciona un widget de chat y una API de contexto para aplicaciones Kotlin a través de Gradle.

### Instalación

Agrega esto a tu `app/build.gradle`:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/keai-org/daikokuten-sdk-android")
        credentials {
            username = "github_username"
            password = "github_token"
        }
    }
    google()
    mavenCentral()
}

dependencies {
    implementation("com.daikokuten.sdk:daikokutensdk:0.1.20")
}
```

### Uso - Widget de Chat

Agrega el `ChatButtonView` a tu diseño:

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daikokuten.sdk.ChatButtonView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val rootLayout = findViewById<ConstraintLayout>(R.id.root_layout)
         val chatView = ChatButtonView(this, userId = "tuUserId", accessToken = "your token", testMode = false)

        rootLayout.addView(chatView)
    }
}
```

- **tuUserId**: Identificador del usuario (cadena, por defecto "user123").
- **tuAccessToken**: Token de acceso (cadena, por defecto "token").
- **testMode**: Modo de prueba (booleano, por defecto false).

### Uso - API de Contexto

Tambien enviar contexto con el ID del usuario y un ID de un evento, usa la accion "interest" para denotar que el usuario a mostrado interes en un evento o "subscribe" para denotar que esta apostando o requiere informacion o ayuda mas precisa.

```kotlin
import com.daikokuten.sdk.Daikokuten

Daikokuten.context("user123", "event456", "subscribe")
```

- **userId**: Identificador del usuario (cadena, requerido).
- **eventId**: Identificador del evento (cadena, requerido).
- **action**: Acción a reportar (cadena, requerido).

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/root_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Requisitos

- Android API 21+ (minSdk)
- Kotlin
- Gradle