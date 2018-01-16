/*

Super Breed
by Michael Blackwell

Version 0.0.9
8/6/2015
-Chanded card class into an abstract class
-Added ranges for Personnel Fire Power

Version 0.0.8
8/5/2015

-Added an end of turn log.
-Added a draw card pile.
-Added additional rules for action cards.
-Added requirements for card play.
 +For example, a fire card requires a certain amount of FP from personnel in a lane before it can be used.
-Added phases for choosing lanes after playing action cards.
-Added card backings to hide players' hands.
-Added a map with Relative Range counters.
 +This is a visual aid for players. It displays the positions of personnel in each lane.
 +Blue for player 1, red for player 2.
-Changed all cards to the same color



Version 0.0.7
8/2/2015

-Added Panicked (yellow outline) and Routed (purple outline) states
-Added a fire roll
 +When a fire card is played, a 2d6 die is rolled and modified based on any terrain, firepower, and if the defender is panicked or routed.
 +4 results: no effect, routed, panicked, and killed in action
-Added Rally cards
 +When played, all personnel in the chosen lane are no longer routed or panicked
-Added an image background to each lane
-Reorganized the layout of cards and lane information
-Added descriptions on the actual cards 

Version 0.0.6
8/1/2015

-Added Rules class.
-Added Turns.
-Added Phases
-Deleted Hand and Team classes. Moved to Table and Lane classes.
-Added Firepower. (FP)
-Added Relative Range. (RR)
-Added Terrain Effect Modifier. (TEM)
-Added some functionality for some of the action cards. (Move and Fire so far)
-Added Setup and Play Phases.
-Added Choose Card and Execute Card Phases

Version 0.0.5
9/18/2014

Added
- 10 Jihad cards for player 2
- player 2 is now fuctional
- player 2's cards are offset from player 1's in the lane

To-do:
- starting the long task of implementing the rules

Version 0.0.4
9/17/2014

Added
- created methods to access attributes
- moved attributes, methods, and object identifiers to their appropriate classes
- cards now shift left when a card is played
- cards are shuffled before delt
- cards are sorted in player's hand and in each lane
- created a deck and hand for a second player
- a card is delt after a card is played

To-do:
- select player two's cards
- move player two's cards to their spot in the lane when selected and right clicked on a lane
- make 10 Jihadist Cards
- start making rules


Version 0.0.3
9/17/2014

Added/Removed:
-Cards are now placed in lanes in appropriate positions
-Added 10 basic cards to use
-Added a discard pile. works the same as a lane but the cards in the discard pile do not get rendered or updated

To Do:
-sort cards based on type in players hand and on board
-shuffle cards and draw only 7 to test this
-shift cards in players hand after a card is played
-*create and use methods for all that are missing (getSize().. etc)

Version 0.0.2
9/17/2014

Added/Removed:
-added containers for lanes
-Fixed spacing and remove cards from lanes
-Reorganized card attibutes
-removed dragging cards feature. changed it to: left click to select. right clight to place

To Do:
-need to reset vertical card positions in lanes during update
-create and use methods for all that are missing (getSize().. etc)


Version 0.0.1
9/16/2014

Added:
-Main menu (where i started this mess)
-Play screen (Table where the game will be played)
-Buttons (play game, exit game)
-Dragable and selectable cards (One at a time; yellow border when selected)
-Green lanes to place cards in (1 team per side per lane)
-Player one's hand
-Draw cards and place in player one's hand

To do: (numbered for priority)
-(1)Create containers for hands, lanes, and discard pile (so that the cards "stick" together) **completed lane container**
-(1)Create a basic deck to play test with (maybe 10 different cards)
-(2)Create second player
-(2)Create buttons on play screen
-(3)Start implementing rules
-(4)Add more menu features
-(4)Add Log
-(4)Add sound


 */

