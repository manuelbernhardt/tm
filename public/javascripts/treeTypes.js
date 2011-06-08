/** multi-purpose trees **/

var requirementTreeTypes = {
    "multipurpose": true,
    "max_depth" : -2,
    "max_children" : -1,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for requirement
            }
        },
        "requirementFolder" : {
            "valid_children" : [ "default", "requirement", "requirementFolder" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            }
        }
    }
};

var repositoryTreeTypes = {
    "multipurpose": true,
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for test script
            }
        },
        "scriptFolder" : {
            "valid_children" : [ "default", "script", "scriptFolder" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png" // TODO icon for release
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : true,
            "remove" : true
        }
    }
};

var scriptCycleTreeTypes = {
    "multipurpose": true,
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO instance icon
            }
        },
        "testCycle" : {
            "valid_children" : [ "instance", "default" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png" // TODO cycle icon
            }
        },
        "release" : {
            "valid_children" : [ "testCycle" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png" // TODO release icon
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        },
        "root": {
            "valid_children" : ["instance, default, release"],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
};

var projectTreeTypes = {
    "multipurpose": true,
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for project
            }
        },
        "category" : {
            "valid_children" : [ "default", "project" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : true,
            "remove" : true
        },
        "root" : {
            "valid_children" : [ "default", "project", "category" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
};

var approachTreeTypes = {
    "multipurpose": true,
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for test cycle
            }
        },
        "release" : {
            "valid_children" : [ "default", "testCycle" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png" // TODO icon for release
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : true,
            "remove" : true
        }
    }
};


/** selection-only trees **/

var projectRoleTreeTypes = {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : { // role
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO role icon
            }
        },
        "category" : {
            "valid_children" : [ "project" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        },
        "project" : {
            "valid_children" : [ "default", "projectRole" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png" // TODO project icon
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false

        }
    }
};

var projectRolesAssignmentTreeTypes = {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for user
            },
            "start_drag" : false,
            "move_node" : true,
            "delete_node" : true,
            "remove" : true
        },
        "projectRole" : {
            "valid_children" : [ "default", "user" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        },
        "root" : {
            "valid_children" : ["projectRole"],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
};

var userTreeTypes = {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for user
            },
            "start_drag" : true,
            "move_node" : true,
            "copy_node": true,
            "delete_node" : false,
            "remove" : false
        },
        "root" : {
            "valid_children" : [ "default", "user" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
};

var adminRolesTreeTypes = {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for user
            },
            "start_drag" : false,
            "move_node" : true,
            "delete_node" : true,
            "remove" : true
        },
        "adminRole" : {
            "valid_children" : [ "default", "user" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        },
        "root" : {
            "valid_children" : ["adminRole"],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
};