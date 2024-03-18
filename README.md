# eden


## Architettura MVVM

Il nostro progetto utilizza l'architettura MVVM (Model-View-ViewModel) per garantire una separazione chiara delle responsabilità e una gestione efficiente dei dati e dell'interfaccia utente.

### Componenti principali dell'architettura MVVM:

- **Model**: Rappresenta i dati e la logica di business dell'applicazione. I dati provengono su un cluster MongoDB.
- **View**: Visualizza i dati provenienti dal ViewModel e reagisce agli input dell'utente.
- **ViewModel**: Fa da intermediario tra il Model e la View, trasformando i dati per la presentazione e gestendo la logica di interazione.

L'utilizzo di MVVM promuove una maggiore modularità, facilità di testing e manutenzione del codice, e una migliore separazione delle preoccupazioni. Questo approccio è particolarmente adatto per le applicazioni basate su interfacce utente complesse.