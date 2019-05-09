# XKCD Comic App

## Zweck



## API

Wir haben uns fuer die XKCD API entschieden, da diese sehr simpel und ein guter Start ist, wenn wir uns mehr auf das Design der Applikation als die API konzentrieren wollen.

### Neustes Comic - Endpoint

Um das letzt veröffentlichte Comic zu erhalten, sendet man ein Request über folgende URL: https://xkcd.com/info.0.json.

#### Rückgabe

```json
{   
    "month": <int>, 
    "num": <int>, 
    "year": <int>, 
    "safe_title": <String>, 
    "transcript": <String>, 
    "alt": <String>, 
    "img": <String>,
    "title": <String>, 
    "day": <int>
}
```

### Comic nach ID - Endpoint

Um ein Comic nach ID zu erhalten, sendet man ein Request über folgende URL, welche die ID beinhaltet: https://<span>xkcd.</span>com/\<int>/info.0.json.

#### Rückgabe

```json
{
    "month": <int>, 
    "num": <int>, 
    "year": <int>, 
    "safe_title": <String>, 
    "transcript": <String>, 
    "alt": <String>, 
    "img": <String>, 
    "title": <String>, 
    "day": <int>
}
```

## Anwendungsfälle

| ID | Titel | Beschreibung | Prioritaet | Vorbedingungen | Schritte | Nachbedingungen |
|----|-------|-------------|----------|---------------|-------|----------------|
| 01 | Neuestes Comic | Das neueste Comic wird auf der homepage dargestellt sobald die Applikation geoeffnet wird. | 0 | - App ist geoeffnet |  | Das neueste Comic ist im ersten Kartenelement der Startseite dargestellt. |
| 02 | Zufaelliges Comic | Ein zufaelliges Comic wird auf der Startseit dargestellt sobald die Applikation geoeffnet wird. | 0 | - App ist goeffnet |  | Ein zuefaelliges Comic ist im ersten Kartenelement der Startseite angezeigt. |
|03 | Suche mit ID | Wenn der Inhalt des Suchfeldes bei einer Suche eine einfache Ganzzahl ist, soll das erste Element das Comic mit der entsprechenden ID darstellen, solange dieses existiert. Nach dem ersten Element werden die Comics angezeigt welche im `transcript`, `title` oder `alt` Feld den Suchbegriff beinhalten. | 0 | - App ist goeffnet | - Der Benutzer sucht mit einer einfachen Ganzzahl ohne Vorzeichen unter dem Wert `2147`. | Die Comics werden wie beschrieben dargestellt. |
| 04 | Suche mit Suchbegriff |  |  |  |  |  |
| 05 | Favorisieren von Comics |  |  |  |  |  |
| 06 | Einsehen der Favoriten |  |  |  |  |  |
| 07 | Detailansicht eines Comics aufrufen |  |  |  |  |  |

### Prioritätenlegende

0 = kritisch <br>
1 = wichtig <br>
2 = optional <br>
3 = nett zum haben

## Tägliches Journal

Die folgenden Paragrafen beschreiben die Arbeit, welche waehrend dem Projekt geleistet wurde.

### Daniela Simões

| Datum | Aktivitäten | Kommentar |
|-------|--------------|-----------|
| 8.5.2019 | - Brainstorming<br>- Präsentation angefangen<br> | Nachdem die Grundideen des Programms sowie Skizzen der verschiedenen Seiten definiert wurden, fing ich mit der Präsentation an. Momentant ist Zeit mein letztes Problem und die Projektarbeit geht gut voran. |
| 9.5.2019 | - Dokumentation<br>- Präsentation<br>- c | Lorem ipsum... |
| 10.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 15.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 16.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |

### Dylan Schmid

| Datum | Aktivitäten | Kommentar |
|-------|--------------|-----------|
| 8.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 9.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 10.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 15.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 16.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |

### Lani Wagner

| Datum | Aktivitäten | Kommentar |
|-------|--------------|-----------|
| 8.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 9.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 10.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 15.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 16.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |

## Reflexion

### Daniela Simões



### Dylan Schmid



### Lani Wagner

