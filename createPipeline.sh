branch=main
if [ -z "$UNIT_TWO_REPO_NAME" ] ; then
  echo "Your environment variables are not properly configured.  Make sure that you have filled out setupEnvironment.sh and that script is set to run as part of your PATH"
  exit 1
fi

echo "Outputting parameters for the pipeline..."
echo "Project name: $UNIT_TWO_PROJECT_NAME"
echo "Github UserName: $GITHUB_USERNAME"
echo "Repo path: $UNIT_TWO_REPO_NAME"
echo "Connection Arn: $CONNECTION_ARN"
echo "Branch: $branch"

aws cloudformation create-stack --stack-name $UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME --template-url https://ata-cloudformation-scripts.s3.us-east-2.amazonaws.com/CICDPipeline-Unit2.yml --parameters ParameterKey=ProjectName,ParameterValue=$UNIT_TWO_PROJECT_NAME ParameterKey=GithubUserName,ParameterValue=$GITHUB_USERNAME ParameterKey=Repo,ParameterValue=$UNIT_TWO_REPO_NAME ParameterKey=Branch,ParameterValue=$branch ParameterKey=ConnectionArn,ParameterValue=$CONNECTION_ARN --capabilities CAPABILITY_IAM
