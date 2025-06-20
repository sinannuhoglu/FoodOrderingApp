# FoodOrderingApp
![Status](https://img.shields.io/badge/status-in--development-yellow)

An Android application built with Kotlin that allows users to explore a variety of meals, view details, manage favorites and cart, then place orders easily. 

---

## Screenshots

![Image](https://github.com/user-attachments/assets/5dd11389-1fb2-45aa-be78-f9c7b66740de)
![Image](https://github.com/user-attachments/assets/f594e1ee-8558-42ff-b495-70bc2d8d4a49)
![Image](https://github.com/user-attachments/assets/8679574a-1980-42fa-8daa-738961360458)
![Image](https://github.com/user-attachments/assets/e1e757d9-bebf-4b5a-968f-c9433034c1aa)
![Image](https://github.com/user-attachments/assets/e707a12a-360b-40cb-b310-58801f96b6d9)

---

## Project Structure

```
com.sinannuhoglu.foodorderingapp/
├── model/                 # Data models
├── network/               # Retrofit API client and service
├── ui/                    # Fragments and ViewModels
│   ├── basket/            # Basket screen and ViewModel
│   ├── detail/            # Food detail screen
│   ├── favorite/          # Favorites screen
│   ├── home/              # Home screen
│   └── splash/            # Intro animation
├── util/                  # Utility classes (Glide, Constants, etc.)
└── MainActivity.kt        # Navigation host and BottomNavigationView
```

---

## Features

- Displaying a list of food items using RecyclerView and GridLayout
- Search functionality to filter food items
- Add and remove items from favorites
- Add and remove items from the shopping cart
- Ability to complete and place orders
- REST API communication with Retrofit
- MVVM architecture with ViewModel and LiveData
- Enhanced UI using Snackbar, Toast, Lottie animations and Glide for image loading

---

## Technologies Used

| Technology                    | Purpose                                                              |
|------------------------------|----------------------------------------------------------------------|
| **Kotlin**                   | Programming language for Android app development                     |
| **Android Jetpack Navigation** | Handling fragment and screen navigation                             |
| **View Binding**             | Safe access to XML views                                             |
| **Retrofit**                 | HTTP client for interacting with RESTful APIs                        |
| **Gson**                     | Converting JSON data to Kotlin data classes                          |
| **Coroutines**               | Managing asynchronous operations                                     |
| **Lottie**                   | Rendering animations based on JSON files                             |
| **Glide**                    | Efficient image loading and caching                                  |
| **Material Components**      | Building modern and user-friendly UI                                |
| **ConstraintLayout**         | Building responsive and flexible UI layouts                          |
| **Snackbar & Toast**         | Displaying quick feedback messages to the user                       |
| **LiveData & ViewModel**     | Lifecycle-aware data handling and UI state management                |

---

## API

This application uses a remote API to fetch and manage food and cart data.

- Base URL: http://kasimadalan.pe.hu/yemekler/
- Endpoints:
  - tumYemekleriGetir.php
  - sepeteYemekEkle.php
  - sepettekiYemekleriGetir.php
  - sepettenYemekSil.php
