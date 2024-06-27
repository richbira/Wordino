# Wordino App

**Group Name** MangoLoco 2.0

## Group Member

- **Breznay Yoshiki** - 
- **Rabi Richard** -

For our Mobile Programming Course at Bicocca-University we have built Wordino.

Wordino is an engaging Android word game that challenges players to guess a five-letter word within six attempts each day. The gameplay mechanics are inspired by the popular game Wordle.

How to Play
Players enter a five-letter word and submit their guess. The game then provides visual feedback for each letter:

Green: The letter is in the correct position.
Yellow: The letter is correct but in the wrong position.
Gray: The letter is not in the word at all.
The game ends prematurely if the word is guessed correctly or continues until all six attempts are used.

Key Technologies Used
Wordino is built using the following technologies:

Android Studio: for the entire application development.
Firebase: for user authentication and storing daily words and user statistics.
Room: to manage local databases that store the top scores in training mode.
Retrofit: for API management.
Google Play Services: to facilitate Google account sign-in.
Application Architecture
Structure
The application is organized into layers that separate the user interface (UI) from the data management:

Activities and Fragments handle UI interactions and transitions between screens.
ViewModels serve as a bridge between the UI components and the data layers, using LiveData to notify the UI of data changes.
Repositories abstract the data sources and provide a high-level interface for accessing data from databases and external APIs.
UI Elements
BottomNavigationView to navigate between the main sections of the app.
CardView for settings segmentation and highlight functions.
TextInputEditText for input fields such as email and password.
Buttons handle interactions like login, registration, and gameplay.
API Integration
Two APIs are integrated to ensure the functionality around word handling:

A random-word API fetches a random five-letter word from a database.
A dictionary API checks if the randomly fetched word exists in its database to ensure it's guessable.
Activities & Fragments
Welcome Activity: The initial screen where users can choose to play as registered users or guests.
Game Activity: Displays the main game interface with a custom QWERTY keyboard and a 5x6 grid for input feedback.
Login and Registration Fragments: Handle user authentication and new user registration.
Statistics and Settings Fragments: Provide access to game statistics, settings adjustments, and instructional guides on how to play the game.
Future Developments
Future enhancements may include premium features like additional attempts after a loss, multilingual support, and a dictionary with definitions of daily words.

Libraries
MPAndroidChart: Used for displaying statistical data through horizontal bar charts.
