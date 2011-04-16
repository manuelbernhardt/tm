var roleProjectTypes = {
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
            "valid_children" : [ "default" ],
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

var requirementTreeTypes = {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
        "default" : {
            "valid_children" : "none",
            "icon" : {
                "image" : "/public/images/jstree/file.png" // TODO icon for requirement
            }
        },
        "requirementFolder" : {
            "valid_children" : [ "default", "requirementFolder" ],
            "icon" : {
                "image" : "/public/images/jstree/folder.png"
            },
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : true,
            "remove" : true
        }
    }
};