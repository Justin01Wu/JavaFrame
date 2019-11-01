

ssh ec2-user@52.21.69.17 -i MyUse1KP.pem

ssh ec2-user@18.212.27.214 -i MyUser2KP.pem

#!/bin/bash
sudo su

# update all installation
yum update -y

# get current system information:
curl http://169.254.169.254/latest/meta-data/
curl http://169.254.169.254/latest/meta-data/public-ipv4

# get boot strap command
curl http://169.254.169.254/latest/user-data/
 
# install JDK 8
yum install java-1.8.0-openjdk-devel -y

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
mvn clean package

cp /opt/tomcat/latest/projects/webUI/target/webUI-1.0-SNAPSHOT.war /opt/tomcat/latest/webapps/webUI.war


java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetObject

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetBigObject master-iter-NorthAmTorHail-v18.txt Single

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.CopyBigObject master-iter-NorthAmTorHail-v18.txt

java -cp "./aws-test.jar:./jars/*" com.justa.test.aws.GetLocalFile master-iter-NorthAmTorHail-v18.txt


#AWS command line:
aws s3 ls

aws sts get-caller-identity
{
    "Account": "137200312110",
    "UserId": "AROAR74OGT4XJA24NUIYS:i-0e387fb768f0e719d",
    "Arn": "arn:aws:sts::137200312110:assumed-role/WebServerRole/i-0e387fb768f0e719d"
}

# here ec2 is assigned a role "WebServerRole"

# add support for EFS
yum install -y amazon-efs-utils


For a 700MB S3 file, run in AWS EC2 only need 12 seconds, 
but it needs 2 minutes 16seconds on remote side 
So EC2 is about 10 times faster than remote side
Also you don't need to set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY in EC2 because I added s3_admin on this EC2

It only needs 2 seconds if read this file from my local(SSD CPU I7 Desktop)
It needs 10 seconds if read this file from EC2(Amazon Linux 2 AMI (HVM), SSD Volume t2.micro)


yum install mysql
 mysql -u admin -h justadb.c52rewpdek7m.us-east-1.rds.amazonaws.com --password=dbPassword
	use sys;
	select * from sys_config;
	use test;
	CREATE TABLE Human ( ID int NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (ID) );
	insert into Human values(3, 'aaa');
	
mysql -u admin -h justadb-readreplic.c52rewpdek7m.us-east-1.rds.amazonaws.com --password=dbPassword

# how to ssh your private EC2
# firstly yu need to ssh into a public EC2
# then you need to create a Private RSA key file and copy content from your local, like MyUser3KP.pem
# chmod 400 MyUser3KP.pem, otherwise SSH will complain UNPROTECTED PRIVATE KEY FILE
# now you ssh from your public EC2 into your private EC2
