// =============================================================================
// ZUSAMMENFASSUNG INF 1 — Listen, Rekursion, Pattern Matching, Bäume
// =============================================================================
//
//   ┌───────────────────────────────────────────────────────────────────────┐
//   │  LISTEN                                                               │
//   │    enthalten()   -> lineare Suche über Vergleichsfunktion             │
//   │    reverse()     -> Liste umdrehen                                    │
//   │    streiche()    -> erstes passendes Element entfernen                │
//   │                                                                       │
//   │  SORTIEREN                                                            │
//   │    kleinstes() / grosste()  -> Extremum einer Liste                   │
//   │    minSort() / maxSort()    -> Selectionsort (O(n^2))                 │
//   │    einfuegen() / insertionsort() -> Insertionsort (O(n^2))            │
//   │    insertionsortFold()      -> dieselbe Idee als foldRight            │
//   │    quicksort() / mergesort()-> Divide and Conquer                     │
//   │                                                                       │
//   │  LISTENFUNKTIONALE                                                    │
//   │    mymap() / myfilter() / myfoldR()  -> je rekursiv,                  │
//   │    mymap2() / myfilter2()            -> je als for-Comprehension      │
//   │                                                                       │
//   │  BÄUME                                                                │
//   │    enum Suchbaum[A] -> generischer Suchbaum mit Methoden              │
//   │    enum Baum        -> einfacher Zeichenbaum + treeToList()           │
//   └───────────────────────────────────────────────────────────────────────┘
//
// Datei-Typ: .sc — Skript, läuft von oben nach unten, kein @main nötig.
// Erklärungen zu allen verwendeten Konstrukten: siehe scala_cheatsheet.md
// =============================================================================

//Listen
//Suchen
// [A] = Typparameter -> funktioniert für JEDEN Elementtyp.
// (gleich)(a, l) = zwei Parameterlisten (Currying): erst die Vergleichsfunktion,
// dann die eigentlichen Daten -> enthalten(glInt) ist wiederverwendbar.
def enthalten[A](gleich: (A, A) => Boolean)(a: A, l: List[A]): Boolean =
  l match
    // x = Kopf, xs = Restliste; Treffer -> true, sonst rekursiv über den Rest
    case x :: xs => if (gleich(a, x)) true else enthalten(gleich)(a, xs)
    case _ => false                      // leere Liste -> nicht gefunden (Rekursionsanker)

//Reverse
def reverse[A](l: List[A]): List[A] =
  l match
    // Rest umdrehen und den Kopf HINTEN anhängen
    // ':::' verkettet zwei Listen, 'x :: List()' ist die Ein-Element-Liste
    case x :: xs => reverse(xs) ::: x :: List()
    case _ => List()

//Sortieralgorithmen
// Entfernt das ERSTE Element, das laut 'gleich' zu a passt (nicht alle!)
def streiche[A](gleich: (A, A) => Boolean)(a: A, l: List[A]): List[A] =
  l match
    // Treffer -> xs zurückgeben (x fällt weg); sonst x behalten und weitersuchen
    case x :: xs => if (gleich(x, a)) xs else x :: streiche(gleich)(a, xs)
    case _ => List()
//Min-Sort
// Sucht das Minimum, indem immer die beiden vordersten Elemente verglichen werden
// und der Verlierer wegfällt ("Turnier").
def kleinstes[A](kl: (A, A) => Boolean)(l: List[A]): A =
  l match
    case x :: List() => x                // genau ein Element -> das ist das Minimum
    // Sieger bleibt vorne, Verlierer wird verworfen
    case x1 :: x2 :: xs => if (kl(x1, x2)) kleinstes(kl)(x1 :: xs) else kleinstes(kl)(x2 :: xs)
    case _ => throw new Exception("kleinstes: leere Liste hat kein Minimum")

// Zwei Vergleichsfunktionen nötig: kl zum Finden des Minimums, gl zum Herausstreichen
def minSort[A](kl: (A, A) => Boolean)(gl: (A, A) => Boolean)(l: List[A]): List[A] =
  l match
    case x :: xs =>
      val min = kleinstes(kl)(l)         // 1. kleinstes Element der GESAMTEN Liste
      val tmpL = streiche(gl)(min, l)    // 2. genau ein Vorkommen davon entfernen
      min :: minSort(kl)(gl)(tmpL)       // 3. Minimum vorne, Rest rekursiv sortieren
    case _ => List()
