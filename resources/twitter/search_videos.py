import urllib.request
from requests_oauthlib import OAuth1Session
# from bs4 import BeautifulSoup
# from TwitterAPI import TwitterAPI
import datetime
import tweepy
import config
import json
import sys
import argparse

CONSUMER_TOKEN = config.CONSUMER_KEY
CONSUMER_SECRET = config.CONSUMER_SECRET
ACCESS_TOKEN = config.ACCESS_TOKEN
ACCESS_SECRET = config.ACCESS_TOKEN_SECRET

#apiを取得
auth = tweepy.OAuthHandler(CONSUMER_TOKEN, CONSUMER_SECRET)
auth.set_access_token(ACCESS_TOKEN, ACCESS_SECRET)
api = tweepy.API(auth)

def get_video_url(status,video_infos):
    if hasattr(status, 'extended_entities'):
        for media in status.extended_entities.get('media', [{}]):
            if (media.get('type', None) == 'video') and (media['video_info']['variants'][0]['content_type'] == 'video/mp4') :

                video_info ={}
                video_info["id"] = status.id_str
                video_info["user_id"] = status.user.name
                video_info["user_name"] = status.user.screen_name
                video_info["tweet_date"] = str(status.created_at + datetime.timedelta(hours=9))
                video_info["tweet_message"] = status.text
                video_info["video_url"] = media['video_info']['variants'][0]['url']
                video_infos.append(video_info)
                print("tweet id:" + video_info["id"])
                print("video url:" + video_info["video_url"])
    return video_infos

def main(args):
    keyword = args.keyword
    search_lang = args.lang
    search_result_type = args.result_type
    search_count = args.count
    max_result = int(args.max_result)
    result_json = args.file
    video_infos = []
    for status in tweepy.Cursor(api.search,q=keyword,count = search_count,result_type = search_result_type,lang=search_lang).items():
        video_infos = get_video_url(status, video_infos)
        if(max_result <= len(video_infos)):
            break;

    fw = open(result_json, 'w')
    json.dump(video_infos, fw, indent=4)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='search tweet video',
        add_help=True,
    )
    parser.add_argument('-k', '--keyword', nargs='+', required=True)
    parser.add_argument('-f', '--file', required=False ,default="search_video_result.json")
    parser.add_argument('-l', '--lang', required=False,default="en")
    parser.add_argument('-r', '--result-type', required=False, default="recent")
    parser.add_argument('-c', '--count', required=False, default="5000")
    parser.add_argument('-m', '--max-result', required=False, default="30")

    args = parser.parse_args()

    sys.exit(main(args))