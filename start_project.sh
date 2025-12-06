#!/bin/bash

# start_project.sh
# Script to setup and start the Portfolio Project (Backend & Frontend)

# === Configuration ===
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$PROJECT_ROOT/backend"
FRONTEND_DIR="$PROJECT_ROOT/frontend"
MAVEN_VERSION="3.9.6"
MAVEN_DIR="$BACKEND_DIR/mvn-local"
MAVEN_BIN="$MAVEN_DIR/bin/mvn"

# === Colors ===
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Starting Portfolio Project Setup ===${NC}"

# === 1. Backend Setup ===
echo -e "${GREEN}[Backend] Checking configuration...${NC}"

# Check for portable Maven
if [ ! -d "$MAVEN_DIR" ]; then
    echo -e "${BLUE}[Backend] Portable Maven not found. Downloading Maven $MAVEN_VERSION...${NC}"
    cd "$BACKEND_DIR" || exit
    wget -q "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}[Backend] Error downloading Maven.${NC}"
        exit 1
    fi
    
    echo -e "${BLUE}[Backend] Extracting Maven...${NC}"
    tar -xzf "apache-maven-$MAVEN_VERSION-bin.tar.gz"
    mv "apache-maven-$MAVEN_VERSION" mvn-local
    rm "apache-maven-$MAVEN_VERSION-bin.tar.gz"
    echo -e "${GREEN}[Backend] Maven installed successfully in $MAVEN_DIR${NC}"
else
    echo -e "${GREEN}[Backend] Portable Maven found.${NC}"
fi

# === 2. Frontend Setup ===
echo -e "${GREEN}[Frontend] Checking configuration...${NC}"
cd "$FRONTEND_DIR" || exit

if [ ! -d "node_modules" ]; then
    echo -e "${BLUE}[Frontend] node_modules not found. Installing dependencies...${NC}"
    npm install
    if [ $? -ne 0 ]; then
        echo -e "${RED}[Frontend] Error installing dependencies.${NC}"
        exit 1
    fi
    echo -e "${GREEN}[Frontend] Dependencies installed.${NC}"
else
     echo -e "${GREEN}[Frontend] node_modules found.${NC}"
fi

# === 3. Start Applications ===
echo -e "${BLUE}=== Launching Applications ===${NC}"

# Function to kill processes on exit
cleanup() {
    echo -e "\n${BLUE}Stopping applications...${NC}"
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    exit
}

# Trap Ctrl+C
trap cleanup SIGINT

# Start Backend
echo -e "${GREEN}[Backend] Starting Spring Boot App...${NC}"
cd "$BACKEND_DIR" || exit
"$MAVEN_BIN" spring-boot:run &
BACKEND_PID=$!
echo -e "${GREEN}[Backend] PID: $BACKEND_PID${NC}"

# Wait a bit for backend to initialize (optional, but good for logs)
sleep 2

# Start Frontend
echo -e "${GREEN}[Frontend] Starting Angular App...${NC}"
cd "$FRONTEND_DIR" || exit
npm start &
FRONTEND_PID=$!
echo -e "${GREEN}[Frontend] PID: $FRONTEND_PID${NC}"

echo -e "${BLUE}=== Project Running ===${NC}"
echo -e "Backend: http://localhost:8080"
echo -e "Frontend: http://localhost:4200"
echo -e "Press Ctrl+C to stop both."

# Keep script running
wait
