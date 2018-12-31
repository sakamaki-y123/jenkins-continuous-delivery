from pytube import YouTube
from enum import Enum

import argparse
import sys

class Loader(Enum):
    MP4 = 'mp4'
    WEBM = 'webm'

    def load(self, yt, res):
        vd = yt.get(self.value, res)
        vd.download('./videos')

def download(url,download_path):
    # URLを引数としてインスタンスの生成
    yt = YouTube(url)
    # もっとも解像度の高いものを取得
    # https://github.com/nficano/pytube#behold-a-perfect-balance-of-simplicity-versus-flexibility
    yt.streams.filter(progressive=True, file_extension='mp4').order_by('resolution').desc().first().download(output_path=download_path, filename=yt.video_id)
    print(yt.video_id + ".mp4")

def parse_args():
    parser = argparse.ArgumentParser(
        description='download youtube video option',
        add_help=True,
    )
    parser.add_argument('-u', '--url', required=True)
    parser.add_argument('-d', '--download-path', default="./")
    args = parser.parse_args()
    return args

def main():
    args = parse_args()
    download(args.url,args.download_path)

if __name__ == '__main__':
    sys.exit(main())
