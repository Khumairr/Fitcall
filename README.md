# FitCal: Health & Fitness Application

FitCal is a web application designed to help users maintain a healthy lifestyle by providing functionalities such as food recognition, calorie prediction, and fitness recommendations. The application is built using Flask, TensorFlow, Firebase, and other essential libraries.

## Features

- **Food Recognition**: Upload an image of your meal, and the app will predict the type of food, estimate its calories, and provide dietary recommendations.
- **Calorie Burn Prediction**: Input your physical parameters and activity details to estimate the calories burned.
- **User Authentication**: Register, login, and manage user sessions securely using Firebase Authentication.
- **User Data Management**: Store and retrieve user data securely using Firebase Firestore.

## Prerequisites

- Python 3.7+
- Firebase account and setup with Firestore and Authentication enabled
- TensorFlow installed
- Flask installed
- PIL installed (for image processing)
- Requests library installed

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Sholahulhaq-Nur-Hamid/FitCal.git
    cd FitCal
    ```

2. Install dependencies:
    ```sh
    pip install -r requirements.txt
    ```

3. Set up Firebase:
    - Download your Firebase service account key and save it as `capstone-fitcal-firebase-adminsdk-0710i-435ec42ac3.json`.
    - Update the `GOOGLE_APPLICATION_CREDENTIALS` environment variable in the code with your Firebase credentials.

4. Set up the models:
    - Ensure the TensorFlow models are placed in the `model/` directory.
    - The models should be named `model_deteksi_makanan_5c_v3_88_37_86_68.h5` and `model.h5`.

## Usage

1. Run the application:
    ```sh
    python app.py
    ```
    The application will run on http://0.0.0.0:8080.

## Code Structure

- `app.py`: Main Flask application file.
- `storeData.py`: Contains functions to store data in Firestore.
- `requirements.txt`: List of dependencies.

## Environment Variables

- `GOOGLE_APPLICATION_CREDENTIALS`: Path to the Firebase service account key JSON file.
- `PORT`: (optional) Port on which the Flask app runs (default is 8080).

## Logging

The application logs important events, errors, and information using Python's logging module. Logs are displayed in the console.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code follows the existing coding style and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Acknowledgements

- TensorFlow and Keras for the deep learning models.
- Firebase for user authentication and data storage.
- Flask for the web framework.
- PIL for image processing.
- Requests for handling HTTP requests.

## Contact

For questions or issues, please open an issue on the GitHub repository or contact the repository owners:
- Sholahulhaq Nur Hamid
- Khumair Ammanullah
- Endhito Baraputra
- Fikri Afif Khairudin
- Galang Satriamahesa Putra Dewa
- Mohammad Jindan Dubbay Al Faresh
- Mohamad Aenur Rokhman
