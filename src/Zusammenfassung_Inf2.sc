// =============================================================================
// ZUSAMMENFASSUNG INF 2 — Arrays, Zerlegungsmuster, Pointer & verkettete Strukturen
// =============================================================================
//
//   ┌───────────────────────────────────────────────────────────────────────┐
//   │  ARRAYS                                                               │
//   │    Initialisierung (ofDim, fill, range, tabulate, 2D)                 │
//   │    Zugriff, Manipulation, mkString                                    │
//   │    Methoden: sum/max/min/sorted/reverse/filter/map/contains/toList    │
//   │                                                                       │
//   │  ZERLEGUNGSMUSTER                                                     │
//   │    linearSearchArray()  -> lineare Verarbeitung   O(n)                │
//   │    binarySearchArray()  -> Bisektion              O(log n)            │
//   │    Divide and Conquer   -> Divide / Conquer / Merge                   │
//   │                                                                       │
//   │  SORTIERALGORITHMEN (auf Arrays, imperativ)                           │
//   │    selectionsortArray() / insertionsortArray()    O(n^2)              │
//   │    quicksortArray()     / mergesortArray()        O(n log n)          │
//   │                                                                       │
//   │  KOMPLEXE ZERLEGUNGSMUSTER (am Rucksackproblem)                       │
//   │    kanpSackGreedy()      -> Greedy, eine Lösung                       │
//   │    knapSackBackTrStart() -> Backtracking, ALLE Lösungen               │
//   │    knapSackDP()          -> Dynamische Programmierung, Optimum        │
//   │                                                                       │
//   │  POINTER & VERKETTETE STRUKTUREN                                      │
//   │    class Elem     -> einfach verkettete Liste (mkString/copy/tail/...) │
//   │    class Queue[T] -> generische Warteschlange (enque/deque/first/...)  │
//   └───────────────────────────────────────────────────────────────────────┘
//
// Datei-Typ: .sc — Skript, läuft von oben nach unten, kein @main nötig.
// Erklärungen zu allen verwendeten Konstrukten: siehe scala_cheatsheet.md
// =============================================================================

//Imports
// ClassTag wird gebraucht, um in generischen Funktionen ein Array[T] anzulegen:
// die JVM löscht Typparameter zur Laufzeit (Type Erasure), der ClassTag reicht
// die Typinformation nach. Bei List[A] ist das nicht nötig.
import scala.reflect.ClassTag

//Exceptions
/*
//Try - Catch
try
  {...}                                   // Code, der fehlschlagen kann
catch                                     // die cases sind Muster wie im match
  case e1:NumberFormatException => {...}  // Typ prüfen und an einen Namen binden
  case e2:ArithmeticException => {...}    // spezielle Typen zuerst,
  case e3:IndexOutOfBoundsException => {...}
  case e4 => {...} //alle anderen Fälle --> case _     allgemeine zuletzt
finally
  {...}                                   // läuft IMMER (auch nach return) -> Aufräumen
//Exceptions werfen
throw new Exception("...")                // allgemeiner Fehler
throw new ArithmeticException("...")      // spezieller Typ -> gezielt fangbar
*/


//Arrays
/*
i) Datenstrukur fester Länge
ii) Speichern Variablen
iii) Alle Elemente gelten als gleichwertig & sind vom gleichen Datentyp
iv) Indizierter Zugriff Möglich Arr(0) --> 1. Element im Array, Arr(4) --> 5. Elem
var name:Array[Datentyp] = new Array[Datentyp](Anzahl Felder)

Zugriff über RUNDE Klammern: arr(0), nicht arr[0] wie in Java.
Auch bei 'val arr' bleiben die FELDER veränderbar — fest ist nur die Referenz.
 */

//Initialisierung
var arr1:Array[Int] = Array.ofDim[Int](5) //Leeres Array auf Basis der Größe und Datentyp der Eingabedaten
for (i <- arr1.indices){ //Durchlauf des Arrays
  // indices = 0 until length -> liefert die gültigen Indizes
  arr1(i) = i //Manuelle Wertezuweisung
}
arr1 //Array(0, 1, 2, 3, 4)

