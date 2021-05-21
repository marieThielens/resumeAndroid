# Memento Android Studio

## Les différents dossiers / fichiers

### Manifests
Contient un seul fichier *AndroidManifest.xml* . C'est la carte d'identité de notre application. C'est là que je vais rajouter mes droits (ex pour passer un coup de fil)

### **Dossier src**

- **MainActivity.java** : l'activitée principale de mon application. Une activité va contenir l'ensemble des éléments graphyques du type champ texte, bouton ..

### **Dossier res**
Contient toutes les ressources de l'application
- **drawable** : les images 
- **layout** : contient les fichiers layout, la mise en page de notre app. Un contenueur de vues
- **mipmap** : Les îcones
- **values** : contient les paramétrages et valeurs. Les couleurs, traductions

## Petite intro

-  **Toast** : petite fenetre popup en bas de l'écran
        `Toast.makeText(getApplicationContext(), "Bonjour", Toast.LENGTH_LONG).show();`
- **Ajuster le clavier** :pour que quand le clavier s'ouvre le texte remonte. Dans le **Manifest** rajouter cette ligne `android:windowsSoftInputMode="ajustResize"`

#### Layout exemple

Type de layout : 
- LinearLayout : éléments les uns à la suite des autres dans le sens horizontal ou vertical. Attention il faut ne pas oublier l'orientation
- RelativeLayout : positionner les éléments les uns par rapport aux autres

Type de balise :
- Text : pour du texte
- EditText : un input, une zone de saisie
- Button : un boutton. Il faut lui donner un id pour pouvoir le récupéere après. 

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:orientation="vertical"
    android:background="#3F3F3F"
    >
    <!-- text: variable qui se trouvera dans le fichier strings.xml (dans dossier values) -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/titre_saluer"
     />
    </LinearLayout>
```
- **dimension** : **dp** pour images sp pour la police.  **sp** d'adapte si jamais un utilisateur à mis le mode mal voyant.

- **:match_parent** : l"élément prend la même taille que son parent
- **:wrap_content** : Prend la taille qu'on lui donne ou la taille par defaut de son contenu

- **:layoutGravity** : Par rapport à son parent. C'est comme du margin
- **:gravity** : A l'intérieur. Comme du padding
- **:layout_marginVertical** : combianaison du margin top et margin bottom

- **:imeOptions="actionDone"** : Avoir le bouton valider vert (v) dans le clavier

## Rendre un bouton cliquable

- **findViewById(R.id.btn_login_connect)** : **R** (ressource). Une classe java qui contient l'ensemble des identifiants de toutes les resources du projet. Ces ressources peuvent être un layout, un texte. Le type renvoyé par **findViewById()** est une view.

```java
// ne pas oublier d'implémenter pour le click
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Button btnLogin, btnCancel;

    // Lier, récupérer mes éléments( visuel) à l'aide de l'id. R (ressource) c'est une classe. La classe de l'id
    btnLogin = findViewById(R.id.btn_login_connect);
    btnCancel = findViewById(R.id.btn_Cancel_connect);
    
    // Ecouter mes boutons
    btnLogin.setOnClickListener(this); 
    btnCancel.setOnClickListener(this);
     // Ce qui se passe après avoir cliqué sur l'un des boutons
    @Override
    public void onClick(View v) { // View v represente le bouton qui a été cliqué
        switch(v.getId(R.id.btn_login_connect)) { // Je cible mon bouton à partir de son id
            case R.id.btn_login_connect:
            Toast.makeText(getApplicationContext(), "Vous allez être redirigé", Toast.LENGTH_LONG).show();
            break;

            case R.id.btn_Cancel_connect:
            Toast.makeText(getApplicationContext(), "Au revoir", Toast.LENGTH_LONG).show();
        }
    }
}
```

## Fichier colors.xml ( dans dossier value)

- Rajouter nos propres couleurs

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="JauneMoche">#FFF1E159</color>
    <color name="blanc">#FFFFFFFF</color>
</resources>
```

- Les utiliser dans notre layout
`android:textColor=@color/jaune`

- A faire creer notre propre style / intents / Passer un coup de fil / Liste et recycler view