# load env
.\check_env.ps1

# make sure database is running
docker compose -f ./docker-compose.yml up nbs-mssql --build -d

# Remove the NiFi container
docker compose -f ./docker-compose.yml down nifi

# Prune all volumes associated with the NiFi container
docker volume prune -f

# Now restart container build
./build_all.ps1