// x wird im case nicht benutzt — das Muster dient nur der Prüfung "Liste nicht leer".

//Max-Sort
def grosste[A](gr: (A, A) => Boolean)(l: List[A]): A =
  l match
    case x :: List() => x                // spiegelbildlich zu kleinstes()
    case x1 :: x2 :: xs => if (gr(x1, x2)) grosste(gr)(x1 :: xs) else grosste(gr)(x2 :: xs)
    case _ => throw new Exception("grosste: leere Liste hat kein Maximum")

def maxSort[A](gr: (A, A) => Boolean)(gl: (A, A) => Boolean)(l: List[A]): List[A] =
  l match
    case x :: xs =>
      val elem = grosste(gr)(l)          // größtes Element suchen ...
      val tmpL = streiche(gl)(elem, l)   // ... entfernen ...
      maxSort(gr)(gl)(tmpL) ::: elem :: List()   // ... und ans ENDE hängen
    case _ => List()

//Insertion-Sort
// Fügt w an der richtigen Stelle einer BEREITS SORTIERTEN Liste ein.
// kg = "kleiner gleich": entscheidet, ab wann w vor x gehört.
def einfuegen[A](kg: (A, A) => Boolean)(w: A, l: List[A]): List[A] =
  l match
    // Platz gefunden -> w davor; sonst x behalten und im Rest weitersuchen
    case x :: xs => if (kg(w, x)) w :: x :: xs else x :: einfuegen(kg)(w, xs)
    case _ => List(w)                    // ans Ende: w ist größer als alles bisher

def insertionsort[A](kg: (A, A) => Boolean)(l: List[A]): List[A] =
  l match
    // erst den REST sortieren, dann x dort einfügen
    // 'kg: (A, A) => Boolean' ist hier nur eine Typangabe (Ascription), kg genügt auch
    case x :: xs => einfuegen(kg: (A, A) => Boolean)(x, insertionsort(kg)(xs))
    case _ => List()

// Dieselbe Logik ohne eigene Rekursion: foldRight faltet die Liste von hinten
// auf und fügt dabei jedes Element in das bereits sortierte Zwischenergebnis ein.
def insertionsortFold[A](kg: (A, A) => Boolean)(l: List[A]): List[A] =
  l.foldRight(List[A]())((y, ys) => einfuegen(kg)(y, ys))
//              ^Startwert  ^y = Element, ys = bisher sortierter Rest

//Quick-Sort
def quicksort(l: List[Int]): List[Int] =
  l match
    case x :: xs =>
      val kl = l.filter(_ < x)           // Pivot = x (erstes Element); alles Kleinere
      val gl = l.filter(_ == x)          // das Pivot selbst und alle Duplikate davon
      val gr = l.filter(_ > x)           // alles Größere
      // beide Hälften rekursiv sortieren und mit dem Pivot in der Mitte zusammensetzen
      quicksort(kl) ::: gl ::: quicksort(gr)
    case _ => List()

//Merge-Sort
def mergesort(l: List[Int]): List[Int] =
  // merge ist eine INNERE Funktion: nur innerhalb von mergesort sichtbar
  def merge(l1: List[Int], l2: List[Int]): List[Int] =
    (l1, l2) match                       // Tupel-Muster: beide Listen gleichzeitig prüfen
      case (x :: xs, y :: ys) =>
        // reißverschlussartig: immer den kleineren Kopf übernehmen
        if (x < y) x :: merge(xs, l2) else y :: merge(l1, ys)
      case (x :: xs, List()) => l1       // rechts leer -> Rest links komplett übernehmen
      case (List(), y :: ys) => l2       // links leer  -> Rest rechts übernehmen
      case _ => List()                   // beide leer

  l match
    case List() => List()                // Rekursionsanker: leere Liste
    case x :: List() => l                // Rekursionsanker: ein Element ist sortiert
    case _ =>
      val mid = l.length / 2             // DIVIDE: in zwei Hälften teilen
      // CONQUER: beide Hälften rekursiv sortieren | MERGE: zusammenmischen
      merge(mergesort(l.take(mid)), mergesort(l.drop(mid)))

//Listenfunktionale
// map: wendet f auf jedes Element an. [A, B] -> der Ergebnistyp darf abweichen.
def mymap[A, B](f: A => B)(l: List[A]): List[B] =
  l match
    case x :: xs => f(x) :: mymap(f)(xs)   // Kopf umrechnen, Rest rekursiv
    case List() => List()
