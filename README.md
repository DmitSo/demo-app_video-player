# Brief info about the project

This is one of my demo tasks before applying on one of the projects. 
Performed a slight code-style fixes and removed everything that could lead to deanonimising project/company that gave the task.

# Task description

Task was to make an app with **PageView** that contains **4 pages**. Every page contains **video player** with play/pause functionality. 
List of files are available in JSON format by given web-address. 
**Required to ignore certificate issue.**

Required to handle following errors: 
- Unavailable server (can occur if tried to take file from "split_v" field at the response)
- Non-present file (can occur if tried to take file from "src" field at the response)
If error, display info at the corresponding page

# Self-roast
The original has made 1.5 years ago so I can see some non-optimal solutions from the current perspective.
You can see the places if you'll search comments containing "Self-roast" string ([feature/DAVP_self-roast-comments](https://github.com/DmitSo/demo-app_video-player/tree/feature/DAVP_self-roast-comments))

# Used tech-stack
* **Language:** Kotlin
* **Views:** XML + DataBinding
* **App structure:** MVVM, ViewModel + LiveData
* **Network:** RxJava 3 && OkHttp && Retrofit
* **DI:** Hilt
* **Player:** ExoPlayer (right now the classes are used became deprecated but wasn't at the time of creating the app)
* **Logging:** Timber
