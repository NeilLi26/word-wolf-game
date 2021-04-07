# Word Wolf Game Project

## Neil Li

**What does the program do:**

This program is meant as a digital version of the party game "Word Wolf", also known as
"Who is Undercover". In this game everyone is assigned a word out of two similar words, and
the "undercover" or "wolf" is the only one who has their word. Players will take turns
describing their word, attempting to find out who is the "undercover". While the "Undercover"
tries to find out what the word is. However, no one knows if they are the undercover or the regulars. 
There are also versions that include a role called "Mr. White", where they are not assigned any word,
their win condition is to guess both words before they or the undercover are voted out. This is not meant
as a complete recreation of the word wolf game, but rather a console that makes it simpler to coordinate a
game of word wolf with friends.

**Who will use it:**

- I definetly plan on having lots of fun with my friends using the final version
- Other people who are unable to meet up with friends due to the current pandemic
- People who enjoy these kind of mafia esque party games

**Why this is a project of interest to me**

Over the Christmas break, my friends and I wanted to have a remote celebration over the new year, as we
were quite grateful for surviving this year. We played many party games such as spyfall, sriblle.io, and
many others. However, the only version of "Word Wolf" we found online was very dissapointing, with many bugs
such as how all players needed to be voted out before the final screen, and many other quality of life features
were also lacking. This is why I want to make an improved version of this game.

##User Stories:

- As a user, I would like a game to start only if there are at least 3 players
- As a user, I would like to add my own custom word pairs to the default words
- As a user, I would like to add players to the game and customize their names
- As a user, I would like to vote out players who have been deemed suspicious by the other players
- As a user, I would like to have predetermined numbers of players assigned as the "wolf" or "regulars"
- As a user, I would like a phase at the beginning where individual players could see their own word
- As a user, I would like to save the current players and words to a file
- As a user, I would like to load up the players and words saved
- As a user, I would like to be able to load from a series of pre made word lists

##Phase 4: Task 2
- As a user, I would like to have a game that would not start if there are players who do not have roles
assigned yet or if there are not enough players

##Phase 4: Task 3
- Refactor the code so that VotingPanel would not have an association with WordWolfGame, done by replacing the calls to
WordWolfGame with calls to StartGamePanel.getWordWolfGame();
- Refactor the code so that WordWolfGUI would not have an association with JsonReader, by having a method in the
SaveAndLoadMenu that is meant to load the default words, and calling it from WordWolfGUI when loading default words
- Refactor the code so that SaveAndLoadMenu would not have an association with PlayersAndWordPairs, this could be done
by having the changing of the PlayersAndWordPairs be handled by WordWolfGUI.
- Refactor the code so that MenuPanel would not have an association with PlayersAndWordPairs, this would also be done by
having the changing of the PlayersAndWordPairs be handled by WordWolfGUI.