// dieselbe Funktion als for-Comprehension — 'yield' sammelt die Ergebnisse
def mymap2[A, B](f: A => B)(l: List[A]): List[B] =
  for x <- l yield f(x)

// filter: behält nur Elemente, für die f true liefert (Prädikat)
def myfilter[A](f: A => Boolean)(l: List[A]): List[A] =
  l match
    case x :: xs => if (f(x)) x :: myfilter(f)(xs) else myfilter(f)(xs)
    case List() => List()
// als for-Comprehension: das 'if' wirkt als Filter
def myfilter2[A](f: A => Boolean)(l: List[A]): List[A] =
  for x <- l if f(x) yield x

// fold von rechts: faltet die Liste mit Startwert s zu EINEM Wert zusammen
// Ablauf: op(x1, op(x2, ... op(xn, s)))
def myfoldR[A, B](s: B)(op: (A, B) => B)(l: List[A]): B =
  l match
    case List() => s                     // am Ende der Liste steht der Startwert
    case x :: xs => op(x, myfoldR(s)(op)(xs))


//Bäume
// enum mit Typparameter [A]: der Baum funktioniert für beliebige Elementtypen.
// Die Fälle tragen Parameter und verweisen auf Suchbaum[A] selbst -> rekursiver Typ.
enum Suchbaum[A] {
  case Knoten(wert: A, li: Suchbaum[A], re: Suchbaum[A])
  case Leer()                            // leerer Teilbaum = Abschluss eines Astes

  // Suchbaum-Eigenschaft: links stehen kleinere, rechts größere Werte.
  // Dadurch muss pro Ebene nur EIN Teilbaum betrachtet werden (Bisektion).
  def enthealt(kl: (A, A) => Boolean)(x: A): Boolean =
    this match                           // 'this' = der aktuelle Knoten
      case Knoten(w, li, re) =>
        if (kl(x, w)) li.enthealt(kl)(x)      // x < w -> links weitersuchen
        else if (kl(w, x)) re.enthealt(kl)(x) // x > w -> rechts weitersuchen
        else true                             // weder kleiner noch größer -> gefunden
      case Leer() => false                    // Ast zu Ende -> nicht enthalten

  // Einfügen erzeugt einen NEUEN Baum: unveränderte Teilbäume werden mitbenutzt,
  // nur der Pfad bis zur Einfügestelle wird neu aufgebaut.
  def ein(kl: (A, A) => Boolean)(x: A): Suchbaum[A] =
    this match
      case Leer() => Knoten(x, Leer(), Leer())    // Einfügestelle erreicht
      case Knoten(w, li, re) =>
        if (kl(x, w)) Knoten(w, li.ein(kl)(x), re)     // neuer linker Teilbaum
        else if (kl(w, x)) Knoten(w, li, re.ein(kl)(x)) // neuer rechter Teilbaum
        else Knoten(w, li, re)                          // schon vorhanden -> unverändert

  // Ganze Liste einfügen: foldRight faltet die Liste über den aktuellen Baum
  def einL(kl: (A, A) => Boolean)(l: List[A]): Suchbaum[A] =
    l.foldRight(this)((x, b) => (b.ein(kl)(x)))
  //          ^Startwert = dieser Baum   ^x = Element, b = Baum aus dem letzten Schritt
  //einL(List(1, 2, 3), b) == ein(1, ein(2, ein(3, b)))

  def loeschen(kl: (A, A) => Boolean)(x: A): Suchbaum[A] =
    this match
      case Leer() => Leer()              // nicht vorhanden -> nichts zu tun
      case Knoten(w, li, re) =>
        if (kl(x, w)) Knoten(w, li.loeschen(kl)(x), re)   // links weitersuchen
        else if (kl(w, x)) Knoten(w, li, re.loeschen(kl)(x))
        else // w == x  -> dieser Knoten muss verschwinden
          if (li == Leer()) re           // höchstens ein Kind -> Kind hochziehen
          else if (re == Leer()) li
          // Zwei Kinder: Ersatz muss die Ordnung wahren. Der größere Teilbaum
          // liefert ihn, damit der Baum nicht einseitig wird.
          else if (li.size() > re.size()) Knoten(li.last(), li.init(), re) //größter vom linken
          else Knoten(re.head(), li, re.tail()) //kleinster von rechten

