

ssh ec2-user@52.21.69.17 -i MyUse1KP.pem

ssh ec2-user@3.89.20.3 -i MyUser2KP.pem

sudo su

# update all installation
yum update

# install JDK 8
sudo yum install java-1.8.0-openjdk-devel

# install Tomcat 8  https://linuxize.com/post/how-to-install-tomcat-8-5-on-centos-7/

cd /tmp
sudo useradd -m -U -d /opt/tomcat -s /bin/false tomcat
wget http://www-us.apache.org/dist/tomcat/tomcat-8/v8.5.45/bin/apache-tomcat-8.5.45.zip

unzip apache-tomcat-*.zip
sudo mkdir -p /opt/tomcat
sudo mv apache-tomcat-8.5.45 /opt/tomcat/

sudo ln -s /opt/tomcat/apache-tomcat-8.5.45 /opt/tomcat/latest
sudo chown -R tomcat: /opt/tomcat

sudo sh -c 'chmod +x /opt/tomcat/latest/bin/*.sh'

#then use nano to edit the server.xml port 8080 to 80

yum install git
yum install maven

cd /opt/tomcat/latest
mkdir projects
cd projects
git clone https://github.com/Justin01Wu/webUI.git

mkdir -p /opt/tomcat/latest/webapps/webUi
ln -s  /opt/tomcat/latest/projects/webUI/src/main/webapp/ /opt/tomcat/latest/webapps/webUi


java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetObject

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetBigObject master-iter-NorthAmTorHail-v18.txt Single

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.CopyBigObject master-iter-NorthAmTorHail-v18.txt

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetLocalFile master-iter-NorthAmTorHail-v18.txt




# add support for EFS
yum install -y amazon-efs-utils


For a 700MB S3 file, run in AWS EC2 only need 12 seconds, 
but it needs 2 minutes 16seconds on remote side 
So EC2 is about 10 times faster than remote side
Also you don't need to set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY in EC2 because I added s3_admin on this EC2

It only needs 2 seconds if read this file from my local(SSD CPU I7 Desktop)
It needs 10 seconds if read this file from EC2(Amazon Linux 2 AMI (HVM), SSD Volume t2.micro)