var arr2 = Array(3,0,1,4,2) //Werte Direkt zuweisen

var arr3 = Array.fill(5)(7) //(n)(wert) --> Array der Länge n mit gleichen Werten --> Array(7, 7, 7, 7, 7)

var arr4 = Array.range(1,4) //(start, end) --> Array(1,2,3)
// Das Ende ist EXKLUSIV -> die 4 fehlt.

var arr5 = Array.tabulate(5)(i => if (i%2==0) i*2 else 0) //(n)(f) --> Array(0, 0, 4, 0, 8, 0)
// tabulate berechnet jeden Wert aus seinem INDEX; f ist eine anonyme Funktion.

var arr62d:Array[Array[Int]] = Array.ofDim(3,3) //3x3 Matrix --> Array[Array[Int]] = Array(Array(0, 0, 0), Array(0, 0, 0), Array(0, 0, 0))
// Zweidimensional = Array von Arrays; arr62d(i) ist selbst ein Array.
var n = 0
for (i <- arr62d.indices){
  for (j <- arr62d.indices){ //oder for (i <- arr62d.indices; j <- arr62d.indices){...}
    arr62d(i)(j) = n //i = Zeile, j = Spalte
    n += 1           // durchnummerieren: 0..8
  }
}
arr62d(1) //Array(3,4,5)


//Zugriff & Manipulation
val elemFirstarr1 = arr1(0) //0
val elemLastarr1 = arr1(4) //4
// Gültig sind nur die Indizes 0 .. length-1 -> sonst IndexOutOfBoundsException

arr1(0) = 1 //arr1 = Array(1,1,2,3,4)
// Zuweisung an ein Feld: erlaubt, obwohl arr1 selbst nicht neu zugewiesen wird.

print(arr1.mkString("(", ", ", ")")) //(1, 1, 2, 3, 4)
// mkString(start, trenner, ende) — nötig, weil println(arr1) nur die Adresse zeigt.

for (elem <- arr1){
  print(elem) //11234
}                 // Durchlauf über die WERTE (ohne Index)

for (row <-arr62d){
  println(row.mkString("(", ", ", ")")) //Ausgabe arr62d als 3x3 Matrix
}                                        // row ist jeweils eine ganze Zeile


//Weitere Methoden
val sumarr1 = arr1.sum  //Summe der Elemente --> Array(1,1,2,3,4) => 11
val maxarr1 = arr1.max //maximum --> 4
val minarr1 = arr1.min //minimum --> 1

val sortarr2:Array[Int] = arr2.sorted  //Gibt eine neue sortierte Kopie des Arrays zurück --> Array(3, 0, 1, 4, 2) => Array(0, 1, 2, 3, 4)

val revarr1:Array[Int] = arr1.reverse  //Array(4, 3, 2, 1, 1)

val evenarr1:Array[Int] = arr1.filter(_ % 2 == 0) //Filter: Gerade Zahlen --> Array(4, 2)
// '_' ist der Platzhalter für das jeweils geprüfte Element.
val larger4arr1:Array[Int] = arr1.filter(_ > 4) //Filter: Gerade Zahlen --> Array()

val doublearr1:Array[Int] = arr1.map(_ * 2) //Mapping: Jede Zahl verdopppeln --> Array(2, 2, 4, 6, 8)

val arr1con5:Boolean = arr1.contains(5) //Elem entahlten ? --> false

val listarr1:List[Int] = arr1.toList //List(1, 1, 2, 3, 4)
// Umwandlung Array -> List, danach sind Pattern Matching und ':: ' möglich.



//Zerlegungsmuster
//Lineare Verarbeitung
/*
Lineare Verarbeitung --> Elementweise Verarbeitung
Für n Elemente Laufzeit von n
 */
