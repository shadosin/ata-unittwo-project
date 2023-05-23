# Step One- Fill out the UNIT_TWO_REPO_NAME and GITHUB_USERNAME

# Step Two - configure your shell to always have these variables.
# For OSX / Linux
# Copy and paste ALL of the properties below into your .bash_profile in your home directly

# For Windows
# Copy and paste ALL of the properties below into your .bash_profile file in your home directory


#replace YourActualGithubUsername with your Github username matching the case exactly
#This must match the casing of your repo name in github EXACTLY.
#If the casing is different you will have issues.

#export UNIT_TWO_REPO_NAME=ata-unit-two-project-student-name
export UNIT_TWO_REPO_NAME=ata-unit-two-project-YourActualGithubUsername

#Replace yourusernameinlowercase with your github username all lowercase
#This MUST be ALL lowercase or you will have problems
#for example if your username was student-name this would read:
#export GITHUB_USERNAME=student-name
export GITHUB_USERNAME=yourusernameinlowercase


# Do not modify the rest of these unless you have been instructed to do so.
export UNIT_TWO_PROJECT_NAME=unitproject2
export UNIT_TWO_PIPELINE_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME
export UNIT_TWO_ARTIFACT_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-artifacts
export UNIT_TWO_DEPLOY_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-application
export UNIT_TWO_APP_STORAGE_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-datastore
