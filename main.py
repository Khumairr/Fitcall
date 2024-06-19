import os
import logging
import secrets
import requests
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify, session
import tensorflow as tf
from tensorflow.keras.models import load_model
from firebase_admin import credentials, firestore, auth, initialize_app
import firebase_admin
import storeData

# Initialize Flask app
app = Flask(__name__)

# Generate a strong secret key
app.secret_key = secrets.token_hex(24)

# Initialize logging
logging.basicConfig(level=logging.INFO)

# Disable oneDNN custom operations if needed
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'

# Set up Google Application Default Credentials
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "capstone-fitcal-fc420e3e3a77.json"

# Initialize Firebase
cred = credentials.Certificate('capstone-fitcal-firebase-adminsdk-0710i-435ec42ac3.json')
firebase_admin.initialize_app(cred)

# Initialize Firestore
db = firestore.client()

# Firebase Auth REST API URL with your API key
API_KEY = "AIzaSyBgActJza4C9yLjKaAEAx-zpIX6mlFVsjk"
url = f"https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={API_KEY}"

# Define custom objects for both models
custom_objects = {
    'mse': tf.keras.losses.MeanSquaredError()
}

@tf.keras.utils.register_keras_serializable()
def mse(y_true, y_pred):
    return tf.reduce_mean(tf.square(y_true - y_pred))

# Model 1 details
model_path1 = 'model/model_deteksi_makanan_5c_v3_88_37_86_68.h5'

# Model 2 details
model_path2 = 'model/model.h5'

# Ensure the model directories exist
os.makedirs(os.path.dirname(model_path1), exist_ok=True)
os.makedirs(os.path.dirname(model_path2), exist_ok=True)


# Load the trained models
model1 = load_model(model_path1, custom_objects=custom_objects)
model2 = load_model(model_path2, custom_objects={'mse': mse})

# List of class names corresponding to the model's output indices for model 1
class_names = ['tempe', 'nasi', 'ayam', 'telur', 'tahu']

# Get the input shape expected by the model 1
input_shape = model1.input_shape[1:3]

# Dictionary untuk menentukan kalori makanan
kalori_makanan = {
    'tempe': 200,
    'nasi': 150,
    'ayam': 250,
    'telur': 180,
    'tahu': 150,
}

# Dictionary untuk rekomendasi makanan
rekomendasi_makanan = {
    'tempe': 'cocok untuk dikonsumsi 2-3 kali seminggu',
    'nasi': 'cocok untuk dikonsumsi sehari-hari',
    'ayam': 'cocok untuk dikonsumsi 1-2 kali sehari',
    'telur': 'cocok untuk dikonsumsi 1-2 kali sehari',
    'tahu': 'cocok untuk dikonsumsi 2-3 kali seminggu',
}

@app.route('/predict', methods=['POST'])
def predict():
    logging.info("Request received.")
    if 'file' not in request.files:
        logging.error("No file part in request.")
        return jsonify({'error': 'No file part'}), 400

    file = request.files['file']

    if file.filename == '':
        logging.error("No selected file.")
        return jsonify({'error': 'No selected file'}), 400

    try:
        logging.info("Processing the file.")
        # Process the image file
        img = Image.open(file.stream)
        img = img.resize(input_shape)
        img = np.array(img) / 255.0
        img = np.expand_dims(img, axis=0)

        logging.info("Making prediction.")
        # Predict using model 1
        prediction = model1.predict(img)
        predicted_class_idx = np.argmax(prediction, axis=1)[0]
        predicted_class = class_names[predicted_class_idx]
        kalori = kalori_makanan.get(predicted_class, 'Unknown')
        rekomendasi = rekomendasi_makanan.get(predicted_class, 'tidak ada rekomendasi')

        logging.info(f"Prediction made: {predicted_class}")
        
        # Store prediction in Firestore
        storeData.store_prediction(predicted_class, kalori, rekomendasi)
          
        return jsonify({
            'hasil prediksi adalah': predicted_class,
            'kalori': kalori,
            'rekomendasi': rekomendasi
        })
    except Exception as e:
        logging.error(f"Error: {str(e)}")
        return jsonify({'error': str(e)}), 500