def linearSearchArray(arr:Array[Int], x:Int): Boolean =
  var i = 0
  var found = false
  // Abbruch, sobald gefunden ODER das Ende erreicht ist
  while (i < arr.length && found != true){
    if (arr(i) == x){
      found = true
    }
    i += 1
  }
  return found


//Bisektion
/*
Problemgröße wird halbiert --> Laufzeit log2(n)
Grenzen bilden und Suchebereich anpassen
Daten müssen sortiert sein
*/
def binarySearchArray(arr:Array[Int], x:Int): Boolean =
  var left = 0                     // untere Grenze des Suchbereichs
  var right = arr.length-1         // obere Grenze
  var found = false
  // '&& !found' beendet die Schleife beim Treffer — sonst bleibt die Bedingung
  // wahr, weil im Trefferfall weder left noch right verändert wird.
  while (left <= right && !found){
    val mid:Int = (left + right)/2      // Mitte des aktuellen Bereichs
    if (x < arr(mid)){
      right = mid-1                     // Ziel liegt links -> rechte Hälfte verwerfen
    } else if (x > arr(mid)){
      left = mid+1                      // Ziel liegt rechts -> linke Hälfte verwerfen
    } else {
      found = true                      // arr(mid) == x
    }
  }
  return found


//Divide and conquer
/*
Lineare Verarbeitung: Aufteilung der Datenmenge in zwei (oder mehr) Untermenge (möglichst gleich groß)
Bearbeitung:
  1. Divide:  Aufteilung vornehmen
  2. Conquer: Lösung der Teile via Rekursion
  3. Merge:   Kombination der Teillösungen zur Gesamtlösung
Beispiele:
  1. Quicksort   -> Aufwand steckt im Divide (Aufteilen am Pivot)
  2. Mergesort   -> Aufwand steckt im Merge (Zusammenfügen)
*/

//Sortieralgorithmen
//Selectionsort (Minimalsortiren) O(n^2)
def selectionsortArray(arr:Array[Int]): Array[Int] =
  var arrSorted:Array[Int] = arr.clone()   // Kopie: das Original bleibt unangetastet
  for (i <- 0 to arrSorted.length-2){      // i = Position, die gefüllt wird
    var cacheIndex = i                     // Annahme: das Minimum steht schon hier
    for (j <- i+1 to arrSorted.length-1){  // im unsortierten Rest suchen
      if (arrSorted(j) < arrSorted(cacheIndex)){
        cacheIndex = j                     // kleineres Element gefunden -> Index merken
      }
    }
    val tmp = arrSorted(i)                 // Tausch über eine Hilfsvariable
    arrSorted(i) = arrSorted(cacheIndex)
    arrSorted(cacheIndex) = tmp
  }
  return arrSorted

//Insertionsort (Sortieren durch Einfügen) O(n^2)
def insertionsortArray(arr:Array[Int]): Array[Int] =
  var arrSorted = arr.clone()
  for (i <- 0 to arrSorted.length-2){
    var current = arrSorted(i+1)           // das Element, das einsortiert wird
    for (j <- 0 to i){                     // linker Teil ist bereits sortiert
      if (current < arrSorted(j)){
        val tmp = arrSorted(j)             // current gehört vor arrSorted(j):
        arrSorted(j) = current             // beide tauschen und mit dem größeren
        current = tmp                      // Wert weiterwandern
      }
    }
    arrSorted(i+1) = current                // übrig gebliebenen Wert ablegen
  }
  return arrSorted

//Quicksort O(n*log2(n))
def quicksortArray(arr:Array[Int]): Array[Int] =
  if (arr.length <= 1){
    return arr                             // Rekursionsanker: 0 oder 1 Element ist sortiert
  }
  var arrSorted = arr.clone()
  val pivot = arrSorted(0)                 // einfachste Pivotwahl: erstes Element
  val left = arrSorted.filter(_ < pivot)   // DIVIDE: alles Kleinere
  val mid = arrSorted.filter(_ == pivot)   // alle Duplikate des Pivots
  var right = arrSorted.filter(_ > pivot)  // alles Größere
  // CONQUER + MERGE: Teile rekursiv sortieren und mit ++ zusammensetzen
  arrSorted = quicksortArray(left) ++ mid ++ quicksortArray(right)
  return arrSorted

