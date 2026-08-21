# Scala

![Scala](https://img.shields.io/badge/Scala-3-red)
![Typ](https://img.shields.io/badge/Inhalt-Skripte_&_Doku-lightgrey)

Zusammenfassungen und Übungen zu den Informatik-Grundlagen in Scala 3:
funktionale Listenverarbeitung mit Pattern Matching, imperative Array-Algorithmen
und die klassischen Zerlegungsmuster (Divide and Conquer, Greedy, Backtracking,
Dynamische Programmierung).

---

## Inhalt

- [Repository-Struktur](#repository-struktur)
- [Zusammenfassung Inf 1](#zusammenfassung-inf-1)
- [Zusammenfassung Inf 2](#zusammenfassung-inf-2)
- [Scala CheatSheet](#scala-cheatsheet)
- [Voraussetzungen](#voraussetzungen)
- [Ausführen](#ausführen)

---

## Repository-Struktur

```
.
├── Zusammenfassung_Inf1.sc   Listen, Rekursion, Pattern Matching, Bäume
├── Zusammenfassung_Inf2.sc   Arrays, Zerlegungsmuster, Pointer, verkettete Strukturen
├── scala_cheatsheet.md       Nachschlagewerk zu allen verwendeten Konstrukten
└── README.md
```

Beide Dateien sind `.sc`-Skripte: sie laufen von oben nach unten durch, brauchen
keinen `@main`-Einstiegspunkt und lassen sich im Worksheet zeilenweise auswerten.
Zur Verwendung als kompiliertes Programm (`object`, `@main`) siehe Kapitel 1 des
CheatSheets.

---

## Zusammenfassung Inf 1

Funktionaler Teil — alles über unveränderliche Listen, Rekursion und Pattern
Matching, ohne eine einzige Schleife.

| Bereich | Inhalt |
|---|---|
| Listen | `enthalten` (lineare Suche), `reverse`, `streiche` |
| Sortieren | `minSort` / `maxSort` (Selection), `insertionsort` und dieselbe Logik als `insertionsortFold`, `quicksort`, `mergesort` |
| Listenfunktionale | `mymap`, `myfilter`, `myfoldR` — jeweils rekursiv, dazu die Varianten als for-Comprehension |
| Bäume | `enum Suchbaum[A]` mit `ein`, `einL`, `enthealt`, `loeschen`, `head`/`tail`/`last`/`init`, `size`, `trav`; dazu `enum Baum` mit `treeToList` |

Roter Faden: Vergleichsfunktionen werden als Parameter übergeben
(`(A, A) => Boolean`) und über mehrere Parameterlisten gecurryt — dadurch
funktioniert derselbe Algorithmus für jeden Typ und jede Ordnung.

---

## Zusammenfassung Inf 2

Imperativer Teil — Arrays mit Index und Schleifen, dazu die komplexen
Zerlegungsmuster am Rucksackproblem.

| Bereich | Inhalt |
|---|---|
| Arrays | Erzeugung (`ofDim`, `fill`, `range`, `tabulate`, 2D), Zugriff, `mkString`, Standardmethoden |
| Zerlegungsmuster | lineare Verarbeitung, Bisektion, Divide and Conquer |
| Sortieren | `selectionsortArray`, `insertionsortArray`, `quicksortArray`, `mergesortArray` (+ generische Quicksort-Variante mit `ClassTag`) |
| Rucksackproblem | `kanpSackGreedy`, `knapSackBackTrStart`, `knapSackDP` — dieselbe Aufgabe mit drei Strategien |
| Pointer | `class Elem` (einfach verkettete Liste), `class Queue[T]` (generische Warteschlange) |

Die drei Rucksack-Lösungen nebeneinander zeigen den Kern des Kapitels:

| Verfahren | Findet | Laufzeit |
|---|---|---|
| Greedy | eine Lösung, nicht zwingend die beste | meist O(n log n) |
| Backtracking | alle Lösungen | exponentiell |
| Dynamische Programmierung | die optimale Lösung | O(n · Kapazität) |

---

## Scala CheatSheet

[`scala_cheatsheet.md`](scala_cheatsheet.md) erklärt in 12 Kapiteln alles, was in
den beiden Skripten vorkommt — von `val`/`var` über Currying, Pattern Matching und
`enum` bis zu den Zerlegungsmustern. Kapitel 1 behandelt zusätzlich die
Verwendung außerhalb von Skripten: `object`, `@main`, Companion Objects und die
Namenskonventionen. Jeder Codeblock ist zeilenweise kommentiert, zwei
Verzeichnisse führen direkt zur gesuchten Stelle.

---

## Voraussetzungen

- **Scala 3** (JDK 17 oder neuer im Hintergrund)
- Empfohlen: [`scala-cli`](https://scala-cli.virtuslab.org/) — führt Skripte direkt
  aus und bringt eine REPL mit
- Für die zeilenweise Auswertung im Worksheet: IntelliJ IDEA mit Scala-Plugin oder
  VS Code mit Metals

```bash
scala -version
```

---

## Ausführen

```bash
scala Zusammenfassung_Inf1.sc          # Skript direkt ausführen
scala-cli run Zusammenfassung_Inf2.sc  # alternativ über scala-cli
scala-cli repl                         # interaktiv ausprobieren
```

In IntelliJ lassen sich `.sc`-Dateien als Worksheet öffnen; das Ergebnis jedes
Ausdrucks erscheint dann rechts neben der Zeile — praktisch, weil beide Dateien am
Ende Testaufrufe ohne `println` enthalten.
