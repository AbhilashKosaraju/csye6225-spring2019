echo Enter Stack name
#read stack name
read sn
#create stack
{
  validresp=$(aws cloudformation validate-template --template-body file://csye6225-aws-cf-policies.json) &&
  echo "Template validated"
} || {
  echo "$validresp"
  echo "Invalid Template"
  exit 1
}
export circleciuser=circleci

createres=$(aws cloudformation create-stack  --stack-name $sn --capabilities CAPABILITY_NAMED_IAM --template-body file://csye6225-aws-cf-policies.json  --parameters ParameterKey=circleci,ParameterValue=$circleciuser)
echo Creating stack "$sn". Please wait...
resp=$(aws cloudformation wait stack-create-complete --stack-name $sn)
if [[ -z "$resp" ]]; then
  echo Stack "$sn" sucessfully created
else
  echo "$resp"
  exit 1
fi
