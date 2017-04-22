#!/usr/bin/env bash
export MONGODB_PASSWORD=zerone01
export MONGODB_USERNAME=zerone
export MONGODB_DB=zofi
#!/bin/bash
if test -z "$MONGODB_PASSWORD"; then
    echo "MONGODB_PASSWORD not defined"
    exit 1
fi

auth="-u user -p $MONGODB_PASSWORD"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('$MONGODB_USERNAME')) { db.createUser({ user: '$MONGODB_USERNAME', pwd: '$MONGODB_PASSWORD', roles: [ {role:'readWrite', db:'$MONGODB_DB'} ]}) }"
until mongo $MONGODB_DB --eval "$create_user" || mongo $MONGODB_DB $auth --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "$INIT_DUMP"; then
    echo "execute dump file"
	until mongo $MONGODB_DB $auth $INIT_DUMP; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb mongod --auth "$@"