# ubuntu 16.04 に jenkins をセットアップする

https://www.digitalocean.com/community/tutorials/how-to-install-jenkins-on-ubuntu-16-04

https://obel.hatenablog.jp/entry/20170531/1496213239

https://tecadmin.net/install-jenkins-in-ubuntu/

## install java
```sh
sudo apt-get -y update
sudo apt-get install -y openjdk-8-jdk
```

## install
```sh
wget -q -O - https://pkg.jenkins.io/debian/jenkins-ci.org.key | sudo apt-key add -
echo deb https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list
sudo apt-get -y update
sudo apt-get -y install jenkins
```

## opening the firewal
```sh
sudo ufw allow 8080
```

### sudo: unable to resolve host が表示されたら
https://qiita.com/ogomr/items/89e19829eb8cc08fcebb

