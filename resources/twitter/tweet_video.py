import os
import sys
import config
import argparse
from TwitterAPI import TwitterAPI

consumer_key = config.CONSUMER_KEY
consumer_secret = config.CONSUMER_SECRET
access_token = config.ACCESS_TOKEN
access_token_secret = config.ACCESS_TOKEN_SECRET

twitter = TwitterAPI(consumer_key, consumer_secret, access_token, access_token_secret)


def check_status(r):
    if r.status_code < 200 or r.status_code > 299:
        print(r.status_code)
        print(r.text)
        sys.exit(0)


def main(args):
    media = args.file
    tweet = args.tweet

    bytes_sent = 0
    total_bytes = os.path.getsize(media)
    file = open(media, 'rb')

    # initialize media upload and get a media reference ID in the response
    r = twitter.request('media/upload', {'command': 'INIT', 'media_type': 'video/mp4', 'total_bytes': total_bytes})
    check_status(r)

    media_id = r.json()['media_id']
    segment_id = 0

    # start chucked upload
    while bytes_sent < total_bytes:
        chunk = file.read(4 * 1024 * 1024)

        # upload chunk of byets (5mb max)
        r = twitter.request('media/upload', {'command': 'APPEND', 'media_id': media_id, 'segment_index': segment_id},
                            {'media': chunk})
        check_status(r)
        segment_id = segment_id + 1
        bytes_sent = file.tell()
        print('[' + str(total_bytes) + ']', str(bytes_sent))

    # finalize the upload
    r = twitter.request('media/upload', {'command': 'FINALIZE', 'media_id': media_id})
    check_status(r)

    # post Tweet with media ID from previous request
    r = twitter.request('statuses/update', {'status': tweet, 'media_ids': media_id})
    check_status(r)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='tweet message',
        add_help=True,
    )
    parser.add_argument('-f', '--file', required=True)
    parser.add_argument('-t', '--tweet', required=True)
    args = parser.parse_args()

    sys.exit(main(args))