## Architecture

- MVVM following [Android architecture](https://developer.android.com/topic/architecture)

## Tech stack - Library

- [Kotlin](https://kotlinlang.org/) , [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) , [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Navigation](https://developer.android.com/guide/navigation/)
- [Moshi](https://github.com/square/moshi)
- [Ktlint gradle](https://github.com/JLLeitschuh/ktlint-gradle)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)

## Application flow

1. Prepopulate database with teams
2. Prepopulate database with players (from json file inside the application)
2. Prepopulate database with generated fixtures, based on the teams (double round robin with home
   and away fixture)

## Generate results
To generate the result for each fixture:

1. Attack strength -> Number of goals scored by homeTeam last season divided by the number of
   average number of scored goals (in the last season for all teams)
2. Defence strength -> Number of goals conceded by awayTeam last season divided by the number of
   average number of conceded goals (in the last season for all teams)
3. Repeat (1) and (2) but reverting the teams in each step
4. Calculate the Probability mass function for each of the occurrences (number of goals) based on
   the attack and defense strenghts
5. Depending on the probability of each, attribute a weight and randomize the number of goals for
   each team

You can try the APK in [Football Sim APK](sampleapk/app-debug.apk)

## Screenshots

<p align="center">
<img src="/appscreenshot/onboarding.png" width="300" height="500"/>
<img src="/appscreenshot/fixtures.png" width="300" height="500"/>
<img src="/appscreenshot/team_standings.png" width="300" height="500"/>
</p>

## Limitations

- The mutual results were not taken into account for the teamStandings
- The ratings of each team could be improved and taken into account for the results of each
  fixture (eg. the rating of each team player could add a random multiplier used in the results)
- Can't access past results (once you generate a new set of fixtures, the previous ones are deleted)
  . Initially I had a competitions and competition_phase table to escalate into several
  seasons/competitions but ended going with the easier and faster approach
- Application was fixed to Portrait (as I didn't have the time to handle configuration changes)