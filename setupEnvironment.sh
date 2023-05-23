# Step One- Fill out the UNIT_TWO_REPO_NAME and GITHUB_USERNAME

# Step Two - configure your shell to always have these variables.
# For OSX / Linux
# Copy and paste ALL of the properties below into your .bash_profile in your home directly

# For Windows
# Copy and paste ALL of the properties below into your .bashrc file in your home directory


# Fill out the following values
# The path of your repo on github.  Don't but the whole URL, just the part after github.com/
export UNIT_TWO_REPO_NAME=KenzieAcademy-SoftwareEngineering/ATA-Unit-Two-Project
# This should be your username on github.  THIS MUST BE ALL LOWERCASE.  DO NOT PUT UPPERCASE CHARACTERS
export GITHUB_USERNAME=yourusernamehere

# Do not modify the rest of these unless you have been instructed to do so.
export CONNECTION_ARN=arn:aws:codestar-connections:us-west-2:340518317325:connection/7a7e49dd-c801-4f60-aa68-accc67c2c85b
export UNIT_TWO_PROJECT_NAME=unitproject2
export UNIT_TWO_PIPELINE_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME
export UNIT_TWO_ARTIFACT_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-artifacts
export UNIT_TWO_DEPLOY_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-application
export UNIT_TWO_APP_STORAGE_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-datastore
