# GeneraBiglietti

Il progetto GeneraBiglietti consiste in un componente software in Spring Boot che riceve in input un'origine e una destinazione e restituisce in output i biglietti del consorzio Unico Campania necessari per effettuare la tratta. Questi ultimi sono ottenuti tramite delle interrogazioni ad un database MongoDB.

## Lancio dell'applicazione

**1)** A partire dalla root del progetto, eseguire da command line il comando
*mongorestore dump/*       (richiede che sia installato MongoDB Database Tools)
dove "dump/" è la directory contenente appunto i file di dump del database MongoDB da utilizzare.

**2)** Importare il progetto all'interno di un IDE come progetto Maven ed eseguirlo come applicazione Spring Boot.

**3)** Tramite una piattaforma di testing delle API come Postman, effettuare una richiesta HTTP di tipo GET al seguente endpoint:
http://localhost:8080/generaBiglietti?origine=*origine*&destinazione=*destinazione*
sostituendo opportunamente i parametri.

### Esempio di output (origine=Casalbuono, destinazione=Caserta)

{
        "bigliettoAcquistabile": {
            "origine": "Casalbuono",
            "destinazione": "Caserta",
            "fascia": "NA_14",
            "prezzo": 13.6,
            "integrato": true
        },
        "bigliettoNonAcquistabile": null
    },
    {
        "bigliettoAcquistabile": {
            "origine": "Casalbuono",
            "destinazione": "Caserta",
            "fascia": "AC_14",
            "prezzo": 12.4,
            "integrato": true
        },
        "bigliettoNonAcquistabile": null
    }

