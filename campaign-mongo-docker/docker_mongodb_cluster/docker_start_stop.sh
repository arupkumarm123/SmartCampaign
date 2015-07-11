#!/bin/bash

# Docker interface ip
DOCKERIP="10.1.42.1"
LOCALPATH="/home/vagrant/docker"

# Clean up
containers=( skydns skydock mongos1r1 mongos1r2 mongos2r1 mongos2r2 mongos3r1 mongos3r2 configservers1 configservers2 configservers3 mongos1 )
for c in ${containers[@]}; do
        
	docker stop ${c}
        sleep 10
	docker start ${c}
        sleep 10
done
