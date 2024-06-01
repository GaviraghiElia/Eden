# eden


## Architettura MVVM

Il nostro progetto utilizza l'architettura MVVM (Model-View-ViewModel) per garantire una separazione chiara delle responsabilità e una gestione efficiente dei dati e dell'interfaccia utente.

### Componenti dell'architettura MVVM:

- **Model**: è il layer responsabile dell’astrazione dei dati e della logica di business dell'applicazione. È responsabile della gestione dei dati e fornisce un’interfaccia di accesso e manipolazione dei dati. I package che fanno parte di questo strato sono il package adapter, repository, database e model.
- **View**: rappresenta il layer dedicato alla visualizzazione  dell'interfaccia utente dell'applicazione, è responsabile di mostrare i dati all’utente e gestirne le interazioni. In questo strato sno comprese tutte le classi Fragment/Activity presententi nei sotto package del package UI e i corrispettivi file .XML associati e presenti nel folder di res.
- **ViewModel**: è il layer che fa da ponte tra il Model e la View, i ViewModel associati ai Fragment/Activity recuperano i dati dal Model e li rendono disponibili alla View in un formato facile da utilizzare. Inoltre, in base alle interazioni utente con la View aggiorna di conseguenza il Model. In questo caso i ViewModel sono contenuti negli stessi sotto package dei Fragment/Activity a cui sono associati.

L'utilizzo di MVVM promuove una maggiore modularità, facilità di testing e manutenzione del codice, e una migliore separazione delle preoccupazioni. Questo approccio è particolarmente adatto per le applicazioni basate su interfacce utente complesse.

### Acceptance test
Il link a cui accedere agli acceptance test è il seguente
https://docs.google.com/spreadsheets/d/1zX2aRX4tUyhpNxgzJ_wMScXBb0pwPlNy/edit?usp=sharing&ouid=113076828669624211387&rtpof=true&sd=true
