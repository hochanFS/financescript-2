var editor_java = document.querySelectorAll(".language-java");
editor_java.forEach(function(editorElem) {
    var editor_java = ace.edit(editorElem);
    editor_java.setTheme("ace/theme/dracula");
    editor_java.setOptions({
        maxLines: Infinity
    });
    editor_java.setReadOnly(true);
    editor_java.getSession().setMode("ace/mode/java");
});

var editor_cpp = document.querySelectorAll(".language-cpp");
editor_cpp.forEach(function(editorElem) {
    var editor_cpp = ace.edit(editorElem);
    editor_cpp.setTheme("ace/theme/dracula");
    editor_cpp.setOptions({
        maxLines: Infinity
    });
    editor_cpp.setReadOnly(true);
    editor_cpp.getSession().setMode("ace/mode/c_cpp");
});

var editor_python = document.querySelectorAll(".language-python");
editor_python.forEach(function(editorElem) {
    var editor_python = ace.edit(editorElem);
    editor_python.setTheme("ace/theme/dracula");
    editor_python.setOptions({
        maxLines: Infinity
    });
    editor_python.setReadOnly(true);
    editor_python.getSession().setMode("ace/mode/python");
});
