import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.SSHLauncher

def launcher = new SSHLauncher('localhost', 22, 'username', 'password', '', '')

def node = new DumbSlave("test", "Test node", 
    "/Users/test/jenkinsslave", "2", Node.Mode.NORMAL,
    "test", launcher, new RetentionStrategy.Always(), new ArrayList())

Hudson.instance.addNode(node)