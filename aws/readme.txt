

ssh ec2-user@52.21.69.17 -i MyUse1KP.pem

ssh ec2-user@54.90.176.201 -i MyUser2KP.pem

#!/bin/bash
sudo su

# update all installation
yum update -y

# get current system information:
curl http://169.254.169.254/latest/meta-data/
curl http://169.254.169.254/latest/meta-data/public-ipv4
curl http://169.254.169.254/latest/meta-data/iam/info

# get boot strap command
curl http://169.254.169.254/latest/user-data/
 

#AWS command line:
aws s3 ls


# add support for EFS
yum install -y amazon-efs-utils


yum install mysql
 mysql -u admin -h justadb.c52rewpdek7m.us-east-1.rds.amazonaws.com --password=dbPassword
	use sys;
	select * from sys_config;
	use test;
	CREATE TABLE Human ( ID int NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (ID) );
	insert into Human values(3, 'aaa');
	
mysql -u admin -h justadb-readreplic.c52rewpdek7m.us-east-1.rds.amazonaws.com --password=dbPassword

# how to ssh your private EC2
# firstly you need to ssh into a public EC2
# then you need to create a Private RSA key file and copy content from your local, like MyUser3KP.pem
# chmod 400 MyUser3KP.pem, otherwise SSH will complain UNPROTECTED PRIVATE KEY FILE
# now you ssh from your public EC2 into your private EC2

# how to increase EBS size without downtime
#    https://hackernoon.com/tutorial-how-to-extend-aws-ebs-volumes-with-no-downtime-ec7d9e82426e
