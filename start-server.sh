#!/bin/bash
echo "Starting RMI Registry..."
rmiregistry &
sleep 2
echo "Starting Pad Server..."
java -Djava.security.policy=security.policy PadImpl