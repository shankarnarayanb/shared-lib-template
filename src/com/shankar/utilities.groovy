import org.jenkins.plugins.lockableresources.LockableResourcesManager as manager

public class utilities implements hudson.model.Action,java.io.Serializable {

def jenkinsResources = manager.get().resources

def is_resource_available(String resource_name) {
    sh "echo $resource_name"
    return manager.jenkinsResources.findAll {
      it.name == resource_name && it.locked && it.isQueued()
    }.size() == 0
    }
}
