#!/usr/bin/python

import httplib2
import os
import sys

from apiclient.discovery import build
from apiclient.errors import HttpError
from oauth2client.client import flow_from_clientsecrets
from oauth2client.file import Storage
from oauth2client.tools import argparser, run_flow


# The CLIENT_SECRETS_FILE variable specifies the name of a file that contains
# the OAuth 2.0 information for this application, including its client_id and
# client_secret. You can acquire an OAuth 2.0 client ID and client secret from
# the Google Developers Console at
# https://console.developers.google.com/.
# Please ensure that you have enabled the YouTube Data API for your project.
# For more information about using OAuth2 to access the YouTube Data API, see:
#   https://developers.google.com/youtube/v3/guides/authentication
# For more information about the client_secrets.json file format, see:
#   https://developers.google.com/api-client-library/python/guide/aaa_client_secrets
CLIENT_SECRETS_FILE = "client_secrets.json"

# This OAuth 2.0 access scope allows for full read/write access to the
# authenticated user's account.
YOUTUBE_READ_WRITE_SCOPE = "https://www.googleapis.com/auth/youtube"
YOUTUBE_API_SERVICE_NAME = "youtube"
YOUTUBE_API_VERSION = "v3"

# This variable defines a message to display if the CLIENT_SECRETS_FILE is
# missing.
MISSING_CLIENT_SECRETS_MESSAGE = """
WARNING: Please configure OAuth 2.0

To make this sample run you will need to populate the client_secrets.json file
found at:

   %s

with information from the Developers Console
https://console.developers.google.com/

For more information about the client_secrets.json file format, please visit:
https://developers.google.com/api-client-library/python/guide/aaa_client_secrets
""" % os.path.abspath(os.path.join(os.path.dirname(__file__),
                                   CLIENT_SECRETS_FILE))

def get_authenticated_service(args):
  client_secrets = args.client_secrets or CLIENT_SECRETS_FILE
  flow = flow_from_clientsecrets(client_secrets,
    scope=YOUTUBE_READ_WRITE_SCOPE,
    message=MISSING_CLIENT_SECRETS_MESSAGE)
  
  credentials = args.credentials_file or "%s-oauth2.json" % sys.argv[0]
  storage = Storage(credentials)
  credentials = storage.get()

  if credentials is None or credentials.invalid:
    credentials = run_flow(flow, storage, args)

  return build(YOUTUBE_API_SERVICE_NAME, YOUTUBE_API_VERSION,
    http=credentials.authorize(httplib2.Http()))

def update_video(youtube, options):
  # Call the API's videos.list method to retrieve the video resource.
  videos_list_response = youtube.videos().list(
    id=options.video_id,
    part='snippet'
  ).execute()

  # If the response does not contain an array of "items" then the video was
  # not found.
  if not videos_list_response["items"]:
    print ("Video '%s' was not found." % options.video_id)
    sys.exit(1)

  # Since the request specified a video ID, the response only contains one
  # video resource. This code extracts the snippet from that resource.
  videos_list_snippet = videos_list_response["items"][0]["snippet"]

  videos_list_snippet.title = options.title
  videos_list_snippet.description = options.description

  # Preserve any tags already associated with the video. If the video does
  # not have any tags, create a new array. Append the provided tag to the
  # list of tags associated with the video.
  if "tags" not in  videos_list_snippet:
    videos_list_snippet["tags"] = []
  videos_list_snippet["tags"].append(options.tag)

  if options.category_id != "":
    videos_list_snippet.category_id = options.category_id

  # Update the video resource by calling the videos.update() method.
  videos_update_response = youtube.videos().update(
    part='snippet',
    body=dict(
      snippet=videos_list_snippet,
      id=options.video_id
    )).execute()

if __name__ == "__main__":
  argparser.add_argument("--video-id", help="ID of video to update.", required=True)
  argparser.add_argument("--title", default="", help="Additional tag to add to video.")
  argparser.add_argument("--description", default="", help="Video description")
  argparser.add_argument("--description-file", default="", help="Video description file")
  argparser.add_argument("--category-id", default="", help="Video category Id")
  argparser.add_argument("--tag", default="youtube", help="Additional tag to add to video.")
  argparser.add_argument("--client-secrets", default=".client_secrets.json", help="Client secrets JSON file")
  argparser.add_argument("--credentials-file", default=".youtube-upload-credentials.json", help="Credentials JSON file")
  args = argparser.parse_args()
  if args.description_file is not None and os.path.exists(args.description_file):
      with open(args.description_file, encoding="utf-8") as file:
          args.description = file.read()

  youtube = get_authenticated_service(args)
  try:
    update_video(youtube, args)
  except HttpError, e:
    print ("An HTTP error %d occurred:\n%s" % (e.resp.status, e.content))
  else:
    print ("Tag '%s' was added to video id '%s'." % (args.tag, args.video_id))
