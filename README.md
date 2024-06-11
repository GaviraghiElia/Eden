# eden

## Struttura progetto

### Generale

```
┣ 📂 eden  # Root directory.
┣ ┣ 📄 gitlab-ci.yml            # CI/CD configuration
┣ ┣ 📄 build.gradle             # file gradle a livello di progetto
┣ ┣ 📄 README.md                # It's me!
┣ ┣ 📂 gradle/wrapper                             
┣ ┣ 📂 app
┣ ┃ ┣ 📄 build.gradle           # build.gradle modulo app 
┣ ┃ ┣ 📄 google-services.json   # Firebase dependencies 
┣ ┃ ┣ 📂 javadoc                # Documentazione Javadoc
┣ ┃ ┣ 📂 src\                         
┣ ┃ ┃ ┣ 📂 androidTest\java\com\unimib\eden\  #Instrumented test
┣ ┃ ┃ ┣ 📂 test\java\com\unimib\eden\         # unit test
┣ ┃ ┃ ┣ 📂 main  
┣ ┃ ┃ ┃ ┣ 📂 java\com\unimib\eden\            # classi implementate
┣ ┃ ┃ ┃ ┣ 📂 res                              # Risorse XML per stili, tema, layout, ...  
┣ ┃ ┃ ┃ ┣ 📄 manifest.xml                     # informazioni del sistema
```

### Struttura package classi implementate

```
┣ 📂 adapter                  # [UI] Classi per view delle liste
┣ ┣ 📄 CropAdapter.java         # RecyclerView Colture
┣ ┣ 📄 ForecastDayAdapter.java  # Per visualizzare banner meteo
┣ ┣ 📄 PhaseAdapter.java        # RecyclerView Fasi
┣ ┣ 📄 PlantAdapter.java        # RecyclerView Piante
┣ ┣ 📄 ProductAdapter.java      # RecyclerView Prodotti
┣ 📂 database                 # [MODEL] Interazioni con Room Firestore
┣ ┣ 📄 ...
┣ 📂 model                    # [MODEL] Classi per modellare le entità
┣ ┣ 📄 Crop.java                # Modellazione culture
┣ ┣ 📄 FirebaseResponse.java    # Modellazione Risposte dal DB
┣ ┣ 📄 Offer.java               # Modellazione offerte [features]
┣ ┣ 📄 Phase.java               # Modellazione delle fasi
┣ ┣ 📄 Plant.java               # Modellazione delle piante
┣ ┣ 📄 Product.java             # Modellazione dei prodotti
┣ ┣ 📄 User.java                # Modellazione degli utenti registrati
┣ ┣ 📂 weather                  # Modellazione risposta API del meteo
┣ ┣ 📄 ...
┣ 📂 repository              # [MODEL] Classi per interazioni con DB
┣ 📂 service                 
┣ ┣ 📄 WeatherService.java   # Classe per interazione diretta con API
┣ 📂 ui                      # [UI] Classi Java per UI (e ViewModel)
┣ ┣ 📂 Authentication           # Classi per login e logout
┣ ┃ ┣ 📄 AuthenticationActivity.java  # Modellazione delle piante
┣ ┃ ┣ 📄 EntryActivity.java           # Classe per check della sessione utente
┣ ┃ ┣ 📄 LoginFragment.java           # Classe che realizza la login utente
┣ ┃ ┣ 📄 RegisterFragment.java        # Classe che realizza la registrazione utente
┣ ┃ ┣ 📄 UserViewmodel.java           # [VIEWMODEL] Classe che realizza i metodi per l'interazione delle operazioni utente
┣ ┣ 📂 cropDetails              
┣ ┃ ┣ 📄 CropDetailsActivity.java      # Classe per dettagli delle colture
┣ ┃ ┣ 📄 CropDetailsViewModel.java     # ViewModel dettagli colture
┣ ┣ 📂 filterSearch             
┣ ┃ ┣ 📄 FilterSearchActivity.java     # Classe per cercare tra le piante disponibili
┣ ┃ ┣ 📄 FilterSearchViewModel.java    # ViewModel corrispondente
┣ ┣ 📂 home                     
┣ ┃ ┣ 📄 HomeFragment.java             # Fragment iniziale contenuto in MainActivity con visualizazione orto
┣ ┃ ┣ 📄 HomeViewModel.java            # ViewModel corrispondente
┣ ┣ 📂 insertCrop               
┣ ┃ ┣ 📄 InsertCropActivity.java       # Activity per inserimento coltura nell'orto
┣ ┃ ┣ 📄 InsertCropViewModel.java      # ViewModel corrispondente
┣ ┣ 📂 insertProduct
┣ ┃ ┣ 📄 InsertProductActivity.java    # Activity per inserimento prodotto
┣ ┃ ┣ 📄 InsertProductViewModel.java   # ViewModel corrispondente
┣ ┣ 📂 main
┣ ┃ ┣ 📄 MainActivity.java             # Activity principale dell'app
┣ ┃ ┣ 📄 WeatherViewModel.java         # ViewModel per get history del meteo
┣ ┣ 📂 plantDetails
┣ ┃ ┣ 📄 PlantDetailsActivity.java     # Activity dettagli piante
┣ ┃ ┣ 📄 PlantDetailsViewModel.java    # ViewModel corrispondente
┣ ┣ 📂 productDetails
┣ ┃ ┣ 📄 ProductDetailsActivity.java   # Activity dettagli prodotti
┣ ┃ ┣ 📄 ProductDetailsViewModel.java  # ViewModel corrispondente
┣ ┣ 📂 searchPlant
┣ ┃ ┣ 📄 SearchPlantActivity.java      # Activity per cercare tra le piante
┣ ┃ ┣ 📄 SearchPlantViewModel.java     # ViewModel corrispondente
┣ ┣ 📂 stand
┣ ┃ ┣ 📄 StandFragment.java            # Framgent contenuto in MainActivity con visualizzazioe prodotti
┣ ┃ ┣ 📄 StandViewModel.java           # ViewModel corrispondente
┣ ┣ 📂 watering
┣ ┃ ┣ 📄 WateringFragment.java         # Framgent contenuto in MainActivity per visualizzazione piante da irrigare
┣ ┃ ┣ 📄 WateringViewModel.java        # ViewModel con getForecast per meteo
```