//Mergesort O(n*log2(n))
// Verschmilzt zwei BEREITS SORTIERTE Arrays reißverschlussartig.
def merge(left:Array[Int], right:Array[Int]):Array[Int] =
  var i = 0                                // Laufindex links
  var j = 0                                // Laufindex rechts
  var n = 0                                // Schreibposition im Ergebnis
  var merged:Array[Int] = Array.ofDim(left.length + right.length)
  while (i < left.length && j < right.length) {   // solange BEIDE Reste haben
    if (left(i) < right(j)) {                     // jeweils den kleineren Kopf nehmen
      merged(n) = left(i)
      n += 1
      i += 1
    } else {
      merged(n) = right(j)
      n += 1
      j += 1
    }
  }
  while(i < left.length){                  // Rest links anhängen
    merged(n) = left(i)
    n += 1
    i += 1
  }
  while (j < right.length) {               // Rest rechts anhängen
    merged(n) = right(j)
    n += 1
    j += 1
  }
  return merged
def mergesortArray(arr:Array[Int]): Array[Int] =
  if (arr.length <= 1){
    return arr                             // Rekursionsanker
  }
  var arrSorted = arr.clone()
  val midIndex = arrSorted.length/2        // DIVIDE: exakt in der Mitte teilen
  val left = arrSorted.take(midIndex) //midIndex Einträge
  val right = arrSorted.drop(midIndex) //minIndex Einträge werden ausgelassen
  // CONQUER: beide Hälften rekursiv sortieren  |  MERGE: zusammenfügen
  arrSorted = merge(mergesortArray(left), mergesortArray(right))
  return arrSorted

//tests
// Vier Ausgangslagen: sortiert, absteigend, teilweise und gemischt
val us1 = Array(1, 2, 3, 4, 5)
val us2 = us1.reverse
val us3 = Array(3, 2, 1, 5, 4)
val us4 = Array(2, 4, 1, 3, 5)
println("Slectionsort:")
selectionsortArray(us1)
selectionsortArray(us2)
selectionsortArray(us3)
selectionsortArray(us4)
println("Insertionsort")
insertionsortArray(us1)
insertionsortArray(us2)
insertionsortArray(us3)
insertionsortArray(us4)
println("Quicksort")
quicksortArray(us1)
quicksortArray(us2)
quicksortArray(us3)
quicksortArray(us4)
println("Mergesort")
mergesortArray(us1)
mergesortArray(us2)
mergesortArray(us3)
mergesortArray(us4)


//Komplexe Zerlegungsmuster
//Greedy-Prinzip
/*
Diese Algorithmen arbeiten nach einem Prinzip, bei dem jede nächste zu treffende Entscheidung bestmöglich (z.B. unter berücksichtgung von Kosten) getroffen wird
Dadruch wird zu einem bestimmten Problem (sofern möglich) eine Lösung gefunden, aber nicht zwingend die beste
--> Entscheidungen können nicht rückgängig gemacht werden

Beispiel, BackPack:
Einbrecher wählt Wertgegenstände(Eigenschaften: Gewicht, Wert), kann aber nur eine gewisse Menge aufgrund einer Trage-Kapazität auswählen
Greedy-Prinzip:
1. Gegenstände nach Wert sortieren
2. Immer den wertvollsten passenden Gegenstand auswählen
3. Wiederhole bis Trage-Kapazität erreicht
*/
// type-Alias: neuer NAME für einen bestehenden Typ (kein neuer Typ).
// Zugriff auf die Bestandteile über _1, _2, _3 — die Zählung beginnt bei 1.
type Gegenstand = (String, Int, Int) //Name, Wert, Gewicht
// Verglichen wird _2, also der WERT des Gegenstands.
def ekWert(g1:Gegenstand, g2:Gegenstand): Boolean = (g1._2 < g2._2)   // "echt kleiner"
def egWert(g1:Gegenstand, g2:Gegenstand): Boolean = (g1._2 > g2._2)   // "echt größer"
def glWert(g1:Gegenstand, g2:Gegenstand): Boolean = (g1._2 == g2._2)  // "gleich"

