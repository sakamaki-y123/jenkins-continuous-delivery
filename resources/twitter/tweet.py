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
    url = "https://api.twitter.com/1.1/statuses/update.json"

    req = twitter.post(url, args.tweet)

    if req.status_code == 200:
        print("Succeed!")
    else:
        print("ERROR : %d" % req.status_code)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='tweet message',
        add_help=True,
    )
    parser.add_argument('-t', '--tweet', required=True)
    args = parser.parse_args()

    sys.exit(main(args))

