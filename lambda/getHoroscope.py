from __future__ import print_function

import json
import boto3
import logging
from botocore.vendored import requests

print('Loading function')

signs ={}
signs['aries'] = 1
signs['taurus'] = 2
signs['gemini'] = 3
signs['cancer'] = 4
signs['leo'] = 5
signs['virgo'] = 6
signs['libra'] = 7
signs['scorpio'] = 8
signs['sagittarious'] = 9
signs['capricorn'] = 10
signs['aquarius'] = 11
signs['pisces'] = 12

def lambda_handler(event, context):
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    logger.info("Received event: " + json.dumps(event, indent=2))
    sign = event['sign']
    logger.info("Received horoscope request for {}".format(sign))
    if sign not in signs:
        print("{} is not a valid sign").format(sign)
        return event
    publish_to_sns(getHoroscope(sign))
    return {'horoscope' : getHoroscope(sign)}

def getHoroscope(sign):
    result = requests.get('https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-today.aspx?sign=' + str(signs[sign]))
    horoscope = ""
    for line in result.text.split('<'):
        if line.find('/strong') > -1:
            horoscope = line
            break
    return horoscope[len('strong>') + 4:]

def publish_to_sns(sign):
    sns = boto3.client(service_name="sns")
    topicArn = 'arn:aws:sns:us-west-2:<account#>:horoscope'
 
    sns.publish(
        TopicArn = topicArn,
        Message = sign
    )
    return
