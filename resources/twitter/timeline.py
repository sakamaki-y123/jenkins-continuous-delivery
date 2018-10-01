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
    url = "https://api.twitter.com/1.1/statuses/user_timeline.json"

    params = {'count': args.count}
    req = twitter.get(url, params=params)

    if req.status_code == 200:
        timeline = json.loads(req.text)
        for tweet in timeline:
            print(tweet['user']['name'] + '::' + tweet['text'])
            print(tweet['created_at'])
            print('----------------------------------------------------')
    else:
        print("ERROR: %d" % req.status_code)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='tweet message',
        add_help=True,
    )
    parser.add_argument('-c', '--count', default=5, type=int)
    args = parser.parse_args()

    sys.exit(main(args))