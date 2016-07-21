# Programmieren mit Scala#
## README für die Code-Beispiele ##

Dean Wampler and Alex Payne  
Übersetzt und an Scala 2.8 angepasst von Jörg Staudemeyer  
Oktober 2010

Dieses Archiv enthält alle Code-Beispiele des Buchs [Programmieren mit Scala](http://www.oreilly.de/catalog/progscalager/index.html/) mit Ausnahme einiger im Text enthaltener Code-Ausschnitte. Im Buch Beginnen alle Beispiele, zu denen es hier korrespondierende Dateien gibt, mit einer Kommentarzeile, die wie hier eine Pfadangabe enthält:

    // code-examples/kapitel/../dateiname
   
Anhand dieser Kommentare können Sie die entsprechende Datei in diesem Paket finden. Außerdem enthält das Archiv Scala-*Specs* zum Testen des Code, von denen jedoch viele im Buch nicht wiedergegeben sind. *Specs* ist ein Behavior-Driven-Development-Tool (BDD-Tool) ähnlich RSpec für Ruby. Es wird im Kapitel _Werkzeuge und Bibliotheken_ des Buchs behandelt.

## Namenskonventionen

Zu den Beispielen gehören Scripts, die mit dem `scala`-Befehl ausgeführt werden, Quelldateien, die mit `scala` zu kompilieren sind sowie Quelldateien, die absichtlich nicht kompilierbar sind. Um sie auseinander zu halten und um den Build-Prozess zu vereinfachen werden für die Dateien folgende Namenskonventionen verwendet.

- `*-script.scala` - Scripts, die Sie beispielsweise mit `scala foo-script.scala` ausführen können. Dieses Scripts werden innerhalb des normalen Compile- und Testprozesses ausgeführt, allerdings werden ihre Ausgaben derzeit nicht automatisch auf Korrektheit geprüft. (Wenn sie aber regelrecht versagen, scheitert der ganze Build-Vorgang.)
- `*-wont-compile.scala` - Scala-Dateien, die im Buch verwendet werden, sich aber absichtlich nicht kompilieren lassen. Sie sind vom Build-Prozess ausgeschlossen.
- Alle anderen `*.scala`-Dateien - Kompilierbare Scala-Dateien

## Build-Prozess für die Code-Beispiele

Der im englischsprachigen Original verwendete Build-Prozess kann hier nicht benutzt werden, da das von den Autoren selbst geschriebene Build-Tool noch nicht für Scala 2.8 verfügbar ist.

Die Code-Beispiele sind aber trotzdem alle getestet worden. Sobald das Build-Tool in der richtigen Version vorliegt, sollen auch die entsprechenden Build-Skripte zur Verfügung gestellt werden.


