# Pricey

### IT-it
Pricey e' un comparatore di prezzi letti da file excel.
Il comparatore e' stato realizzato per un'attivita' commerciale, inoltre, i file excel non sono realizzati seguendo uno standard e pertanto,
il codice e' stato sviluppato per adattarsi al formato di determinati file. Il risultato e' che il comparatore con molta probabilita'
non sara' in grado di adattarsi a file excel che presentano una struttura diversa da quelli gia' testati.

L'applicazione e' stata sviluppata inizialmente in pure Java e successivamente con il framework Spring.
Mette a disposizione sull'indirizzo localhost:8080 una pagina web interattiva con il quale e' possibile utilizzare lo strumento, in particolare:
- Scegliere la posizione relativa o assoluta dei file excel (di default e' la cartella in cui e' presente il listino finale).
- Caricare uno o piu' file excel contemporaneamente.
- Infine scaricare un file zip contenente il listino ordinato.

Il listino conterra' tutti i prodotti con i relativi prezzi ordinati in senso crescente. Inoltre, il prezzo di ogni prodotto sara' linkato al file in cui e' stato rilevato.
Nota: il link non funzionera' correttamente se la posizione del file non e' stata impostata correttamente.

### EN-en
Pricey compares prices read from excel files.
The comparator has been created for a commercial activity, also, the excel files do not follow a standard which means, the code was developed to adapt to the 
format of certain files. As a consequence, the comparator will most likely won't be able to adapt to the excel files that have a different structure from the 
tested files.

The application was initially written in pure Java and then using the Spring framework.
It open an interactive webpage at the address localhost:8080, in particular:
- You can set the relative or absolute path of the excel files (it defaults to the relative folder where the final catalog is stored).
- Upload one or more excel files simultaneously.
- Download a zip file containing the ordered catalog.

The catalog will have all the products with their relative prices in ascending order. Furthermore, each product will be linked to the file containing that specific price.
Note: the link won't work if the set path is not valid.
