# football_simulator

Football simulator challenge.

**Application flow**
1. Prepopulate database with teams
2. Prepopulate dataase with players (from json file inside the application)
2. Prepopulate database with generated fixtures, based on the teams (double round robin with home and away fixture)

**Generate results**
To generate the result for each fixture:
1. Attack strenght -> Number of goals scored by homeTeam last season divided by the number of average number of scored goals (in the last season for all teams)
2. Defence strenght -> Number of goals conceded by awayTeam last season divided by the number of average number of conceded goals (in the last season for all teams)
3. Repeat (1) and (2) but reverting the teams in each step
4. Calculate the Probability mass function for each of the ocurrences (number of goals) based on the attack and defense strenghts
5. Depending on the probability of each, attribute a weight and randomize the number of goals for each team

For now you can try the WIP wiht the apk located in [Football Sim APK](sampleapk/app-debug.apk)
