# football_simulator

Football simulator challenge.

**Architecture**
- MVVM following [Android architecture](https://developer.android.com/topic/architecture)

**Application flow**
1. Prepopulate database with teams
2. Prepopulate database with players (from json file inside the application)
2. Prepopulate database with generated fixtures, based on the teams (double round robin with home and away fixture)

**Generate results**
To generate the result for each fixture:
1. Attack strength -> Number of goals scored by homeTeam last season divided by the number of average number of scored goals (in the last season for all teams)
2. Defence strength -> Number of goals conceded by awayTeam last season divided by the number of average number of conceded goals (in the last season for all teams)
3. Repeat (1) and (2) but reverting the teams in each step
4. Calculate the Probability mass function for each of the occurrences (number of goals) based on the attack and defense strenghts
5. Depending on the probability of each, attribute a weight and randomize the number of goals for each team

For now you can try the WIP with the apk located in [Football Sim APK](sampleapk/app-debug.apk)

**Screenshots**

<p align="center">
<img src="/appscreenshot/fixtures.png" width="300" height="500"/>
<img src="/appscreenshot/team_standings.png" width="300" height="500"/>
</p>

**Limitations**
- Due to timeconstraints the UI still needs a bit work (namely the teamstandings and the onboarding screen)
- The mutual results were not taken into account for the teamStandings
- The ratings for each team could be improved and taken into account for the results of each fixture