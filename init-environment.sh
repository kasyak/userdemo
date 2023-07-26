#!/usr/bin/env bash

source scripts/my-functions.sh

MYSQL_VERSION="8.0.32"
VAULT_VERSION="1.13.0"

echo
echo "Starting environment"
echo "===================="

echo
echo "Creating network"
echo "----------------"
docker network create userdemo-network

echo
echo "Pulling vault image"
echo "-------------------"
docker pull hashicorp/vault

echo
echo "Starting vault container"
echo "------------------------"

docker run -d --rm --name hashicorp/vault \
  -p 8200:8200 \
  -v ${PWD}/docker/vault:/my/vault \
  --network=userdemo-network \
  vault:${VAULT_VERSION} vault server -config=/my/vault/config/config.hcl

echo
wait_for_container_log "vault" "Vault server started!"

echo
echo "Starting mysql container"
echo "------------------------"

docker run -d --rm --name mysql \
  -p 3306:3306 \
  -e "MYSQL_ROOT_PASSWORD=secret" \
  -e "MYSQL_DATABASE=exampledb" \
  --network=userdemo-network \
  --health-cmd="mysqladmin ping -u root -p$${MYSQL_ROOT_PASSWORD}" \
  mysql:${MYSQL_VERSION}

echo
wait_for_container_log "mysql" "port: 3306"

source scripts/unseal-vault-enable-approle-databases.sh
source scripts/setup-spring-cloud-vault-approle-mysql.sh

echo
echo "*********************************************"
echo "VAULT_ROOT_TOKEN=${VAULT_ROOT_TOKEN}"
echo "*********************************************"

echo
echo "Environment Up and Running"
echo "=========================="
echo