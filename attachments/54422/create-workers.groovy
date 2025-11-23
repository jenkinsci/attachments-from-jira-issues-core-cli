import hudson.model.Node
import hudson.plugins.swarm.SwarmSlave
remoteFsRoot = "/var/lib/jenkins"
executors = "1"
mode = Node.Mode.EXCLUSIVE
nodeProperties = []
numWorkers = 400
workerCreateStart = System.currentTimeMillis()
for(def i = 0; i <= numWorkers; i++) {
    labels = "10.224.0.${i} docker docker-buildx fossa gh go gvm hub immutable jq kind kubectl linux linux-immutable nested-virtualization swarm terraform ubuntu-18 ubuntu-18.04 vagrant virtual wget x86_64"
    // labels = "swarm"
    SwarmSlave agent = new SwarmSlave("swarm-test-${i}", "swarm-test-${i} description", remoteFsRoot, executors, mode, labels, nodeProperties)
    start = System.currentTimeMillis()
    Jenkins.instance.addNode(agent)
    took = System.currentTimeMillis() - start
    println("uniqueLabels: ${Jenkins.instance.labels.size()} nodes: ${Jenkins.instance.nodes.size()} ${agent.name}: ${took}ms")
}
workerCreateTook = System.currentTimeMillis() - workerCreateStart
println("Total time to create ${numWorkers} workers: ${workerCreateTook}ms")
