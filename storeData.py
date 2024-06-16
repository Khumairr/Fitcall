from google.cloud import firestore
import os
import logging

# Set up logging
logging.basicConfig(level=logging.INFO)

# Set up Google Application Default Credentials
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "capstone-fitcal-fc420e3e3a77.json"

try:
    # Initialize Firestore DB
    db = firestore.Client()
    logging.info("Firestore client initialized successfully.")
except Exception as e:
    logging.error(f"Failed to initialize Firestore client: {e}")
    raise

def store_prediction(predicted_class, kalori, rekomendasi):
    try:
        doc_ref = db.collection('predictions').document()
        doc_ref.set({
            'predicted_class': predicted_class,
            'kalori': kalori,
            'rekomendasi': rekomendasi
        })
        logging.info(f"Stored prediction: {predicted_class}, kalori: {kalori}, rekomendasi: {rekomendasi}")
    except Exception as e:
        logging.error(f"Failed to store prediction: {e}")
        raise

def store_burned_calories(user_id, calories_burned):
    try:
        if not isinstance(calories_burned, list) or not all(isinstance(i, float) for i in calories_burned):
            raise ValueError("calories_burned must be a list of floats.")
        if not user_id:
            raise ValueError("user_id must be provided.")
        
        doc_ref = db.collection('burned_calories').document(user_id)
        doc_ref.set({
            'user_id': user_id,
            'calories_burned': calories_burned
        })
        logging.info(f"Stored burned calories for user {user_id}: {calories_burned}")
    except Exception as e:
        logging.error(f"Failed to store burned calories: {e}")
        raise
