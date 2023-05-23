#!/bin/bash
set -eo pipefail

FUNCTION=$(aws cloudformation describe-stack-resource --stack-name $UNIT_TWO_DEPLOY_STACK --logical-resource-id caninvoiceclient --query 'StackResourceDetail.PhysicalResourceId' --output text)
aws lambda invoke --function-name $FUNCTION --cli-binary-format raw-in-base64-out --payload file://events/caninvoiceclient.json caninvoiceclient.json