// Slice-Funktion für Arrays (Kombination zweier Arrays)
// [T: ClassTag] -> nötig, um Array.ofDim[T] zur Laufzeit anlegen zu können.
// (Entspricht dem eingebauten a1 ++ a2, hier zu Übungszwecken selbst gebaut.)
def slice[T: ClassTag](a1:Array[T], a2:Array[T]): Array[T] =
  var ret:Array[T] = Array.ofDim[T](a1.length + a2.length)   // Zielarray in Gesamtlänge
  var n = 0                                                   // Schreibposition
  //a1
  for (i <- a1){
    ret(n) = i
    n += 1
  }
  for (j <- a2){
    ret(n) = j
    n+= 1
  }
  return ret

// Generische Quicksort-Variante: die Ordnung kommt über drei Vergleichsfunktionen
// herein (Currying), dadurch für JEDEN Typ nutzbar — hier für Gegenstand-Tupel.
def quicksortArray[T: ClassTag](arr:Array[T])(ek:(T, T) => Boolean)(eg:(T, T) => Boolean)(gl:(T, T) => Boolean): Array[T] =
  if (arr.length <= 1) return arr
  val pivot: T = arr(0)
  val left: Array[T] = arr.filter(ek(_, pivot))    // ek(_, pivot) == e => ek(e, pivot)
  val mid: Array[T] = arr.filter(gl(_, pivot))     // Duplikate des Pivots
  val right: Array[T] = arr.filter(eg(_, pivot))
  // dreimal zusammensetzen: (links + mitte) + rechts
  slice(slice(quicksortArray(left)(ek)(eg)(gl), mid), quicksortArray(right)(ek)(eg)(gl))

def kanpSackGreedy(g:Array[Gegenstand], capacity:Int): List[Gegenstand] =
  var backPack:List[Gegenstand] = Nil        // Nil = leere Liste
  var carriedW:Int = 0                        // bisher getragenes Gewicht
  // Schritt 1: nach Wert ABSTEIGEND sortieren -> ek und eg vertauscht übergeben,
  // damit der wertvollste Gegenstand vorne steht
  val gSorted = quicksortArray[Gegenstand](g)(egWert)(ekWert)(glWert)
  for (i <- gSorted){                         // Schritt 2+3: der Reihe nach durchgehen
    if (carriedW + i._3 <= capacity) {        // _3 = Gewicht; passt es noch?
      backPack = backPack ::: i :: Nil        // hinten anhängen
      carriedW += i._3                        // Entscheidung ist endgültig (Greedy)
    }
  }
  return backPack

val test1 = Array(
  ("Goldbarren", 100, 10),                    // (Name, Wert, Gewicht)
  ("Silberbarren", 60, 20),
  ("Diamant", 150, 5),
  ("Laptop", 80, 7),
  ("Kamera", 70, 6),
  ("Uhr", 50, 4),
  ("Münzen", 40, 3)
)
val kapazitaet1 = 30

val test2 = Array(
  ("Kleines Buch", 5, 1),
  ("Notizblock", 8, 2),
  ("Kugelschreiber", 3, 1),
  ("Handy-Ladegerät", 10, 2),
  ("Sonnenbrille", 12, 2),
  ("Portemonnaie", 15, 3),
  ("Schlüssel", 7, 1)
)
val kapazitaet2 = 7

val l1:List[Gegenstand] = kanpSackGreedy(test1, kapazitaet1)
val l2:List[Gegenstand] = kanpSackGreedy(test2, kapazitaet2)


