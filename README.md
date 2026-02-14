# Pokemon Ga-Ole

A console-based Java game inspired by the Pokemon Ga-Ole arcade experience. Catch wild Pokémon, battle with your team, use different Poke Balls and Discs, and climb the leaderboard.

## Features

- **Starter selection** — Choose Charmander (Fire), Squirtle (Water), or Bulbasaur (Grass) to begin.
- **Battle & Catch mode** — Encounter wild Pokémon (including a chance for legendaries), try to catch them, then enter battles once you have at least 2 Pokémon.
- **Poke Balls** — Poke Ball, Great Ball, Ultra Ball, and Master Ball with different catch rates and limited quantities.
- **Type system** — Fire, Water, and Grass with type effectiveness affecting damage.
- **Double Rush** — Catch legendaries to earn Double Rush Discs and unleash powerful signature moves in battle (e.g. Origin Pulse, Fusion Flare).
- **Leaderboard** — Top 5 scores are saved to `scores.txt` and displayed in-game.

## Requirements

- **Java 21** (or compatible JRE)
- No external libraries; standard Java only

## How to Run

### From Eclipse

1. Open the project in Eclipse (File → Open Projects from File System, select the project folder).
2. Right-click the project → **Run As** → **Java Application**.
3. Select **Main** as the main class if prompted.

### From command line

```bash
# Compile
javac -d bin src/*.java

# Run
java -cp bin Main
```

On Windows (PowerShell), from the project root:

```powershell
javac -d bin src\*.java
java -cp bin Main
```

## Gameplay

1. **Start** — Enter your name and pick a starter Pokémon.
2. **Menu**
   - **1. Play Battle & Catch** — See 3 wild Pokémon (levels 3–10, 20% chance one is legendary). Catch at least one so you have 2+ Pokémon, then fight wild Pokémon with your selected team. After the battle you can try to catch any defeated wild Pokémon.
   - **2. View Pokemon Collection** — See your Pokémon, score, and Poke Balls.
   - **3. View Top Scores** — See the top 5 scores (loaded from `scores.txt`).
   - **4. Exit** — Quit the game.

3. **Catching** — Choose a Poke Ball; success depends on the ball’s catch rate and the Pokémon’s catch rate (legendaries are harder).
4. **Battles** — Pick moves each turn. If you have a Double Rush Disc for your active Pokémon, you can choose to use Double Rush for a powerful attack (once per battle).

## Project Structure

```
PokemonGaOle1/
├── src/
│   ├── Main.java           # Entry point
│   ├── Game.java           # Game flow, menu, catch & battle phases
│   ├── Player.java         # Player state, Pokémon, Balls, Discs
│   ├── Battle.java         # Battle logic, turns, Double Rush
│   ├── Pokemon.java        # Base Pokémon (abstract)
│   ├── FirePokemon.java
│   ├── WaterPokemon.java
│   ├── GrassPokemon.java
│   ├── PokemonFactory.java # Creates Pokémon by name/type or random
│   ├── Move.java / MoveType.java
│   ├── DoubleRushMove.java # Legendary signature moves
│   ├── PokeBall.java       # Base ball
│   ├── GreatBall.java / UltraBall.java / MasterBall.java
│   ├── Disc.java / RegularDisc.java / DoubleRushDisc.java
│   ├── ScoreManager.java   # Load/save top 5 scores
│   ├── ScoreEntry.java
│   └── BattleResult.java
├── bin/                    # Compiled classes (after build)
├── scores.txt              # Persisted leaderboard (created at runtime)
├── .project
├── .classpath
└── README.md
```

## Legendary Pokémon

Catching a legendary grants a **Double Rush Disc** with a signature move, for example:

| Pokémon  | Move         |
|----------|--------------|
| Kyogre   | Origin Pulse |
| Moltres  | Incinerate   |
| Celebi   | Magical Leaf |
| Reshiram | Fusion Flare |
| Entei    | Eruption     |
| Suicune  | Hydro Pump   |
| Keldeo   | Aqua Tail    |
| Virizion | Giga Drain   |
| Shaymin  | Seed Flare   |


