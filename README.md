# nagyhf3
A BME VIK mérnökinformatikus szak 3. félévének programozás 3 tárgyhoz tartozó nagyházi feladatának a megoldása

A program egy a weben elérhető a League of Legends nevű játékot stratégia szempontból elemző weboldal alapján készült ([link](https://map.riftkit.net/))

A lényeg, hogy stratégiákat lehessen kidolgozni egyes pozíciókban a játékban, és ehhez nyújt többletfunkciókat a program.

Elsősorban ilyen a továbbfejlesztett hősválasztási szakasz, ahol további adatok állnak rendelkezésre hősök választására, akár a jelenlegi patch notes-ok.

## Fordítás

A következő paranccsal lehet a jar fájlt létrehozni.

```
mvn package
```

### uml generálása
 
A következő paranccsal egy uml diagram kép készül a target/generated-docs mappába.

```
java -jar ~/Downloads/plantuml-1.2024.8.jar -DPLANTUML_LIMIT_SIZE=8192 ./target/generated-docs/testdiagram1.txt
```