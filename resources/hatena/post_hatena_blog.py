# coding=utf-8
import requests
from datetime import datetime
import hashlib
import base64
from xml.sax.saxutils import escape
import random
import codecs
import sys
import argparse
import config

USER_NAME = config.USER_NAME
BLOG_NAME = config.BLOG_NAME
API_KEY = config.API_KEY

def create_hatena_text(title, name, body, updated, categories, is_draft):
    is_draft = 'yes' if is_draft else 'no'
    categories_text = ''
    for category in categories:
        categories_text = categories_text + '<category term="{}" />\n'.format(category)
    template = """<?xml version="1.0" encoding="utf-8"?><entry xmlns="http://www.w3.org/2005/Atom" xmlns:app="http://www.w3.org/2007/app">
      <title>{0}</title>
      <author><name>{1}</name></author>
      <content type="text/x-markdown">{2}</content>
      <updated>{3}</updated>
      {4}
      <app:control>
        <app:draft>{5}</app:draft>
      </app:control>
    </entry>"""
    text = template.format(escape(title), name, escape(body), updated, categories_text, is_draft).encode()
    return text


def post_hatena_blog(user_name, password, entry_id, blog_name, data):
    headers = {'X-WSSE': create_wsse_auth_text(user_name, password), 'content-type': 'application/xml'}
    if entry_id is None:
        url = 'http://blog.hatena.ne.jp/{0}/{1}/atom/entry'.format(user_name, blog_name)
    else:
        url = 'http://blog.hatena.ne.jp/{0}/{1}/atom/entry/{2}'.format(user_name, blog_name, entry_id)
    request = requests.post(url, data=data, headers=headers)
    if request.status_code == 201:
        print('POST SUCCESS!!\n' + 'message: ' + request.text)
    else:
        print('Error!\n' + 'status_code: ' + str(request.status_code) + '\n' + 'message: ' + request.text)


def create_wsse_auth_text(user_name, password):
    created = datetime.now().isoformat() + "Z"
    b_nonce = hashlib.sha1(str(random.random()).encode()).digest()
    b_digest = hashlib.sha1(b_nonce + created.encode() + password.encode()).digest()
    c = 'UsernameToken Username="{0}", PasswordDigest="{1}", Nonce="{2}", Created="{3}"'
    return c.format(user_name, base64.b64encode(b_digest).decode(), base64.b64encode(b_nonce).decode(), created)

def main(args):
    title = args.title

    f = codecs.open(args.file, 'r', 'utf8', 'ignore')
    body = f.read()
    f.close()

    categories = args.categories
    now = datetime.now()

    is_draft = args.is_draft

    article = create_hatena_text(title, USER_NAME, body, now, categories, is_draft)
    post_hatena_blog(USER_NAME, API_KEY, entry_id=None, blog_name=BLOG_NAME, data=article)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='post hatena blog',
        add_help=True,
    )
    parser.add_argument('-t', '--title', required=True)
    parser.add_argument('-f', '--file', required=True ,default="post_hatena_blog.md")
    parser.add_argument('-c', '--categories', nargs='+', required=False)
    parser.add_argument('-d', '--draft', action='store_true', default=True, dest='is_draft')
    parser.add_argument('-p', '--publish', action='store_false', default=True, dest='is_draft')
    args = parser.parse_args()
    sys.exit(main(args))



