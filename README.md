# XKCD Comic App

## Zweck

Ueber diese Applikation koennen diverse Comics von der xkcd Webseite eingesehen werden. Es kann auch nach Comics mit der ID und Schlagwoertern gesucht werden. Sobald ein Comic gefunden wurde, welches einem besonders gefaellt, kann man es favorisieren und somit speichern.

## API

Wir haben uns fuer die XKCD API entschieden, da diese sehr simpel und ein guter Start ist, wenn wir uns mehr auf das Design der Applikation als die API konzentrieren wollen.

Die [Dokumentation dieser API](https://xkcd.com/json.html) ist sehr simpel da nur 2 Endpunkte zur Verfuegung stehen.

### Neustes Comic - Endpoint

Um das letzt veröffentlichte Comic zu erhalten, sendet man ein Request über folgende URL: https://xkcd.com/info.0.json.

Von dieser URL wird dann folgende Rueckgabe erwartet.

```json
{
    "month": {int},
    "num": {int},
    "year": {int},
    "safe_title": {str},
    "transcript": {str},
    "alt": {str},
    "img": {str},
    "title": {str},
    "day": {int}
}
```

### Comic nach ID - Endpoint

Um ein Comic nach ID zu erhalten, sendet man eine Abfrage über folgende URL, welche die ID beinhaltet: <https://xkcd.com/{int}/info.0.json>

Von dieser URL wird dann folgende Rueckgabe erwartet.

```json
{
    "month": {int},
    "num": {int},
    "year": {int},
    "safe_title": {str},
    "transcript": {str},
    "alt": {str},
    "img": {str},
    "title": {str},
    "day": {int}
}
```

## Anwendungsfälle

| ID | Titel                        | Beschreibung                                                                                                                                                                                                                                                                                                      | Prioritaet | Vorbedingungen                                       | Schritte                                                                                  | Nachbedingungen                                                                                                         |
| --- | ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ---------------------------------------------------- | ----------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| 01  | Neuestes Comic               | Das neueste Comic wird auf der homepage dargestellt sobald die Applikation geoeffnet wird.                                                                                                                                                                                                                        | 0          | - App ist geoeffnet                                  |                                                                                           | Das neueste Comic ist im ersten Kartenelement der Startseite dargestellt.                                               |
| 02  | Zufaelliges Comic            | Ein zufaelliges Comic wird auf der Startseit dargestellt sobald die Applikation geoeffnet wird.                                                                                                                                                                                                                   | 0          | - App ist goeffnet                                   |                                                                                           | Ein zuefaelliges Comic ist im ersten Kartenelement der Startseite angezeigt.                                            |
| 03  | Aktualisieren der Startseite | Bei Betaetigung der Reload-Taste erneuert sich das neueste Comic falls eine neue Ausgabe erhaetlich ist, und es wird ein neues zufaelliges Comic geladen.                                                                                                                                                         | 0          | - App ist geoeffnet                                  | - Der Benutzer tippt die Reload-Taste an                                                  | Das neueste Comic ist im ersten Kartenelement der Startseite dargestellt und ein neues zufaelliges Comic wurde geladen. |
| 04  | Suche mit ID                 | Wenn der Inhalt des Suchfeldes bei einer Suche eine einfache Ganzzahl ist, soll das erste Element das Comic mit der entsprechenden ID darstellen, solange dieses existiert. Nach dem ersten Element werden die Comics angezeigt welche in den `transcript`, `title` und `alt` Feldern den Suchbegriff beinhalten. | 0          | - App ist goeffnet                                   | - Der Benutzer sucht mit einer einfachen Ganzzahl ohne Vorzeichen, unter dem Wert `2147`. | Die Comics werden wie beschrieben dargestellt.                                                                          |
| 05  | Suche nach Erscheinungsdatum | Wird ein Datum als Suchbegriff verwendet, werden die Comics nach Erscheinungsdatum angezeigt.                                                                                                                                                                                                                     | 4          | - App ist geoeffnet                                  | - Benutzer sucht mit einem Datum                                                          | Falls ein Comic am angegebenen Datum erschienen ist, wird dieses angezeigt.                                             |
| 06  | Suche mit Suchbegriff        | Wenn der Inhalt des Suchfeldes bei einer Suche keine einfache Ganzzahl ist, sollen die Elemente welche den Suchbegriff in in den Feldern `transcript`, `title` und `alt` beinhalten.                                                                                                                              | 1          | - App ist goeffnet                                   | Der Benutzer sucht mit einem Begriff welcher keine Zahl ist                               | Die Comics werden wie beschrieben dargestellt.                                                                          |
| 07  | Favorisieren von Comics      | Wenn auf das Stern Symbol eines Comics getippt wird, wird das Comic zu den Favoriten hinzugefuegt und das Stern Symbol aendert sich so, dass erkennbar ist, dass das Comic favorisiert ist.                                                                                                                       | 0          | - App ist geoeffnet                                  | - Der Benutzer tippt auf das Stern Symbol eines Comics                                    | Der Ablauf wickelt sich ab wie beschrieben.                                                                             |
| 08  | Einsehen der Favoriten       | Die favorisierten Comics koennen unter den Favoriten eingesehen werden.                                                                                                                                                                                                                                           | 0          | - App ist geoeffnet<br>- Der Favoriten-Tab ist offen |                                                                                           | Alle favorisierten Comics werden angezeigt.                                                                             |
| 09  | Detailansicht eines Comics   | Die Details, welche den Titel, das Transkript, den Alt Text und das Erscheinungsdatum angeben, koennen eingesehen werden.                                                                                                                                                                                         | 1          | - App ist geoeffnet                                  | - Der Benutzer tippt auf ein Comic                                                        | Die Detailansicht mit allen Feldern wird angezeigt.                                                                     |
| 10  | Suche personalisieren        | Es kann bestimmt werden, in welchen Feldern (`transcript`, `title` und `alt`) der Suchbegriff gesucht wird.                                                                                                                                                                                                       | 3          | - App ist geoffnet                                   | - Der Benutzer tippt auf den Pfeil links von der Suchleiste.                              | Es oeffnet sich ein Menue in dem er bestimmen kann in welchen Feldern der Comics er suchen will.                        |

### Prioritätenlegende

- 0 = kritisch
- 1 = wichtig
- 2 = optional
- 3 = nett zum haben

## Mockups

Die folgenden Mockups stellen unser vorgesehenes Design dar.

### Portrait



#### Flow



### Landscape



#### Flow



## Tägliches Journal

Die folgenden Paragrafen beschreiben die Arbeit, welche waehrend dem Projekt geleistet wurde.

### Daniela Simões

| Datum     | Aktivitäten                                      | Kommentar                                                                                                                                                                                                     |
| --------- | ------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 8.5.2019  | - Brainstorming<br>- Präsentation angefangen<br> | Nachdem die Grundideen des Programms sowie Skizzen der verschiedenen Seiten definiert wurden, fing ich mit der Präsentation an. Momentant ist Zeit mein letztes Problem und die Projektarbeit geht gut voran. |
| 9.5.2019  | - Dokumentation<br>- Präsentation<br>- c         | Lorem ipsum...                                                                                                                                                                                                |
| 10.5.2019 | - a<br>- b<br>- c                                | Lorem ipsum...                                                                                                                                                                                                |
| 15.5.2019 | - a<br>- b<br>- c                                | Lorem ipsum...                                                                                                                                                                                                |
| 16.5.2019 | - a<br>- b<br>- c                                | Lorem ipsum...                                                                                                                                                                                                |

### Dylan Schmid

| Datum     | Aktivitäten       | Kommentar      |
| --------- | ----------------- | -------------- |
| 8.5.2019  | - a<br>- b<br>- c | Lorem ipsum... |
| 9.5.2019  | - a<br>- b<br>- c | Lorem ipsum... |
| 10.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 15.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |
| 16.5.2019 | - a<br>- b<br>- c | Lorem ipsum... |

### Lani Wagner

| Datum     | Aktivitäten                                                           | Kommentar                                                                                                                                                            |
| --------- | --------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 8.5.2019  | - App Design Diskussion<br>- Use Cases schreiben                      | Wir konnten heute noch nicht viel machen da uns nur eine halbe Stunde zur Verfuegung stand, aber ich denke, dass wir in den folgenden Tagen gut voran kommen werden. |
| 9.5.2019  | - Grundstruktur der Dokumentation aufstellen<br>- Use Cases schreiben | Lorem ipsum...                                                                                                                                                       |
| 10.5.2019 | - a<br>- b<br>- c                                                     | Lorem ipsum...                                                                                                                                                       |
| 15.5.2019 | - a<br>- b<br>- c                                                     | Lorem ipsum...                                                                                                                                                       |
| 16.5.2019 | - a<br>- b<br>- c                                                     | Lorem ipsum...                                                                                                                                                       |

## Reflexion

### Daniela Simões



### Dylan Schmid



### Lani Wagner