//Backtracking
/*
Bruteforce; Durchprobieren aller Möglichkeiten
Falls entscheidung nicht zur Lösung führt, wird sie rückgängig gemacht
Findet alle Lösungen
Hohe Laufzeit --> Exponentiell
*/
// Der Suchbaum verzweigt an jedem Gegenstand in zwei Äste -> 2^n Pfade.
// index = welcher Gegenstand ist dran, path = bisher gewählte Gegenstände
def knapSackBackTrAlg(g: Array[Gegenstand], index: Int, capacityCurrent: Int, capacity: Int, path: List[Gegenstand]): List[List[Gegenstand]] = {
  if (capacityCurrent > capacity) return Nil // Falls Gewicht überschritten wurde -> ungültig
                                             // (Zweig wird abgeschnitten = "Backtrack")
  if (index >= g.length) return List(path) // Alle Gegenstände getestet -> Rückgabe der aktuellen Lösung
  // Zwei Möglichkeiten: (1) Gegenstand aufnehmen, (2) Gegenstand ignorieren
  // ':+' hängt hinten an die Pfadliste an
  val withItem = knapSackBackTrAlg(g, index + 1, capacityCurrent + g(index)._3, capacity, path :+ g(index))
  val withoutItem = knapSackBackTrAlg(g, index + 1, capacityCurrent, capacity, path)

  withItem ++ withoutItem // Alle möglichen Lösungen sammeln
}

// Startet die Rekursion mit den Anfangswerten (Wrapper-Funktion)
def knapSackBackTrStart(g: Array[Gegenstand], capacity: Int): List[List[Gegenstand]] = {
  knapSackBackTrAlg(g, 0, 0, capacity, List())
}


val loesungen = knapSackBackTrStart(test1, kapazitaet1)
// **Ausgabe der Lösungen**
println("Alle möglichen Rucksack-Lösungen:")
for (i <- loesungen.indices){
  println(s"Lösung $i: ${loesungen(i)}")   // ${...} für ganze Ausdrücke im String
}


//Dynamische Programmierung
/*
Vermeidet doppelte Berechnungen, indem Teilergebnisse gespeichert werden
Kann Probleme lösen, die Rekursiv exponentiell wären
Optimierungsprobleme mit optimalen Teilproblemen

Beispiel, BackPack:
Tabelle mit Teillösungen wird angelegt, um Berechnungen zu ersparen
*/
// Rückgabe ist ein Tupel: (bester erreichbarer Wert, Liste der gewählten Gegenstände)
def knapSackDP(g:Array[Gegenstand], capacity: Int): (Int, List[Gegenstand]) = {
  val n = g.length
  // dp(i)(w) = bester Wert, wenn die ersten i Gegenstände bei Kapazität w
  // zur Verfügung stehen. Zeile 0 und Spalte 0 bleiben 0 (nichts verfügbar).
  val dp = Array.ofDim[Int](n + 1, capacity + 1)

  // Schritt 1: DP-Tabelle füllen
  for (i <- 1 to n; w <- 0 to capacity) {      // zwei Generatoren = geschachtelte Schleifen
    val (name, value, weight) = g(i - 1)       // Tupel direkt in drei Werte zerlegen
    if (weight <= w) {
      // Entscheidung: Gegenstand weglassen ODER mitnehmen — das Bessere gewinnt
      dp(i)(w) = Math.max(dp(i - 1)(w), dp(i - 1)(w - weight) + value)
    } else {
      dp(i)(w) = dp(i - 1)(w)                  // passt nicht -> Wert aus der Zeile darüber
    }
  }

  // Schritt 2: Rückverfolgung der gewählten Gegenstände
  var w = capacity                             // von der vollen Kapazität rückwärts
  var resValue = dp(n)(capacity)               // ... und vom Gesamtoptimum aus
  var chosenItems = List[Gegenstand]()

  for (i <- n until 0 by -1 if resValue > 0) { // rückwärts zählen: n, n-1, ... 1
    val (name, value, weight) = g(i - 1)
    if (resValue != dp(i - 1)(w)) {  // Item wurde aufgenommen
      chosenItems = (name, value, weight) :: chosenItems   // vorne anhängen -> Originalreihenfolge
      resValue -= value                                     // Wert und Kapazität
      w -= weight                                           // zurückrechnen
    }
  }
  (dp(n)(capacity), chosenItems)               // letzter Ausdruck = Rückgabewert
}


