# Step One- Fill out the UNIT_TWO_REPO_NAME and GITHUB_USERNAME

# Step Two - configure your shell to always have these variables.
# For OSX / Linux
# Copy and paste ALL of the properties below into your .bash_profile in your home directly

# For Windows
# Copy and paste ALL of the properties below into your .bashrc file in your home directory

export GITHUB_TOKEN=''
export GITHUB_USERNAME=yourusernameinlowercase

export UNIT_TWO_REPO_NAME=ata-unit-two-project-$GITHUB_USERNAME

# Do not modify the rest of these unless you have been instructed to do so.
export UNIT_TWO_PROJECT_NAME=unitproject2
export UNIT_TWO_PIPELINE_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME
export UNIT_TWO_ARTIFACT_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-artifacts
export UNIT_TWO_DEPLOY_STACK=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-application
export UNIT_TWO_APP_STORAGE_BUCKET=$UNIT_TWO_PROJECT_NAME-$GITHUB_USERNAME-datastore
