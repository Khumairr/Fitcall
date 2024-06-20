## FitCal: Health & Fitness Application

### Overview
FitCal is a web application designed to help users maintain a healthy lifestyle by providing functionalities such as food recognition, calorie prediction, and fitness recommendations. The application is built using Flask, TensorFlow, Firebase, and other essential libraries. 

### Features
- **Food Recognition**: Upload an image of your meal, and the app will predict the type of food, estimate its calories, and provide dietary recommendations.
- **Calorie Burn Prediction**: Input your physical parameters and activity details to estimate the calories burned.
- **User Authentication**: Register, login, and manage user sessions securely using Firebase Authentication.
- **User Data Management**: Store and retrieve user data securely using Firebase Firestore.

### Prerequisites
- Python 3.7+
- Firebase account and setup with Firestore and Authentication enabled
- TensorFlow installed
- Flask installed
- PIL installed (for image processing)
- Requests library installed

### Installation
1. **Clone the repository**:
    ```sh
    git clone https://github.com/yourusername/FitCal.git
    cd FitCal
    ```

2. **Install dependencies**:
    ```sh
    pip install -r requirements.txt
    ```

3. **Set up Firebase**:
    - Download your Firebase service account key and save it as `capstone-fitcal-firebase-adminsdk-0710i-435ec42ac3.json`.
    - Update the `GOOGLE_APPLICATION_CREDENTIALS` environment variable in the code with your Firebase credentials.

4. **Set up the models**:
    - Ensure the TensorFlow models are placed in the `model/` directory.
    - The models should be named `model_deteksi_makanan_5c_v3_88_37_86_68.h5` and `model.h5`.

5. **Run the application**:
    ```sh
    python app.py
    ```
    The application will run on `http://0.0.0.0:8080`.

### API Endpoints
#### `/predict` [POST]
Predict the type of food, its calories, and provide dietary recommendations.
- **Request**: Form-data with an image file.
- **Response**: JSON with prediction results, calories, and recommendations.

**Test using Postman**:
1. Open Postman and create a new POST request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/predict`.
3. In the `Body` tab, select `form-data`.
4. Add a key `file` with type `File`, and upload an image file.
5. Click `Send`.

#### `/burn_kalori` [POST]
Predict the calories burned based on user input.
- **Request**: JSON with user physical parameters and activity details.
- **Response**: JSON with the predicted calories burned.

**Test using Postman**:
1. Open Postman and create a new POST request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/burn_kalori`.
3. In the `Body` tab, select `raw` and set the type to `JSON`.
4. Add the following JSON structure:
    ```json
    {
        "Gender": "Laki-laki",
        "Age": 25,
        "Height": 175,
        "Weight": 70,
        "Duration": 30,
        "Heart_Rate": 110,
        "Body_Temp": 37.5,
        "User_ID": "user_id_example"
    }
    ```
5. Click `Send`.

#### `/register` [POST]
Register a new user.
- **Request**: JSON with `email`, `username`, and `password`.
- **Response**: JSON with a success message or error.

**Test using Postman**:
1. Open Postman and create a new POST request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/register`.
3. In the `Body` tab, select `raw` and set the type to `JSON`.
4. Add the following JSON structure:
    ```json
    {
        "email": "test@example.com",
        "username": "testuser",
        "password": "password123"
    }
    ```
5. Click `Send`.

#### `/login` [POST]
Login an existing user.
- **Request**: JSON with `email` and `password`.
- **Response**: JSON with a success message or error.

**Test using Postman**:
1. Open Postman and create a new POST request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/login`.
3. In the `Body` tab, select `raw` and set the type to `JSON`.
4. Add the following JSON structure:
    ```json
    {
        "email": "test@example.com",
        "password": "password123"
    }
    ```
5. Click `Send`.

#### `/logout` [POST]
Logout the current user.
- **Request**: No parameters.
- **Response**: JSON with a success message.

**Test using Postman**:
1. Open Postman and create a new POST request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/logout`.
3. Click `Send`.

#### `/user/<user_id>` [GET]
Retrieve user data from Firestore.
- **Request**: No parameters.
- **Response**: JSON with user data or error.

**Test using Postman**:
1. Open Postman and create a new GET request.
2. Set the URL to `https://fitcal-app-4tgyvabarq-et.a.run.app/user/<user_id>` (replace `<user_id>` with the actual user ID).
3. Click `Send`.

### Code Structure
- **`app.py`**: Main Flask application file.
- **`storeData.py`**: Contains functions to store data in Firestore.
- **`requirements.txt`**: List of dependencies.

### Environment Variables
- `GOOGLE_APPLICATION_CREDENTIALS`: Path to the Firebase service account key JSON file.
- `PORT`: (optional) Port on which the Flask app runs (default is 8080).

### Logging
The application logs important events, errors, and information using Python's logging module. Logs are displayed in the console.

### Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code follows the existing coding style and includes appropriate tests.

### License
This project is licensed under the MIT License. See the LICENSE file for more details.

### Acknowledgements
- TensorFlow and Keras for the deep learning models.
- Firebase for user authentication and data storage.
- Flask for the web framework.
- PIL for image processing.
- Requests for handling HTTP requests.

### Contact
For questions or issues, please open an issue on the GitHub repository or contact the repository owner.

---

Feel free to customize this README file according to your specific requirements and project details.
