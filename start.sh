#!/bin/bash
# Check if Docker is installed
if command -v docker >/dev/null 2>&1; then
    echo "Docker is installed."

        # Run docker-compose up and wait until the service is started
        docker-compose up --build -d

else
    echo "Error: Docker is not installed. Install Docker to proceed."
    exit 1
fi
