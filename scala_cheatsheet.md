# Scala CheatSheet

> Nachschlagewerk mit kommentierten Beispielen — Scala 3, Schwerpunkt funktionale
> Programmierung, Pattern Matching und Zerlegungsmuster.
> Zwei Verzeichnisse: **Übersicht** (nur Kapitel) zum groben Orientieren,
> **Detail** (alle Unterpunkte) zum direkten Anspringen.

---

## Übersicht

- [1. Konventionen & Programmformen](#1-konventionen--programmformen)
- [2. Grundlagen](#2-grundlagen)
- [3. Funktionen](#3-funktionen)
- [4. Pattern Matching](#4-pattern-matching)
- [5. Listen](#5-listen)
- [6. Arrays](#6-arrays)
- [7. Algorithmen & Zerlegungsmuster](#7-algorithmen--zerlegungsmuster)
- [8. Algebraische Datentypen mit `enum`](#8-algebraische-datentypen-mit-enum)
- [9. Klassen, Referenzen & verkettete Strukturen](#9-klassen-referenzen--verkettete-strukturen)
- [10. Fehlerbehandlung](#10-fehlerbehandlung)
- [11. Tupel & Typ-Aliase](#11-tupel--typ-aliase)
- [12. Schnellreferenz](#12-schnellreferenz)

---

## Inhaltsverzeichnis (Detail)

- [1. Konventionen & Programmformen](#1-konventionen--programmformen)
    - [1.1 Skript (`.sc`) vs. Programm (`.scala`)](#11-skript-sc-vs-programm-scala)
    - [1.2 Einstiegspunkt: `@main` und `object`](#12-einstiegspunkt-main-und-object)
    - [1.3 Ausführen](#13-ausführen)
    - [1.4 Namens- und Stilkonventionen](#14-namens--und-stilkonventionen)
    - [1.5 Einrückung statt Klammern (Scala 3)](#15-einrückung-statt-klammern-scala-3)
- [2. Grundlagen](#2-grundlagen)
    - [2.1 `val`, `var`, `def`](#21-val-var-def)
    - [2.2 Typen & Typinferenz](#22-typen--typinferenz)
    - [2.3 Ausgabe & String-Interpolation](#23-ausgabe--string-interpolation)
    - [2.4 Ausdrücke statt Anweisungen](#24-ausdrücke-statt-anweisungen)
- [3. Funktionen](#3-funktionen)
    - [3.1 Definition & Parameter](#31-definition--parameter)
    - [3.2 Funktionen höherer Ordnung](#32-funktionen-höherer-ordnung)
    - [3.3 Currying — mehrere Parameterlisten](#33-currying--mehrere-parameterlisten)
    - [3.4 Anonyme Funktionen & Platzhalter](#34-anonyme-funktionen--platzhalter)
    - [3.5 Generics & `ClassTag`](#35-generics--classtag)
- [4. Pattern Matching](#4-pattern-matching)
    - [4.1 Grundform](#41-grundform)
    - [4.2 Listenmuster `x :: xs`](#42-listenmuster-x--xs)
    - [4.3 Tupel- und Konstruktormuster](#43-tupel--und-konstruktormuster)
    - [4.4 Vollständigkeit](#44-vollständigkeit)
- [5. Listen](#5-listen)
    - [5.1 Aufbau & Grundoperationen](#51-aufbau--grundoperationen)
    - [5.2 Rekursion über Listen](#52-rekursion-über-listen)
    - [5.3 Listenfunktionale: `map`, `filter`, `fold`](#53-listenfunktionale-map-filter-fold)
    - [5.4 for-Comprehension](#54-for-comprehension)
- [6. Arrays](#6-arrays)
    - [6.1 Eigenschaften & Erzeugung](#61-eigenschaften--erzeugung)
    - [6.2 Zugriff & Manipulation](#62-zugriff--manipulation)
    - [6.3 Wichtige Methoden](#63-wichtige-methoden)
    - [6.4 Array vs. List](#64-array-vs-list)
- [7. Algorithmen & Zerlegungsmuster](#7-algorithmen--zerlegungsmuster)
    - [7.1 Lineare Verarbeitung](#71-lineare-verarbeitung)
    - [7.2 Bisektion (binäre Suche)](#72-bisektion-binäre-suche)
    - [7.3 Divide and Conquer](#73-divide-and-conquer)
    - [7.4 Sortierverfahren](#74-sortierverfahren)
    - [7.5 Greedy-Prinzip](#75-greedy-prinzip)
    - [7.6 Backtracking](#76-backtracking)
    - [7.7 Dynamische Programmierung](#77-dynamische-programmierung)
- [8. Algebraische Datentypen mit `enum`](#8-algebraische-datentypen-mit-enum)
    - [8.1 Grundform](#81-grundform)
    - [8.2 Methoden im `enum`](#82-methoden-im-enum)
    - [8.3 Suchbaum](#83-suchbaum)
    - [8.4 Traversierung](#84-traversierung)
- [9. Klassen, Referenzen & verkettete Strukturen](#9-klassen-referenzen--verkettete-strukturen)
    - [9.1 Klassen](#91-klassen)
    - [9.2 Referenzen & Pointer](#92-referenzen--pointer)
    - [9.3 Einfach verkettete Liste](#93-einfach-verkettete-liste)
    - [9.4 Generische Klasse](#94-generische-klasse)
- [10. Fehlerbehandlung](#10-fehlerbehandlung)
- [11. Tupel & Typ-Aliase](#11-tupel--typ-aliase)
- [12. Schnellreferenz](#12-schnellreferenz)
    - [12.1 Operatoren & Symbole](#121-operatoren--symbole)
    - [12.2 Scala ↔ Java ↔ Python](#122-scala--java--python)

---

## 1. Konventionen & Programmformen

### 1.1 Skript (`.sc`) vs. Programm (`.scala`)

Scala kennt zwei Betriebsarten. Sie unterscheiden sich vor allem darin, ob
**ausführbare Anweisungen** auf oberster Ebene stehen dürfen.

| | `.sc` (Skript / Worksheet) | `.scala` (Programmdatei) |
|---|---|---|
| Aufbau | reine Abfolge von Definitionen und Anweisungen | Definitionen; Ausführung nur über einen Einstiegspunkt |
| `println` auf oberster Ebene | erlaubt | **nicht** erlaubt |
| Einstiegspunkt | keiner nötig, läuft von oben nach unten | `@main` oder `object` mit `main` |
| Rückgabewerte | jeder Ausdruck wird ausgewertet und (im Worksheet) rechts angezeigt | nur, was explizit ausgegeben wird |
| Einsatz | Üben, Ausprobieren, Zusammenfassungen | echte Programme, mehrere Dateien, Projekte |

```scala
// --- Datei: uebung.sc ---------------------------------------------
def quadrat(x: Int): Int = x * x   // Definition

val a = quadrat(5)                 // Anweisung auf oberster Ebene: im Skript ERLAUBT
println(a)                         // 25
a + 1                              // Ausdruck ohne Zuweisung -> Wert wird angezeigt
```

### 1.2 Einstiegspunkt: `@main` und `object`

```scala
// --- Datei: Main.scala --------------------------------------------

// Variante 1 (Scala 3, empfohlen): @main markiert die Startmethode.
// Der Name der Methode ist frei wählbar.
@main def start(): Unit =
  println("Hallo Welt")

// Parameter werden automatisch aus den Kommandozeilenargumenten befüllt
// und in den passenden Typ konvertiert:
@main def begruesse(name: String, anzahl: Int): Unit =
  for _ <- 1 to anzahl do println(s"Hallo $name")
```

```scala
// Variante 2: object mit main — das direkte Gegenstück zu Java.
// 'object' erzeugt genau EINE Instanz (Singleton). Scala kennt kein 'static';
// alles, was in Java static wäre, kommt in ein object.
object Main {
  def main(args: Array[String]): Unit = {   // Signatur wie in Java
    println("Hallo Welt")
    if (args.nonEmpty) println(s"Erstes Argument: ${args(0)}")
  }
}
```

```scala
// class vs. object vs. companion object
class Punkt(val x: Int, val y: Int)        // Bauplan -> beliebig viele Instanzen

object Punkt {                             // Companion: gleicher Name, gleiche Datei
  val Ursprung = new Punkt(0, 0)           // "statische" Konstante
  def apply(x: Int, y: Int) = new Punkt(x, y)   // apply -> Aufruf ohne 'new' möglich
}

val p = Punkt(3, 4)                        // ruft Punkt.apply auf
```

> In Scala 3 dürfen `def`, `val` und `enum` auch in einer `.scala`-Datei direkt
> auf oberster Ebene stehen (anders als in Java, wo alles in einer Klasse liegt).
> Nur *ausführbare Anweisungen* brauchen den Einstiegspunkt.

### 1.3 Ausführen

```bash
# Skripte
scala uebung.sc                 # direkt ausführen (Scala 3.5+)
scala-cli run uebung.sc         # dasselbe über scala-cli
scala-cli repl                  # interaktive Konsole (REPL) zum Ausprobieren

# Programme
scalac Main.scala               # kompiliert zu .class-Dateien (JVM-Bytecode)
scala Main                      # startet die main-Methode
scala-cli run Main.scala        # kompiliert und startet in einem Schritt

# Projekte (mehrere Dateien, Abhängigkeiten)
sbt run                         # Quellcode liegt unter src/main/scala/
```

### 1.4 Namens- und Stilkonventionen

| Element | Konvention | Beispiel |
|---|---|---|
| Klassen, Objekte, Traits, `enum`, Typparameter | `PascalCase` | `Suchbaum`, `Elem`, `T` |
| Methoden, Werte, Variablen | `camelCase` | `insertionsort`, `arrSorted` |
| Konstanten | `PascalCase` oder `UPPER_SNAKE` | `Ursprung` |
| Pakete | klein, ohne Unterstriche | `algorithmen.sortieren` |

Weitere Gepflogenheiten: `val` vor `var` (Unveränderlichkeit ist der Normalfall) ·
`return` weglassen, der letzte Ausdruck ist der Rückgabewert · Typen bei
öffentlichen Methoden hinschreiben, innerhalb der Methode der Inferenz überlassen ·
zwei Leerzeichen Einrückung. Der Dateiname ist bei `.scala` frei wählbar — anders
als in Java —, üblich ist trotzdem der Name des enthaltenen Typs.

### 1.5 Einrückung statt Klammern (Scala 3)

```scala
// Scala 3 erlaubt Blöcke über die Einrückung — Klammern sind optional.
def f(x: Int): Int =
  val y = x * 2       // Rumpf beginnt hinter dem '=' auf der nächsten Ebene
  y + 1               // letzter Ausdruck = Rückgabewert

// Klassische Klammer-Schreibweise, weiterhin gültig und gleichwertig:
def g(x: Int): Int = {
  val y = x * 2
  y + 1
}

// match, if und for gehen ebenfalls klammerfrei:
l match
  case x :: xs => x
  case _       => 0

if (x > 0) then println("positiv") else println("negativ")   // 'then' ist Scala 3
for i <- 1 to 3 do println(i)                                 // 'do' bei Schleifen ohne yield

// end-Marker für lange Blöcke (optional, nur zur Lesbarkeit)
def langeMethode(): Unit =
  ...
end langeMethode
```

---

## 2. Grundlagen

### 2.1 `val`, `var`, `def`

```scala
val x = 42               // unveränderlich (wie 'final' in Java) -> Standardfall
// x = 43                // FEHLER: reassignment to val
var y = 42               // veränderlich -> nur nutzen, wenn wirklich nötig
y = 43                   // erlaubt

val name: String = "Ada" // Typ optional; die Inferenz leitet ihn sonst aus dem Wert ab
lazy val teuer = rechne()// wird erst beim ERSTEN Zugriff ausgewertet

def quadrat(x: Int) = x * x   // def = Methode; wird bei JEDEM Aufruf neu ausgewertet
```

### 2.2 Typen & Typinferenz

```scala
// Es gibt keine primitiven Typen wie in Java — alles ist ein Objekt.
val i: Int = 5           // 32 Bit Ganzzahl
val d: Double = 2.5      // 64 Bit Gleitkomma
val c: Char = 'A'
val s: String = "Text"
val b: Boolean = true
val u: Unit = ()         // Unit = "kein sinnvoller Wert" (entspricht void in Java)

val liste = List(1, 2, 3)          // abgeleitet: List[Int]
val gemischt = List(1, "a")        // abgeleitet: List[Any] -> meist ein Fehlerzeichen

// Typ-Ascription: den Typ ausdrücklich festschreiben
val n = 5: Int
```

### 2.3 Ausgabe & String-Interpolation

```scala
println("Text mit Umbruch")
print("ohne Umbruch")

val l1 = List(4, 1, 5)
println(s"l1: $l1")                     // s-Interpolator: $name setzt den Wert ein
println(s"Summe: ${l1.sum + 1}")        // ${...} für ganze Ausdrücke
println(s"Zeile1 \n Zeile2")            // \n = Zeilenumbruch
println(f"Wert: ${3.14159}%.2f")        // f-Interpolator: formatiert wie printf
println(l1.mkString("(", ", ", ")"))    // (4, 1, 5) — Start, Trenner, Ende
```

### 2.4 Ausdrücke statt Anweisungen

```scala
// Fast alles in Scala IST ein Ausdruck und liefert einen Wert:
val max = if (a > b) a else b           // if liefert einen Wert (kein Ternary nötig)

val text = x match                       // match liefert ebenfalls einen Wert
  case 1 => "eins"
  case _ => "andere"

// Der LETZTE Ausdruck einer Methode ist automatisch der Rückgabewert.
def addiere(a: Int, b: Int): Int =
  val summe = a + b
  summe                                  // kein 'return' nötig — und in Scala unüblich
```

> `return` funktioniert zwar (in den `.sc`-Dateien wird es verwendet), gilt aber als
> Fremdkörper: es bricht aus der Methode heraus statt einen Wert zu liefern und
> verhindert bestimmte Optimierungen. Im Zweifel weglassen.

---

## 3. Funktionen

### 3.1 Definition & Parameter

```scala
def enthalten(a: Int, l: List[Int]): Boolean = ...
//  ^Name    ^Parameter mit Typ      ^Rückgabetyp

def gruss(name: String, gruss: String = "Hallo"): String =   // Default-Parameter
  s"$gruss, $name!"

gruss("Bob")                        // Hallo, Bob!
gruss("Bob", gruss = "Hi")          // benanntes Argument -> Reihenfolge egal

def zeigeAn(x: Int): Unit =         // Unit = gibt nichts Sinnvolles zurück
  println(x)
```

### 3.2 Funktionen höherer Ordnung

```scala
// Eine Funktion kann selbst Parameter sein. Der Typ einer Funktion, die zwei A
// nimmt und Boolean liefert, schreibt sich: (A, A) => Boolean
def kleinstes[A](kl: (A, A) => Boolean)(l: List[A]): A = ...
//                   ^ Vergleichsfunktion als Parameter

// Damit wird derselbe Algorithmus für beliebige Typen und Ordnungen nutzbar:
def klInt(x: Int, y: Int): Boolean = x < y          // als Methode
val vklint = ((x: Int, y: Int) => x < y)            // als anonyme Funktion in einem val

kleinstes(klInt)(List(3, 1, 2))                     // 1
```

### 3.3 Currying — mehrere Parameterlisten

```scala
// Statt EINER Liste mit allen Parametern werden mehrere Listen geschrieben:
def minSort[A](kl: (A, A) => Boolean)(gl: (A, A) => Boolean)(l: List[A]): List[A] = ...
minSort(klInt)(glInt)(l1)          // Aufruf: eine Klammer pro Liste

// Vorteil 1: partielle Anwendung — nur einen Teil der Parameter festlegen
val sortiereInts = minSort(klInt)(glInt)   // liefert eine Funktion List[Int] => List[Int]
sortiereInts(l1)
sortiereInts(l2)                            // Vergleichsfunktionen stehen schon fest

// Vorteil 2: die Typinferenz arbeitet Liste für Liste ab -> in späteren Listen
// muss der Typ oft nicht mehr angegeben werden.
```

### 3.4 Anonyme Funktionen & Platzhalter

```scala
(x: Int) => x + 5                 // anonyme Funktion (Lambda): Parameter => Rumpf
l.map((n: Int) => n + 5)          // ausgeschrieben
l.map(n => n + 5)                 // Typ ergibt sich aus der Liste
l.map(_ + 5)                      // Platzhalter: '_' steht für das aktuelle Element

l.filter(_ % 2 == 0)              // jede '_' steht für EIN Vorkommen, von links nach rechts
arr.filter(ek(_, pivot))          // entspricht: arr.filter(e => ek(e, pivot))
```

### 3.5 Generics & `ClassTag`

```scala
// [A] ist ein Typparameter: die Funktion arbeitet mit beliebigen Typen,
// ohne etwas über sie anzunehmen.
def reverse[A](l: List[A]): List[A] = ...
def mymap[A, B](f: A => B)(l: List[A]): List[B] = ...     // zwei Typparameter

// Sonderfall Array: zur Laufzeit muss der Elementtyp bekannt sein, um ein neues
// Array anzulegen (Type Erasure der JVM). Dafür sorgt ein ClassTag:
import scala.reflect.ClassTag

def slice[T: ClassTag](a1: Array[T], a2: Array[T]): Array[T] =
  var ret: Array[T] = Array.ofDim[T](a1.length + a2.length)   // ohne ClassTag: Compilerfehler
  ...
// Bei List[A] ist das NICHT nötig — nur Arrays sind auf der JVM echte Arrays.
```

---

## 4. Pattern Matching

### 4.1 Grundform

```scala
// match vergleicht einen Wert der Reihe nach mit MUSTERN. Der erste Treffer
// gewinnt; sein Zweig wird ausgewertet und ist das Ergebnis des Ausdrucks.
x match
  case 1 => "eins"
  case 2 | 3 => "zwei oder drei"        // '|' = oder
  case n if n < 0 => "negativ"          // Guard: Zusatzbedingung
  case _ => "etwas anderes"             // '_' = Wildcard, passt immer
```

### 4.2 Listenmuster `x :: xs`

```scala
// Das wichtigste Muster überhaupt: eine Liste in Kopf und Rest zerlegen.
l match
  case x :: xs => ...      // x = erstes Element (Kopf), xs = Restliste (kann leer sein)
  case Nil     => ...      // leere Liste
  case _       => ...      // Auffangfall

// Weitere Listenmuster:
case x :: List()      => ...   // genau EIN Element (List() == Nil)
case x1 :: x2 :: xs   => ...   // mindestens ZWEI Elemente
case List(a, b, c)    => ...   // genau drei Elemente
```

Damit ergibt sich das Standardschema jeder Listenrekursion: ein Fall für die leere
Liste (Rekursionsanker) und ein Fall `x :: xs`, in dem `x` verarbeitet und für `xs`
rekursiv weitergemacht wird.

### 4.3 Tupel- und Konstruktormuster

```scala
// Mehrere Werte gleichzeitig prüfen: als Tupel zusammenfassen
(l1, l2) match
  case (x :: xs, y :: ys) => ...       // beide Listen haben Elemente
  case (x :: xs, List())  => l1        // nur die linke
  case (List(), y :: ys)  => l2        // nur die rechte
  case _                  => List()    // beide leer

// Konstruktormuster: zerlegt ein Objekt und bindet seine Felder an Namen
this match
  case Knoten(w, li, re) => ...        // w, li, re sind sofort verwendbar
  case Leer()            => ...

// Muster können geschachtelt werden:
case Knoten(w, Leer(), re) => ...      // Knoten, dessen linkes Kind leer ist

// Destrukturierung außerhalb von match (auch für Tupel):
val (name, value, weight) = g(i - 1)   // zerlegt ein 3er-Tupel in drei Werte
```

### 4.4 Vollständigkeit

```scala
// Passt KEIN Muster, gibt es zur Laufzeit eine MatchError.
def kleinstes[A](kl: (A, A) => Boolean)(l: List[A]): A =
  l match
    case x :: List() => x
    case x1 :: x2 :: xs => ...
    // kein Fall für die leere Liste -> kleinstes(kl)(List()) wirft MatchError!

// Bei enum und versiegelten Typen warnt der Compiler, wenn Fälle fehlen.
// Bei Listen tut er das nicht immer -> den Nil-Fall bewusst mitdenken.
```

---

## 5. Listen

### 5.1 Aufbau & Grundoperationen

```scala
// Eine List ist UNVERÄNDERLICH und einfach verkettet: ein Kopf plus eine Restliste.
val l = List(4, 1, 5)
val leer = List()                 // leere Liste; identisch zu Nil

x :: l                            // 'cons': EIN Element vorne anhängen -> O(1)
l ::: l2                          // zwei LISTEN verketten                -> O(n) über die linke
x :: Nil                          // Ein-Element-Liste (häufig am Rekursionsende)

l.head                            // erstes Element      (Exception, wenn leer)
l.tail                            // Rest ohne den Kopf  (Exception, wenn leer)
l.isEmpty  l.length  l.reverse
l.contains(5)  l.sum  l.max  l.min
l.take(2)  l.drop(2)              // erste n Elemente / erste n überspringen
l :+ x                            // hinten anhängen — O(n), erzeugt eine neue Liste
```

> Merkhilfe: der Doppelpunkt zeigt immer zur Liste. `x :: liste` (Element links),
> `liste :+ x` (Element rechts). Anhängen vorne ist billig, hinten teuer.

### 5.2 Rekursion über Listen

```scala
// Immer dasselbe Schema: Muster zerlegen -> Kopf verarbeiten -> Rest rekursiv.
def enthalten[A](gleich: (A, A) => Boolean)(a: A, l: List[A]): Boolean =
  l match
    case x :: xs => if (gleich(a, x)) true else enthalten(gleich)(a, xs)
    case _ => false                 // Rekursionsanker: leere Liste -> nicht gefunden

def reverse[A](l: List[A]): List[A] =
  l match
    case x :: xs => reverse(xs) ::: x :: List()   // Rest umdrehen, Kopf ans ENDE
    case _ => List()
```

### 5.3 Listenfunktionale: `map`, `filter`, `fold`

```scala
// map: wendet f auf JEDES Element an -> gleiche Länge, evtl. anderer Typ
def mymap[A, B](f: A => B)(l: List[A]): List[B] =
  l match
    case x :: xs => f(x) :: mymap(f)(xs)
    case List() => List()

// filter: behält nur Elemente, für die das Prädikat true liefert
def myfilter[A](f: A => Boolean)(l: List[A]): List[A] =
  l match
    case x :: xs => if (f(x)) x :: myfilter(f)(xs) else myfilter(f)(xs)
    case List() => List()

// fold: faltet die Liste mit einem Startwert zu EINEM Ergebnis zusammen
def myfoldR[A, B](s: B)(op: (A, B) => B)(l: List[A]): B =
  l match
    case List() => s                              // Startwert am Ende
    case x :: xs => op(x, myfoldR(s)(op)(xs))      // op(x1, op(x2, ... op(xn, s)))

// Eingebaut und identisch nutzbar:
l.map(_ + 5)
l.filter(_ % 2 == 0)
l.foldRight(0)((x, acc) => x + acc)   // von RECHTS: op(x1, op(x2, ... op(xn, start)))
l.foldLeft(0)((acc, x) => acc + x)    // von LINKS:  op(op(op(start, x1), x2) ...)

// Typische Anwendung: eine Rekursion durch ein fold ersetzen
def insertionsortFold[A](kg: (A, A) => Boolean)(l: List[A]): List[A] =
  l.foldRight(List[A]())((y, ys) => einfuegen(kg)(y, ys))
```

| | `foldRight` | `foldLeft` |
|---|---|---|
| Richtung | von hinten nach vorne | von vorne nach hinten |
| Operator | `(element, akku) => …` | `(akku, element) => …` |
| Typisch für | Listen wieder aufbauen (`::`) | Werte aufsummieren, Zähler |

### 5.4 for-Comprehension

```scala
// for … yield baut eine neue Sammlung — es ist Kurzschreibweise für map/filter.
for x <- l yield f(x)                 // entspricht: l.map(f)
for x <- l if f(x) yield x            // entspricht: l.filter(f)
for x <- (0 to 6) if x % 2 == 0 yield x   // Vector(0, 2, 4, 6)

// Ohne yield: reine Schleife, Rückgabe ist Unit (nur Seiteneffekte)
for (elem <- arr) print(elem)

// Mehrere Generatoren = geschachtelte Schleifen
for (i <- 0 to 2; j <- 0 to 2) yield (i, j)
```

---

## 6. Arrays

### 6.1 Eigenschaften & Erzeugung

Ein Array ist eine Datenstruktur **fester Länge**, deren Elemente alle denselben
Datentyp haben und über einen Index angesprochen werden. Anders als `List` sind
die Felder **veränderbar** — auch bei `val arr`, denn unveränderlich ist dann nur
die Referenz, nicht der Inhalt.

```scala
var arr1: Array[Int] = Array.ofDim[Int](5)   // leeres Array der Länge 5 (mit Nullen gefüllt)
var arr2 = Array(3, 0, 1, 4, 2)              // Werte direkt zuweisen
var arr3 = Array.fill(5)(7)                  // (n)(wert)  -> Array(7, 7, 7, 7, 7)
var arr4 = Array.range(1, 4)                 // (start, end) -> Array(1, 2, 3), Ende exklusiv
var arr5 = Array.tabulate(5)(i => i * 2)     // (n)(f) -> Wert je Index berechnen

var matrix: Array[Array[Int]] = Array.ofDim(3, 3)   // 3x3-Matrix, alle Felder 0
```

### 6.2 Zugriff & Manipulation

```scala
arr1(0)                       // RUNDE Klammern statt eckiger (arr1(0) ruft arr1.apply(0))
arr1(0) = 1                   // Feld überschreiben
arr1.length                   // Länge (bei List: .length ebenfalls, bei Java-Array: .length)
arr1.indices                  // 0 until length -> ideal für Schleifen

for (i <- arr1.indices) arr1(i) = i          // Durchlauf MIT Index
for (elem <- arr1) print(elem)               // Durchlauf über die Werte

for (i <- matrix.indices; j <- matrix.indices)   // zwei Generatoren = zwei Schleifen
  matrix(i)(j) = i * 3 + j                       // i = Zeile, j = Spalte

for (row <- matrix) println(row.mkString("(", ", ", ")"))   // Matrix zeilenweise ausgeben
print(arr1.mkString("(", ", ", ")"))         // (1, 1, 2, 3, 4)
```

> `println(arr1)` gibt nur die Speicheradresse aus — für Arrays immer `mkString`
> verwenden.

### 6.3 Wichtige Methoden

```scala
arr1.sum   arr1.max   arr1.min          // Summe, Maximum, Minimum
arr2.sorted                              // NEUE sortierte Kopie (Original unverändert)
arr1.reverse                             // neue umgedrehte Kopie
arr1.filter(_ % 2 == 0)                  // nur passende Elemente
arr1.map(_ * 2)                          // jedes Element umrechnen
arr1.contains(5)                         // enthalten? -> Boolean
arr1.clone()                             // echte Kopie — wichtig vor In-place-Sortierung!
arr1.take(n)   arr1.drop(n)              // erste n / alles ab n
a1 ++ a2                                 // zwei Arrays verketten
arr1.toList                              // in eine List umwandeln
```

### 6.4 Array vs. List

| | `Array` | `List` |
|---|---|---|
| Länge | fest | beliebig, wächst über `::` |
| Inhalt | veränderbar (`arr(0) = 1`) | unveränderlich |
| Zugriff per Index | O(1) | O(n) |
| Vorne anfügen | nicht möglich | O(1) über `::` |
| Zerlegung im `match` | unüblich | `x :: xs` |
| Typischer Stil | imperativ, `while`/`for` mit Index | rekursiv, Pattern Matching |

---

## 7. Algorithmen & Zerlegungsmuster

### 7.1 Lineare Verarbeitung

Elementweise Abarbeitung: für *n* Elemente ergibt sich eine Laufzeit von *O(n)*.

```scala
def linearSearchArray(arr: Array[Int], x: Int): Boolean =
  var i = 0
  var found = false
  while (i < arr.length && !found) {   // Abbruch, sobald gefunden
    if (arr(i) == x) found = true
    i += 1
  }
  found
```

### 7.2 Bisektion (binäre Suche)

Die Problemgröße wird pro Schritt **halbiert** — Laufzeit *O(log₂ n)*. Voraussetzung:
die Daten müssen **sortiert** sein. Über zwei Grenzen wird der Suchbereich verkleinert.

```scala
def binarySearchArray(arr: Array[Int], x: Int): Boolean =
  var left = 0
  var right = arr.length - 1
  var found = false
  while (left <= right && !found) {          // '&& !found' beendet die Schleife beim Treffer
    val mid = (left + right) / 2             // Mitte des aktuellen Bereichs
    if (x < arr(mid)) right = mid - 1        // Ziel liegt links  -> rechte Hälfte verwerfen
    else if (x > arr(mid)) left = mid + 1    // Ziel liegt rechts -> linke Hälfte verwerfen
    else found = true                        // Treffer
  }
  found
```

### 7.3 Divide and Conquer

Die Datenmenge wird in zwei (oder mehr) möglichst gleich große Teilmengen zerlegt:

1. **Divide** — Aufteilung vornehmen
2. **Conquer** — Teile rekursiv lösen
3. **Merge** — Teillösungen zur Gesamtlösung kombinieren

Beispiele: Quicksort (Aufwand steckt im Divide) und Mergesort (Aufwand steckt im Merge).

### 7.4 Sortierverfahren

```scala
// --- Min-Sort: kleinstes Element suchen, herausstreichen, vorne anhängen ---
def minSort[A](kl: (A, A) => Boolean)(gl: (A, A) => Boolean)(l: List[A]): List[A] =
  l match
    case x :: xs =>
      val min = kleinstes(kl)(l)          // Minimum der Restliste bestimmen
      val tmpL = streiche(gl)(min, l)     // genau ein Vorkommen entfernen
      min :: minSort(kl)(gl)(tmpL)        // Minimum vorne, Rest rekursiv
    case _ => List()

// --- Insertion-Sort: Element in eine bereits sortierte Liste einsortieren ---
def einfuegen[A](kg: (A, A) => Boolean)(w: A, l: List[A]): List[A] =
  l match
    case x :: xs => if (kg(w, x)) w :: x :: xs        // Platz gefunden
                    else x :: einfuegen(kg)(w, xs)     // weiter nach hinten
    case _ => List(w)                                   // ans Ende

// --- Quicksort: Pivot wählen, aufteilen, rekursiv zusammensetzen ---
def quicksort(l: List[Int]): List[Int] =
  l match
    case x :: xs =>
      val kl = l.filter(_ < x)                  // alles kleiner als das Pivot
      val gr = l.filter(_ > x)                  // alles größer
      quicksort(kl) ::: x :: quicksort(gr)      // sortiert links + Pivot + sortiert rechts
    case _ => List()

// --- Merge: zwei SORTIERTE Listen reißverschlussartig verschmelzen ---
def merge(l1: List[Int], l2: List[Int]): List[Int] =
  (l1, l2) match
    case (x :: xs, y :: ys) => if (x < y) x :: merge(xs, l2) else y :: merge(l1, ys)
    case (x :: xs, List()) => l1              // rechte Liste leer -> Rest übernehmen
    case (List(), y :: ys) => l2
    case _ => List()
```

| Verfahren | Best | Average | Worst | Prinzip |
|---|---|---|---|---|
| Selection- / Min-Sort | O(n²) | O(n²) | O(n²) | Minimum suchen und nach vorne |
| Insertion-Sort | O(n) | O(n²) | O(n²) | in sortierten Teil einfügen |
| Quicksort | O(n log n) | O(n log n) | O(n²) | Divide & Conquer, Aufwand beim Teilen |
| Mergesort | O(n log n) | O(n log n) | O(n log n) | Divide & Conquer, Aufwand beim Mischen |
| Lineare Suche | O(1) | O(n) | O(n) | jedes Element prüfen |
| Binäre Suche | O(1) | O(log n) | O(log n) | Bisektion, setzt Sortierung voraus |

### 7.5 Greedy-Prinzip

Jede Entscheidung wird im Moment **bestmöglich** getroffen (etwa nach Kosten oder
Wert). Das liefert – sofern möglich – eine Lösung, aber nicht zwingend die beste;
getroffene Entscheidungen werden **nicht zurückgenommen**.

Beispiel Rucksackproblem: ein Einbrecher wählt Wertgegenstände (Gewicht, Wert) bei
begrenzter Tragekapazität.

1. Gegenstände nach Wert sortieren
2. immer den wertvollsten noch passenden Gegenstand nehmen
3. wiederholen, bis die Kapazität erreicht ist

```scala
type Gegenstand = (String, Int, Int)      // Typalias: Name, Wert, Gewicht

def knapSackGreedy(g: Array[Gegenstand], capacity: Int): List[Gegenstand] =
  var backPack: List[Gegenstand] = Nil
  var carriedW: Int = 0
  val gSorted = sortiereNachWertAbsteigend(g)     // Schritt 1
  for (i <- gSorted) {                            // Schritt 2 + 3
    if (carriedW + i._3 <= capacity) {            // passt es noch hinein?
      backPack = backPack ::: i :: Nil
      carriedW += i._3
    }
  }
  backPack
```

### 7.6 Backtracking

Systematisches Durchprobieren **aller** Möglichkeiten. Führt eine Entscheidung nicht
zur Lösung, wird sie zurückgenommen. Findet alle Lösungen, hat aber exponentielle
Laufzeit.

```scala
def knapSackBackTrAlg(g: Array[Gegenstand], index: Int, capacityCurrent: Int,
                      capacity: Int, path: List[Gegenstand]): List[List[Gegenstand]] = {
  if (capacityCurrent > capacity) return Nil      // Zweig ungültig -> abschneiden
  if (index >= g.length) return List(path)        // alle geprüft -> Lösung zurückgeben

  // An JEDEM Gegenstand verzweigt der Suchbaum in zwei Äste:
  val withItem    = knapSackBackTrAlg(g, index + 1, capacityCurrent + g(index)._3,
                                      capacity, path :+ g(index))   // (1) mitnehmen
  val withoutItem = knapSackBackTrAlg(g, index + 1, capacityCurrent,
                                      capacity, path)               // (2) weglassen
  withItem ++ withoutItem                          // beide Teilergebnisse sammeln
}
```

### 7.7 Dynamische Programmierung

Vermeidet **doppelte Berechnungen**, indem Teilergebnisse in einer Tabelle abgelegt
werden. Damit lassen sich Probleme lösen, die rekursiv exponentiell wären —
Voraussetzung sind optimale Teillösungen (optimale Substruktur).

```scala
def knapSackDP(g: Array[Gegenstand], capacity: Int): (Int, List[Gegenstand]) = {
  val n = g.length
  val dp = Array.ofDim[Int](n + 1, capacity + 1)   // dp(i)(w) = bester Wert mit i Gegenständen
                                                    //            bei Kapazität w
  // Schritt 1: Tabelle zeilenweise füllen
  for (i <- 1 to n; w <- 0 to capacity) {
    val (name, value, weight) = g(i - 1)
    if (weight <= w)
      dp(i)(w) = Math.max(dp(i - 1)(w),                    // ohne diesen Gegenstand
                          dp(i - 1)(w - weight) + value)   // mit ihm
    else
      dp(i)(w) = dp(i - 1)(w)                              // passt nicht -> Wert von oben
  }

  // Schritt 2: rückwärts ablesen, WELCHE Gegenstände gewählt wurden
  var w = capacity
  var resValue = dp(n)(capacity)
  var chosenItems = List[Gegenstand]()
  for (i <- n until 0 by -1 if resValue > 0) {
    val (name, value, weight) = g(i - 1)
    if (resValue != dp(i - 1)(w)) {          // Wert hat sich geändert -> Item war dabei
      chosenItems = (name, value, weight) :: chosenItems
      resValue -= value
      w -= weight
    }
  }
  (dp(n)(capacity), chosenItems)
}
```

| Verfahren | Findet | Laufzeit | Kernidee |
|---|---|---|---|
| Greedy | eine, evtl. nicht optimale Lösung | meist O(n log n) | lokal bestmöglich wählen |
| Backtracking | alle Lösungen | exponentiell | alles durchprobieren, zurücknehmen |
| Dynamische Programmierung | optimale Lösung | O(n · Kapazität) | Teilergebnisse speichern |

---

## 8. Algebraische Datentypen mit `enum`

### 8.1 Grundform

```scala
// enum definiert einen Typ, der GENAU EINE aus mehreren Formen annehmen kann.
// Anders als Javas enum dürfen die Fälle Parameter tragen -> rekursive Strukturen.
enum Baum {
  case LeerB()                                  // Fall ohne Inhalt
  case KnotenB(x: Char, li: Baum, re: Baum)     // Fall mit Feldern, verweist auf sich selbst
}
import Baum.*          // macht LeerB() und KnotenB(...) ohne Präfix nutzbar

val abc: Baum = KnotenB('A', KnotenB('B', LeerB(), LeerB()), LeerB())

// Der Compiler kennt ALLE Fälle -> er warnt, wenn ein match einen Fall vergisst.
def treeToList(b: Baum): List[Char] =
  b match
    case LeerB() => List()
    case KnotenB(x, li, re) => x :: treeToList(li) ::: treeToList(re)   // Pre-Order
```

### 8.2 Methoden im `enum`

```scala
// Methoden dürfen direkt im enum stehen. 'this' ist dann der aktuelle Fall,
// der über 'this match' zerlegt wird — das Gegenstück zur Vererbung in Java.
enum Suchbaum[A] {                                   // [A] -> für beliebige Elementtypen
  case Knoten(wert: A, li: Suchbaum[A], re: Suchbaum[A])
  case Leer()

  def size(): Int =
    this match
      case Leer() => 0
      case Knoten(w, li, re) => 1 + li.size() + re.size()   // rekursiv über beide Teilbäume
}
```

### 8.3 Suchbaum

Ein Suchbaum hält die Ordnung ein: links stehen kleinere, rechts größere Werte.
Dadurch halbiert jeder Vergleich den Suchraum (Bisektion, vgl. 7.2).

```scala
def enthaelt(kl: (A, A) => Boolean)(x: A): Boolean =
  this match
    case Knoten(w, li, re) =>
      if (kl(x, w)) li.enthaelt(kl)(x)         // x < w -> nur links weitersuchen
      else if (kl(w, x)) re.enthaelt(kl)(x)    // x > w -> nur rechts weitersuchen
      else true                                 // weder kleiner noch größer -> gleich
    case Leer() => false                        // Ende erreicht, nichts gefunden

def ein(kl: (A, A) => Boolean)(x: A): Suchbaum[A] =
  this match
    case Leer() => Knoten(x, Leer(), Leer())    // Einfügestelle gefunden
    case Knoten(w, li, re) =>
      if (kl(x, w)) Knoten(w, li.ein(kl)(x), re)     // neuer Knoten mit neuem linken Teilbaum
      else if (kl(w, x)) Knoten(w, li, re.ein(kl)(x))
      else Knoten(w, li, re)                          // schon vorhanden -> unverändert
// Wichtig: es wird nichts überschrieben. Jede Änderung erzeugt einen NEUEN Baum,
// der die unveränderten Teilbäume mitbenutzt (persistente Datenstruktur).
```

```scala
// Ganze Liste einfügen — foldRight faltet die Liste über den Baum
def einL(kl: (A, A) => Boolean)(l: List[A]): Suchbaum[A] =
  l.foldRight(this)((x, b) => b.ein(kl)(x))
// einL(List(1, 2, 3)) entspricht ein(1) angewendet auf ein(2) auf ein(3) auf diesem Baum
```

Beim **Löschen** ist der Knoten mit zwei Kindern der interessante Fall: er wird durch
den größten Wert des linken oder den kleinsten des rechten Teilbaums ersetzt, damit
die Ordnung erhalten bleibt.

```scala
else // w == x, der zu löschende Knoten
  if (li == Leer()) re                                    // nur ein Kind -> hochziehen
  else if (re == Leer()) li
  else if (li.size() > re.size()) Knoten(li.last(), li.init(), re)  // größter von links
  else Knoten(re.head(), li, re.tail())                             // kleinster von rechts
```

### 8.4 Traversierung

```scala
def trav(): String =
  this match
    case Leer() => "."
    case Knoten(w, li, re) => "( " + li.trav() + " " + w.toString + " " + re.trav() + " )"
// In-Order: erst links, dann der eigene Wert, dann rechts
// -> bei einem Suchbaum ergibt das die aufsteigend sortierte Reihenfolge.
// Pre-Order:  Wert zuerst      (x :: links ::: rechts)
// Post-Order: Wert zuletzt     (links ::: rechts ::: List(x))
```

---

## 9. Klassen, Referenzen & verkettete Strukturen

### 9.1 Klassen

```scala
// Die Konstruktorparameter stehen direkt hinter dem Klassennamen.
// 'var' davor macht daraus ein veränderbares Feld, 'val' ein festes,
// ohne Schlüsselwort bleibt der Parameter privat.
class Elem(var value: Int = 0, var next: Elem = null) {   // mit Default-Werten
  def head(): Int = this.value                             // 'this' = dieses Objekt

  def length(): Int =
    var i = 0
    var p = this
    while (p != null) { i += 1; p = p.next }
    i
}

val e = new Elem(2, null)     // Instanz erzeugen
val e2 = Elem()               // 'new' ist in Scala 3 meist optional
e.value = 5                   // Feldzugriff (weil als var deklariert)
```

### 9.2 Referenzen & Pointer

Werden Daten in den Speicher geschrieben, verweist eine **Referenz** auf sie; sie
wird in einer Variablen dieses Datentyps abgelegt und dient als Pointer, über den
die Daten adressiert und verändert werden. Nach demselben Prinzip entstehen
verkettete Listen und Bäume: jedes Objekt speichert eine Referenz auf das nächste.

Für die **Traversierung** braucht es einen zusätzlichen Pointer, weil die vorhandene
Referenz sonst verändert würde und der Einstiegspunkt verloren ginge. Daten, die von
keiner Referenz mehr erreichbar sind, räumt der **Garbage Collector** zur Laufzeit weg.

```scala
var p = this                  // Arbeitszeiger — 'this' selbst bleibt unangetastet
while (p.next != null) {
  p = p.next                  // nur der Zeiger wandert weiter
}
```

### 9.3 Einfach verkettete Liste

```scala
// value = Nutzwert, next = Referenz auf das nächste Element (null = Ende)
class Elem(var value: Int = 0, var next: Elem = null)

var tList: Elem = new Elem(2, null)   // hinterstes Element zuerst
tList = new Elem(4, tList)            // neues Element ZEIGT auf das bisherige -> vorne einfügen
tList = new Elem(9, tList)            // ergibt: 9 -> 4 -> 2

// Kopieren: ohne Kopie zeigen zwei Variablen auf DIESELBE Kette
def copy(): Elem =
  var c = new Elem(this.value, null)  // neuer Kopf
  var p = this.next                   // läuft über das Original
  var q = c                           // läuft über die Kopie
  while (p != null) {
    q.next = new Elem(p.value, null)  // für jedes Original-Element ein NEUES anlegen
    p = p.next
    q = q.next
  }
  c
```

```scala
// Dummy-Knoten: erspart Sonderfälle am Listenanfang
var dummy = new Elem()          // Platzhalter VOR dem eigentlichen Kopf
var pointerRes = dummy
...                             // bequem anhängen, ohne den ersten Fall gesondert zu prüfen
dummy.next                      // am Ende den Platzhalter überspringen
```

### 9.4 Generische Klasse

```scala
// [T] macht die Klasse für beliebige Elementtypen nutzbar.
class Queue[T](var v: T, var n: Queue[T])       // v = Wert, n = nächstes Element

def isEmpty[T](q: Queue[T]): Boolean = q == null

def enque[T](q: Queue[T], x: T): Queue[T] =     // FIFO: hinten anfügen
  if (q == null) new Queue[T](x, null)
  else
    var p = q
    while (p.n != null) p = p.n                 // bis ans Ende laufen
    p.n = new Queue[T](x, null)
    q                                            // Kopf bleibt derselbe

def deque[T](q: Queue[T]): Queue[T] =           // FIFO: vorne entnehmen
  if (q == null) throw new Exception("Q ist leer")
  else q.n                                       // Rest ohne den Kopf
```

---

## 10. Fehlerbehandlung

```scala
try
  {...}
catch
  // Die Fälle sind Muster wie im match: Typ prüfen und an einen Namen binden
  case e1: NumberFormatException  => {...}
  case e2: ArithmeticException    => {...}
  case e3: IndexOutOfBoundsException => {...}
  case e4 => {...}          // alle übrigen Fälle (entspricht case _)
finally
  {...}                     // läuft IMMER — zum Aufräumen

// Exceptions selbst auslösen
throw new Exception("...")
throw new ArithmeticException("...")
```

```scala
// try ist ein AUSDRUCK und liefert einen Wert:
val zahl = try text.toInt catch case _: NumberFormatException => 0

// Funktionaler Weg ohne Exceptions: Option / Try
val z: Option[Int] = text.toIntOption      // Some(42) oder None
```

---

## 11. Tupel & Typ-Aliase

```scala
// Ein Tupel fasst mehrere Werte unterschiedlichen Typs zusammen.
val g = ("Goldbarren", 100, 10)      // Typ: (String, Int, Int)
g._1                                  // "Goldbarren" — Zugriff über Position, ab 1 gezählt!
g._3                                  // 10

// Destrukturierung ist meist lesbarer als _1/_2/_3
val (name, value, weight) = g

// type-Alias: gibt einem bestehenden Typ einen sprechenden Namen
type Gegenstand = (String, Int, Int)  // Name, Wert, Gewicht
def ekWert(g1: Gegenstand, g2: Gegenstand): Boolean = g1._2 < g2._2
// Der Alias erzeugt KEINEN neuen Typ — er ist nur eine Abkürzung für den Compiler.
```

---

## 12. Schnellreferenz

### 12.1 Operatoren & Symbole

| Symbol | Bedeutung |
|---|---|
| `::` | Element vorne an eine Liste anfügen (cons) |
| `:::` | zwei Listen verketten |
| `++` | zwei Sammlungen verketten (auch Arrays) |
| `:+` | Element hinten anfügen |
| `=>` | Funktionspfeil bzw. Trenner im `case` |
| `_` | Wildcard im Muster / Platzhalter im Lambda / alle Namen beim Import |
| `<-` | Generator in `for` |
| `Nil` | leere Liste, identisch zu `List()` |
| `|` | Oder-Verknüpfung im Muster |
| `@main` | markiert den Einstiegspunkt eines Programms |

### 12.2 Scala ↔ Java ↔ Python

| Thema | Scala | Java | Python |
|---|---|---|---|
| Konstante | `val x = 1` | `final int x = 1;` | Konvention `X = 1` |
| Variable | `var x = 1` | `int x = 1;` | `x = 1` |
| Typangabe | `x: Int` (nachgestellt) | `int x` (vorangestellt) | optionaler Hint |
| Methode | `def f(x: Int): Int = …` | `int f(int x) { … }` | `def f(x): …` |
| Rückgabe | letzter Ausdruck | `return` | `return` |
| Einstiegspunkt | `@main` / `object … main` | `public static void main` | `if __name__ == "__main__":` |
| „static" | `object` | `static` | Modulebene |
| Liste | `List` (unveränderlich) | `ArrayList` | `list` |
| Fallunterscheidung | `match` / `case` | `switch` | `match` (ab 3.10) |
| Lambda | `(x: Int) => x + 1` | `x -> x + 1` | `lambda x: x + 1` |
| Ausführung | `scala Datei.sc` | `javac` + `java` | `python datei.py` |
