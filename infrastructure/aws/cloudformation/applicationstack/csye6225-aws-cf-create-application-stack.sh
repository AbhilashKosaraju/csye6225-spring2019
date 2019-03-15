echo "Please enter Network Stack Name:"
read networkStackName
if [ -z "$networkStackName" ]
then
	echo "StackName error exiting!"
	exit 1
fi
echo "$networkStackName"



echo "Please enter Application Stack Name:"
read appStackName
if [ -z "$appStackName" ]
then
	echo "StackName error exiting!"
	exit 1
fi
echo "$appStackName"



echo "Please enter an active keyPair to associate with EC2:"
read keyName
if [ -z "$keyName" ]
then
	echo "StackName error exiting!"
	exit 1
fi
echo "$keyName"

#echo "Please enter the ImageID of centos AMI  created"
#read imageid
echo "Your latest AMI ID is:"
imageid=$(aws ec2 describe-images --filters "Name=name,Values=csye6225*" --query "sort_by(Images, &CreationDate)[-1].[ImageId]" --output "text")
if [ $? -eq 0 ]
then
        echo "$imageid"
else
        echo "AMI Id retrival Failed"
        exit 0
fi





vpcID=$(aws ec2 describe-vpcs --query 'Vpcs[].{VpcId:VpcId}' \
--filters "Name=tag:Name,Values=$networkStackName-csye6225-vpc" "Name=is-default, Values=false" --output text 2>&1)


if [ -z "$vpcID" ]
then
	echo "VPC ID error \n Exiting"
	exit 1
fi


subnet=$(aws ec2 describe-subnets --filters Name=vpc-id,Values=${vpcID})
subnetid1=$(echo -e "$subnet" | jq '.Subnets[0].SubnetId' | tr -d '"')
subnetid2=$(echo -e "$subnet" | jq '.Subnets[1].SubnetId' | tr -d '"')
subnetid3=$(echo -e "$subnet" | jq '.Subnets[2].SubnetId' | tr -d '"')



if [ -z "$subnetid1" ]
then
	echo "Subnet ID 1 error \n Exiting"
	exit 1
fi




if [ -z "$subnetid2" ]
then
	echo "Subnet ID 2 error \n Exiting"
	exit 1
fi


if [ -z "$subnetid3" ]
then
	echo "Subnet ID 3 error \n Exiting"
	exit 1
fi

zone=$(aws route53 list-hosted-zones --query HostedZones[].{Name:Name} --output text | sed 's/.$//')
    bucket="code-deploy.$zone"
    echo "bucketName = $bucket"

# Create CloudFormation Stack
echo "Validating template"
TMP_code=`aws cloudformation validate-template --template-body file://./csye6225-cf-application.json`
if [ -z "$TMP_code" ]
then
	echo "Template error exiting!"
	exit 1
fi
echo "Cloudformation template validation success"

echo "Now Creating CloudFormation Stack"


CRTSTACK_Code=`aws cloudformation create-stack --stack-name $appStackName --template-body file://./csye6225-cf-application.json --capabilities CAPABILITY_NAMED_IAM --parameters   ParameterKey=KeyName,ParameterValue=$keyName ParameterKey=myVpc,ParameterValue=$vpcID ParameterKey=PublicSubnetKey1,ParameterValue=$subnetid1 ParameterKey=PublicSubnetKey2,ParameterValue=$subnetid2 ParameterKey=PublicSubnetKey3,ParameterValue=$subnetid3 ParameterKey=bucketName,ParameterValue=$bucket ParameterKey=ImageID,ParameterValue=$imageid`

if [ -z "$CRTSTACK_Code" ]
then
	echo "Stack Creation error exiting!"
	exit 1
fi
aws cloudformation wait stack-create-complete --stack-name $appStackName
echo "Application Stack Created"
echo "Check AWS Cloudformation"