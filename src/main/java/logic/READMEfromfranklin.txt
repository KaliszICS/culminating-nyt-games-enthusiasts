a few notes on my logic classes:

wordle and spelling bee both take in ANY number of letters 4 and up. this might require more graphics though.

the classes for each game do NOT interact with each other. the connections board, at most, will return an unrevealed tile; it is the main class's job to handle it and create a wordle instance.
the reason this is is because i'm not sure how it would work if connections opened an instance of wordle. i'm pretty sure it wouldn't render the graphics, i.e., main MUST render all of the logic backend itself.

both wordle and spelling bee have a win boolean (for spelling bee, it toggles once the player guesses the keyword). connections does NOT have a win boolean; you must check the return value of submitGuess and check categoriesCompleted.
if this is too inconvenient let me know and i'll code in a win boolean instead.

i am not sure if leaderboard works at all to be honest since i'm not very confident in my file i/o. if it does work though, it uses .txt files within the same folder for data.
passwords.txt stores every username-password pair, line by line, space-separated.
all other .txt files should be named after each username (e.g., fz.txt) and contain all the stats in order of wordleStats, connectionsStats, wordleAttempts, connectionsAttempts, spellingBeeStats.
(i put spellingBeeStats at the end since it can vary in size and it was easier to scan that way)

adplayer just reads from the .txt file mostly. i don't know how to code the gui to embed the video or make it redirect on click (or a skip ad button).

do i catch exception or specific exceptions based on the methods used in the try block? does it matter or can i just catch exceptions for all of them?
i'm using instance variables for scanner/printwriter and calling on this.sc for the methods - does this work?

does it matter if we assign default values inside a constructor? can't we just do it outside the constructor?? does it matter at all actually