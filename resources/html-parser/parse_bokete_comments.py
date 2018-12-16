# -*- coding: utf-8
import urllib.request
import codecs
import argparse
import json
from html.parser import HTMLParser


# HTMLParserを継承したクラスを定義
class Parser(HTMLParser):

    def __init__(self):
        HTMLParser.__init__(self)
        self.comments = False
        self.comment_content = False
        self.comment_user = False
        self.comment_text = False
        self.comment_data = {}
        self.comment_data_list = []

    def handle_starttag(self, tag, attrs):
        attrs = dict(attrs)
        if tag == "div" and "class" in attrs and attrs['class'] == "comments":
            self.comments = True
        if self.comments == True and tag == "div" and "class" in attrs and attrs['class'] == "comment-content":
            self.comment_content = True


    def handle_endtag(self, tag):
        if self.comment_content == True and tag == "div":
            self.comment_content = False
            self.comment_user = True
        if self.comment_content == False and self.comments == True and tag == "div":
            self.comments == False

    def handle_data(self, data):
        trimed_data = data.replace('\n','').strip()
        if self.comment_user and trimed_data != '':
            self.comment_user = False
            self.comment_text = True
            self.comment_data = {}
            self.comment_data['user'] = trimed_data
            return
        if self.comment_text and trimed_data != '':
            self.comment_text = False
            self.comment_data['comment'] = trimed_data
            self.comment_data_list.append(self.comment_data)
            return


def parse_option():
    parser = argparse.ArgumentParser(
        description='search tweet video',
        add_help=True,
    )
    parser.add_argument('-u', '--url', required=True)
    parser.add_argument('-r', '--result', required=False, default="search_bokete_comments.json")
    args = parser.parse_args()
    return args

def main():
    # 取得先URL
    args = parse_option()
    url = args.url
    result_json = args.result

    # HTMLファイルを開く
    data = urllib.request.urlopen(url)

    # HTMLの取得
    html = data.read()
    html = html.decode('utf-8')

    # HTML解析(タイトルタグの値取得)
    parser = Parser()
    parser.feed(html)

    print(parser.comment_data_list)
    fw = codecs.open(result_json, 'w', 'utf-8')
    json.dump(parser.comment_data_list, fw, ensure_ascii=False,indent=4)

    # 終了処理
    parser.close()
    data.close()

if __name__ == "__main__":
    main()