@app.route('/burn_kalori', methods=['POST'])
def prediksi():
    try:
        # Get data from POST request
        data = request.json
        logging.info(f"Received data: {data}")

        # Extract input based on expected structure
        jenis_kelamin = 1 if data['Gender'] == 'Laki-laki' else 0
        umur = data['Age']
        tinggi = data['Height']
        berat = data['Weight']
        durasi = data['Duration']
        detak_jantung = data['Heart_Rate']
        suhu_tubuh = data['Body_Temp']
        
        # Create input array
        inputan = np.array([[jenis_kelamin, umur, tinggi, berat, durasi, detak_jantung, suhu_tubuh]])
        logging.info(f"Input for model: {inputan}")

        # Predict using model 2
        prediksi = model2.predict(inputan)
        
        # Convert prediction to list of floats for JSON serialization
        daftar_prediksi = prediksi.flatten().tolist()
        logging.info(f"Prediction: {daftar_prediksi}")
        
        # Store prediction in Firestore
        try:
            logging.info(f"Storing burned calories for user {data['User_ID']}: {daftar_prediksi}")
            storeData.store_burned_calories(data['User_ID'], daftar_prediksi)
        except Exception as e:
            logging.error(f"Error storing burned calories: {str(e)}")
            return jsonify({'error': 'Error storing burned calories'}), 500
        
        # Return response as JSON
        return jsonify({'User_ID': data['User_ID'], 'kalori_terbakar': daftar_prediksi})
    except Exception as e:
        logging.error(f"Error: {str(e)}")
        return jsonify({'error': str(e)})

# Register User
@app.route('/register', methods=['POST'])
def register():
    data = request.json
    email = data.get('email')
    username = data.get('username')
    password = data.get('password')
    
    try:
        # Create a new user with email and password
        user = auth.create_user(
            email=email,
            password=password,
        )
        
        # Save additional user data in Firestore
        user_data = {
            'username': username,
            'email': email
        }
        db.collection('users').document(user.uid).set(user_data)
        
        return jsonify({'message': 'User registered successfully'}), 201
    except Exception as e:
        return jsonify({'message': str(e)}), 400

# Login User
@app.route('/login', methods=['POST'])
def login():
    data = request.json
    email = data.get('email')
    password = data.get('password')

    payload = {
        "email": email,
        "password": password,
        "returnSecureToken": True
    }

    try:
        # Kirim permintaan POST ke Firebase Auth REST API
        response = requests.post(url, json=payload)
        response_data = response.json()
        
        # Debugging: Cetak response data untuk melihat detail error
        print(response_data)

        if response.status_code == 200:
            # Login berhasil
            user_id = response_data['localId']
            session['user_id'] = user_id
            return jsonify({'message': 'User logged in successfully'}), 200
        else:
            # Login gagal
            error_message = response_data.get('error', {}).get('message', 'Login failed')
            
            # Periksa pesan error spesifik untuk ketidakcocokan email/kata sandi
            if error_message in ['EMAIL_NOT_FOUND', 'INVALID_PASSWORD']:
                return jsonify({'message': 'Email atau kata sandi salah'}), 400
            else:
                return jsonify({'message': error_message}), 400

    except Exception as e:
        return jsonify({'message': str(e)}), 400

# Logout User
@app.route('/logout', methods=['POST'])
def logout():
    session.pop('user_id', None)
    return jsonify({'message': 'User logged out successfully'}), 200

# Example Endpoint to Retrieve User Data from Firestore
@app.route('/user/<user_id>', methods=['GET'])
def get_user(user_id):
    try:
        user_ref = db.collection('users').document(user_id)
        user = user_ref.get()
        if user.exists:
            return jsonify(user.to_dict()), 200
        else:
            return jsonify({'message': 'User not found'}), 404
    except Exception as e:
        return jsonify({'message': str(e)}), 400

if __name__ == '__main__':
   app.run(debug=True, host='0.0.0.0', port=int(os.environ.get('PORT', 8080)))
