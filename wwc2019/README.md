# Mobile Serverless with Firestore and Google Cloud Functions

This project is about a workshop showing how to use Google Cloud Functions and Google Firebase Firestore to build a serverless backend for a mobile application. This uses Google's provided sample application and codelab, Friendly Eats. This is available for Android and iOS.

Note: The full implementation here was only done for Android due to time constraints but can be implemented with iOS as well.

*Costs:* This workshop does not require you to spend any money. To make sure you don't have any costs after the workshop, delete your GCP project at the end.

# The Story
You've built a Friendly Eats restaurant reviewing app. Your app has become so popular, restuarants are emailing you to request email notifications when there is a new review on their restaurant. They've also asked if they can see all the reviews to their restaurant without using the app.

Let's build a serverless solution for these features.

# Setup

There is a lot of setup for this project but you can do it in pieces and focus on the part you're most interested in.

* Android Studio can be downloaded [here](https://developer.android.com/studio/)
* Xcode can be downloaded [here](https://developer.apple.com/xcode/)
* Create a Github account [here](https://github.com/)
* Create a free Google Cloud Platform account [here](https://cloud.google.com/free/)

# Google Cloud Platform Setup
* Enable Cloud Functions with these instructions:
* SendGrid (if you want to send real emails):
    * Enable SendGrid from the Google Marketplace (search for SendGrid in GCP console)
    * Sign up for a SendGrid account
    * Generate (and protect) a SendGrid API key
* Enable Firebase by creating a project [here](https://console.firebase.google.com/)
* Firestore:
    * Enable Firestore
    * Allow Firestore email authentication
* Create a Firestore app for your mobile application
    * When in the guide, fill out the package field with TODO

# Android Setup

* You can follow the tutorial here https://codelabs.developers.google.com/codelabs/firestore-android/#0 or you can clone my repo with most of the code already written here https://github.com/albendz/friendlyeats-android

# iOS Setup
Unfortunately, I don't have the iOS code ready so you'll need to follow the tutorial here https://codelabs.developers.google.com/codelabs/firestore-ios/#0


# Writing Data to Firestore
For this workshop, I've added contact info to the default restaurant information with the email of the restaurant. You can see the code changes for Android here: https://github.com/albendz/friendlyeats-android

If you do not have the mobile app running and still want to trigger the Cloud Functions, you can follow these instructions to create data in Firestore.
1. Go to the Firebase console
2. Go to the Firestore console
3. Add a new collection called restaurants
4. Add the following data: TODO
5. To trigger the function, add a new collection called ratings if it isn't already there and add a rating document with the following data: TODO

# Cloud Function: Email the Restaurant
The code for this cloud function is written here:

You can copy paste the main.py and requirements.txt into a new cloud function in the GCP console to run the code. Make sure to set the function to execute as TODO, the trigger as TODO, the resourced as TODO.

# Cloud Function: Get the Reviews via HTTP
The code for this cloud function is written here:

You can copy paste the main.py and requirements.txt into a new cloud function in the GCP console to run the code. Make sure to set the function to execute as TODO, the trigger as TODO, the resourced as TODO.

When asked for permissions, you can either leave this as all external or make it private. We can change this later.

# Additional Information
## Deploying via gcloud command line tools
Instead of deploying by hand, you can deploy cloud functions through two different command line tools.
* (Recommended) Firebase CLI can be downloaded [here](https://firebase.google.com/docs/cli/)
* gcloud CLI can be downloaded [here](https://cloud.google.com/sdk/gcloud/)
## Cloud Function Configuration
Here's a bit more info about the configuration of the Cloud Functions:
* Source Code: You can upload source code through a few different methods depending on your workflow. We've used the inline editor but for automated deployments you can use zip upload, zip from cloud storage, or cloud source repository (GCP hosted code repo). Using Cloud Storage can allow you to keep a history of your deployments. Otherwise, the old code is deleted and you won't be able to restore old versions.
* Runtime: I chose Python for my runtime but any runtime will work fine.
* Region: Choosing a region close to where your other GCP or external services are hosted will reduce latency and costs.
* Maximum function instances: This will limit the amount of instances are running and can help with throttling or cost limiting.
* Service Account: This is the account that the funciton will run as. You can set it as a different account if you want to limit the permissions of the function.
* Retry on failure: This will cause the failed executions to retry infinitely for 7 days. Don't use this.
* Networking: Allow access to resources in private VPCs.
* Environment variables: Set environment variables to access in your code. I've had issues with these so use carefully.
## Firestore Security Rules
* The codelab and Firestore setup shows how to set rules to only allow authenticated users to  access Firestore. These can be expanded to be collection or document specific, authentication type specific, and you can add custom rules as well. More information can be found on this here: https://firebase.google.com/docs/firestore/security/rules-structure
## Logging, Metrics, and Alerting
By default, logs are sent to STDOUT and you can view them in the log console. All the logs and metrics are available in Stackdriver to create dashboards and alerts here: https://cloud.google.com/stackdriver/. 

If you're using something else, like Splunk, Datadog, or New Relic, you can make REST calls to the APIs from those services or you can look into the GCP integrations for each of them.

## Using Secrets in Cloud Functions with KMS
If you need to use API keys, like with SendGrid in this workshop, you will need to store them encrypted for security. You can use Cloud KMS to encrypt your keys before you use them. A tutorial on how to do this can be found here: https://cloud.google.com/kms/docs/tutorials

## Other Firebase Triggers for Cloud Functions
https://cloud.google.com/functions/docs/calling/cloud-firestore

https://cloud.google.com/functions/docs/calling/firebase-auth

https://cloud.google.com/functions/docs/calling/firebase-remote-config

https://cloud.google.com/functions/docs/calling/google-analytics-firebase

https://firebase.google.com/docs/firestore/