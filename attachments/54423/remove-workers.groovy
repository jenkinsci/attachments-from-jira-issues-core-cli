workerRemoveStart = System.currentTimeMillis()
numWorkers = 0
for (node in Jenkins.instance.nodes) {
  if (node.name.startsWith('swarm-test')) {
    start = System.currentTimeMillis()
    Jenkins.instance.removeNode(node)
    took = System.currentTimeMillis() - start
    println("uniqueLabels: ${Jenkins.instance.labels.size()} nodes: ${Jenkins.instance.nodes.size()} ${node.name}: ${took}ms")
    numWorkers += 1
  }
}
workerRemoveTook = System.currentTimeMillis() - workerRemoveStart
println("Total time to remove ${numWorkers} workers: ${workerRemoveTook}ms")
