import urllib.request
from requests_oauthlib import OAuth1Session
# from bs4 import BeautifulSoup
# from TwitterAPI import TwitterAPI
import datetime
import tweepy
import config
import json
import codecs
import sys
import argparse

CONSUMER_TOKEN = config.CONSUMER_KEY
CONSUMER_SECRET = config.CONSUMER_SECRET
ACCESS_TOKEN = config.ACCESS_TOKEN
ACCESS_SECRET = config.ACCESS_TOKEN_SECRET

#apiを取得
auth = tweepy.OAuthHandler(CONSUMER_TOKEN, CONSUMER_SECRET)
auth.set_access_token(ACCESS_TOKEN, ACCESS_SECRET)
api = tweepy.API(auth,wait_on_rate_limit=True)

def get_video_infos(status,video_infos):
    if hasattr(status, 'extended_entities') and not hasattr(status, 'retweeted_status'):
        for media in status.extended_entities.get('media', [{}]):
            if media.get('type', None) == 'video' :
                for variants in media['video_info']['variants']:
                    if (variants.get('bitrate', None) == 2176000) and (variants['content_type'] == 'video/mp4'):
                        video_info = {}
                        video_info['tweet_id'] = media['id']
                        video_info['tweet_url'] = media['url']
                        video_info['tweet_text'] = status._json['text'].split(media['url'])[0]
                        video_info['user_id'] = status.user._json['id']
                        video_info['user_name'] = status.user._json['name']
                        video_info['video_url'] = variants['url']
                        video_info['created_at'] = status._json['created_at']
                        video_infos.append(video_info)
    return list(video_infos)

def main(args):
    keyword = args.keyword
    search_result_type = args.result_type
    search_count = int(args.count)
    max_result = int(args.max_result)
    result_json = args.file
    video_infos = []
    for status in tweepy.Cursor(api.search,q=keyword,result_type = search_result_type,count = search_count).items():
        video_infos = get_video_infos(status, video_infos)
        if(max_result <= len(video_infos)):
            break;

    for video_info in video_infos:
        print(video_info['tweet_text'])
        print(video_info['video_url'])

    fw = codecs.open(result_json, 'w', 'utf-8')
    json.dump(video_infos, fw, ensure_ascii=False,indent=4)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='search tweet video',
        add_help=True,
    )
    parser.add_argument('-k', '--keyword', nargs='+', required=True)
    parser.add_argument('-f', '--file', required=False ,default="search_video_result.json")
    parser.add_argument('-r', '--result-type', required=False, default="recent")
    parser.add_argument('-c', '--count', required=False, default="15000")
    parser.add_argument('-m', '--max-result', required=False, default="30")

    args = parser.parse_args()

    sys.exit(main(args))