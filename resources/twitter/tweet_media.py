# -*- coding:utf-8 -*-
import json, config
import sys
import argparse
from requests_oauthlib import OAuth1Session

CK = config.CONSUMER_KEY
CS = config.CONSUMER_SECRET
AT = config.ACCESS_TOKEN
ATS = config.ACCESS_TOKEN_SECRET
twitter = OAuth1Session(CK, CS, AT, ATS)

def main(args):
    url_media = "https://upload.twitter.com/1.1/media/upload.json"
    url_text = "https://api.twitter.com/1.1/statuses/update.json"

    media_name = args.file
    print('-----------------------------------')

    files = {"media": open(media_name, 'rb')}
    req_media = twitter.post(url_media, files=files)

    if req_media.status_code != 200:
        print("MEDIA UPLOAD FAILED... %s", req_media.text)
        exit()

    media_id = json.loads(req_media.text)['media_id']
    print("MEDIA ID: %d" % media_id)

    tweet = args.tweet
    print('-----------------------------------')

    params = {"status": tweet, "media_ids": [media_id]}
    req_media = twitter.post(url_text, params=params)

    if req_media.status_code != 200:
        print("TEXT UPLOAD FAILED... %s", req_media.text)
        exit()

    print("SUCCEED!")

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='tweet message',
        add_help=True,
    )
    parser.add_argument('-f', '--file', required=True)
    parser.add_argument('-t', '--tweet', required=True)
    args = parser.parse_args()

    sys.exit(main(args))

