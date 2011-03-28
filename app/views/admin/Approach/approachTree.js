var tree = $("#approachTree").jstree({
  "plugins" : [ "json_data", "ui", "crrm",  "types", "contextmenu", "hotkeys", "themeroller" ],

  "json_data" : {
    "ajax" : {
      "url" : "@{TMTreeController.getChildren()}",
      "data" : function (n) {
        return {
          "id" : n.attr ? n.attr("id").replace("node_", "") : -1,
          "treeId" : "approachTree",
          "args" : {"projectId": "${project == null ? controllers.TMController.getActiveProject().getId() : project.id}"}

        };
      }
    }
  },
  "ui" : {
    "select_limit": 1
  },
  "themeroller": {
    "item": ""
  },
  "types" : {
    "max_depth" : -2,
    "max_children" : -2,
    "types" : {
      "default" : {
        "valid_children" : "none",
        "icon" : {
          "image" : "@{'public/images/jstree/file.png'}" // TODO icon for test cycle
        }
      },
      "release" : {
        "valid_children" : [ "default" ],
        "icon" : {
          "image" : "@{'public/images/jstree/folder.png'}" // TODO icon for release
        },
        "start_drag" : false,
        "move_node" : false,
        "delete_node" : true,
        "remove" : true
      }
    }
  },
  "hotkeys": {
    "Ctrl+x" : function() {
      this.cut(null)
    },
    "Ctrl+v" : function() {
      this.paste(null)
    },
    "Ctrl+n" : function() {
      this.create_node(null, "after", {attr:{"rel":"default"}});
    }
  }
});