#!/bin/bash

# Define the installation directory and script details
INSTALL_DIR="/usr/local/bin"
SCRIPT_NAME="cillers"
REPO_URL="https://github.com/Cillers-com/cillers-cli"
TAG="v0.0.3"

echo "Downloading $SCRIPT_NAME from $REPO_URL..."

# Download the script using curl or wget
curl -L "${REPO_URL}/archive/${TAG}.tar.gz" -o "${SCRIPT_NAME}.tar.gz" || wget "${REPO_URL}/archive/${TAG}.tar.gz" -O "${SCRIPT_NAME}.tar.gz"

# Extract the downloaded tarball
tar -xzf "${SCRIPT_NAME}.tar.gz"

# Make the script executable
cd "${SCRIPT_NAME}-${TAG}"  
chmod +x cillers-cli.rb

# Move and rename the script to the installation directory as 'cillers'
mv cillers-cli.rb "${INSTALL_DIR}/${SCRIPT_NAME}"

# Clean up the downloaded and extracted files
cd ..
rm -rf "${SCRIPT_NAME}-${TAG}"
rm "${SCRIPT_NAME}.tar.gz"

echo "${SCRIPT_NAME} installed successfully to ${INSTALL_DIR}"
echo "You can now use the command '${SCRIPT_NAME}' to access the CLI tool."
