#!/bin/bash

# Set the path where the templates folder is located
TEMPLATES_FOLDER="./"

# Generate a timestamp-based template ID
TIMESTAMP=$(date +%s)
TEMPLATE_ID="TID${TIMESTAMP}"

# Read user input for templating name
read -p "Enter templating name: " TEMPLATING_NAME

# Get the current date
CREATION_DATE=$(date +%Y-%m-%d)

# Create the folder name using the naming convention
FOLDER_NAME="${TEMPLATE_ID}_${TEMPLATING_NAME}_${CREATION_DATE}"

# Create the new folder
NEW_FOLDER="${TEMPLATES_FOLDER}/${FOLDER_NAME}"
mkdir -p "$NEW_FOLDER"

# Create the Apache Velocity template file
TEMPLATE_FILE="${NEW_FOLDER}/template.vm"
touch "$TEMPLATE_FILE"

# Create the smtp.properties file
SMTP_PROPERTIES_FILE="${NEW_FOLDER}/smtp.properties"
touch "$SMTP_PROPERTIES_FILE"

echo "New template folder created: $NEW_FOLDER"