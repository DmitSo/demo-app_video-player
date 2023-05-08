# Brief info about the project

This is one of my demo tasks before applying on one of the projects. It was pushed to this repository with three iterations. I've been interested if this approach might be interesting for those who will review this repository or not. 

# Task description

Task was to make an app with **PageView** that contains **4 pages**. Every page contains **video player** with play/pause functionality. 
List of files are available in JSON format by given web-address. 
**Required to ignore certificate issue.**

Required to handle following errors: 
- Unavailable server (can occur if tried to take file from "split_v" field at the response)
- Non-present file (can occur if tried to take file from "src" field at the response)
If error, display info at the corresponding page

# First iteration (tag v.0.1.0): As it was
In the first approach, I have performed a slight code-style fixes and removed everything that could lead to deanonimising project/company that gave the task. So almost everything are equal to the code I have been writing 1.5 years ago. 
Perform checkout of tag [v.0.1.0](https://github.com/DmitSo/demo-app_video-player/releases/tag/v.0.1.0) to see this version.

# Second iteration (tag v.0.1.1): Self-roast
In the second approach, I have reviewed my own code and added some coments regarding quality of code and its optimization.
The original has made 1.5 years ago so I can see some non-optimal solutions from the current perspective.
You can see the places if you'll search comments containing "Self-roast" string when you will check-out tag [v.0.1.1](https://github.com/DmitSo/demo-app_video-player/releases/tag/v.0.1.1)

# Third iteration (tag v.0.2.0): As it should be
In the third approach, I have changed given codebase based on my own comments from the second iteration. 
It has improved UX in following aspects: Player's resources consumption, Remembering state of the player (playback position, list position), Removing bug related to player which was playing in the background, Adding error-handling, Changed UI to be user-friendly (swipe-refresh, error showing)
It has improved code in following aspects: Removed redundant code, Player's right lifecycle handling, Moving URL to build.gradle, Moving to ViewBinding, Adding stateful screen in general, Leaving requests functioning on Repository's behalf, Improving code style 

# Used tech-stack
* **Language:** Kotlin
* **Views:** XML + DataBinding
* **App structure:** MVVM, ViewModel + LiveData
* **Network:** RxJava 3 && OkHttp && Retrofit
* **DI:** Hilt
* **Player:** ExoPlayer (right now the classes are used became deprecated but wasn't at the time of creating the app)
* **Logging:** Timber