## Architettura MVVM

Il nostro progetto utilizza l'architettura MVVM (Model-View-ViewModel) per garantire una separazione chiara delle responsabilità e una gestione efficiente dei dati e dell'interfaccia utente.

### Componenti dell'architettura MVVM:

- **Model**: è il layer responsabile dell’astrazione dei dati e della logica di business dell'applicazione. È responsabile della gestione dei dati e fornisce un’interfaccia di accesso e manipolazione dei dati. I package che fanno parte di questo strato sono il package adapter, repository, database e model.
- **View**: rappresenta il layer dedicato alla visualizzazione  dell'interfaccia utente dell'applicazione, è responsabile di mostrare i dati all’utente e gestirne le interazioni. In questo strato sno comprese tutte le classi Fragment/Activity presententi nei sotto package del package UI e i corrispettivi file .XML associati e presenti nel folder di res.
- **ViewModel**: è il layer che fa da ponte tra il Model e la View, i ViewModel associati ai Fragment/Activity recuperano i dati dal Model e li rendono disponibili alla View in un formato facile da utilizzare. Inoltre, in base alle interazioni utente con la View aggiorna di conseguenza il Model. In questo caso i ViewModel sono contenuti negli stessi sotto package dei Fragment/Activity a cui sono associati.

L'utilizzo di MVVM promuove una maggiore modularità, facilità di testing e manutenzione del codice, e una migliore separazione delle preoccupazioni. Questo approccio è particolarmente adatto per le applicazioni basate su interfacce utente complesse.

## Acceptance test
Il link a cui accedere agli acceptance test è il seguente
https://docs.google.com/spreadsheets/d/1zX2aRX4tUyhpNxgzJ_wMScXBb0pwPlNy/edit?usp=sharing&ouid=113076828669624211387&rtpof=true&sd=true