//Pointer
/*
Werden Dateneinträge in den Speicher geschrieben, kann via Referenz auf diese Daten verwiesen werden. Die Referenz wird dabei in eine Variable dieses Datentyps gespeichert.
Die Referenz dient als Pointer, über den die Daten adressiert, manipuliert, ... werden können.
Auf gleiche Art und Weise können verkettete Listen/Bäume/Etc. erstellt werden, welche in sich auf ein nächstes Objekt verweisen, also eine Referenz speichern.
Für die Traversierung ist es wichtig, einen zusätzlichen Pointer zu erstellen, da die bestehende Referenz für ein konkretes Objekt ggf. verändert wird.
Daten im Speicher, die nicht adressiert werden können, werde während der Laufzeit von einem "Garbage Collecter" entfernt, um den Speicher zu räumen.
*/
//Einfach verkettete Listen
//value --> Wert, next --> Speichert Referenz auf nachfolgendes Objekt
// 'var' vor den Konstruktorparametern macht sie zu veränderbaren Feldern;
// '= 0' bzw. '= null' sind Default-Werte -> new Elem() ist möglich.
class Elem(var value: Int = 0, var next: Elem = null){

  // Gibt die ganze Kette als String aus, Default-Parameter wie bei mkString
  def mkString(start:String = "(", delimiter:String = ", ", end:String = ")"): String =
    var s = start
    var current = this                    // Arbeitszeiger — 'this' bleibt unangetastet
    while (current.next != null) {         // bis EINS VOR dem Ende laufen ...
      s = s + current.value.toString + delimiter
      current = current.next
    }
    s = s + current.value.toString + end   // ... damit das letzte Element keinen Trenner bekommt
    return s

  // Echte Kopie der Kette: ohne sie würden zwei Variablen auf DIESELBEN Objekte zeigen
  def copy(): Elem =
    if (this == null) return null
    var c = new Elem(this.value, null)    // neuer Kopf
    var p = this.next                     // Zeiger auf das Original
    var q = c                             // Zeiger auf die Kopie
    while (p != null) {
      print(p.value.toString + " ")       // (Debug-Ausgabe beim Kopieren)
      q.next = new Elem(p.value, null)    // für jedes Original-Element ein NEUES anlegen
      p = p.next                          // beide Zeiger gleichzeitig weiterschieben
      q = q.next
    }
    return c

  // Schnittmenge zweier SORTIERTER Ketten
  def intersec(l:Elem): Elem =
    var t = this
    var ltmp = l
    var ret = new Elem()                  // Dummy-Kopf, am Ende übersprungen
    var q = ret                           // Schreibzeiger auf das Ergebnis
    while (t != null && ltmp != null) {
      if (t.value > ltmp.value) {
        ltmp = ltmp.next                  // rechts nachziehen
      }
      else if (t.value < ltmp.value) {
        t = t.next                        // links nachziehen
      }
      else {
        q.next = new Elem(t.value, null)  // Treffer anhängen ...
        q = q.next                        // ... Schreibzeiger mitführen
        t = t.next                        // ... und BEIDE Lesezeiger weiterschieben
        ltmp = ltmp.next
      }
    }
    return ret.next                       // Dummy überspringen

  def head(): Int =
    return this.value                     // erstes Element = dieses Objekt

  def tail(): Elem =
    //Gibt Kopie zurück
    var dummy = new Elem()                // Platzhalter erspart den Sonderfall "erstes Element"
    var pointer = this                    // läuft über das Original
    var pointerRes = dummy                // läuft über das Ergebnis
    while(pointer != null){
      pointerRes.next = new Elem(pointer.value)
      pointerRes = pointerRes.next
      pointer = pointer.next
    }
    return dummy.next.next                // Dummy UND ersten Wert überspringen -> Rest

