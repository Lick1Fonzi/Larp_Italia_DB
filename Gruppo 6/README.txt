Estrarre il sorgente della cartella source.zip

Passi per l'installazione:
    - Creare il database larp_italia
    - Importare il dtabase dal file larp_italia-difinitivo.sql
    - Lanciare il programma:
        -  java -cp progetto-java.jar .\src\larp_italia\main.java


Passi alternativi per codice java:
	-aprire un ide
	-creare un nuovo progetto ed importare il package larp_italia con all'interno i sorgenti ed il package view
	-importare i driver opportuni di postgresql in formato jar nel classpath 
		-per l'ide eclipse si clicca su run configurations->dependencies-> add external jars -> selezionare i driver postgres ( contenuti nello zip nella cartella lib)
	-far partire il programma da run del proprio ide 
	-controllare al bisogno nel sorgente DB.java che la variabile final url sia corretta per il proprio database ( impostata ai valori di default -> localhost:5432) 