  // kleinster Wert = ganz links unten
  def head(): A =
    this match
      case Knoten(w, Leer(), _) => w     // kein linkes Kind mehr -> Minimum gefunden
      case Knoten(_, li, _) => li.head() // sonst weiter nach links
      case Leer() => throw new Exception("head: leerer Baum")

  // Baum ohne seinen kleinsten Wert
  def tail(): Suchbaum[A] =
    this match
      case Knoten(_, Leer(), re) => re              // Minimum entfernen, rechtes Kind ersetzt es
      case Knoten(w, li, re) => Knoten(w, li.tail(), re)   // Pfad neu aufbauen
      case Leer() => Leer()

  // größter Wert = ganz rechts unten
  def last(): A =
    this match
      case Knoten(w, _, Leer()) => w
      case Knoten(_, _, re) => re.last()
      case Leer() => throw new Exception("last: leerer Baum")

  // Baum ohne seinen größten Wert
  def init(): Suchbaum[A] =
    this match
      case Knoten(_, li, Leer()) => li
      case Knoten(w, li, re) => Knoten(w, li, re.init())
      case Leer() => Leer()

  // Knotenanzahl: eigener Knoten + beide Teilbäume
  def size(): Int =
    this match
      case Leer() => 0
      case Knoten(w, li, re) => 1 + li.size() + re.size()

  // In-Order-Traversierung als String: links, Wert, rechts
  // -> gibt die Werte in aufsteigender Reihenfolge aus; '.' markiert leere Äste
  def trav(): String =
    this match
      case Leer() => "."
      case Knoten(w, li, re) => "( " + li.trav() + " " + w.toString + " " + re.trav() + " )"
}
import Suchbaum.*                        // Knoten/Leer ohne Präfix "Suchbaum." nutzbar


// Einfacher Baum ohne Ordnung und ohne Methoden — nur die Struktur
enum Baum {
  case LeerB()
  case KnotenB(x: Char, li: Baum, re: Baum)
}
import Baum.*

// Pre-Order: erst der eigene Wert, dann linker, dann rechter Teilbaum
def treeToList(b: Baum): List[Char] =
  b match
    case LeerB() => List()
    case KnotenB(x, li, re) => x :: treeToList(li) ::: treeToList(re)


// --- Vergleichsfunktionen -----------------------------------------------------
val vklint = ((x: Int, y: Int) => x < y)          // anonyme Funktion in einem val
def klInt(x: Int, y: Int): Boolean = x < y        // dasselbe als Methode
def glInt(x: Int, y: Int): Boolean = x == y       // Gleichheit (für streiche/enthalten)
println(vklint(4,4))                              // false — 4 < 4 gilt nicht

// --- Tests --------------------------------------------------------------------
val l1 = List(4, 1, 5, 7, 1, 31, 4)
val l2 = List(6, 9, 2, 1, 31)

println(s"l1: $l1 \n l2: $l2")                    // s-Interpolator: $name setzt den Wert ein

enthalten(glInt)(31, l2)                          // true
reverse(l2)                                       // List(31, 1, 2, 9, 6)
kleinstes(klInt)(l2)                              // 1
streiche(glInt)(1, l1)                            // entfernt nur die ERSTE 1
mergesort(l1)                                     // List(1, 1, 4, 4, 5, 7, 31)

mymap((n: Int) => n + 5)(l1)                      // jedes Element + 5
myfilter((n: Int) => n % 2 == 0)(l2)              // nur gerade Zahlen -> List(6, 2)

var b:Suchbaum[Int] = Leer()                      // var, weil b bei jedem Schritt neu entsteht
b = b.einL((x: Int, y: Int) => x < y)(List(3, 3, 53, 2, 5, 54, 32, 12, 4, 9))
b.trav()                                          // In-Order -> aufsteigend sortiert
b = b.loeschen(vklint)(32)                        // 32 entfernen
print(s"${b.trav()} |32: " + b.enthealt(vklint)(32))   // ... |32: false
println(for x <- (0 to 6) if x % 2 == 0 yield x)  // Vector(0, 2, 4, 6)

// Baum:        A
//           B     E
//         C   D     F
val abc: Baum = KnotenB('A', KnotenB('B', KnotenB('C', LeerB(), LeerB()), KnotenB('D', LeerB(), LeerB())), KnotenB('E', LeerB(), KnotenB('F', LeerB(), LeerB())))
treeToList(abc)                                   // List(A, B, C, D, E, F) — Pre-Order
