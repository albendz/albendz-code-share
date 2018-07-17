from __future__ import print_function

import json
import boto3
import logging

print('Loading function')


def lambda_handler(event, context):
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    logger.info("Received event: " + json.dumps(event, indent=2))
    contact = event['contactEmail']
    message = event['message']
    date = event['date']
    logger.info("Received message {} from {} on {}".format(message, contact, date))
    publish_to_sns(message, contact, date)
    return event

def publish_to_sns(message, contact, date):
    sns = boto3.client(service_name="sns")
    topicArn = 'arn:aws:sns:us-west-2:068306185445:contact'
 
    sns.publish(
        TopicArn = topicArn,
        Message = "Received message {} from {} on {}".format(message, contact, date)
    )
    return

def save_to_dynamo(message):
    ddb = boto3.client(service_name="dynamodb")
    table = "arn:aws:dynamodb:us-west-2:068306185445:table/contactRequests"
    
    ddb.putItem(table, message);
    return
