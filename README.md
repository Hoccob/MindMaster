# MindMaster

Ad based, tasuta Single ja Multiplayer võimalustega app G Play storest. eesmärk 1000 allalaadimist.	!
													!				
--------------------------------------------------------------------------------------------------------!


Interface : Main screen (Single player, practice, high score) + game screen, High Score screen nuppudele t2hendused
Prepare_level
{
Tehete pool: 16 pooli, 4 levelit, poolide kalkulaator (2x array of arrays), tehte valija (kirjutame array iga kord yle, viimaseks uus tehe)
}
{
Game loop:

if time > Time: 0,
{ 
	Tehe printida screenile, kontrollida kas vastus oige, vastavad tegevused
	}
else end game
}

High score tabel telefoni m2llu. 

Classes:

Activity
Main_view
Singleplr_view
	Timer
high_score_view

Gameplay
	
ButtonZ
Tehte_vaade
Vastuse_vaade
Pooli_arvutus



Edasi:

Leveleid juurde, kohendada leveleid. 

Lisada practice mode * 5;


V6rku yhendamine, server taha jooksma (andmebaas);

Multiplayer
	1 : 1
	Tournament
	Game room

Disain

Alpha run - kogume andmeid, standardiseerime skoorimist jne;

Adid juurde
