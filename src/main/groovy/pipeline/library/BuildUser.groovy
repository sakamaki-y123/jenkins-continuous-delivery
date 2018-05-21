package main.groovy.pipeline.library;


// need user build vars plugin

def String getFullName() {
    wrap([$class: 'BuildUser']) {
        return "${BUILD_USER}"
    }
}

def String getFirstName() {
    wrap([$class: 'BuildUser']) {
        return "${BUILD_USER_FIRST_NAME}"
    }
}

def String getLastName() {
    wrap([$class: 'BuildUser']) {
        return "${BUILD_USER_LAST_NAME}"
    }
}

def String getId() {
    wrap([$class: 'BuildUser']) {
        return "${BUILD_USER_ID}"
    }
}

def String getEmail() {
    wrap([$class: 'BuildUser']) {
        return "${BUILD_USER_EMAIL}"
    }
}

return this