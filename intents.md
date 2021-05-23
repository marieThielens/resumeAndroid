#Les intents

- transmission de données entre les activités
- 3 types de données saisies dans les intents
    - Activity/action
    - data
    - extras

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

- Ecouter mes boutons et lancer l'activitée ou aler sur un site externe
    - **setData()** 

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
            Intent url = new Intent(Intent.ACTION_VIEW);
            url.setData(Uri.parse("https://www.thielens-marie.be"));
            startActivity(url);
        }
    }


```