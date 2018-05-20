# kvdb
Simple Distributed In-Memory Key Value Database

# Features
1. Equal distribution of entries based on a hashing function of the key across all Nodes
2. Fixed replication factor of 2, that is every entry will be stored in exactly 2 nodes; primary and replica
3. Any Node can be queried for any key
4. Data will be retrieved/stored from the replica node if the primary node is down

# Usage

## Build

### System Requirements

```
JDK 8 or above
```

```
maven 3 or above
```

### Use the following command to build and run a node

```
mvn clean spring-boot:run
```

### Example Usage

For running two instances/nodes, run the first node with node-idx: 0 and second node with node-idx:1 as separate processes.

Make sure the port number of the node and node index follow the relation

```
${server.port} = ${kvdb.master-node-port} + ${kvdb.node-idx}
```


## Config

Location of config file

```
src/main/resources/application.yml
```
Number of Nodes/Processes to run.
```
num-of-nodes: 2
```
Index of the node/process. Index starts at 0, corresponds to port number of the node
```
node-idx: 0
```
Port Number of Node at index 0  
```
master-node-port: 6600
```
Hostname of the machine
```
host-name: localhost
```
Port Number of the current Node
```  
server:
  port: 6600
```

### Example Config of each node in a 3 node setup

Node 1
```
kvdb:
  num-of-nodes: 2
  node-idx: 0
  master-node-port: 6600
  host-name: localhost
server:
  port: 6600
```

Node 2
```
kvdb:
  num-of-nodes: 2
  node-idx: 1
  master-node-port: 6600
  host-name: localhost
server:
  port: 6601
```
Node 2
```
kvdb:
    num-of-nodes: 3
    node-idx: 2
    master-node-port: 6600
    host-name: localhost
  server:
    port: 6602
```

# API
## Set a key value pair
```
curl -X POST http://localhost:6600/set/test1 -d test1value
```

## Get the value of a Key
```
curl -X GET http://localhost:6600/get/test1
```

# Future Features
1. Containerization, service discovery, automated deployment
2. Persistent Storage
3. Fault Tolerance
4. Search capabilities
