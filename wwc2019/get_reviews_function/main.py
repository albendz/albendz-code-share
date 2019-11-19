from google.cloud import firestore
import google.auth
import json

def get_reviews(request):
    """Responds to any HTTP request.
    Args:
        request (flask.Request): HTTP request object.
    Returns:
        The response text or any set of values that can be turned into a
        Response object using
        `make_response <http://flask.pocoo.org/docs/1.0/api/#flask.Flask.make_response>`.
    """

    # https://us-central1-wwcproject-493a1.cloudfunctions.net/get_reviews?restaurant=4tcK1e378m9AsiUSVZrQ

    if request.args and 'restaurant' in request.args:
        google.auth.default()
        firestore_client = firestore.Client()

        # Get the restaurant document ID
        restaurant_id = request.args.get('restaurant')

        # Get a reference to the restaruant document
        document_reference = firestore_client.document('restaurants', restaurant_id)

        # Get the name of the restaurant
        restaurant_name = document_reference.get().to_dict()['name']

        # Get a reference to all ratings for this restaurant
        collection_reference = firestore_client.collection('restaurants', restaurant_id, 'ratings')
        rating_references = collection_reference.list_documents()

        # A list of the html strings for the ratings
        ratings = []

        # For each Firestore rating, create html and add to list to display
        for rating in rating_references:
            document_reference = firestore_client.document('restaurants', restaurant_id, 'ratings', rating.id)
            ratings.append(str(Rating(document_reference.get().to_dict())))
        
        # Concat all the ratings and generate response html
        ratings_list = '\n'.join(ratings)
        response_html = f'<html><body><h1>Ratings for: {restaurant_name}</h1>{ratings_list}</body></html>'

        return response_html
    else:
        return f'Sorry, we couldn\'t find that restaurant.'

class Rating():
    def __init__(self, rating_blob):
        self.text = rating_blob['text']
        self.star_rating = rating_blob['rating']
        self.date = rating_blob['timestamp'].strftime('%m/%d/%Y')
    
    def __str__(self):
        return f'<p><h2>Stars: {self.star_rating}</h2><i>{self.date}</i><p>{self.text}</p></p>'