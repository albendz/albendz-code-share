# using SendGrid's Python Library - https://github.com/sendgrid/sendgrid-python
import sendgrid
from google.cloud import firestore
import google.auth

def email_restaurant(event, context=None):
    """Triggered by a change to a Firestore document.
    Args:
         event (dict): Event payload.
         context (google.cloud.functions.Context): Metadata for the event.
    """
    google.auth.default()
    
    # Get the restaurant id
    restaurant_id = "/".join(event['value']['name'].split('/')[6:7])

    # Get the restaurant data
    firestore_client = firestore.Client()
    document_reference = firestore_client.document('restaurants', restaurant_id)
    restaurant_data = document_reference.get().to_dict()
    print(restaurant_id)
    email = restaurant_data['contact']
    name = restaurant_data['name']

    # Create Link to Get Reviews Function
    url = "https://us-central1-wwcproject-493a1.cloudfunctions.net/get_reviews?restaurant=" + restaurant_id

    # Structure the review email
    message_html = f"""<html><body><h1>You have a new review on Friendly Eats!
    </h1><p>Dear {name},</p><p>You have a new review on Friendly Eats. 
    Click here to see all your reviews: <a href={url}>your reviews</a>
    </p>Friendly Eats</body></html>"""

    # Send the email
    sg = sendgrid.SendGridAPIClient("APIKEY")
    to_email = email
    from_email = 'friends@friendlyeats.com'
    subject = "New Review on Friendly Eats"
    message = sendgrid.helpers.mail.Mail(to_emails=to_email, from_email=from_email, subject=subject, html_content=message_html)
    sg.send(message)