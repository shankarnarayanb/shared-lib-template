package com.shankar
import org.jenkins.plugins.lockableresources.*

// def jenkinsResources = org.jenkins.plugins.lockableresources.LockableResourcesManager.get().resources

def is_resource_available(String resource_name) {
    sh """ echo "Resource is: $resource_name" """
    return  org.jenkins.plugins.lockableresources.LockableResourcesManager.get().getResources().findAll {
        it.name == resource_name && it.locked && it.isQueued()
    }.size() == 0
}