  def length(): Int =
    var i = 0
    var p = this
    while (p != null){                    // bis zum Ende zählen (null markiert das Ende)
      i += 1
      p = p.next
    }
    return i

  def append(w: Int): Unit = {            // Unit -> nur Seiteneffekt, kein Rückgabewert
    var p = this
    while (p.next != null){               // ans Ende laufen ...
      p = p.next
    }
    p.next = new Elem(w, null)            // ... und dort anhängen
  }
}

// Aufbau von HINTEN nach vorne: jedes neue Element zeigt auf das bisherige
var tList:Elem = new Elem(2, null)
tList = new Elem(4, tList)
tList = new Elem(9, tList)
tList = new Elem(13, tList)
tList = new Elem(15, tList)      // Kette: 15 -> 13 -> 9 -> 4 -> 2

val List1:Elem = null            // leere Liste = keine Referenz
val List2 = Elem()               // Element mit den Default-Werten (0, null); 'new' optional

val s =  tList.mkString()        // (15, 13, 9, 4, 2)
val neu = tList.copy()           // unabhängige Kopie
neu.mkString()
neu.length()                     // 5
neu.tail().mkString()            // ohne das erste Element

//Queue - generisch
// [T] -> dieselbe Queue funktioniert für Int, String, Gegenstand, ...
// v = Wert, n = Referenz auf das nächste Element (null = Ende)
class Queue[T](var v: T, var n: Queue[T])

def isEmpty[T](q: Queue[T]): Boolean = {
  return q == null                        // leere Queue wird durch null dargestellt
}

def enque[T](q: Queue[T], x: T): Queue[T] = {   // FIFO: hinten einreihen
  if (q == null){
    return new Queue[T](x, null)          // Sonderfall: leere Queue -> neuer Kopf
  }
  var p = q
  while (p.n != null){                    // ans Ende laufen
    p = p.n
  }
  p.n = new Queue[T](x, null)             // dort anhängen
  return q                                // der Kopf bleibt derselbe
}

def deque[T](q: Queue[T]): Queue[T] = {   // FIFO: vorne entnehmen
  if (q == null){
    throw new Exception("Q ist leer")     // Fehlerfall bewusst als Exception
  }
  return q.n                              // alles ohne den Kopf
}

def first[T](q: Queue[T]): T = {          // vorderstes Element ansehen (ohne entfernen)
  if (q == null){
    throw new Exception("Q ist leer")
  }
  return q.v
}

def contains[T](q: Queue[T], x: T): Boolean = {
  var p = q
  while (p != null){
    if (p.v == x){                        // Wert des aktuellen Elements prüfen
      return true
    }
    p = p.n
  }
  return false
}

def queueToString[T](q: Queue[T]): String = {
  var p = q
  var s: String = ""
  while (p != null){                      // Kette durchlaufen und aufbauen
    s = s + s"${p.v} <- "
    p = p.n
  }
  return s
}

//Test
var qInt = new Queue[Int](0,null)          // Queue mit dem Startwert 0
qInt = enque(qInt, 3)
qInt = enque(qInt, 7)
qInt = enque(qInt, 5)                      // 0 <- 3 <- 7 <- 5
contains(qInt, 9)
contains(qInt, 7)
first(qInt)                                // 0 (ältestes Element)
queueToString(qInt)
qInt = deque(qInt)                         // 0 entfernen
queueToString(qInt)
first(qInt)                                // jetzt 3
qInt = deque(qInt)
queueToString(qInt)
qInt = deque(qInt)
queueToString(qInt)
qInt = deque(qInt)                         // letztes Element entfernt -> qInt ist null
isEmpty(qInt)                              // true
deque(qInt)                                // wirft "Q ist leer" — gewolltes Verhalten
