#Les intents

- transmission de données entre les activités

## Intent sur un bouton pour aller d'une page à l'autre ( sans transmettre d'info)

- J'ai un bouton qui va vers mon autre activité (page )
- J'ai un bouton qui va vers un site web externe

### Le layout de activity_main.xml

- Juste un bouton avec un id

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:gravity="center_vertical">
            <!--   dp : pour les mal voyant. S'adapte         -->
            <Button
                android:id="@+id/btn_entrer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Clique moi"
                />
            <Button
                android:id="@+id/btn_externe"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Aller sur un site externe"
                />
        </LinearLayout>
</RelativeLayout>
```

### Le fichier MainActivity

- Ecouter mes boutons et lancer l'activitée ou aller sur un site externe
    - **setData()** : envoyer les données à l'intent
    - **Uri** : Uri est une chaine de caractère qui permet d'identifier un endroit
    - **Uri.parse()** : crée un nouvel Uri objet à partir d'un string

    - **ACTION_VIEW** : si on veut voir quelque chose. Permet de visionner une donnée. Quand on lance un ACTION_VIEW avec une adresse internet, c'est le navigateur qui se lance, et quand on lance un ACTION_VIEW avec un numéro de téléphone, c'est le composeur de numéros qui se lance.
    - **ACTION_DIAL** :  pour ouvrir le composeur de numéros téléphoniques
    - **ACTION_DELETE*** : Un URI vers les données à supprimer
    - **ACTION_EDIT*** : Ouvrir un éditeur adapté pour modifier les données fournies. Un URI vers les données à éditer
    - **ACTION_INSERT*** :  insérer des données
    - **ACTION_PICK*** : Séléctionner un élément dans un ensemble de données
    - **ACTION_SEARCH** : Effectuer une recherche
    - **ACTION_SENDTO** : ENvoyer un message à quelqu'un
    - **ACTION_WEB_SEARCH** : Effectuer une recherche sur internet

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnEntrer, btnSiteExterieur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lier ma vue(le layout) à mon activité
        btnEntrer = findViewById(R.id.btn_entrer);
        btnSiteExterieur = findViewById(R.id.btn_externe);
        // Ecouter mes boutons
        btnEntrer.setOnClickListener(this);
        btnSiteExterieur.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == btnEntrer) {
            Intent intent = new Intent(getApplicationContext(), ListePersonneActivity.class);
            startActivity(intent); // demarrer l'activité
            finish();
        }
        if(v == btnSiteExterieur) {
            // ACTION_VIEW :  si on veut voir quelque chose. ACTION_DIAL pour ouvrir le composeur de numéros téléphoniques
            Intent url = new Intent(Intent.ACTION_VIEW);
            // un objet Uri est une chaine de caractère qui permet d'identifier un endroit
            url.setData(Uri.parse("https://www.thielens-marie.be")); // setData pour envoyer les données. Uri.parse() crée un nouvel Uri objet à partir d'un string
            startActivity(url); // demarer l'activité avec en paramètre l'intent
        }
    }
```
Mettre l'action dans le manifest

```xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

### Exemple pour passer un coup de fil

```java
monBtn.setOnClickListener(new View.OnClickListener() {
  @Override
  public void onClick(View v) {
    Uri numTel = Uri.parse("tel:0606060606");
    Intent telIntent = new Intent(Intent.ACTION_DIAL, telIntent);
    startActivity(telIntent);
  }
});
```
## Faire passer les infos entre les activités ( le nom et prénom de l'utilisateur)
Je vais changer le bouton qui me servait à aller sur un site web par un contenu pour pouvoir transmettre le nom et prenom. J'aurai aussi besoin d'un textEdit en plus pour transmettre le nom

### Modification du layout et du fichier strings.xml

- Je vais changer le bouton qui me servait à aller sur un site web par un contenu pour pouvoir transmettre le nom. J'aurai aussi besoin d'un textEdit en plus pour transmettre le nom

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:gravity="center_vertical">
            <EditText
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Votre nom : "
                android:id="@+id/inputNom"
                />
            <Button
                android:id="@+id/btn_entrer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Clique moi"
                />
            <Button
                android:id="@+id/btn_externe"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Me connecter"
                />
        </LinearLayout>
</RelativeLayout>
```
- Ma string de mise en forme ( voir fichier string.xml)
`<string name="bonjour_s">Bonjour %s</string>`

### Modification du fichier MainActivity
- Créer mon intent. Modification du nom du bouton. Liaison du textEdit

- putExtra() : Méthode pour envoyer mes données d'une activité à l'autre

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnEntrer, btnSaluer;
    private EditText nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lier ma vue(le layout) à mon activité
        nom = findViewById(R.id.inputNom);
        btnEntrer = findViewById(R.id.btn_entrer);
        btnSaluer = findViewById(R.id.btn_externe);
        // Ecouter mes boutons
        btnEntrer.setOnClickListener(this);
        btnSaluer.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == btnEntrer) {
            Intent intent = new Intent(getApplicationContext(), ListePersonneActivity.class);
            startActivity(intent); // demarrer l'activité
            finish();
        }
        if(v == btnSaluer) {
            // Creation d'un intent pour ouvrir l'activité ListePersonne.java
            Intent saluerIntent = new Intent(getApplicationContext(), ListePersonneActivity.class);
            // Méthode pour envoyer mes données d'une activité à l'autre
            saluerIntent.putExtra("nom", nom.getText().toString());
            startActivity(saluerIntent);
            finish();
        }
```
## Le fichier qui va recevoir les datas ( le nom de l'utilisateur )


```java
public class ListePersonneActivity extends AppCompatActivity {

    public TextView saluerT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_personne);

        // Liaison avec le layout
        saluerT = findViewById(R.id.texte_personne_saluer);

        // Intent
        // C'est l'objet Bundle qui peut véhiculer nos données s'une activité à l'autre
        Bundle extra = this.getIntent().getExtras();
        String nomI = extra.getString("nom");
        // Affichage du message
        String message = String.format(getString(R.string.bonjour_s), nomI);
        saluerT.setText(message);
    }
}
```

- La suite :
- [Jouer avec le style](https://github.com/marieThielens/resumeAndroid/blob/master/Creer%20notre%20propre%20style_theme%20Sombre_orientation)md